package com.c123.MySQLConsole.dao;

import com.c123.MySQLConsole.config.NamingPatternConfiguration;
import com.c123.MySQLConsole.config.SQLStatmentsConfig;
import com.c123.MySQLConsole.entity.Environment;
import com.c123.MySQLConsole.entity.User;
import com.c123.MySQLConsole.rowmapper.EnvironmentRowMapper;
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

    @Autowired
    EnvironmentDAO environmentDAO;

    @Override
    public List<User> getall() {
        return jdbcTemplate.queryForList(sqlStatmentsConfig.getGetAllEnvs(), Environment.class)
        .stream().map(env -> new User(env.getUsername(), env.getPassword()))
        .collect(Collectors.toList());
    }

    @Override
    public User getOne(String envId) {
        final Environment env = jdbcTemplate.queryForObject(
                sqlStatmentsConfig.getGetEnv(),
                new Object[] {envId},
                new EnvironmentRowMapper());

        return new User(env.getUsername(), env.getPassword());
    }

    @Override
    public Integer getConnections(String envId) {
        final String user = patterns.getUser().replace("[[ENVID]]", envId);

        List<Integer> list = jdbcTemplate.queryForList(sqlStatmentsConfig.getGetConnections()).stream()
                .filter(conns -> ((String)conns.get("user")).equals(user))
                .map(conns -> (int)conns.get("Connections"))
                .collect(Collectors.toList());
        return list.isEmpty() ? 0 : list.get(0);
    }

    @Override
    public User create(String envId, String host, String password) {
        final String username = patterns.getUser().replace("[[ENVID]]", envId);
        final String dbanme = patterns.getDb().replace("[[ENVID]]", envId);

        final String sql = sqlStatmentsConfig.getCreateUser()
                .replace("[[USER]]", username)
                .replace("[[HOST]]", host)
                .replace("[[PASSWORD]]", password);

        jdbcTemplate.execute(sql);
        grantMax(envId, host);
        return new User(username, password);
    }

    @Override
    public boolean updateHost(String envId, String host) {
        final String oldHost = environmentDAO.getOne(envId).getHostIp();
        final String user = patterns.getUser().replace("[[ENVID]]", envId);

        final String sql = sqlStatmentsConfig.getChangeHost()
                .replace("[[USER]]", user)
                .replace("[[OLD_HOST]]", oldHost)
                .replace("[[NEW_HOST]]", host);

        jdbcTemplate.execute(sql);
        return true;
    }

    @Override
    public boolean updatePassword(String envId, String password) {
        final Environment environment = environmentDAO.getOne(envId);

        final String sql = sqlStatmentsConfig.getChangePassword()
                .replace("[[USER]]", environment.getUsername())
                .replace("[[HOST]]", environment.getHostIp())
                .replace("[[PASSWORD]]", password);

        jdbcTemplate.update(sql);
        return true;
    }

    @Override
    public boolean grantMax(String envId, String host) {
        final String username = patterns.getUser().replace("[[ENVID]]", envId);
        final String dbanme = patterns.getDb().replace("[[ENVID]]", envId);

        final String sql = sqlStatmentsConfig.getUserGrantMax()
                .replace("[[DBNAME]]", dbanme)
                .replace("[[USER]]", username)
                .replace("[[HOST]]", host);

        jdbcTemplate.execute(sql);
        return true;
    }

    @Override
    public boolean grantMin(String envId, String host) {
        final String username = patterns.getUser().replace("[[ENVID]]", envId);
        final String dbanme = patterns.getDb().replace("[[ENVID]]", envId);

        final String sql = sqlStatmentsConfig.getUserGrantMin()
                .replace("[[DBNAME]]", dbanme)
                .replace("[[USER]]", username)
                .replace("[[HOST]]", host);

        jdbcTemplate.execute(sql);
        return true;
    }

    @Override
    public boolean drop(String envId, String host) {
        final String username = patterns.getUser()
                .replace("[[ENVID]]", envId);

        final String sql = sqlStatmentsConfig.getDropUser()
                .replace("[[USER]]", username)
                .replace("[[HOST]]",host);

        jdbcTemplate.execute(sql);
        return true;
    }

    @Override
    public boolean dropAll() {
        return false;
    }
}
