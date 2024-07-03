package com.pathtools;

import com.pathtools.exception.InvalidPathFormatException;
import com.pathtools.pathnode.PathNode;
import com.pathtools.pathnode.PathNodeProps;

import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Utility class for parsing a string representation of a path and converting it into a Path object.
 * @author Mykola Melnyk
 */
public final class PathParser {

    private static PathParser INSTANCE;

    public static PathParser getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PathParser();
        return INSTANCE;
    }

    public PathParser() {}

    /**
     * Parses the given string representation of a path and returns the corresponding Path object.
     *
     * <pre>
     * Input string: "$user1/dir$repo#archive.zip%file.txt"
     * Parsed Path object:
     * {
     *      "path": "$user1/dir$repo%file.txt",
     *      "nodes": [
     *          {
     *              'rootPath': '',
     *              'rootName': '$user1',
     *              'rootFullName': '$user1',
     *              'type': NodeType.REPOSITORY
     *          },
     *          {
     *              'rootPath': '$user1',
     *              'rootName': '/dir',
     *              'rootFullName': '$user1/dir',
     *              'type': NodeType.DIRECTORY
     *          },
     *          {
     *              'rootPath': '$user1/dir',
     *              'rootName': '$repo',
     *              'rootFullName': '$user1/dir$repo',
     *              'type': NodeType.REPOSITORY
     *          },
     *          {
     *              'rootPath': '$user1/dir$repo',
     *              'rootName': '#archive.zip',
     *              'rootFullName': '$user1/dir$repo#archive.zip',
     *              'rootFormat': 'zip',
     *              'type': NodeType.ARCHIVE
     *          },
     *          {
     *              'rootPath': '$user1/dir$repo#archive.zip',
     *              'rootName': '%file.txt',
     *              'rootFullName': '$user1/dir$repo#archive.zip%file.txt',
     *              'rootFormat': 'txt',
     *              'type': NodeType.FILE
     *          }
     *      ]
     * }
     * </pre>
     *
     * @param pathStr The string representation of the path to be parsed.
     * @return The Path object representing the parsed path. If pathStr is empty then Paths.EMPTY_PATH.
     * @throws NullPointerException if the input pathStr is null.
     * @throws IllegalArgumentException if path has invalid format
     */
    public Path parse(final String pathStr) {
        if (pathStr == null) {
            throw new NullPointerException("Argument 'pathStr' is null");
        }
        final String pathTrim = pathStr.trim();

        if (pathTrim.isEmpty()) {
            return Paths.EMPTY_PATH;
        }

        final StringBuilder builder = new StringBuilder();
        final LinkedList<PathNode> nodes = new LinkedList<>();

        final int len = pathTrim.length();

        int i = 0;
        char c = ' ';

        final StringBuilder nodeBuilder = new StringBuilder();
        nodeBuilder.append(pathTrim.charAt(i));
        c = pathTrim.charAt(i);
        NodeType prevType = NodeType.fromNodeChar(c);
        NodeType curType = NodeType.NONE;
        while(++i < len) {
            c = pathTrim.charAt(i);
            if (curType != NodeType.NONE) {
                prevType = curType;
                nodeBuilder.append(prevType.nodeChar());
            }
            while(NodeType.fromNodeChar(c) == NodeType.NONE && ++i < len) {
                nodeBuilder.append(c);
                c = pathTrim.charAt(i);
                if (i == len - 1)
                    nodeBuilder.append(c);
                curType = NodeType.fromNodeChar(c);
            }
            final String rootPath = builder.toString();
            builder.append(nodeBuilder);
            final String rootFullname = builder.toString();
            final String rootName = nodeBuilder.toString();
            nodeBuilder.setLength(0);

            PathNode node = getPathNode(prevType, rootPath, rootName, rootFullname);
            if (node != null) {
                nodes.add(node);
            }
        }

        return new PathImpl(builder.toString(), nodes);
    }

    private PathNode getPathNode(NodeType type, String rootPath, String rootName, String rootFullName) {
        return type.node(Map.of(
                PathNodeProps.ROOT_PATH, rootPath,
                PathNodeProps.ROOT_NAME, rootName,
                PathNodeProps.ROOT_FULL_NAME, rootFullName
        ));
    }

    public String parseString(Path path, String sep) {
        String str = path.getPath();
        for (NodeType type : NodeType.values()) {
            final String regex = "\\" + type.nodeChar();
            str = str.replaceAll(regex, Matcher.quoteReplacement(sep));
        }
        return str;
    }

}
