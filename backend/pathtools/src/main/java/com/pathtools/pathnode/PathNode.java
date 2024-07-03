package com.pathtools.pathnode;

import com.pathtools.NodeType;

public interface PathNode {

    String getRootPath();
    String getRootName();
    String getRootFullName();
    String getSimpleName();
    NodeType getType();
    String getRootProp(String prop);

}
