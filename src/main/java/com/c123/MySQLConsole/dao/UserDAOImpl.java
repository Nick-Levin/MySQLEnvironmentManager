package com.c123.MySQLConsole.dao;

import com.c123.MySQLConsole.config.NamingPatternConfiguration;
import com.c123.MySQLConsole.config.SQLStatmentsConfig;
import com.c123.MySQLConsole.entity.Environment;
import com.c123.MySQLConsole.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDAOImpl implements UserDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SQLStatmentsConfig sqlStatmentsConfig;

    @Autowired
    NamingPatternConfiguration patterns;

    @Override
    public List<User> getall() {
        return jdbcTemplate.queryForList(sqlStatmentsConfig.getGetAllEnvs(), Environment.class)
        .stream().map(env -> new User(env.getUsername(), env.getPassword()))
        .collect(Collectors.toList());
    }

    @Override
    public User getOne(String envId) {
        final Environment env = jdbcTemplate.queryForObject(sqlStatmentsConfig.getGetEnv(), Environment.class, envId);
        return new User(env.getUsername(), env.getPassword());
    }

    @Override
    public Integer getConnections(String envId) {
        final String user = patterns.getUser().replace("{{ENVID}}", envId);

        List<Integer> list = jdbcTemplate.queryForList(sqlStatmentsConfig.getGetConnections()).stream()
                .filter(conns -> ((String)conns.get("user")).equals(user))
                .map(conns -> (int)conns.get("Connections"))
                .collect(Collectors.toList());
        return list.isEmpty() ? 0 : list.get(0);
    }

    @Override
    public User create(String envId, String host, String password) {
        final String username = patterns.getUser().replace("{{ENVID}}", envId);
        final String sql = sqlStatmentsConfig.getCreateUser()
                .replace("{{USER}}", username)
                .replace("{{HOST}}", host)
                .replace("{{PASSWORD}}", password);
        jdbcTemplate.execute(sql);
        return new User(username, password);
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
