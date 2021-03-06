package com.baboototo.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.baboototo.common.Constant;
import com.baboototo.common.RequestUtil;
import com.baboototo.security.RestResultData;
import com.baboototo.security.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;	

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private AuthenticationService authenticationService;
	

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if(  RequestUtil.isJson(request) ) {
			response.setHeader("Content-Type", "application/json; charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_OK);
			response.setHeader(Constant.HEADER_TOKEN_KEY, authenticationService.getUser(authentication).getAccessToken());
			
			
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(new RestResultData("로그인되었습니다.", Constant.HTTP_LOGIN_OK));
			
			
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
       
        }else{
        	super.onAuthenticationSuccess(request, response, authentication);        	
       }
        
	}
}
