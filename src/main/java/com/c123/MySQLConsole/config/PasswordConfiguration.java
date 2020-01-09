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
@ConfigurationProperties("application.password")
public class PasswordConfiguration {

    private int minLength;
    private int maxLength;
    private String mockValue;
    private String symbols;

}
