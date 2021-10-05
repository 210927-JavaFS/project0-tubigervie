package com.revature.controllers;

import java.util.Scanner;

import com.revature.models.User;
import com.revature.services.StandardUserService;

public class LoginMenuController {
	private boolean applicationIsRunning = true;
	private static Scanner scan = new Scanner(System.in);
	private StandardUserService userService = new StandardUserService();
	
	public boolean getApplicationStatus() {
		return applicationIsRunning;
	}
	
	public User getUser()
	{
		System.out.println("Are you logging in or creating a new account? Type login/register/quit");
		String response = scan.nextLine();
		switch(response) {
			case "login":
				System.out.println("Enter your username: ");
				String username = scan.nextLine();
				System.out.println("Enter your password: ");
				String password = scan.nextLine();
				User user = userService.createNewUser(username, password);
				System.out.println("Now logging in...\n");
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
