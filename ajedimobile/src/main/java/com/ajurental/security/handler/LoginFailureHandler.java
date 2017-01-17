package com.ajurental.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.ajurental.common.RequestUtil;
import com.ajurental.common.AJURentalConstant;
import com.ajurental.security.RestResultData;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		if(  RequestUtil.isJson(request) ) {
			String message = exception.getMessage();

			response.setHeader("Content-Type", "application/json; charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(new RestResultData(message, AJURentalConstant.HTTP_STATUS_AUTHENTICATIONFAILURE));
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
       
        }else{
        	super.onAuthenticationFailure(request, response, exception);        	
       }
	}
}