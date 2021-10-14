package com.revature.services;

import java.util.ArrayList;

import com.revature.daos.LoginDAO;
import com.revature.daos.StandardUserDAO;
import com.revature.models.User;

public class ModerationService 
{
	private static StandardUserDAO userDAO = new StandardUserDAO();
	private static LoginDAO loginDAO = new LoginDAO();
	
	public User findUserByName(String username)
	{
		User user = userDAO.findUser(username);
		return user;
	}
	
	public ArrayList<User> findUsersByType(User.AccountType accType)
	{
		return userDAO.findUsers(accType);
	}
	
	public boolean deleteUser(int id)
	{
		return loginDAO.deleteLogin(id);
	}
}
