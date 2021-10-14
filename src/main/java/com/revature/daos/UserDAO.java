package com.revature.daos;

import com.revature.models.User;

public interface UserDAO {
	
	//public ArrayList<User> getAll();
	
	public User findUser(int id);
	
	public boolean addUser(User user);
}
