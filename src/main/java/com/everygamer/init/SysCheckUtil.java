package com.everygamer.init;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SysCheckUtil {
    public void init() {
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
            log.info("退出程序");
            System.exit(0);
        }
    }
}
