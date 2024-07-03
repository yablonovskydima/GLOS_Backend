package com.pathtools;

import com.pathtools.pathnode.PathNode;
import com.pathtools.reader.PathReader;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PathNodeReader implements PathReader {

    private final Path path;

    public PathNodeReader(String path) {
        this.path = PathParser.getInstance().parse(path);
    }

    public PathNodeReader(Path path) {
        this.path = path;
    }

    @Override
    public PathNode first() {
        return path.getFirst();
    }

    @Override
    public PathNode first(int index) {
        checkIndex(index, path.length(), false);
        int i = 0;
        for(PathNode node : path) {
            if (i++ == index) {
                return node;
            }
        }
        return null;
    }

    @Override
    public PathNode first(String rootFullName) {
        Objects.requireNonNull(rootFullName);
        if (Paths.isRootName(rootFullName)) {
            for (PathNode node : path) {
                if (node.getRootFullName().equals(rootFullName)) {
                    return node;
                }
            }
        } else {
            for (PathNode node : path) {
                if (node.getSimpleName().equals(rootFullName)) {
                    return node;
                }
            }
        }
        return null;
    }

    @Override
    public PathNode first(NodeType type) {
        Iterator<PathNode> iter = path.iterator();
        while(iter.hasNext()){
            final PathNode node = iter.next();
            if (node.getType() == type) {
                return node;
            }
        }
        return null;
    }

    @Override
    public PathNode last() {
        return path.getLast();
    }

    @Override
    public PathNode last(int index) {
        checkIndex(index, path.length(), false);
        int i = path.length() - 1;
        for(PathNode node : path) {
            if (i-- == index) {
                return node;
            }
        }
        return null;
    }

    @Override
    public PathNode last(String rootFullName) {
        Objects.requireNonNull(rootFullName);
        if (!Paths.isRootName(rootFullName)) {
            Iterator<PathNode> iter = ((LinkedList<PathNode>)path.getNodes()).descendingIterator();
            while(iter.hasNext()){
                final PathNode node = iter.next();
                if (node.getRootFullName().equals(rootFullName)) {
                    return node;
                }
            }
        } else {
            Iterator<PathNode> iter = ((LinkedList<PathNode>)path.getNodes()).descendingIterator();
            while(iter.hasNext()){
                final PathNode node = iter.next();
                if (node.getSimpleName().equals(rootFullName)) {
                    return node;
                }
            }
        }
        return null;
    }

    public List<PathNode> range(int start, int end) {
        return path.getNodes().subList(start, end);
    }

    @Override
    public List<PathNode> range(String rootNameStart, String rootNameEnd) {
        int start = indexOf(rootNameStart);
        int end = indexOf(rootNameEnd);
        return path.getNodes().subList(start, end);
    }

    @Override
    public List<PathNode> range(NodeType type, String rootName) {
        int start = indexOf(type);
        int end = indexOf(rootName);
        return path.getNodes().subList(start, end);
    }

    @Override
    public List<PathNode> range(String rootName, NodeType type) {
        int start = indexOf(rootName);
        int end = indexOf(type);
        return path.getNodes().subList(start, end);
    }

    @Override
    public PathNode last(NodeType type) {
        Iterator<PathNode> iter = ((LinkedList<PathNode>)path.getNodes()).descendingIterator();
        while(iter.hasNext()){
            final PathNode node = iter.next();
            if (node.getType() == type) {
                return node;
            }
        }
        return null;
    }

    @Override
    public Stream<PathNode> stream() {
        return StreamSupport.stream(path.spliterator(), false);
    }

    @Override
    public Path parent() {
        if (path.length() < 2) {
            return null;
        }
        LinkedList<PathNode> list = new LinkedList<>(path.getNodes());
        list.removeLast();
        return new PathImpl(list.getLast().getRootFullName(), list);
    }

    @Override
    public Path parent(NodeType type) {
        if (path.length() < 2) {
            return null;
        }
        LinkedList<PathNode> list = new LinkedList<>(path.getNodes());

        if (list.getLast().getType() == type)
            list.removeLast();

        PathNode node = list.getLast();
        while(!list.isEmpty()) {
            node = list.getLast();
            if (node.getType() == type) {
                return new PathImpl(list.getLast().getRootFullName(), list);
            }
            list.removeLast();
        }
        return null;
    }

    @Override
    public int indexOf(PathNode node) {
        return indexOf(node.getRootFullName());
    }

    @Override
    public int indexOf(String rootName) {
        Objects.requireNonNull(rootName);
        if (rootName.isEmpty()) {
            throw new IllegalArgumentException("rootName is empty");
        }
        int i = 0;
        for(PathNode node : path) {
            if (node.getRootFullName().equals(rootName)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public int indexOf(NodeType type) {
        int i = 0;
        for (PathNode node : path) {
            if (node.getType() == type)
                return i;
            i++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(PathNode node) {
        return lastIndexOf(node.getRootFullName());
    }

    @Override
    public int lastIndexOf(String rootName) {
        Objects.requireNonNull(rootName);
        if (rootName.isEmpty()) {
            throw new IllegalArgumentException("rootName is empty");
        }
        int i = path.length() - 1;
        for(PathNode node : path) {
            if (node.getRootFullName().equals(rootName)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(NodeType type) {
        int i = path.length() - 1;
        Iterator<PathNode> iter = ((LinkedList<PathNode>)path.getNodes()).descendingIterator();
        while(iter.hasNext()){
            final PathNode node = iter.next();
            if (node.getType() == type) {
                return i;
            }
            i--;
        }
        return -1;
    }

    @Override
    public List<PathNode> range() {
        return path.getNodes();
    }

    @Override
    public List<PathNode> rangeByType(NodeType type) {
        return stream()
                .filter(x -> x.getType() == type)
                .toList();
    }

    @Override
    public List<PathNode> rangeByProp(String prop, String value) {
        return stream()
                .filter(x -> x.getRootProp(prop) != null && x.getRootProp(prop).equals(value))
                .toList();
    }

    @Override
    public List<PathNode> rangeAfter(int index) {
        checkIndex(index, path.length(), false);
        return path.getNodes().subList(index, path.length() - 1);
    }

    @Override
    public List<PathNode> rangeBefore(int index) {
        checkIndex(index, path.length(), false);
        return path.getNodes().subList(0, index);
    }

    @Override
    public List<PathNode> rangeAfterByProp(String prop, String value) {
        Objects.requireNonNull(prop);
        Objects.requireNonNull(value);
        if (prop.isEmpty()) {
            throw new IllegalArgumentException("prop is empty");
        }
        final List<PathNode> nodes = new LinkedList<>();
        boolean isFoundNode = false;
        for(PathNode node : path) {
            if (!isFoundNode && node.getRootProp(prop).equals(value))
                isFoundNode = true;
            if (isFoundNode)
                nodes.add(node);
        }
        return nodes;
    }

    @Override
    public List<PathNode> rangeBefore(String rootName) {
        Objects.requireNonNull(rootName);
        if (rootName.isEmpty())
            throw new IllegalArgumentException("rootName is empty");
        final List<PathNode> nodes = new LinkedList<>();
        boolean isFoundNode = false;
        for(PathNode node : path) {
            if (!isFoundNode && node.getRootName().equals(rootName))
                isFoundNode = true;
            if (isFoundNode)
                nodes.add(node);
        }
        return nodes;
    }

    @Override
    public List<PathNode> rangeBeforeByProp(String prop) {
        Objects.requireNonNull(prop);
        if (prop.isEmpty())
            throw new IllegalArgumentException("prop is empty");
        final List<PathNode> nodes = new LinkedList<>();
        boolean isFoundNode = false;
        for(PathNode node : path) {
            if (!isFoundNode && node.getRootName().equals(prop))
                isFoundNode = true;
            if (isFoundNode)
                nodes.add(node);
        }
        return nodes;
    }


    private int checkIndex(int index, int size, boolean includeSize) {
        if (index < 0 || index > size || index == size && !includeSize) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        return index;
    }

}
