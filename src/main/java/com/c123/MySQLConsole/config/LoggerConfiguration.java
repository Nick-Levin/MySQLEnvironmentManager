package com.c123.MySQLConsole.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfiguration {

    @Bean
    public Logger logger(InjectionPoint injectionPoint) {
        Class cls = injectionPoint.getMember().getDeclaringClass();
        return LoggerFactory.getLogger(cls);
    }

}
