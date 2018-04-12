package com.everygamer.logger;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Log
@Order(-99)
public class SystemLogAspect {
    /**
     * 层切点
     */
    @Pointcut("execution(* com.everygamer.control..*(..)) && @annotation(com.everygamer.logger.SystemLog)")
    public void serviceAspect() {

    }

    @After("serviceAspect()")
    public void doServiceLog(JoinPoint joinPoint) {
        log.info("日志I记录");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        System.out.println(request.getSession());
    }
}
