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
    public Environment create(String envId) {
        return null;
    }

    @Override
    public void update(String envId, Environment environment) {

    }

    @Override
    public void updateHost(String envId, String host) {

    }

    @Override
    public void updateStatus(String envId, Status status) {

    }

    @Override
    public void updatePassword(String envId, String password) {

    }

    @Override
    public void drop(String envId) {

    }

    @Override
    public void dropAll() {

    }
}
