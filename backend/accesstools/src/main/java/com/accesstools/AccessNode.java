package com.accesstools;

import java.util.*;

public final class AccessNode {
    public static final AccessNode EMPTY = new AccessNode();

    public static class AccessNodeBuilder {
        private AccessReadType readType = AccessReadType.R;
        private AccessNodeType nodeType = AccessNodeType.USER;
        private final Set<String> names = new LinkedHashSet<>();
        private final StringBuilder prefixName = new StringBuilder();

        public AccessNodeBuilder() {
        }

        public AccessNodeBuilder(String pattern) {
            setPattern(pattern);
        }

        public AccessNodeBuilder(AccessReadType readType, AccessNodeType nodeType, String prefixName, Iterable<String> names) {
            this.readType = readType;
            this.nodeType = nodeType;
            this.prefixName.append(prefixName);
            names.forEach(this.names::add);
        }

        public AccessNodeBuilder setReadType(AccessReadType readType) {
            this.readType = readType;
            return this;
        }

        public AccessNodeBuilder setNodeType(AccessNodeType nodeType) {
            this.nodeType = nodeType;
            return this;
        }

        public AccessNodeBuilder setName(String name) {
            this.names.clear();
            this.prefixName.setLength(0);
            this.prefixName.append(name);
            return this;
        }

        public AccessNodeBuilder addName(String name) {
            this.names.add(name);
            return this;
        }

        public AccessNodeBuilder removeName(String name) {
            this.names.remove(name);
            return this;
        }

        public AccessNodeBuilder setAvailableAny(Iterable<String> iterable) {
            this.names.clear();
            this.prefixName.setLength(0);
            this.prefixName.append(Constants.SPEC_ANY);
            if (iterable.iterator().hasNext())
                iterable.forEach(this.names::add);
            return this;
        }

        public AccessNodeBuilder setAvailableAny(String...names) {
            this.names.clear();
            this.prefixName.setLength(0);
            this.prefixName.append(Constants.SPEC_ANY);
            if (names.length > 0)
                this.names.addAll(Arrays.asList(names));
            return this;
        }

        public AccessNodeBuilder setAvailableExceptAny(Iterable<String> iterable) {
            this.names.clear();
            this.prefixName.setLength(0);
            this.prefixName.append(Constants.SPEC_EXCEPT);
            if (iterable.iterator().hasNext())
                iterable.forEach(this.names::add);
            return this;
        }

        public AccessNodeBuilder setAvailableExceptAny(String...names) {
            this.names.clear();
            this.prefixName.setLength(0);
            this.prefixName.append(Constants.SPEC_EXCEPT);
            if (names.length > 0)
                this.names.addAll(Arrays.asList(names));
            return this;
        }

        public AccessNodeBuilder setPattern(String pattern) {
            Objects.requireNonNull(pattern);
            final String patternStrTrim = pattern.trim();
            if (patternStrTrim.isEmpty())
                throw new InvalidAccessPatternException("string is blank or empty");
            String[] parts = new String[3];
            try {
                int start = 0;
                for(int i = 0; i < 3; i++) {
                    if (parts[1] != null) {
                        parts[2] = patternStrTrim.substring(start).trim();
                        break;
                    }
                    int indexSeparator = patternStrTrim.indexOf(Constants.PART_SEPARATOR,  start);
                    parts[i] = patternStrTrim.substring(start, indexSeparator).trim();
                    if (parts[i].isEmpty())
                        throw new InvalidAccessPatternException("invalid pattern format");
                    start = indexSeparator + 1;
                }
            } catch (IndexOutOfBoundsException ex) {
                throw new InvalidAccessPatternException("invalid pattern format");
            }
            this.readType = AccessReadType.valueOf(parts[0].toUpperCase());
            this.nodeType = AccessNodeType.valueOf(parts[1].toUpperCase());

            final String namePart = parts[2].trim();
            if (namePart.startsWith(Constants.SPEC_ANY) ||
                    namePart.startsWith(Constants.SPEC_EXCEPT)) {
                this.names.clear();
                this.prefixName.setLength(0);
                if (namePart.length() == 1) {
                    this.prefixName.append(namePart);
                } else {
                    String[] nameParts = namePart.split(Constants.SPEC_PART_SEPARATOR);
                    if (nameParts.length > 2 || nameParts.length < 1) {
                        throw new InvalidAccessPatternException("invalid pattern format");
                    } else if (nameParts.length == 1) {
                        this.prefixName.append(nameParts[0]);
                    } else {
                        this.prefixName.append(nameParts[0]);
                        String[] names = nameParts[1].substring(1, nameParts[1].length()-1).split(Constants.SPEC_ELEMENT_SEPARATOR);
                        this.names.addAll(Arrays.asList(names));
                    }
                }
            } else if (namePart.contains(Constants.SPEC_ELEMENT_SEPARATOR)
                    || namePart.contains(Constants.SPEC_PART_SEPARATOR)) {
                throw new InvalidAccessPatternException("invalid pattern format");
            } else {
                this.prefixName.append(namePart);
            }
            return this;
        }

