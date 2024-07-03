package com.glos.databaseAPIService.domain.entities;

import java.util.Objects;

public enum Roles {
    ROLE_ADMIN,
    ROLE_USER;

    private final Role role;

    Roles() {
        long id = ordinal()+1;
        this.role = new Role(id, name());
    }

    public static Roles fromId(Long id) {
        Roles[] values = Roles.values();
        for(Roles role : values) {
            if (role.ordinal()+1 == id.intValue()) {
                return role;
            }
        }
        throw new IllegalArgumentException("role with id %s not found".formatted(id));
    }
    public static Roles fromName(String name) {
        Objects.requireNonNull(name);
        final String ROLE_PREFIX = "ROLE_";
        String n = name.toUpperCase().replace("-", "_");
        if (!n.startsWith(ROLE_PREFIX)) {
            n = ROLE_PREFIX + n;
        }
        return valueOf(n);
    }

    public Role asEntity() {
        return role;
    }
}
