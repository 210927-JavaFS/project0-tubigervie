package com.revature.controllers;

import java.util.Scanner;

import com.revature.models.User;
import com.revature.services.StandardUserService;

public class LoginMenuController {
	private static Scanner scan = new Scanner(System.in);
	private StandardUserService userService = new StandardUserService();
	
	public User getUser()
	{
		System.out.println("Are you logging in or creating a new account? Type login/register");
		String response = scan.nextLine();
		if(response.toLowerCase().equals("login"))
		{
			System.out.println("Enter your username: ");
			String username = scan.nextLine();
			System.out.println("Enter your password: ");
			String password = scan.nextLine();
			User user = userService.createNewUser(username, password);
			System.out.println("Welcome " + username + "! Now logging in...");
			return user;
		}
		else
			return null;
	}
}
