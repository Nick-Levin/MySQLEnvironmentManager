package com.c123.MySQLConsole.controller;

import com.c123.MySQLConsole.entity.Environment;
import com.c123.MySQLConsole.entity.Message;
import com.c123.MySQLConsole.entity.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("environments/")
public class EnvironmentControllerImpl implements EnvironmentController{

    @Autowired
    Logger logger;

    @Override
    public ResponseEntity<List<Environment>> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<Environment> getById(String envId) {
        return null;
    }

    @Override
    public ResponseEntity<User> create(String host, String passwordType, String passwordLength, String custom) {
        return null;
    }

    @Override
    public ResponseEntity<Message> drop(String envId) {
        return null;
    }

    @Override
    public ResponseEntity<Message> dropAll() {
        return null;
    }
}
