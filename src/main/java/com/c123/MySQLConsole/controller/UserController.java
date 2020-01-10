package com.c123.MySQLConsole.controller;

import com.c123.MySQLConsole.entity.Message;
import com.c123.MySQLConsole.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserController {

    // GET
    ResponseEntity<User> restore(String envId);

    // POST

    // PUT
    ResponseEntity<Message> rename(String envId, String newHost);
    ResponseEntity<Message> secure(String envId);
    ResponseEntity<Message> extend(String envId);
    ResponseEntity<Message> revoke(String envId);
    ResponseEntity<User> rotate(String envId, String passwordLength);

    // DELETE

}
