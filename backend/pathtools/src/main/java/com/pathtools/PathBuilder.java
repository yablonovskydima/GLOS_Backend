package com.pathtools;

import com.pathtools.pathnode.ArchivePathNode;
import com.pathtools.pathnode.FilePathNode;
import com.pathtools.pathnode.PathNode;

import java.util.Objects;

public class PathBuilder {

    private final StringBuilder builder;

    public PathBuilder() {
        this.builder = new StringBuilder();
    }

    public PathBuilder(String str) {
        this.builder = new StringBuilder(str);
    }

    public PathBuilder repository(String name, boolean isFirstInsert) {
        return add(name, NodeType.REPOSITORY, isFirstInsert);
    }

    public PathBuilder repositories(Iterable<String> names, boolean isFirstInsert) {
        Objects.requireNonNull(names);
        names.forEach(name -> add(name, NodeType.REPOSITORY, isFirstInsert));
        return this;
    }

    public PathBuilder repositories(boolean isFirstInsert, String...names) {
        Objects.requireNonNull(names);
        for(String name : names) {
            add(name, NodeType.REPOSITORY, isFirstInsert);
        }
        return this;
    }


    public PathBuilder directory(String name, boolean isFirstInsert) {
        return add(name, NodeType.DIRECTORY, isFirstInsert);
    }

    public PathBuilder directories(Iterable<String> names, boolean isFirstInsert) {
        Objects.requireNonNull(names);
        names.forEach(name -> add(name, NodeType.DIRECTORY, isFirstInsert));
        return this;
    }

    public PathBuilder directories(boolean isFirstInsert, String...names) {
        Objects.requireNonNull(names);
        for(String name : names) {
            add(name, NodeType.DIRECTORY, isFirstInsert);
        }
        return this;
    }

    public PathBuilder archive(String name, boolean isFirstInsert) {
        return add(name, NodeType.ARCHIVE, isFirstInsert);
    }

    public PathBuilder archive(String name, String format, boolean isFirstInsert) {
        return node(new ArchivePathNode("", name, "", format), isFirstInsert);
    }

    public PathBuilder file(String name) {
        return addLast(name, NodeType.FILE);
    }

    public PathBuilder file(String name, String format, boolean isFirstInsert) {
        Objects.requireNonNull(name, format);
        if (name.isEmpty()) {
            throw new IllegalArgumentException("argument name is empty");
        }
        node(new FilePathNode("", name+'.'+format, "", format), isFirstInsert);
        builder.insert(0, NodeType.FILE.nodeChar());
        return this;
    }

    public PathBuilder add(String name, NodeType nodeType, boolean isFirstInsert) {
        return isFirstInsert ? addFirst(name, nodeType) : addLast(name, nodeType);
    }

    public PathBuilder remove(boolean isFirstDelete) {
        return isFirstDelete ? removeFirst() : removeLast();
    }

    public PathBuilder addLast(String name, NodeType nodeType) {
        Objects.requireNonNull(name);
        if (name.isEmpty()) {
            throw new IllegalArgumentException("argument name is empty");
        } else if (name.charAt(0) != nodeType.nodeChar()) {
            builder.append(nodeType.nodeChar());
        }
        builder.append(name);
        return this;
    }

    public PathBuilder addFirst(String name, NodeType nodeType) {
        Objects.requireNonNull(name);
        if (name.isEmpty()) {
            throw new IllegalArgumentException("argument name is empty");
        } else if (name.charAt(0) != nodeType.nodeChar()) {
            builder.insert(0, nodeType.nodeChar());
        }
        builder.insert(1, name);
        return this;
    }

    public PathBuilder removeFirst() {
        final int index = specFirst(1, 0, NodeType.NONE);
        builder.delete(0, index);
        return this;
    }

    public PathBuilder removeFirst(NodeType type) {
        final int index = specFirst(1, 0, type);
        builder.delete(0, index-1);
        return this;
    }

    public PathBuilder removeLast() {
        final int len = builder.length()-1;
        final int index = specLast(-1, len, NodeType.NONE);
        builder.delete(index, builder.length());
        return this;
    }

    public PathBuilder node(PathNode node, boolean isFirstInsert) {
        if (isFirstInsert)
            builder.insert(0, node.getRootName());
        else
            builder.append(node.getRootName());
        return this;
    }

    public Path build() {
        return PathParser.getInstance().parse(builder.toString());
    }

    private int specFirst(int index, int start, NodeType type) {
        int count = 0;
        for(int i = start; i < builder.length(); i++) {
            char c = builder.charAt(i);
            if (Paths.isSpec(c)) {
                count++;
                if (index < 0 || count == index+1) {
                    if (type == NodeType.NONE || type.nodeChar() == c) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    private int specLast(int index, int start, NodeType type) {
        int count = 0;
        for(int i = start; i >= 0; i--) {
            char c = builder.charAt(i);
            if (Paths.isSpec(c)) {
                count++;
                if (index < 0 || count == index) {
                    if (type == NodeType.NONE || type.nodeChar() == c) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

}
