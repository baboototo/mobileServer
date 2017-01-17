package com.baboototo.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.baboototo.common.Constant;
import com.baboototo.common.RequestUtil;
import com.baboototo.security.RestResultData;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginOutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if(RequestUtil.isJson(request)) {
			response.setHeader("Content-Type", "application/json; charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_OK);

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(new RestResultData("로그아웃되었습니다.", Constant.HTTP_STATUS_OK));
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}