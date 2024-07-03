package com.glos.filestorageservice.domain.utils;

import com.pathtools.Path;
import com.pathtools.pathnode.PathNode;

public class MinioUtil {

    public static String normalizeBucketName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Bucket name cannot be null");
        }
        return name.trim().toLowerCase().replaceAll("\\s+", "-");
    }

    public static Path normilizePath(Path path) {
        PathNode firstNode = path.getFirst();
        String firstNormalize = MinioUtil.normalizeBucketName(firstNode.getSimpleName());
        return path.createBuilder()
                .removeFirst()
                .repository(firstNormalize, true)
                .build();
    }

    public static boolean match(String name, String normalizedName) {
        if (name == null || normalizedName == null) {
            throw new IllegalArgumentException("Names cannot be null");
        }
        return normalizeBucketName(name).equals(normalizedName);
    }
}
