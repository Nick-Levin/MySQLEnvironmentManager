package com.c123.MySQLConsole.dao;

import com.c123.MySQLConsole.config.EnvironmentConfiguration;
import com.c123.MySQLConsole.config.NamingPatternConfiguration;
import com.c123.MySQLConsole.config.SQLStatmentsConfig;
import com.c123.MySQLConsole.entity.Environment;
import com.c123.MySQLConsole.entity.Status;
import com.c123.MySQLConsole.entity.User;
import com.c123.MySQLConsole.rowmapper.EnvironmentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class EnvironmentDAOImpl implements EnvironmentDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SQLStatmentsConfig sqlStatmentsConfig;

    @Autowired
    NamingPatternConfiguration patterns;

    @Autowired
    UserDAO userDAO;

    @Override
    public List<Environment> getAll() {
        return jdbcTemplate.query(sqlStatmentsConfig.getGetAllEnvs(), new EnvironmentRowMapper());
    }

    @Override
    public Environment getOne(String envId) {
        return jdbcTemplate.queryForObject(sqlStatmentsConfig.getGetEnv(), new Object[] {envId}, new EnvironmentRowMapper());
    }

    @Override
    public Environment create(String envId, String host, String password) {
        final Timestamp creationTime = new Timestamp(new Date().getTime());
        final String dbname = patterns.getDb().replace("{{ENVID}}", envId);
        final String sql = sqlStatmentsConfig.getCreateDB().replace("{{DBNAME}}", dbname);
        jdbcTemplate.execute(sql);
        final User user = userDAO.create(envId, host, password);
        jdbcTemplate.update(sqlStatmentsConfig.getInsertEnv(),
                envId, user.getUsername(), dbname, creationTime, Status.CREATED.getDesc(), host, user.getPassword());
        return jdbcTemplate.queryForObject(sqlStatmentsConfig.getGetEnv(), new Object[] {envId}, new EnvironmentRowMapper());
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
        final Environment env = jdbcTemplate.queryForObject(
                sqlStatmentsConfig.getGetEnv(),
                new Object[] {envId},
                new EnvironmentRowMapper()
        );

        final String dropUser = sqlStatmentsConfig.getDropUser()
                .replace("{{USER}}", env.getUsername())
                .replace("{{HOST}}",env.getHostIp());

        final String dropDb = sqlStatmentsConfig.getDropDB()
                .replace("{{DBNAME}}", env.getDbName());

        final String deleteEnv = sqlStatmentsConfig.getDeleteEnv()
                .replace("{{ENVID}}",envId);

        jdbcTemplate.execute(dropUser);
        jdbcTemplate.execute(dropDb);
        jdbcTemplate.execute(deleteEnv);

        return true;
    }

    @Override
    public boolean dropAll() {
        jdbcTemplate.query(sqlStatmentsConfig.getGetAllEnvs(), new Object[] {}, new EnvironmentRowMapper())
        .forEach(env -> {
            drop(env.getId());
        });

        return true;
    }
}
