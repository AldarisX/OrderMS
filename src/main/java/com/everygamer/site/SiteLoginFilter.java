package com.everygamer.site;

import com.everygamer.logger.SystemLog;
import com.everygamer.service.RSAService;
import com.everygamer.site.expection.SiteLoginExpection;
import com.everygamer.util.RSAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.PrivateKey;

public class SiteLoginFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    RSAService rsaService;

    @Override
    @SystemLog(description = "尝试登陆")
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String inCode = request.getParameter("vcode");
        String serCode = (String) request.getSession().getAttribute("vCode");
        if (serCode == null || !serCode.toLowerCase().equals(inCode.toLowerCase())) {
            throw new SiteLoginExpection("验证码错误");
        }
        return super.attemptAuthentication(request, response);
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        HttpSession session = request.getSession();
        PrivateKey priKey = rsaService.getRSAById((Long) session.getAttribute("rsa")).getPriKey();
        return RSAUtils.decryptBase64(priKey, request.getParameter("password"));
    }
}
