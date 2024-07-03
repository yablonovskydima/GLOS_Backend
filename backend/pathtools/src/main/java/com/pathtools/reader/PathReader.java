package com.pathtools.reader;

public interface PathReader
        extends PathNodeReaderFirstLast,
        PathNodeReaderParent,
        PathNodeReaderRange,
        PathNodeReaderIndex,
        Streamable { }
