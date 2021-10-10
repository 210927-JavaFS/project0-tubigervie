package com.revature.services;

import com.revature.daos.LoginDAO;
import com.revature.models.User;

public class LoginService {
	
	private static LoginDAO loginDAO = new LoginDAO();
	
	
	public User retrieveExistingLogin(String username, String password) //move to login service
	{
		return loginDAO.findExistingLogin(username, password);
	}
	
	public void uploadNewLogin(User user)
	{
		loginDAO.addNewLogin(user);
	}
}
