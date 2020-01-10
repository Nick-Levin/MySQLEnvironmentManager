package com.c123.MySQLConsole.controller;

import com.c123.MySQLConsole.entity.Environment;
import com.c123.MySQLConsole.entity.Message;
import com.c123.MySQLConsole.entity.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("environments")
public class EnvironmentControllerImpl implements EnvironmentController{

    @Autowired
    Logger logger;

    @Override
    @GetMapping()
    public ResponseEntity<List<Environment>> getAll() {
        return null;
    }

    @Override
    @GetMapping("")
    public ResponseEntity<Environment> getById(String envId) {
        return null;
    }

    @Override
    @PostMapping("")
    public ResponseEntity<User> create(String host, String passwordType, String passwordLength, String custom) {
        return null;
    }

    @Override
    @DeleteMapping("")
    public ResponseEntity<Message> drop(String envId) {
        return null;
    }

    @Override
    @DeleteMapping("")
    public ResponseEntity<Message> dropAll() {
        return null;
    }
}
