package com.estudospringsecurity.SecurityTest.entity;

public enum Role {
    ADMIN("Admin"),
    USER("User");

    private final String value;

    Role(String value) {
        this.value = value;
    }
}
