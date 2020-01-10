package com.c123.MySQLConsole.controller;

import com.c123.MySQLConsole.entity.Environment;
import com.c123.MySQLConsole.entity.Message;
import com.c123.MySQLConsole.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface EnvironmentController {

    // GET
    ResponseEntity<List<Map<String, Object>>> getAll();
    ResponseEntity<Environment> getById(String envId);

    // POST
    ResponseEntity<User> create(String host, String passwordType, String passwordLength, String custom);

    // PUT

    // DROP
    ResponseEntity<Message> drop(String envId);
    ResponseEntity<Message> dropAll();

}
