package com.c123.MySQLConsole.dao;

import com.c123.MySQLConsole.entity.User;

import java.util.List;

public interface UserDAO {

    List<User> getall();
    User getOne(String envId);
    Integer getConnections(String envId);

    User create(String envId, String host, String password);

    void updateHost(String envId, String host);
    void updatePassword(String envId, String password);
    void grantMax(String envId);
    void grantMin(String envId);

    void drop(String envId);
    void dropAll();

}
