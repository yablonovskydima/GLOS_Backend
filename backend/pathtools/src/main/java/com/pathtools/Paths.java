package com.pathtools;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class Paths {

    public static final Path EMPTY_PATH = new PathImpl("", new ArrayList<>());


    public static final boolean isRootName(String rootName) {
        for(NodeType type : NodeType.values())
            if (rootName.charAt(0) == type.nodeChar())
                return true;
        return false;
    }

    public static final Pattern getSpecCharactersPattern() {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for(NodeType type : NodeType.values()) {
            if(builder.length() != 1) {
                builder.append("|");
            }
            builder.append(type.nodeChar());
        }
        builder.append(")");
        return Pattern.compile(builder.toString());
    }

    public static boolean isSpec(char c) {
        return NodeType.fromNodeChar(c) != NodeType.NONE;
    }

}
