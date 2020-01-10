package com.c123.MySQLConsole.dao;

import com.c123.MySQLConsole.entity.Environment;
import com.c123.MySQLConsole.entity.Status;

import java.util.List;

public class EnvironmentDAOImpl implements EnvironmentDAO {
    @Override
    public List<Environment> getAll() {
        return null;
    }

    @Override
    public Environment getOne(String envId) {
        return null;
    }

    @Override
    public Environment create(String host, String passwordType, String passwordLength, String custom) {
        return null;
    }

    @Override
    public boolean update(String envId, Environment environment) {
        return false;
    }

    @Override
    public boolean updateHost(String envId, String host) {
        return false;
    }

    @Override
    public boolean updateStatus(String envId, Status status) {
        return false;
    }

    @Override
    public boolean updatePassword(String envId, String password) {
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
