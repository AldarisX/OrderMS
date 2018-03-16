package com.everygamer.util;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpSession;

public class SpringSecurityUtil {
    //session 由controller 注入参数传入
    public static String currentUser(HttpSession session) {
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        return ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
    }
}
