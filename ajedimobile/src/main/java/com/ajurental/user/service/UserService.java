package com.ajurental.user.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajurental.comm.dao.CommonBaseDAO;

/**
 * User Service
 * @author User
 *
 */
@Service
public class UserService {

	private Logger logger = LoggerFactory.getLogger(UserService	.class);
	
	@Autowired
	private CommonBaseDAO commonBaseDAO;
	
	public Map<String, Object> selectUserInfo(String userCode) throws Exception{
		return (Map<String, Object>)this.commonBaseDAO.selectByPk("UserDAO.selectUserInfo", userCode);
	}
}
