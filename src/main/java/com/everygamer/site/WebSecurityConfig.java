package com.everygamer.site;

import com.everygamer.service.security.AdminUserService;
import com.everygamer.site.expection.VCodeExpection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.security.auth.login.AccountExpiredException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
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
        return loginUrlAuthenticationEntryPoint;
    }

    @Bean
    VCodeFilter vCodeFilter() throws Exception {
        VCodeFilter filter = new VCodeFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setFilterProcessesUrl("/login/login.do");
        filter.setAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return filter;
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        //根据抛出的不同的异常跳转到不同的URL
        ExceptionMappingAuthenticationFailureHandler failureHandler = new ExceptionMappingAuthenticationFailureHandler();
        Map<String, String> failureUrlMap = new HashMap<>();
        failureUrlMap.put(BadCredentialsException.class.getName(), "/login/getLogin?error");
        failureUrlMap.put(VCodeExpection.class.getName(), "/login/getLogin?verror");
        failureUrlMap.put(AccountExpiredException.class.getName(), "/login/getLogin?error");
        failureUrlMap.put(LockedException.class.getName(), "/login/getLogin?error");
        failureUrlMap.put(DisabledException.class.getName(), "/login/getLogin?error");
        failureHandler.setExceptionMappings(failureUrlMap);
        return failureHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/api/*")
                .and()
                .addFilterBefore(vCodeFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(loginUrlAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/login/*", "/logout", "/error/*", "/favicon.ico").permitAll()
                .antMatchers("/admin").access("hasRole('ADMIN')")
                .anyRequest().authenticated()
                .and()
                .logout().permitAll()
                .and()
                .logout().clearAuthentication(true)
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login/getLogin?logout");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(passwordEncoder());
    }
}
