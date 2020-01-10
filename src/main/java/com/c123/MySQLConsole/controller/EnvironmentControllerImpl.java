package com.c123.MySQLConsole.controller;

import com.c123.MySQLConsole.dao.EnvironmentDAO;
import com.c123.MySQLConsole.dao.UserDAO;
import com.c123.MySQLConsole.entity.Environment;
import com.c123.MySQLConsole.entity.Message;
import com.c123.MySQLConsole.entity.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
                map.put("connections", userDAO.getConnections(env.getId()));
                return map;
            }).collect(Collectors.toList()),
            HttpStatus.OK);
    }

    @Override
    @GetMapping("/{envId}")
    public ResponseEntity<Environment> getById(@PathVariable String envId) {
        return new ResponseEntity(environmentDAO.getOne(envId), HttpStatus.OK);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<User> create(
            @RequestParam(name = "host", required = true) String host,
            @RequestParam(name = "type", required = false, defaultValue = "generate") String passwordType,
            @RequestParam(name = "length", required = true) String passwordLength,
            @RequestParam(name = "custom", required = false, defaultValue = "") String custom) {
        final Environment env = environmentDAO.create(host, passwordType, passwordLength, custom);
        final User user = userDAO.getOne(env.getId());
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{envId}")
    public ResponseEntity<Message> drop(@PathVariable String envId) {
        final boolean result = environmentDAO.drop(envId);
        final Message message = result ? new Message(envId + " dropped") : new Message("Operation failed");
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
