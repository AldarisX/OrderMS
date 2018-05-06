package com.everygamer.logger;

import java.lang.annotation.*;

/**
 * 系统日志注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    /**
     * 日志描述
     */
    String description() default "";

    /**
     * 处理位置
     */
    SystemLogOpType opType() default SystemLogOpType.AfterReturning;

    /**
     * 日志级别
     */
    DBLogLevel logLevel() default DBLogLevel.Info;
}
