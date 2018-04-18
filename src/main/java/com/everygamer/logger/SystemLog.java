package com.everygamer.logger;

import java.lang.annotation.*;

/**
 * 系统日志注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    /**
     * 日志描述
     */
    String description() default "";

    /**
     * 操作表类型
     */
    int tableType() default 0;
}
