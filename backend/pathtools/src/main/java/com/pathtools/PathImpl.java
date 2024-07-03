package com.pathtools;

import com.pathtools.pathnode.PathNode;
import com.pathtools.reader.PathReader;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class PathImpl implements Path {

    private final String path;
    private final LinkedList<PathNode> nodes;

    PathImpl(String path, List<PathNode> nodes) {
        this.path = path;
        this.nodes = new LinkedList<>(nodes);
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getSimplePath(String sep, boolean startRoot) {
        String simple = PathParser.getInstance().parseString(this, sep);
        if (!startRoot)
            return simple.substring(1);
        return simple;
    }

    @Override
    public List<PathNode> getNodes() {
        return nodes;
    }

    @Override
    public PathNode getFirst() {
        return nodes.getFirst();
    }

    @Override
    public PathNode getLast() {
        return nodes.getLast();
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public Iterator<PathNode> iterator() {
        return nodes.iterator();
    }

    @Override
    public void forEach(Consumer<? super PathNode> action) {
        nodes.forEach(action);
    }

    @Override
    public Spliterator<PathNode> spliterator() {
        return nodes.spliterator();
    }

    @Override
    public int length() {
        return nodes.size();
    }

    @Override
    public PathReader reader() {
        return new PathNodeReader(this);
    }

    @Override
    public PathBuilder createBuilder() {
        return new PathBuilder(path);
    }
}