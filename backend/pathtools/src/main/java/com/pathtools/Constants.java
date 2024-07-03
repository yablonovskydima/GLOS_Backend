package com.pathtools;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    private static final Map<String, Character> SPEC_PATH_CHARS;

    static {
        SPEC_PATH_CHARS = new HashMap<>();
        SPEC_PATH_CHARS.put("repository", '$');
        SPEC_PATH_CHARS.put("directory", '+');
        SPEC_PATH_CHARS.put("file", '%');
    }

}
