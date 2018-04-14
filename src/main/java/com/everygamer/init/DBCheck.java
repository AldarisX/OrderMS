package com.everygamer.init;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class DBCheck implements SysCheck {
    private static DBConfig dbConfig = DBConfig.getInstance();

    static {
        try {
            Class.forName(dbConfig.getDbDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean check() {
        try {
            return checkDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean checkDB() throws SQLException {
        @Cleanup
        Connection conn = dbConfig.getConn(dbConfig.getDbUrl().substring(0, dbConfig.getDbUrl().lastIndexOf("/") + 1) + "postgres");
        @Cleanup
        Statement st = conn.createStatement();
        @Cleanup
        ResultSet rs = st.executeQuery("SELECT datname FROM pg_database;");
        boolean hasDB = false;
        while (rs.next()) {
            if (rs.getString("datname").equals("orderms")) {
                hasDB = true;
            }
        }
        if (hasDB) {
            log.info("发现数据库:orderms");
            return true;
        } else {
            log.info("数据库不存在,准备建立数据库");
            return createDB();
        }
    }

    private boolean createDB() throws SQLException {
        @Cleanup
        Connection conn = dbConfig.getConn(dbConfig.getDbUrl().substring(0, dbConfig.getDbUrl().lastIndexOf("/") + 1) + "postgres");
        @Cleanup
        Statement st = conn.createStatement();
        int cRows = st.executeUpdate("CREATE DATABASE orderms WITH ENCODING = 'UTF8' CONNECTION LIMIT = -1;");
        if (cRows == 0) {
            log.info("数据库创建完成,准备初始化数据表");
            return initTables();
        } else {
            log.error("数据库创建失败。修改数:" + cRows);
            return false;
        }
    }

    private boolean initTables() throws SQLException {
        @Cleanup
        Connection conn = dbConfig.getConn();
        ScriptRunner runner = new ScriptRunner(conn);
        //自动提交
        runner.setAutoCommit(true);
        //执行所有代码,否则按行识别代码
        runner.setSendFullScript(true);
        runner.setStopOnError(true);

        try {
            runner.runScript(new InputStreamReader(new FileInputStream("update/pgsql-init.sql"), "utf-8"));
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            if (e instanceof FileNotFoundException) {
                log.error("找不到数据库初始化文件(update/pgsql-init.sql)");
            } else {
                e.printStackTrace();
            }
            return false;
        }
        return setDbVer();
    }

    private boolean setDbVer() throws SQLException {
        @Cleanup
        Connection conn = dbConfig.getConn();
        @Cleanup
        Statement st = conn.createStatement();
        int cRows = st.executeUpdate("INSERT INTO site_config (name, value) VALUES ('dbVer','" + dbConfig.getDbVer() + "');");
        return true;
    }

}
