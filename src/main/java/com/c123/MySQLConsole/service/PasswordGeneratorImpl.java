package com.c123.MySQLConsole.service;

import com.c123.MySQLConsole.config.PasswordConfiguration;
import com.c123.MySQLConsole.entity.PasswordType;
import com.c123.MySQLConsole.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordGeneratorImpl implements PasswordGenerator {

    @Autowired
    PasswordConfiguration passwordConfiguration;

    @Override
    public String getPass(PasswordType passwordType, Integer passwordLength, String custom) {
        String password = "";

        switch (passwordType) {
            case GENERATE:
                password = generateRandom(passwordLength);
                break;
            case SAME:
            case CUSTOM:
                password = custom;
                break;
            case MOCK:
                password = passwordConfiguration.getMockValue();
                break;
            case EMPTY:
                break;
        }

        return password;
    }

    private String generateRandom(int length) {
        final StringBuilder password = new StringBuilder(length);

        while(password.length() < length)
            password.append(StringUtils.getRandomChar.apply(passwordConfiguration.getSymbols()));

        return password.toString();
    }

}
