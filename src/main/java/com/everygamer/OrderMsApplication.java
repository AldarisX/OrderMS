package com.everygamer;

import com.everygamer.init.SysCheckUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.session.SessionRepository;
import org.springframework.session.jdbc.JdbcOperationsSessionRepository;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.transaction.PlatformTransactionManager;

@Log4j2
@SpringBootApplication
@EnableJdbcHttpSession
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackageClasses = OrderMsApplication.class)
@ServletComponentScan
public class OrderMsApplication {

    public static void main(String[] args) throws InterruptedException {
        //判断是否有参数
        if (args.length > 0) {
            new SysCheckUtil().init();
            SpringApplication.run(OrderMsApplication.class, args);
        } else {
            log.error("请随便输入参数，确保不是双击启动");
        }
    }

    //将session保存到数据库
    @Bean
    public SessionRepository<?> sessionRepository(JdbcOperations jdbcOperations, PlatformTransactionManager transactionManager) {
        JdbcOperationsSessionRepository sessionRepository = new JdbcOperationsSessionRepository(jdbcOperations, transactionManager);
        sessionRepository.setDefaultMaxInactiveInterval(1800);
        return sessionRepository;
    }
}
