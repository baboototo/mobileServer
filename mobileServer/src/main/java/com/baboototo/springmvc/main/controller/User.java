package com.baboototo.springmvc.main.controller;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 사용자 정보 VO
 * @author baboototo
 *
 */
@SuppressWarnings("serial")
public class User implements Serializable{
	
	private int userCd;
	private String userId;
	private String pwd; 	
	
	public int getUserCd() {
		return userCd;
	}
	public void setUserCd(int userCd) {
		this.userCd = userCd;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * toString 메소드를 대치한다.
	 */
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}
