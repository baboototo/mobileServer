package com.ajurental.security.filter;

import java.io.IOException;
import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.ajurental.common.AuthenticationToken;
import com.ajurental.common.AJURentalConstant;
import com.ajurental.security.User;

@Component
public class AuthenticationTokenFilter extends GenericFilterBean {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

	@Resource(name = "userDetailsServiceImpl")
	private UserDetailsService userService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = this.getAsHttpRequest(request);

		String uriStr = httpRequest.getRequestURI();

		if (uriStr.startsWith("/api/")) {
			logger.debug("*************************** FILTER ***************************");

			String authToken = this.extractAuthTokenFromRequest(httpRequest);
			String username = AuthenticationToken.getUserName(authToken);

			logger.debug(">>>>>>>>>>>>>>>> FILTER 1 : " + authToken);
			
			if (username != null) {
				UserDetails userDetails = userService.loadUserByUsername(username);

				logger.debug(">>>>>>>>>>>>>>>> FILTER 2");
				if (!AuthenticationToken.validate(authToken, userDetails)) {
					logger.debug(">>>>>>>>>>>>>>>> FILTER 3");
					SecurityContextHolder.clearContext();
				} else {
					logger.debug(">>>>>>>>>>>>>>>> FILTER 4");
					String accessToken = AuthenticationToken.create(userDetails);

					Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
					User user = new User(username, userDetails.getPassword(), accessToken, authorities);

					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), authorities);
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
		chain.doFilter(request, response);
	}


	private HttpServletRequest getAsHttpRequest(ServletRequest request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting an HTTP request");
		}

		return (HttpServletRequest) request;
	}


	private String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
		String authToken = httpRequest.getHeader(AJURentalConstant.HEADER_TOKEN_KEY);
		return authToken;
	}
}
