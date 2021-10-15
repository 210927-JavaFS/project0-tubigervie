package com.revature.services;

import com.revature.daos.LoginDAO;
import com.revature.models.StandardUser;
import com.revature.models.User;

public class LoginService {
	
	private static LoginDAO loginDAO = new LoginDAO();
	private StandardUserService userService = new StandardUserService();
	
	public User retrieveExistingLogin(String username, String password) //tested
	{
		User user =  loginDAO.findExistingLogin(username, password);
		if(user != null && user.hasAnInventory())
			userService.loadInventory((StandardUser)user);
		return user;
	}
	
	public boolean loginExists(String username) //tested
	{
		return loginDAO.checkIfExists(username);
	}
	
	public void uploadNewLogin(User user) //tested
	{
		loginDAO.addNewLogin(user);
	}
	
	public boolean deleteLogin(int id)
	{
		return loginDAO.deleteLogin(id);
	}
}
