package com.revature.services;

import com.revature.models.User;

public abstract class UserService {
	//private static userDAO = new UserDAO()
	
	public abstract User createNewUser(String username, String password);
}
