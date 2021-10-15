package com.revature.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

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
		User user = null;
		System.out.println("LOGIN | REGISTER | QUIT");
		String response = (scan.nextLine()).trim();
		switch(response) {
			case "login":
				String username;
				String password;
				while(true) {
					System.out.println("Enter your username or type R to return: ");
					username = scan.nextLine().toLowerCase();
					if(username.equals("r")) return null;
					if(username.length() > 0) break;
					System.out.println("Invalid input try again.\n");
				}

				while(true)
				{
					System.out.println("Enter your password or type R to return: ");
					password = scan.nextLine();
					if(password.equalsIgnoreCase("r")) return null;
					if(password.length() > 2) break;
					System.out.println("Invalid input try again.\n");
				}
				try 
				{
					byte[] sha = CryptoUtils.getSHA(password);
					password = CryptoUtils.Encrypt(sha);
				} catch (NoSuchAlgorithmException e) {
					System.out.println("Could not encrypt password");
					e.printStackTrace();
				}
					
				user = loginService.retrieveExistingLogin(username, password);
				if(user == null) {
					System.out.println("\nInvalid username/password combination.");
				}
				else {
					System.out.println("Now logging in...\n");
				}		
				return user;
			case "register":
				while(true) {
					System.out.println("Enter a username or type R to return: ");
					username = scan.nextLine().toLowerCase().trim();
					if(username.equals("r")) return null;
					if(username.split(" ").length > 1) {
						System.out.println("\nUsername cannot have spaces.\n");
						continue;
					}
					if(loginService.loginExists(username))
					{
						System.out.println("\nThis username is already taken.\n");
						continue;
					}
					if(username.length() > 1) break;
					System.out.println("\nUsername must have atleast 2 characters.\n");
				}

				while(true)
				{
					System.out.println("Enter a password or type R to return: ");
					password = scan.nextLine();
					if(password.equalsIgnoreCase("r")) return null;
					if(password.split(" ").length > 1) {
						System.out.println("\nPassword cannot have spaces.\n");
						continue;
					}
					if(password.length() > 2) break;
					System.out.println("\nPassword must have atleast 3 characters.\n");
				}
				try 
				{
					byte[] sha = CryptoUtils.getSHA(password);
					password = CryptoUtils.Encrypt(sha);
				} catch (NoSuchAlgorithmException e) {
					System.out.println("Could not encrypt password");
					e.printStackTrace();
				}
				System.out.println("Created new account!\n");
				user = userService.createNewUser(username, password);
				loginService.uploadNewLogin(user);
				return null;
			case "quit":
				System.out.println("\nThank you for using the application. Hope to see you again!");
				applicationIsRunning = false;
				return null;
			default:
				System.out.println("\nInvalid output. Try again.\n");
				return null;
		}
	}
}
