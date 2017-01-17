package com.ajurental.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Controller
 *  - GET 	: 조회
 *  - POST 	: 등록
 *  - PUT 	: 수정
 *  - DELETE: 삭제
 * @author kjy
 *
 */
@RestController
@RequestMapping("api")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
}
