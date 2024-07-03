package com.pathtools;

import com.pathtools.pathnode.*;

import java.util.Map;
import java.util.function.Function;

/**
 * Enum representing types of nodes in a path.
 * <pre>
 * $ - REPOSIOTRY NODE. Default class RepositoryPathNode.
 * + - DIRECTORY NODE, Default class DirectoryPathNode.
 * % - FILE NODE, Default class FilePathNode.
 * # - ARCHIVE NODE, Default class ArchivePathNode.
 * </pre>
 * @author Mykola Melnyk
 */
public enum NodeType {
    REPOSITORY('$', p ->
            new RepositoryPathNode(
                    p.get(PathNodeProps.ROOT_PATH),
                    p.get(PathNodeProps.ROOT_NAME),
                    p.get(PathNodeProps.ROOT_FULL_NAME))
    ),
    DIRECTORY('+', p ->
            new DirectoryPathNode(
                    p.get(PathNodeProps.ROOT_PATH),
                    p.get(PathNodeProps.ROOT_NAME),
                    p.get(PathNodeProps.ROOT_FULL_NAME))
    ),
    FILE('%', p ->
            new FilePathNode(
                    p.get(PathNodeProps.ROOT_PATH),
                    p.get(PathNodeProps.ROOT_NAME),
                    p.get(PathNodeProps.ROOT_FULL_NAME))
    ),
    ARCHIVE('#', p ->
            new ArchivePathNode(
                    p.get(PathNodeProps.ROOT_PATH),
                    p.get(PathNodeProps.ROOT_NAME),
                    p.get(PathNodeProps.ROOT_FULL_NAME))
    ),
    NONE(' ', p -> null);

    public static NodeType fromNodeChar(char nodeChar) {
        for(NodeType nodeType : values()) {
            if (nodeType.nodeChar == nodeChar) {
                return nodeType;
            }
        }
        return NONE;
    }

    private char nodeChar;
    private Function<Map<String, String>, PathNode> nodeSupplier;

    NodeType(char nodeChar, Function<Map<String, String>, PathNode> nodeSupplier) {
        this.nodeChar = nodeChar;
        this.nodeSupplier = nodeSupplier;
    }

    public char nodeChar() {
        return nodeChar;
    }

    public PathNode node(Map<String, String> props) {
        return nodeSupplier.apply(props);
    }
}
