package com.myf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Filter;
import java.security.Principal;

/**
 * @author myf
 * **/
@SpringBootApplication
//@EnableOAuth2Sso
@EnableOAuth2Client
//@RestController
@Controller
public class Client2Application extends WebSecurityConfigurerAdapter{

	@Autowired
	private OAuth2ClientContext oauth2ClientContext;

	public static void main(String[] args) {
		SpringApplication.run(Client2Application.class, args);
	}

	@RequestMapping("/")
	public String index(Model model){
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
			return "index";
		}else {
			model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			return "welcome";
		}
	}

	@RequestMapping("/user")
	@ResponseBody
	public Principal user(Principal user){
		return user;
	}

	@RequestMapping("welcome")
	public String welcome(Model model){
		model.addAttribute("user",SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return "welcome";
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.antMatcher("/**")
				.authorizeRequests()
					.antMatchers("/").permitAll()
					.anyRequest().authenticated()
			.and()
				.exceptionHandling()
					.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
			.and()
				.logout()
					.logoutSuccessUrl("/").permitAll()
			.and()
			.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
			;
	}

	private Filter ssoFilter(){
		OAuth2ClientAuthenticationProcessingFilter filter =
				new OAuth2ClientAuthenticationProcessingFilter("/login/github");
		OAuth2RestTemplate template = new OAuth2RestTemplate(github(),oauth2ClientContext);
		filter.setRestTemplate(template);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(githubResource().getUserInfoUri(),github().getClientId());
		tokenServices.setRestTemplate(template);
		filter.setTokenServices(tokenServices);
		return filter;
	}

	//激活自动跳转sso服务端
//	@Bean
//	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter){
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setFilter(filter);
//		registration.setOrder(-100);
//		return registration;
//	}

	@Bean
	@ConfigurationProperties("security.oauth2.client")
	public AuthorizationCodeResourceDetails github(){
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("security.oauth2.resource")
	public ResourceServerProperties githubResource(){
		return new ResourceServerProperties();
	}
}
