package com.everygamer.init;

import com.everygamer.init.pgsql.DBCheck;
import com.everygamer.init.pgsql.DBVerCheck;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SysCheckUtil {
    public void init() throws InterruptedException {
        try {
            log.info("开始检查环境");
            log.info("开始检查数据库");
            SysCheck dbCheck = new DBCheck();
            boolean dbCheckFlag = dbCheck.check();

            log.info("开始检查数据库是否有更新");
            SysCheck dbVerCheck = new DBVerCheck();
            boolean dbVerChekcFlag = dbVerCheck.check();

            if (dbCheckFlag && dbVerChekcFlag) {
                log.info("检查完成,启动Spring");
            } else {
                exit();
            }
        } catch (Exception e) {
            //等异常刷完
            Thread.sleep(1000);
            log.error(e.getLocalizedMessage());
            exit();
        }
    }

    private void exit() {
        log.info("退出程序");
        System.exit(0);
    }
}
