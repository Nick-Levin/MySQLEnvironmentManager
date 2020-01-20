package com.c123.MySQLConsole.controller;

import com.c123.MySQLConsole.dao.EnvironmentDAO;
import com.c123.MySQLConsole.dao.UserDAO;
import com.c123.MySQLConsole.entity.Message;
import com.c123.MySQLConsole.entity.PasswordType;
import com.c123.MySQLConsole.entity.Status;
import com.c123.MySQLConsole.entity.User;
import com.c123.MySQLConsole.service.PasswordGenerator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserControllerImpl implements UserController {

    @Autowired
    Logger logger;

    @Autowired
    EnvironmentDAO environmentDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    PasswordGenerator passwordGenerator;

    @Override
    @GetMapping("/{envId}")
    public ResponseEntity<User> restore(@PathVariable String envId) {
        final User user = userDAO.getOne(envId);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @Override
    @PutMapping("/rename/{envId}")
    public ResponseEntity<Message> rename(
            @PathVariable String envId,
            @RequestParam(name = "host", required = true) String newHost) {
        final boolean resultUser = userDAO.updateHost(envId, newHost);
        final boolean resultEnv = environmentDAO.updateHost(envId, newHost);
        final Message message = resultUser && resultEnv
                ? new Message("Updated")
                : new Message("operation failed");
        return new ResponseEntity(message, HttpStatus.OK);
    }

    @Override
    @PutMapping("/secure/{envId}")
    public ResponseEntity<Message> secure(@PathVariable String envId) {
        final String host = environmentDAO.getOne(envId).getHostIp();
        final boolean result = userDAO.grantMin(envId, host);
        environmentDAO.updateStatus(envId, Status.SECURED);
        final Message message = result ? new Message("Updated") : new Message("operation failed");
        return new ResponseEntity(message, HttpStatus.OK);
    }

    @Override
    @PutMapping("/extend/{envId}")
    public ResponseEntity<Message> extend(@PathVariable String envId) {
        final String host = environmentDAO.getOne(envId).getHostIp();
        final boolean result = userDAO.grantMax(envId, host);
        environmentDAO.updateStatus(envId, Status.EXTENDED);
        final Message message = result ? new Message("Updated") : new Message("operation failed");
        return new ResponseEntity(message, HttpStatus.OK);
    }

    @Override
    @PutMapping("/revoke/{envId}")
    public ResponseEntity<Message> revoke(@PathVariable String envId) {
        final String host = environmentDAO.getOne(envId).getHostIp();
        final boolean result = userDAO.grantMin(envId, host);
        environmentDAO.updateStatus(envId, Status.SECURED);
        final Message message = result ? new Message("Updated") : new Message("operation failed");
        return new ResponseEntity(message, HttpStatus.OK);
    }

    @Override
    @PutMapping("/rotate/{envId}")
    public ResponseEntity<User> rotate(
            @PathVariable String envId,
            @RequestParam(name = "length", required = true) String passwordLength) {
        final String newPassword = passwordGenerator.getPass(
                PasswordType.GENERATE, Integer.valueOf(passwordLength), "");
        final boolean resultUser = userDAO.updatePassword(envId, newPassword);
        final boolean resultEnv = environmentDAO.updatePassword(envId, newPassword);
        final User user = userDAO.getOne(envId);
        return new ResponseEntity(user, HttpStatus.OK);
    }
}
