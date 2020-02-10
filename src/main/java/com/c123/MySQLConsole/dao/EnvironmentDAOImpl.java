package com.c123.MySQLConsole.dao;

import com.c123.MySQLConsole.config.NamingPatternConfiguration;
import com.c123.MySQLConsole.config.SQLStatmentsConfig;
import com.c123.MySQLConsole.entity.Environment;
import com.c123.MySQLConsole.entity.Status;
import com.c123.MySQLConsole.entity.User;
import com.c123.MySQLConsole.rowmapper.EnvironmentRowMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    @Autowired
    Logger logger;

    @Override
    public List<Environment> getAll() {
        return jdbcTemplate.query(sqlStatmentsConfig.getGetAllEnvs(), new EnvironmentRowMapper());
    }

    @Override
    public Environment getOne(String envId) throws DataAccessException {
        return jdbcTemplate.queryForObject(sqlStatmentsConfig.getGetEnv(), new Object[] {envId}, new EnvironmentRowMapper());
    }

    @Override
    public Environment create(String envId, String host, String password) {
        final Timestamp creationTime = new Timestamp(new Date().getTime());
        final String dbname = patterns.getDb().replace("[[ENVID]]", envId);
        final String sql = sqlStatmentsConfig.getCreateDB().replace("[[DBNAME]]", dbname);
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
        jdbcTemplate.update(sqlStatmentsConfig.getUpdateHost(), host, envId);
        return true;
    }

    @Override
    public boolean updateStatus(String envId, Status status) {
        final String sql = sqlStatmentsConfig.getUpdateStatus();
        jdbcTemplate.update(sql, status.getDesc(), envId);
        return true;
    }

    @Override
    public boolean updatePassword(String envId, String password) {
        jdbcTemplate.update(sqlStatmentsConfig.getUpdatePassword(), password, envId);
        return true;
    }

    @Override
    public boolean drop(String envId) {
        try{
            final Environment env = jdbcTemplate.queryForObject(
                    sqlStatmentsConfig.getGetEnv(),
                    new Object[] {envId},
                    new EnvironmentRowMapper()
            );

            final String dropDb = sqlStatmentsConfig.getDropDB()
                    .replace("[[DBNAME]]", env.getDbName());

            userDAO.drop(envId, env.getHostIp());
            jdbcTemplate.execute(dropDb);
            jdbcTemplate.update(sqlStatmentsConfig.getUpdateStatus(), Status.DROPPED.getDesc(), envId);
            return true;
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean dropAll() {
        jdbcTemplate.query(sqlStatmentsConfig.getGetAllEnvs(), new Object[] {}, new EnvironmentRowMapper())
        .forEach(env -> {
            drop(env.getId());
        });

        truncate();
        return true;
    }

    private void truncate() {
        jdbcTemplate.execute(sqlStatmentsConfig.getTruncate());
    }
}
