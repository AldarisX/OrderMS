package com.everygamer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.session.SessionRepository;
import org.springframework.session.jdbc.JdbcOperationsSessionRepository;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
@EnableJdbcHttpSession
@ServletComponentScan
@MapperScan(basePackages = {"com.everygamer.dao"})
public class OrderMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderMsApplication.class, args);
    }

    //将session保存到数据库
    @Bean
    public SessionRepository<?> sessionRepository(JdbcOperations jdbcOperations, PlatformTransactionManager transactionManager) {
        JdbcOperationsSessionRepository sessionRepository = new JdbcOperationsSessionRepository(jdbcOperations, transactionManager);
        sessionRepository.setDefaultMaxInactiveInterval(1800);
        return sessionRepository;
    }
}
