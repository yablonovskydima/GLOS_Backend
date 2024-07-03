package com.pathtools.pathnode;

import com.pathtools.AbstractPathNode;
import com.pathtools.NodeType;

public class DirectoryPathNode extends AbstractPathNode {

    public DirectoryPathNode() {
        super(NodeType.DIRECTORY);
    }

    public DirectoryPathNode(NodeType type, String rootFullName) {
        super(type, rootFullName);
    }

    public DirectoryPathNode(String rootPath, String rootName, String rootFullName) {
        super(NodeType.DIRECTORY, rootPath, rootName, rootFullName);
    }

}
