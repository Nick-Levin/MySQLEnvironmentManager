package com.c123.MySQLConsole.entity;

import lombok.Getter;

public enum PasswordType {

    EMPTY("empty"),
    SAME("same"),
    GENERATE("generate"),
    MOCK("mock"),
    CUSTOM("custom");

    @Getter private String type;

    PasswordType(String type) {
        this.type = type;
    }

}
