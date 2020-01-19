package com.c123.MySQLConsole.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("application.sql")
public class SQLStatmentsConfig {

    private String getAllEnvs;
    private String getEnv;
    private String createDB;
    private String insertEnv;
    private String createUser;
    private String dropUser;
    private String dropDB;
    private String deleteEnv;
    private String getConnections;

}
