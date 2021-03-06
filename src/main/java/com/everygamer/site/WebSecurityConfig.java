package com.everygamer.site;

import com.everygamer.service.security.AdminUserService;
import com.everygamer.util.SpringSecurityUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new MessageDigestPasswordEncoder("MD5");
        return encoder;
    }

    @Bean
    UserDetailsService customUserService() {
        return new AdminUserService();
    }

    @Bean
    public LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
        LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new LoginUrlAuthenticationEntryPoint("/login/getLogin");
        //使用forward显示登陆页面
        loginUrlAuthenticationEntryPoint.setUseForward(true);
        return loginUrlAuthenticationEntryPoint;
    }

    @Bean
    SiteLoginFilter loginFilter() throws Exception {
        SiteLoginFilter filter = new SiteLoginFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setFilterProcessesUrl("/login/login.do");
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                JSONObject result = new JSONObject();
                result.accumulate("result", true);
                result.accumulate("url", "/index.html");
                response.getWriter().print(result.toString());
            }
        });
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                JSONObject result = new JSONObject();
                result.accumulate("result", false);
                result.accumulate("msg", exception.getMessage());
                response.getWriter().print(result.toString());
            }
        });
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().maximumSessions(1);
        //允许iFrame
        http.headers().cacheControl().disable().frameOptions().disable();
        //允许csrf
        http.csrf().ignoringAntMatchers("/static/**", "/api/**", "/login/login.do");
        //开始配置各种权限
        http.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(loginUrlAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/login/**", "/logout", "/error/**", "/favicon.ico").permitAll()
                .antMatchers("/css/**", "/js/**", "/extend/**").permitAll()
                .antMatchers("/api/user/rsa").permitAll()
                .antMatchers("/swagger-ui.html", "/webjars/springfox-swagger-ui/**", "/swagger-resources/**", "/v2/api-docs").access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
                .logout().permitAll()
                .and()
                .logout().clearAuthentication(true)
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                //成功登出
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
                        httpServletResponse.setCharacterEncoding("UTF-8");
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        JSONObject result = new JSONObject();
                        result.accumulate("result", true);
                        result.accumulate("url", "/?logout");
                        httpServletResponse.getWriter().print(result.toString());
                    }
                })
                .invalidateHttpSession(true);
        //权限被拒绝时记日志
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
                logger.warn("Access denied !!! Page:" + request.getRequestURI() + " User:" + SpringSecurityUtil.currentUser(request.getSession()) + " IP:" + request.getRemoteAddr());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        });
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(passwordEncoder());
    }
}
