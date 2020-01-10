package com.c123.MySQLConsole.dao;

import com.c123.MySQLConsole.entity.User;

import java.util.List;

public interface UserDAO {

    List<User> getall();
    User getOne(String envId);
    Integer getConnections(String envId);

    User create(String envId, String host, String password);

    boolean updateHost(String envId, String host);
    boolean updatePassword(String envId, String password);
    boolean grantMax(String envId);
    boolean grantMin(String envId);

    boolean drop(String envId);
    boolean dropAll();

}
