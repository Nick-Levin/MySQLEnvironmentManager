package com.c123.MySQLConsole.controller;

import com.c123.MySQLConsole.config.PasswordConfiguration;
import com.c123.MySQLConsole.dao.EnvironmentDAO;
import com.c123.MySQLConsole.dao.UserDAO;
import com.c123.MySQLConsole.entity.*;
import com.c123.MySQLConsole.service.IdGenerator;
import com.c123.MySQLConsole.service.PasswordGenerator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("environments")
public class EnvironmentControllerImpl implements EnvironmentController{

    @Autowired
    Logger logger;

    @Autowired
    EnvironmentDAO environmentDAO;

    @Autowired
    IdGenerator idGenerator;

    @Autowired
    PasswordGenerator passwordGenerator;

    @Autowired
    PasswordConfiguration passwordConfiguration;

    @Autowired
    UserDAO userDAO;

    @Override
    @GetMapping()
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        return new ResponseEntity(environmentDAO.getAll().stream()
            .map(env -> {
                final Map<String, Object> map = new HashMap<>();
                map.put("id", env.getId());
                map.put("username", env.getUsername());
                map.put("dbName", env.getDbName());
                map.put("createDate", env.getCreateDate());
                map.put("host", env.getHostIp());
                map.put("status", env.getStatus());
                final long connections = env.getStatus().equals(Status.DROPPED)
                        ? userDAO.getConnections(env.getId())
                        : 0;
                map.put("connections", connections);
                return map;
            }).collect(Collectors.toList()),
            HttpStatus.OK);
    }

    @Override
    @GetMapping("/{envId}")
    public ResponseEntity getById(@PathVariable String envId) {
        try {
            final Environment env = environmentDAO.getOne(envId);
            return new ResponseEntity(env, HttpStatus.OK);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return new ResponseEntity("Environment not found", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<User> create(
            @RequestParam(name = "env", required = false, defaultValue = "") String customEnv,
            @RequestParam(name = "host", required = true) String host,
            @RequestParam(name = "type", required = false, defaultValue = "generate") String passwordType,
            @RequestParam(name = "length", required = false, defaultValue = "8") int passwordLength,
            @RequestParam(name = "custom", required = false, defaultValue = "") String custom) {
        try {
            final PasswordType type = PasswordType.valueOf(passwordType.toUpperCase());

            final int passLength = passwordLength >= passwordConfiguration.getMinLength()
                    && passwordLength <= passwordConfiguration.getMaxLength()
                    ? passwordLength
                    : passwordConfiguration.getMinLength();

            final String envId = customEnv.isEmpty()
                    ? idGenerator.generate()
                    : customEnv;

            final String password = type.equals(PasswordType.SAME)
                    ? envId
                    : passwordGenerator.getPass(type, passLength, custom);

            if(type.equals(PasswordType.CUSTOM) && custom.isEmpty())
                return new ResponseEntity(new Message("Please provide password"), HttpStatus.BAD_REQUEST);

            if(!type.equals(PasswordType.CUSTOM) && !custom.isEmpty())
                return new ResponseEntity(new Message("Invalid argument custom passed"), HttpStatus.BAD_REQUEST);

            try {
                environmentDAO.getOne(envId);
                return new ResponseEntity(new Message("Environment ID already exists"), HttpStatus.BAD_REQUEST);
            } catch (DataAccessException e) {}

            final Environment env = environmentDAO.create(envId, host, password);
            final User user = new User(env.getUsername(), env.getPassword());

            return new ResponseEntity(user, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(new Message("Please provide a valid type value for password type"), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @DeleteMapping("/{envId}")
    public ResponseEntity<Message> drop(@PathVariable String envId) {
        final boolean result = environmentDAO.drop(envId);
        final Message message = result ? new Message(envId + " dropped") : new Message("Environment not found");
        return new ResponseEntity(message, HttpStatus.OK);
    }

    @Override
    @DeleteMapping()
    public ResponseEntity<Message> dropAll() {
        final boolean result = environmentDAO.dropAll();
        final Message message = result ? new Message("dropped") : new Message("Operation failed");
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
