package com.pds.pingou.framework.core.security.user;

/**
 * Enum que representa os papéis de usuário no sistema.
 * 
 * @author Pingou Framework Team
 * @version 1.0
 * @since 1.0
 */
public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
