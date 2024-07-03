package com.pathtools.pathnode;

import com.pathtools.NodeType;

public class ArchivePathNode extends FilePathNode {

    public ArchivePathNode() {
        super(NodeType.ARCHIVE);
    }

    public ArchivePathNode(NodeType type, String rootFullName) {
        super(type, rootFullName);
    }

    public ArchivePathNode(String rootPath, String rootName, String rootFullName) {
        super(rootPath, rootName, rootFullName);
    }

    public ArchivePathNode(String rootPath, String rootName, String rootFullName, String rootFormat) {
        super(rootPath, rootName, rootFullName, rootFormat);
    }

}
