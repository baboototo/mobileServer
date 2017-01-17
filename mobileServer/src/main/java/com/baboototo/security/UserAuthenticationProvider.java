package com.baboototo.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.baboototo.common.AuthenticationToken;
import com.baboototo.security.service.UserDetailsServiceImpl;

public class UserAuthenticationProvider implements AuthenticationProvider {
	private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationProvider.class);

	private PasswordEncoder passwordEncoder;
	private UserDetailsServiceImpl userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		logger.debug("==============> UserAuthenticationProvider");
		logger.debug("username: " + username);
		logger.debug("password: " + password);
		logger.debug("password: " + userDetails.getPassword());
		logger.debug("encoding: " + passwordEncoder.encode(password));

//		if ( !passwordEncoder.matches(password, userDetails.getPassword()) ) {
//			logger.debug("==============> 비밀번호가 일치하지 않습니다.");
//			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
//		}
		
		if(!password.equals(userDetails.getPassword())){
			logger.debug("==============> 비밀번호가 일치하지 않습니다.");
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		}

		String accessToken = AuthenticationToken.create(userDetails);

		logger.debug("===============> Access Token Create: " + accessToken);

		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		User user = new User(username, passwordEncoder.encode(password), accessToken, authorities);

		return new UsernamePasswordAuthenticationToken(user, user.getPassword(), authorities);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public UserDetailsServiceImpl getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	
}