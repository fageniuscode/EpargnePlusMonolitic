package com.fageniuscode.epargneplus.api.enumeration;
public enum SecurityAction {
    CREATE("create"),
    READ("read"),
    UPDATE("update"),
    DELETE("delete"),
    VIEW("view");
    String name;
    SecurityAction(String name) {
        this.name = name.toUpperCase();
    }
    public static SecurityAction fromString(String actionName) {
        return SecurityAction.valueOf(actionName.toUpperCase());
    }
    public String toString() {
        return name;
    }
}
