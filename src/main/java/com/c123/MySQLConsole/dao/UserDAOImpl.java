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
    public boolean updateHost(String envId, String host) {
        return false;
    }

    @Override
    public boolean updatePassword(String envId, String password) {
        return false;
    }

    @Override
    public boolean grantMax(String envId) {
        return false;
    }

    @Override
    public boolean grantMin(String envId) {
        return false;
    }

    @Override
    public boolean drop(String envId) {
        return false;
    }

    @Override
    public boolean dropAll() {
        return false;
    }
}
