package com.everygamer.init;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class DBConfig {
    private static DBConfig config;

    @Getter
    private String dbDriver;
    @Getter
    private String dbUrl;
    @Getter
    private String dbUser;
    @Getter
    private String dbPasswd;
    @Getter
    private String dbVer;

    private DBConfig() {
    }


    public static DBConfig getInstance() {
        if (config == null) {
            config = new DBConfig();
            config.loadConfig();
        }
        return config;
    }

    private void loadConfig() {
        try {
            //加载SpringBoot配置文件
            Properties prop = new Properties();
            prop.load(new FileInputStream("config/application-db.properties"));
            dbDriver = prop.getProperty("spring.datasource.driver-class-name");
            dbUrl = prop.getProperty("spring.datasource.url");
            dbUser = prop.getProperty("spring.datasource.username");
            dbPasswd = prop.getProperty("spring.datasource.password");
            prop.load(new FileInputStream("config/application-system.properties"));
            dbVer = prop.getProperty("site.config.db-ver");

        } catch (IOException e) {
            log.error("配置文件不存在");
        }
    }

    public Connection getConn() throws SQLException {
        return DriverManager.getConnection(getDbUrl(), getDbUser(), getDbPasswd());
    }

    public Connection getConn(String url) throws SQLException {
        return DriverManager.getConnection(url, getDbUser(), getDbPasswd());
    }
}
