package com.c123.MySQLConsole.dao;

import com.c123.MySQLConsole.entity.User;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public List<User> getall() {
        return null;
    }

    @Override
    public User getOne(String envId) {
        return null;
    }

    @Override
    public Integer getConnections(String envId) {
        return null;
    }

    @Override
    public User create(String envId, String host, String password) {
        return null;
    }

    @Override
    public void updateHost(String envId, String host) {

    }

    @Override
    public void updatePassword(String envId, String password) {

    }

    @Override
    public void grantMax(String envId) {

    }

    @Override
    public void grantMin(String envId) {

    }

    @Override
    public void drop(String envId) {

    }

    @Override
    public void dropAll() {

    }
}
