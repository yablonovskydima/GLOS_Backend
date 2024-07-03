package com.glos.api.authservice.util;

import java.nio.file.Path;

public final class PathUtils {

    public static String normalizeForUrl(String path) {
        if(path == null)
            return path;
        return path.replaceAll("[/\\\\]", "+");
    }

    public static String originalPath(String path) {
        if (path == null) {
            return path;
        }
        return path.replaceAll("\\+", "/");
    }

    public static String[] splitPathFilename(String fullName) {
        if (fullName == null) {
            return null;
        }
        Path path = Path.of(fullName);
        Path parent = path.getParent();
        String pathFile = (parent != null) ? parent.toString() : "";
        String filename = path.toFile().getName();
        pathFile = pathFile.replaceAll("[\\\\]", "/");
        return new String[] {pathFile, filename};
    }
    
    public static String[] splitNormalizedFilename(String filename) {
        if (filename == null) {
            return null;
        }
        return filename.split("\\+");
    }
}
