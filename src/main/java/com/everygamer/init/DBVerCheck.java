package com.everygamer.init;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

@Slf4j
public class DBVerCheck implements SysCheck {
    private DBConfig dbConfig = DBConfig.getInstance();

    private String dbVer;

    @Override
    public boolean check() {
        try {
            if (canUpdate()) {
                log.info("发现数据库更新,请立即备份数据库,输入YES进行更新,否则退出");
                Scanner sc = new Scanner(System.in);
                String uIn = sc.next();
                if (!uIn.equals("YES")) {
                    log.info("取消更新");
                    return false;
                }
                return updateDB();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean canUpdate() throws SQLException {
        @Cleanup
        Connection conn = dbConfig.getConn();
        @Cleanup
        Statement st = conn.createStatement();
        @Cleanup
        ResultSet rs = st.executeQuery("SELECT value FROM site_config WHERE name='dbVer'");
        rs.next();
        dbVer = rs.getString("value");

        return !dbVer.equals(dbConfig.getDbVer());
    }

    private boolean updateDB() throws SQLException {
        File[] sqlFiles = new File("update").listFiles();
        for (File sqlFile : sqlFiles) {
            if (sqlFile.getName().startsWith(dbVer)) {
                log.info(sqlFile.getName());
                runSQL(sqlFile);
                canUpdate();
                updateDB();
            }
        }
        return true;
    }

    private void runSQL(File sqlFile) throws SQLException {
        @Cleanup
        Connection conn = dbConfig.getConn();
        ScriptRunner runner = new ScriptRunner(conn);
        //自动提交
        runner.setAutoCommit(true);
        //执行所有代码,否则按行识别代码
        runner.setSendFullScript(true);
        runner.setStopOnError(true);

        try {
            runner.runScript(new InputStreamReader(new FileInputStream(sqlFile), "utf-8"));
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            if (e instanceof FileNotFoundException) {
                log.error("找不到数据库初始化文件(update/pgsql-init.sql)");
            } else {
                e.printStackTrace();
            }
        }
    }
}
