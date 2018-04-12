package com.everygamer.site;

import com.everygamer.logger.SystemLog;
import com.everygamer.site.expection.SiteLoginExpection;
import com.everygamer.util.RSAUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.PrivateKey;

public class SiteLoginFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    @SystemLog(description = "尝试登陆")
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String inCode = request.getParameter("vcode");
        String serCode = (String) request.getSession().getAttribute("vCode");
        if (!serCode.toLowerCase().equals(inCode.toLowerCase())) {
            throw new SiteLoginExpection("验证码错误");
        }
        return super.attemptAuthentication(request, response);
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        HttpSession session = request.getSession();
        PrivateKey priKey = (PrivateKey) session.getAttribute("priKey");
        return RSAUtils.decryptBase64(priKey, request.getParameter("password"));
    }
}
