package com.c123.MySQLConsole.dao;

import com.c123.MySQLConsole.entity.Environment;
import com.c123.MySQLConsole.entity.Status;
import com.c123.MySQLConsole.entity.User;

import java.util.List;

public interface EnvironmentDAO {

    List<Environment> getAll();
    Environment getOne(String envId);

    Environment create(String envId);

    void update(String envId, Environment environment);
    void updateHost(String envId, String host);
    void updateStatus(String envId, Status status);
    void updatePassword(String envId, String password);

    void drop(String envId);
    void dropAll();

}
