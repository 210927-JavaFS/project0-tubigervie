package com.revature.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import com.revature.models.StandardUser;
import com.revature.models.User;
import com.revature.services.LoginService;
import com.revature.services.StandardUserService;
import com.revature.utils.CryptoUtils;

public class LoginMenuController {
	private boolean applicationIsRunning = true;
	private static Scanner scan = new Scanner(System.in);
	private StandardUserService userService = new StandardUserService();
	private LoginService loginService = new LoginService();
	
	public boolean getApplicationStatus() {
		return applicationIsRunning;
	}
	
	public User getUser()
	{
		System.out.println("Are you logging in or creating a new account? Type login/register/quit");
		String response = (scan.nextLine()).trim();
		switch(response) {
			case "login":
				System.out.println("Enter your username: ");
				String username = scan.nextLine().toLowerCase();
				System.out.println("Enter your password: ");
				String password = scan.nextLine();
				System.out.println("Now logging in...\n");
				try 
				{
					byte[] sha = CryptoUtils.getSHA(password);
					password = CryptoUtils.Encrypt(sha);
				} catch (NoSuchAlgorithmException e) {
					System.out.println("Could not encrypt password");
					e.printStackTrace();
				}
					
				User user = loginService.retrieveExistingLogin(username, password);
				if(user == null) {
					System.out.println("creating new account");
					user = userService.createNewUser(username, password);
					loginService.uploadNewLogin(user);
					//userService.registerAccountInfo(user);
				}
				else {
					userService.loadInventory((StandardUser)user);	
				}
				return user;
			case "register":
				return null;
			case "quit":
				System.out.println("Thank you for using the application. Hope to see you again!");
				applicationIsRunning = false;
				return null;
			default:
				System.out.println("Invalid output. Try again.\n");
				return null;
		}
	}
}
