package com.pathtools;

import com.pathtools.exception.InvalidPathFormatException;
import com.pathtools.pathnode.PathNode;
import com.pathtools.pathnode.PathNodeProps;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractPathNode implements PathNode {

    protected final NodeType type;
    protected final Map<String, String> props = new HashMap<>();

    public AbstractPathNode(NodeType type) {
        this.type = type;
        setRootFullName(null);
        setRootPath(null);
        setRootName(null);
    }

    public AbstractPathNode(NodeType type, Map<String, String> props) {
        this.type = type;
        setRootFullName(null);
        setRootPath(null);
        setRootName(null);
        this.props.putAll(props);
    }

    public AbstractPathNode(NodeType type, String rootFullName) {
        this.type = type;
        final String[] parts = PathUtil.splitPath(rootFullName);
        if (parts.length == 0) {
            throw new InvalidPathFormatException("invalid format path");
        }
        setRootPath(parts[0]);
        setRootName(parts[1]);
        setRootFullName(Objects.requireNonNull(rootFullName));
    }

    public AbstractPathNode(NodeType type, String rootPath, String rootName, String rootFullName) {
        this.type = Objects.requireNonNull(type);
        setRootPath(rootPath);
        setRootName(rootName);
        setRootFullName(rootFullName);
    }

    @Override
    public String getRootPath() {
        return this.props.get(PathNodeProps.ROOT_PATH);
    }

    public void setRootPath(String rootPath) {
        setRootProp(PathNodeProps.ROOT_PATH, rootPath);
    }

    @Override
    public String getRootName() {
        return this.props.get(PathNodeProps.ROOT_NAME);
    }

    public void setRootName(String rootName) {
        setRootProp(PathNodeProps.ROOT_NAME, rootName);
    }

    @Override
    public String getSimpleName() {
        String name = getRootName();
        if (name.isEmpty()) {
            throw new RuntimeException("Name is empty");
        }
        return name.substring(1);
    }

    @Override
    public String getRootFullName() {
        return this.props.get(PathNodeProps.ROOT_FULL_NAME);
    }

    public void setRootFullName(String rootFullName) {
        setRootProp(PathNodeProps.ROOT_FULL_NAME, rootFullName);
    }

    @Override
    public NodeType getType() {
        return type;
    }

    @Override
    public String getRootProp(String prop) {
        if (!props.containsKey(prop)) {
            throw new InvalidPathFormatException("Properties not found");
        }
        return props.get(prop);
    }

    public void setRootProp(String name, String prop) {
        this.props.put(name, prop);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AbstractPathNode that = (AbstractPathNode) object;
        return type == that.type && Objects.equals(props, that.props);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, props);
    }

    @Override
    public String toString() {
        return getRootFullName();
    }
}
