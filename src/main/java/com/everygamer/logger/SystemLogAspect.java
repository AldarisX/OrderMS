package com.everygamer.logger;

import com.everygamer.util.SpringSecurityUtil;
import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@Log
public class SystemLogAspect {
    /**
     * 层切点
     */
    @Pointcut("@annotation(com.everygamer.logger.SystemLog)")
    public void serviceAspect() {
    }

    @Before("serviceAspect()")
    public void doBeforeLog(JoinPoint joinPoint) {
        DBLog dbLog = getLogInfo(joinPoint);
        if (dbLog.getOpType() == SystemLogOpType.Before) {
            String user = getUserName();
            dbLog.setUser(user);
            log.info(dbLog.toString());
        }
    }

    @AfterReturning("serviceAspect()")
    public void doAfterReturningLog(JoinPoint joinPoint) {
        DBLog dbLog = getLogInfo(joinPoint);
        if (dbLog.getOpType() == SystemLogOpType.AfterReturning) {
            String user = getUserName();
            dbLog.setUser(user);
            log.info(dbLog.toString());
        }
    }

    @AfterThrowing(throwing = "ex", pointcut = "serviceAspect()")
    public void doAfterThrowingLog(Throwable ex) {
        String user = getUserName();
        DBLog dbLog = new DBLog();
        dbLog.setOpType(SystemLogOpType.AfterThrowing);
        dbLog.setLevel(DBLogLevel.Error);
        dbLog.setUser(user);
        dbLog.setMsg(ex.getMessage());
    }

    private DBLog getLogInfo(JoinPoint joinPoint) {
        String desc;
        SystemLogOpType opType;
        DBLogLevel logLevel;

        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = joinPoint.getTarget().getClass();

        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs != null && clazzs.length == arguments.length && method.getAnnotation(SystemLog.class) != null) {
                    opType = method.getAnnotation(SystemLog.class).opType();
                    desc = method.getAnnotation(SystemLog.class).description();
                    logLevel = method.getAnnotation(SystemLog.class).logLevel();

                    DBLog dbLog = new DBLog();
                    dbLog.setMsg(desc);
                    dbLog.setLevel(logLevel);
                    dbLog.setOpType(opType);
                    return dbLog;
                }
            }
        }
        return null;
    }

    private String getUserName() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user = SpringSecurityUtil.currentUser(request.getSession());
        return user;
    }
}
