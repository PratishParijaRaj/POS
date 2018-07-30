package com.example.pratishparija.pos.models;

public class UserRole {
    public static final String ROLE_SUPER_ADMIN = "superAdmin";
    public static final String ROLE_STORE_MANAGER = "storeManager";
    public static final String ROLE_SALES_EXECUTIVE = "salesExecutive";
    public static final String ROLE_ADMIN = "admin";

    private String roleId;
    private String description;
    private String type;

    public UserRole() {
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserRole(String roleId, String description, String type) {
        this.roleId = roleId;
        this.description = description;
        this.type = type;
    }
}
