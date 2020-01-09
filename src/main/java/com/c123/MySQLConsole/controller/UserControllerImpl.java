package com.c123.MySQLConsole.controller;

import com.c123.MySQLConsole.entity.Message;
import com.c123.MySQLConsole.entity.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users/")
public class UserControllerImpl implements UserController {

    @Autowired
    Logger logger;

    @Override
    public ResponseEntity<User> restore(String envId) {
        return null;
    }

    @Override
    public ResponseEntity<Message> rename(String envId, String newHost) {
        return null;
    }

    @Override
    public ResponseEntity<Message> secure(String envId) {
        return null;
    }

    @Override
    public ResponseEntity<Message> extend(String envId) {
        return null;
    }

    @Override
    public ResponseEntity<Message> revoke(String envId) {
        return null;
    }

    @Override
    public ResponseEntity<User> rotate(String envId) {
        return null;
    }
}
