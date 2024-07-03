package com.pathtools;

import com.pathtools.pathnode.PathNode;
import com.pathtools.reader.PathReader;

import java.util.List;

/**
 * Represents a path consisting of nodes.
 * <pre>
 * Examples:
 *   "$user1"
 *   "$user1%file.txt"
 *   "$user1/dir%file.txt"
 *   "$user1/dir$repo%file.txt"
 *   "$user1$repo"
 *   "$user1/dir#archive.zip"
 *   "$user1/dir#archive.zip%file.txt"
 *   "$user1/dir#archive.zip/adir%file.txt"
 * </pre>
 *
 * @author Mykola Melnyk
 */
public interface Path extends Iterable<PathNode> {

     static PathBuilder builder() {
          return new PathBuilder();
     }

     static PathBuilder builder(String path) {
          return new PathBuilder(path);
     }

     String getPath();
     String getSimplePath(String sep, boolean startRoot);
     List<PathNode> getNodes();
     PathNode getFirst();
     PathNode getLast();
     int length();
     PathReader reader();
     PathBuilder createBuilder();
}
