package com.c123.MySQLConsole.dao;

import com.c123.MySQLConsole.entity.Environment;
import com.c123.MySQLConsole.entity.Status;

import java.util.List;

public interface EnvironmentDAO {

    List<Environment> getAll();
    Environment getOne(String envId);

    Environment create(String envId, String host, String password);

    boolean update(String envId, Environment environment);
    boolean updateHost(String envId, String host);
    boolean updateStatus(String envId, Status status);
    boolean updatePassword(String envId, String password);

    boolean drop(String envId);
    boolean dropAll();

}
