package com.myf.security.config;

import com.myf.security.CustomAccessDecisionManager;
import com.myf.security.CustomFilterSecurityInterceptor;
import com.myf.security.CustomSecurityMetadataSource;
import com.myf.security.LoginSuccessHandler;
import com.myf.security.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

/**
 * Created by myf on 2017/10/20.
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    private DataSource dataSource;
    private CustomUserDetailService userDetailService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Autowired
    public void setUserDetailService(CustomUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/public/login")
                .permitAll()
                .successHandler(loginSuccessHandler())
                .defaultSuccessUrl("/security/index",false)
            .and()
                .authorizeRequests()
                    .antMatchers("/images/**","/checkcode","/script/**","/style/**","/public/**").permitAll()
                    .antMatchers(securitySettings().getPermitall().split(",")).permitAll()
                    //.antMatchers("/admin/**").hasRole("ADMIN")
                    //.antMatchers("/manager/**").hasAuthority("ROLE_MANAGER")
                    .anyRequest().authenticated()
            .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and()
                .logout()
                    .logoutSuccessUrl(securitySettings().getLogoutsuccessurl())
            .and()
                .exceptionHandling()
                    .accessDeniedPage(securitySettings().getDeniedpage())
            .and()
                .rememberMe()
                    .tokenValiditySeconds(1209600)
                    .tokenRepository(tokenRepository());
        http.addFilterAfter(filterSecurityInterceptor(),FilterSecurityInterceptor.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false);
    }

    @Bean
    public CustomFilterSecurityInterceptor filterSecurityInterceptor(){
        CustomFilterSecurityInterceptor interceptor = new CustomFilterSecurityInterceptor();
        interceptor.setAuthenticationManager(authenticationManager);
        interceptor.setSecurityMetadataSource(securityMetadataSource());
        interceptor.setAccessDecisionManager(accessDecisionManager());
        return interceptor;
    }

    @Bean
    public AccessDecisionManager accessDecisionManager(){
        return new CustomAccessDecisionManager();
    }

    @Bean
    public FilterInvocationSecurityMetadataSource securityMetadataSource(){
        return new CustomSecurityMetadataSource(securitySettings().getUserroles());
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }

    @Bean
    public JdbcTokenRepositoryImpl  tokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    public Md5PasswordEncoder passwordEncoder(){
        return new Md5PasswordEncoder();
    }

    @Bean
    public SecuritySettings securitySettings(){
        return new SecuritySettings();
    }
}
