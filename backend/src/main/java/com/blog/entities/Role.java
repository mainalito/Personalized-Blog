package com.blog.entities;

import lombok.Data;


public enum Role {
    USER("User"),
    ADMIN("Admin");
    private final String value;
    Role(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
