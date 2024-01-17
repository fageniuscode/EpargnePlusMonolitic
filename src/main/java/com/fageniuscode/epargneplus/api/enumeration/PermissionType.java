package com.fageniuscode.epargneplus.api.enumeration;
public enum PermissionType {
    ALLOW("allow"),
    DENY("deny");
    PermissionType(String name) {
        this.name = name;
    }
    String name;
    @Override
    public String toString() {
        return name;
    }
}
