package com.pathtools.pathnode;

import com.pathtools.AbstractPathNode;
import com.pathtools.NodeType;
import com.pathtools.exception.InvalidPathFormatException;

public class FilePathNode extends AbstractPathNode {

    protected FilePathNode(NodeType nodeType) {
        super(nodeType);
    }
    public FilePathNode() {
        super(NodeType.FILE);
        setRootFormat(null);
    }

    public FilePathNode(NodeType type, String rootFullName) {
        super(type, rootFullName);
        String[] parts = getRootName().split("\\.");
        if (parts.length == 0) {
            throw new InvalidPathFormatException("invalid filename '%s'".formatted(getRootName()));
        }
        setRootFormat(parts[1]);
    }

    public FilePathNode(String rootPath, String rootName, String rootFullName) {
        super(NodeType.FILE, rootPath, rootName, rootFullName);
        String[] parts = getRootName().split("\\.");
        if (parts.length != 2) {
            throw new InvalidPathFormatException("invalid filename '%s'".formatted(getRootName()));
        }
        setRootFormat(parts[1]);
    }


    public FilePathNode(String rootPath, String rootName, String rootFullName, String rootFormat) {
        super(NodeType.FILE, rootPath, rootName, rootFullName);
        setRootFormat(rootFormat);
    }

    public String getRootFormat() {
        return getRootProp(PathNodeProps.ROOT_FORMAT);
    }

    public void setRootFormat(String rootFormat) {
        setRootProp(PathNodeProps.ROOT_FORMAT, rootFormat);
    }
}
