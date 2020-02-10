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
import org.springframework.dao.DataAccessException;
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
        try {
            return new ResponseEntity(userDAO.getOne(envId), HttpStatus.OK);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(new Message("Environment not found"), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PutMapping("/rename/{envId}")
    public ResponseEntity<Message> rename(
            @PathVariable String envId,
            @RequestParam(name = "host", required = true) String newHost) {
        try {
            userDAO.updateHost(envId, newHost);
            environmentDAO.updateHost(envId, newHost);
            return new ResponseEntity(new Message("Updated"), HttpStatus.OK);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(new Message("Operation failed"), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PutMapping("/secure/{envId}")
    public ResponseEntity<Message> secure(@PathVariable String envId) {
        try {
            final String host = environmentDAO.getOne(envId).getHostIp();
            userDAO.grantMin(envId, host);
            environmentDAO.updateStatus(envId, Status.SECURED);
            return new ResponseEntity(new Message("Updated"), HttpStatus.OK);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(new Message("Operation failed"), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PutMapping("/extend/{envId}")
    public ResponseEntity<Message> extend(@PathVariable String envId) {
        try {
            final String host = environmentDAO.getOne(envId).getHostIp();
            userDAO.grantMax(envId, host);
            environmentDAO.updateStatus(envId, Status.EXTENDED);
            return new ResponseEntity(new Message("Updated"), HttpStatus.OK);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(new Message("Operation failed"), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PutMapping("/revoke/{envId}")
    public ResponseEntity<Message> revoke(@PathVariable String envId) {
        return secure(envId);
    }

    @Override
    @PutMapping("/rotate/{envId}")
    public ResponseEntity<User> rotate(
            @PathVariable String envId,
            @RequestParam(name = "length", required = true, defaultValue = "8") int passwordLength) {
        try {
            final String newPassword = passwordGenerator.getPass(PasswordType.GENERATE, passwordLength, "");
            userDAO.updatePassword(envId, newPassword);
            environmentDAO.updatePassword(envId, newPassword);
            final User user = userDAO.getOne(envId);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(new Message("Operation failed"), HttpStatus.BAD_REQUEST);
        }
    }
}
