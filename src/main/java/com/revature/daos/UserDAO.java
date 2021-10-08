package com.revature.daos;

import java.util.ArrayList;

import com.revature.models.User;

public interface UserDAO {
	
	public ArrayList<User> getAll();
	
	public User findUser(int id);
	
	public boolean addUser(User user);
}
