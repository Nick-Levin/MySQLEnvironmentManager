package com.c123.MySQLConsole.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Environment {

    private String id;
    private String username;
    private String password;
    private String dbName;
    private Timestamp createDate;
    private Status status;
    private String hostIp;

}
