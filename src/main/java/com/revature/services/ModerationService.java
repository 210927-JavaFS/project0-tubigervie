package com.revature.services;

import java.util.ArrayList;

import com.revature.daos.LoginDAO;
import com.revature.daos.StandardUserDAO;
import com.revature.models.AdminUser;
import com.revature.models.ModeratorUser;
import com.revature.models.StandardUser;
import com.revature.models.User;
import com.revature.models.User.AccountType;

public class ModerationService 
{
	private static StandardUserDAO userDAO = new StandardUserDAO();
	private static LoginDAO loginDAO = new LoginDAO();
	
	public User findUserByName(String username) //tested
	{
		User user = userDAO.findUser(username);
		return user;
	}
	
	public ArrayList<User> findUsersByType(User.AccountType accType) //tested
	{
		return userDAO.findUsers(accType);
	}
	
	public boolean deleteUser(int id)
	{
		userDAO.deleteStandardUser(id);
		return loginDAO.deleteLogin(id);
	}
	
	public User createNewUser(String username, String password, AccountType accType) { //tested
		switch(accType)
		{
			case admin:
				return new AdminUser(username, password, AccountType.admin);
			case moderator:
				return new ModeratorUser(username, password, AccountType.moderator);
			case standard:
				return new StandardUser(username, password, AccountType.standard);
			default:
				return new StandardUser(username, password, AccountType.standard);
		}
	}
}
