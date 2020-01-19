package com.c123.MySQLConsole.rowmapper;

import com.c123.MySQLConsole.entity.Environment;
import com.c123.MySQLConsole.entity.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnvironmentRowMapper implements RowMapper<Environment> {

    @Override
    public Environment mapRow(ResultSet rs, int i) throws SQLException {
        return new Environment(
                rs.getString("id"),
                rs.getString("env_mysql_user"),
                rs.getString("password"),
                rs.getString("env_mysql_db"),
                rs.getTimestamp("create_date"),
                Status.stringToStatus(rs.getString("status")),
                rs.getString("host_ip")
        );
    }
}
