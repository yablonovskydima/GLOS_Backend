package com.pathtools.reader;

import com.pathtools.pathnode.PathNode;

import java.util.stream.Stream;

public interface Streamable {
    Stream<PathNode> stream();
}
