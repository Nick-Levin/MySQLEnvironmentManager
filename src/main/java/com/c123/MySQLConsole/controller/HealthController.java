package com.c123.MySQLConsole.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    Logger logger;

    @GetMapping
    public ResponseEntity healthCheck() {
        logger.info("health check CALL");
        return new ResponseEntity("Alive", HttpStatus.OK);
    }

}
