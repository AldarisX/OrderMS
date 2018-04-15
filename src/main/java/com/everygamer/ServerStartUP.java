package com.everygamer;

import com.everygamer.service.RSAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServerStartUP implements ApplicationRunner {
    @Autowired
    RSAService rsaService;

    @Override
    public void run(ApplicationArguments args) {
        try {
            log.info("开始清理RSA表");
            int rsaCount = rsaService.getCount();
            int delRSACount = rsaCount / 2;
            log.info("清理了" + rsaService.deleteRSA(delRSACount) + "条RSA记录");
            log.info("开始生成RSA");
            for (int i = 0; i < 20; i++) {
                rsaService.addRSA();
            }
            log.info("RSA生成完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
