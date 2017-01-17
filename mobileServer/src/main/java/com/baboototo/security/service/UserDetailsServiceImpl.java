package com.baboototo.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.baboototo.user.service.UserService;

import kr.co.stc.core.util.StringUtil;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(final String userCode) throws UsernameNotFoundException {
		Map<String, Object> userInfoDataMap = null;
		
		try {
			userInfoDataMap =  this.userService.selectUserInfo(userCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(userInfoDataMap == null || !userInfoDataMap.containsKey("PASS_WORD")){
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		
		logger.debug("==============> UserDetailsService");
		logger.debug("username: " + userCode);
		logger.debug("password: " + userInfoDataMap.get("PASS_WORD"));
		
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority("ROLE_USER"));

		User user = new User(userCode, StringUtil.null2Blank(userInfoDataMap.get("PASS_WORD")), roles);
		return user;
	}
}