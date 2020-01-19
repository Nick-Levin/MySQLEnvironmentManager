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

    public static Status stringToStatus(String status) {
        switch (status) {
            case "env_created": return CREATED;
            case "env_secured": return SECURED;
            case "env_extended": return EXTENDED;
            case "env_dropped": return DROPPED;
            default: return null;
        }
    }

}
