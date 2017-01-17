package com.ajurental.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.ajurental.common.RequestUtil;
import com.ajurental.common.AJURentalConstant;
import com.ajurental.security.RestResultData;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 인증(로그인)은 되었으나 해당 요청에 대한 권한이 없을 경우 발생하는 핸들러
 * @author baboototo 2016. 6. 8.
 */
@Component
public class AccessFailureHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
		if(RequestUtil.isJson(request)) {
			response.setHeader("Content-Type", "application/json; charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(new RestResultData("접근할 수 있는 권한이 없습니다.", AJURentalConstant.HTTP_STATUS_ACCESSFAILURE));
			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
		}
	}
}