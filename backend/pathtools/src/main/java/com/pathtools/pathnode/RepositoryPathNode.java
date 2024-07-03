package com.pathtools.pathnode;

import com.pathtools.AbstractPathNode;
import com.pathtools.NodeType;

public class RepositoryPathNode extends AbstractPathNode {

    public RepositoryPathNode() {
        super(NodeType.REPOSITORY);
    }

    public RepositoryPathNode(String rootFullName) {
        super(NodeType.REPOSITORY, rootFullName);
    }

    public RepositoryPathNode(String rootPath, String rootName, String rootFullName) {
        super(NodeType.REPOSITORY, rootPath, rootName, rootFullName);
    }

}
