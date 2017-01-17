package com.ajurental.springmvc.main.service;

import java.util.List;

import com.ajurental.springmvc.main.model.User2;



public interface UserServiceRestTest {
	
	User2 findById(long id);
	
	User2 findByName(String name);
	
	void saveUser(User2 user);
	
	void updateUser(User2 user);
	
	void deleteUserById(long id);

	List<User2> findAllUsers(); 
	
	void deleteAllUsers();
	
	public boolean isUserExist(User2 user);
	
}
