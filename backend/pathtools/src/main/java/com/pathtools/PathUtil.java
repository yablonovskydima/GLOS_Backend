package com.pathtools;

import java.util.Map;

public class PathUtil {

    public static String[] splitPath(final String str) {
        Map.Entry<NodeType, Integer> separatorPair = lastSeparatorIndex(str);

        if (separatorPair.getValue() < 0) {
            return new String[0];
        }

        final String path = str.substring(0, separatorPair.getValue());
        final String name = str.substring(separatorPair.getValue());
        return new String[] {path, name};
    }

    private static Map.Entry<NodeType, Integer> lastSeparatorIndex(String str) {
        int index = -1;
        NodeType nodeType = null;
        for(NodeType type : NodeType.values()) {
            int lindex = str.lastIndexOf(type.nodeChar());
            if (index < lindex) {
                index = lindex;
                nodeType = type;
            }
        }
        return Map.entry(nodeType, index);
    }

}
