package com.ajurental.springmvc.main.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajurental.springmvc.main.model.User2;

@Service("userServiceRestTest")
@Transactional
public class UserServiceRestTestImpl implements UserServiceRestTest{
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<User2> users;
	
	static{
		users= populateDummyUsers();
	}

	public List<User2> findAllUsers() {
		return users;
	}
	
	public User2 findById(long id) {
		for(User2 user : users){
			if(user.getId() == id){
				return user;
			}
		}
		return null;
	}
	
	public User2 findByName(String name) {
		for(User2 user : users){
			if(user.getName().equalsIgnoreCase(name)){
				return user;
			}
		}
		return null;
	}
	
	public void saveUser(User2 user) {
		user.setId(counter.incrementAndGet());
		users.add(user);
	}

	public void updateUser(User2 user) {
		int index = users.indexOf(user);
		users.set(index, user);
	}

	public void deleteUserById(long id) {
		
		for (Iterator<User2> iterator = users.iterator(); iterator.hasNext(); ) {
		    User2 user = iterator.next();
		    if (user.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isUserExist(User2 user) {
		return findByName(user.getName())!=null;
	}

	private static List<User2> populateDummyUsers(){
		List<User2> users = new ArrayList<User2>();
		users.add(new User2(counter.incrementAndGet(),"Sam",30, 70000));
		users.add(new User2(counter.incrementAndGet(),"Tom",40, 50000));
		users.add(new User2(counter.incrementAndGet(),"Jerome",45, 30000));
		users.add(new User2(counter.incrementAndGet(),"Silvia",50, 40000));
		return users;
	}

	public void deleteAllUsers() {
		users.clear();
	}

}
