package com.pathtools.reader;

import com.pathtools.NodeType;
import com.pathtools.pathnode.PathNode;

import java.util.List;

public interface PathNodeReaderRange {
    List<PathNode> range(int start, int end);

    List<PathNode> range(String rootNameStart, String rootNameEnd);

    List<PathNode> range(NodeType type, String rootName);

    List<PathNode> range(String rootName, NodeType type);

    List<PathNode> range();

    List<PathNode> rangeByType(NodeType type);

    List<PathNode> rangeByProp(String prop, String value);

    List<PathNode> rangeAfter(int index);

    List<PathNode> rangeBefore(int index);

    List<PathNode> rangeAfterByProp(String prop, String value);

    List<PathNode> rangeBefore(String rootName);

    List<PathNode> rangeBeforeByProp(String prop);

}
