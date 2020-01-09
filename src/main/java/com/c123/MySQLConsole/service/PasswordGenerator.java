package com.c123.MySQLConsole.service;

import com.c123.MySQLConsole.entity.PasswordType;

public interface PasswordGenerator {

    String getPass(PasswordType passwordType, Integer passwordLength, String custom);

}
