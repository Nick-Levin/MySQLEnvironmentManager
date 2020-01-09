package com.c123.MySQLConsole.service;

import com.c123.MySQLConsole.config.EnvironmentConfiguration;
import com.c123.MySQLConsole.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdGeneratorImpl implements IdGenerator {

    @Autowired
    EnvironmentConfiguration environmentConfiguration;

    @Override
    public String generate() {
        final StringBuilder envId = new StringBuilder(environmentConfiguration.getLength());
        while(envId.length() < environmentConfiguration.getLength())
            envId.append(StringUtils.getRandomChar.apply(environmentConfiguration.getSymbols()));
        return null;
    }

}
