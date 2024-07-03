package com.pathtools.reader;

import com.pathtools.NodeType;
import com.pathtools.pathnode.PathNode;

public interface PathNodeReaderFirstLast {
    PathNode first();
    PathNode first(int index);
    PathNode first(String rootName);
    PathNode first(NodeType type);
    PathNode last();
    PathNode last(int index);
    PathNode last(String rootName);
    PathNode last(NodeType type);
}
