CREATE DATABASE IF NOT EXISTS environments;

CREATE TABLE IF NOT EXISTS environments.mts(
  id VARCHAR(100),
  env_mysql_user VARCHAR(100),
  env_mysql_db VARCHAR(100),
  create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  status ENUM('env_created','env_secured','env_dropped', "env_extended"),
  host_ip VARCHAR(100),
  password VARCHAR(1024),
  PRIMARY KEY(id)
);