package com.accesstools;

import java.util.List;
import java.util.Objects;

public enum AccessReadType {
    NONE, R, RW;

    public static AccessReadType fromName(String name) {
        Objects.requireNonNull(name);
        String name0 = name.trim().toLowerCase();
        name0 = name0.replace("-_\\s", "");
        if (List.of("r", "read").contains(name0))
            return R;
        else if (List.of("rw", "readwrite").contains(name0))
            return RW;
        throw new IllegalArgumentException("AccessReadType enum object with specific name not found: %s".formatted(name));
    }
}