        public AccessNode build() {
            return new AccessNode(readType, nodeType, prefixName.toString(), names);
        }
    }

    public static AccessNodeBuilder builder() {
        return new AccessNodeBuilder();
    }

    public static AccessNodeBuilder builder(String pattern) {
        return new AccessNodeBuilder(pattern);
    }

    public static AccessNodeBuilder builder(AccessReadType readType, AccessNodeType nodeType, String name, Iterable<String> list) {
        return new AccessNodeBuilder(readType, nodeType, name, list);
    }

    public static AccessNodeBuilder builder(AccessReadType readType, AccessNodeType nodeType, String name) {
        Set<String> names = new LinkedHashSet<>();
        names.add(name);
        return new AccessNodeBuilder(readType, nodeType, name, null);
    }

    private final String pattern;
    private final AccessReadType readType;
    private final AccessNodeType nodeType;
    private final String name;
    private final List<String> list;


    private AccessNode() {
        this.pattern = "";
        this.readType = AccessReadType.NONE;
        this.nodeType = AccessNodeType.NONE;
        this.name = "";
        this.list = new ArrayList<>();
    }

    public AccessNode(AccessNode pattern) {
        this.pattern = pattern.pattern;
        this.readType = pattern.readType;
        this.nodeType = pattern.nodeType;
        this.name = pattern.name;
        this.list = pattern.list;
    }

    AccessNode(AccessReadType readType, AccessNodeType nodeType, String prefixName, Iterable<String> names) {
        final StringBuilder patternBuilder = new StringBuilder();
        patternBuilder.append(Objects.requireNonNull(readType).name());
        this.readType = readType;
        patternBuilder.append(Constants.PART_SEPARATOR);
        patternBuilder.append(Objects.requireNonNull(nodeType).name());
        this.nodeType = nodeType;
        patternBuilder.append(Constants.PART_SEPARATOR);

        Objects.requireNonNull(prefixName);
        this.name = prefixName;

        patternBuilder.append(this.name);
        if (names != null && names.iterator().hasNext()) {
            this.list = new ArrayList<>();
            for(String name : names) {
                String nameTrim = name.trim();
                if (nameTrim.isEmpty())
                    throw new IllegalArgumentException("invalid pattern format");
                this.list.add(name.trim());
            }
            patternBuilder.append(Constants.SPEC_PART_SEPARATOR);
            patternBuilder.append(list);
        } else {
            this.list = new ArrayList<>();
        }
        this.pattern = patternBuilder.toString();
    }

    public String getPattern() {
        return pattern;
    }

    public AccessReadType getReadType() {
        return readType;
    }

    public AccessNodeType getNodeType() {
        return nodeType;
    }

    public String getName() {
        return name;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public String toString() {
        return this.pattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessNode that = (AccessNode) o;
        return Objects.equals(pattern, that.pattern) && readType == that.readType && nodeType == that.nodeType && Objects.equals(name, that.name) && Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern, readType, nodeType, name, list);
    }
}
