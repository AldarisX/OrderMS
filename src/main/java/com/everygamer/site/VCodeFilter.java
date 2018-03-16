package com.everygamer.site;

import com.everygamer.site.expection.VCodeExpection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VCodeFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String inCode = request.getParameter("vcode");
        String serCode = (String) request.getSession().getAttribute("vCode");
        if (!serCode.toLowerCase().equals(inCode.toLowerCase())) {
            throw new VCodeExpection("验证码错误");
        }
        return super.attemptAuthentication(request, response);
    }
}
