package com.pathtools.reader;

import com.pathtools.NodeType;
import com.pathtools.Path;

public interface PathNodeReaderParent {
    Path parent();

    Path parent(NodeType type);
}
