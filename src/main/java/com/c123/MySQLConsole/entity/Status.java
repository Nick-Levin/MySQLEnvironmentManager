package com.c123.MySQLConsole.entity;

import lombok.Getter;

public enum Status {

    CREATED("env_created"),
    SECURED("env_secured"),
    EXTENDED("env_extended"),
    DROPPED("env_dropped");

    @Getter private String desc;

    Status(String desc) {
        this.desc = desc;
    }

}
