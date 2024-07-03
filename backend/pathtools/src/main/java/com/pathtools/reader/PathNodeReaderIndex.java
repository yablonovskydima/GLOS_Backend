package com.pathtools.reader;

import com.pathtools.NodeType;
import com.pathtools.pathnode.PathNode;

public interface PathNodeReaderIndex {
    int indexOf(PathNode node);

    int indexOf(String rootName);

    int indexOf(NodeType type);

    int lastIndexOf(PathNode node);

    int lastIndexOf(String rootName);

    int lastIndexOf(NodeType type);
}
