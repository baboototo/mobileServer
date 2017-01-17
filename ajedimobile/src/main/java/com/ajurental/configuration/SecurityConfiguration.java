package com.ajurental.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ajurental.security.UserAuthenticationProvider;
import com.ajurental.security.filter.AuthenticationTokenFilter;
import com.ajurental.security.handler.AccessFailureHandler;
import com.ajurental.security.handler.AuthenticationEntryPointHandler;
import com.ajurental.security.handler.LoginFailureHandler;
import com.ajurental.security.handler.LoginSuccessHandler;
import com.ajurental.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailService;
	
	@Autowired
	private AuthenticationEntryPointHandler authenticationEntryPointHandler;
	
	@Autowired
	private AccessFailureHandler accessDeniedHandler;
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	private LoginFailureHandler loginFailureHandler;
	
	@Autowired
	private AuthenticationTokenFilter tokenFilter;
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public UserAuthenticationProvider authenticationProvider(){
		UserAuthenticationProvider userAuthenticationProvider = new UserAuthenticationProvider();
		userAuthenticationProvider.setUserDetailsService(userDetailService);
		userAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return userAuthenticationProvider;
    }
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
		
	}

	
	/* 
	 * HttpSecurity 설정
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  
		http.csrf().disable();			// CSRF 공격을 방비해 준다.
		http.httpBasic();				// 제한된 영역에 접근할 경우 로그인 화면을 오픈하도록 설정
		http.rememberMe();
		http.authorizeRequests()
			  	.antMatchers("/ng-app/**").hasAuthority("ROLE_USER")
			  	.antMatchers("/any/**").permitAll()
				.antMatchers("/api/**").hasAuthority("ROLE_USER")
			  	.antMatchers("/**").hasAuthority("ROLE_USER")
				.anyRequest().fullyAuthenticated();
	  
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPointHandler);
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		
		this.configureLoginForm(http);
		this.configureLogOut(http);
		this.configureAddFilter(http);
	}
	
	/**
	 * HttpSecurity LoginForm 설정
	 * @param http
	 * @throws Exception
	 */
	private void configureLoginForm(HttpSecurity http) throws Exception {
		http.formLogin()
			.defaultSuccessUrl("/", true)
			.loginPage("/login")
			.failureUrl("/login?error")
			.successHandler(loginSuccessHandler)
	        .failureHandler(loginFailureHandler)
			.permitAll();
	}
	
	/**
	 * HttpSecurity Logout 설정
	 * @param http
	 * @throws Exception
	 */
	private void configureLogOut(HttpSecurity http) throws Exception {
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).deleteCookies("remember-me").permitAll();
	}
	
	/**
	 * HttpSecurity Filter 설정
	 * @param http
	 * @throws Exception
	 */
	private void configureAddFilter(HttpSecurity http) throws Exception {
		http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
