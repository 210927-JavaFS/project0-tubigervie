package com.revature.services;

import java.util.ArrayList;

import com.revature.daos.StandardUserDAO;
import com.revature.models.User;

public class ModerationService 
{
	private static StandardUserDAO userDAO = new StandardUserDAO();
	
	public User findUserByName(String username)
	{
		User user = userDAO.findUser(username);
		return user;
	}
	
	public ArrayList<User> findUsersByType(User.AccountType accType)
	{
		return userDAO.findUsers(accType);
	}
}
