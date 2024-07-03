package com.accesstools;

import java.util.List;
import java.util.Objects;

public final class AccessUtils {

    public static boolean containsInList(String pattern, String name) {
        Objects.requireNonNull(pattern);
        Objects.requireNonNull(name);
        AccessNode node = AccessNode.builder(pattern).build();
        return containsInList(node, name);
    }

    public static boolean containsInList(AccessNode node, String name) {
        Objects.requireNonNull(node);
        Objects.requireNonNull(name);
        return node.getList().contains(name);
    }

    public static boolean isType(String pattern, AccessNodeType type) {
        Objects.requireNonNull(pattern);
        Objects.requireNonNull(type);
        AccessNode node = AccessNode.builder(pattern).build();
        return node.getNodeType() == type;
    }

    public static boolean isType(AccessNode node, AccessNodeType type) {
        Objects.requireNonNull(node);
        Objects.requireNonNull(type);
        return node.getNodeType() == type;
    }

    public static boolean checkOnRead(AccessNode accessNode, String name) {
        return checkAndReadOnly(accessNode, name, true);
    }

    public static boolean checkOnWrite(AccessNode accessNode, String name) {
        return checkAndReadOnly(accessNode, name, false);
    }

    public static boolean checkAndReadOnly(AccessNode accessNode, String name, boolean isReadOnly) {
        boolean result = isReadOnly || !isReadOnly(accessNode);
        return check(accessNode, name) && result;
    }

    public static boolean isReadOnly(String accessPattern) {
        Objects.requireNonNull(accessPattern);
        AccessNode node = AccessNode.builder(accessPattern).build();
        return isReadOnly(node);
    }

    public static boolean isReadOnly(AccessNode accessNode)
            throws IllegalArgumentException {
        Objects.requireNonNull(accessNode);
        if (accessNode == AccessNode.EMPTY)
            throw new IllegalArgumentException("AccessNode argument is empty");
        return accessNode.getReadType() == AccessReadType.R;
    }

    public static boolean isOwner(AccessNode accessNode) {
        return accessNode.getNodeType() == AccessNodeType.OWNER;
    }

    public static boolean isAny(AccessNode accessNode) {
        return accessNode.getName().equals(Constants.SPEC_ANY);
    }

    public static boolean isExpect(AccessNode accessNode) {
        return accessNode.getName().equals(Constants.SPEC_EXCEPT);
    }

    public static boolean check(String accessPattern, String name) {
        Objects.requireNonNull(accessPattern);
        Objects.requireNonNull(name);
        AccessNode node = AccessNode.builder(accessPattern).build();
        return check(node, name);
    }

    public static boolean check(AccessNode accessNode, String name) {
        Objects.requireNonNull(accessNode);
        Objects.requireNonNull(name);
        if (accessNode.getName().equals(Constants.SPEC_ANY)) {
            if (accessNode.getList().isEmpty()) {
                return true;
            } else {
                return containsInList(accessNode, name);
            }
        } else if(accessNode.getName().equals(Constants.SPEC_EXCEPT)) {
            if (accessNode.getList().isEmpty()) {
                return false;
            } else {
                return !containsInList(accessNode, name);
            }
        }
        return accessNode.getName().equals(name);
    }

    public static boolean checkAny(List<AccessNode> nodes, String name){
        Objects.requireNonNull(nodes);
        Objects.requireNonNull(name);
        return nodes.stream().anyMatch(x -> check(x, name));
    }

}
