package com.revature.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.User;
import com.revature.models.User.AccountType;
import com.revature.services.LoginService;
import com.revature.services.ModerationService;
import com.revature.utils.CryptoUtils;

public class AdminCreatePageMenuController 
{
	private static Logger log = LoggerFactory.getLogger(AdminCreatePageMenuController.class);
	private static Scanner scan = new Scanner(System.in);
	
	private ModerationService adminService = new ModerationService();
	private LoginService loginService = new LoginService();
	
	public boolean enterCreatePage(User user)
	{
		boolean inCreate = true;
		while(inCreate)
		{
			System.out.println("\nCreate - What type of account would you like to create?\n" + "STANDARD | " + "MOD | " + "ADMIN | " + "RETURN");
			String response = scan.nextLine().trim();
			AccountType accType;
			switch(response.toLowerCase())
			{
				case "standard":
					accType = AccountType.standard;
					break;
				case "return":
					inCreate = false;
					continue;
				case "admin":
					accType = AccountType.admin;
					break;
				case "mod":
					accType = AccountType.moderator;
					break;
				default:
					System.out.println("Invalid input. Try again.");
					continue;
			}
			String username = null;
			String password = null;
			while(true) {
				System.out.println("Enter a username or type R to return: ");
				username = scan.nextLine().toLowerCase();
				if(username.equals("r")) {
					username = null;
					break;
				};
				if(username.length() < 2 || username.split(" ").length > 1) 
				{
					System.out.println("\nUsername must have atleast 2 characters and cannot have spaces.\n");
					continue;
				}
				if(loginService.loginExists(username))
				{
					System.out.println("\nThis username is already taken.\n");
					continue;
				}
				break;
			}
			if(username == null) continue;
			while(true)
			{
				System.out.println("Enter a password or type R to return: ");
				password = scan.nextLine();
				if(password.equalsIgnoreCase("r")){
					password = null;
					break;
				};
				if(password.length() < 3 || password.split(" ").length > 1) 
				{
					System.out.println("\nPassword must have atleast 3 characters and cannot have spaces.\n");
					continue;
				}
				break;
			}
			if(password == null) continue;
			try 
			{
				log.info("Beginning password encryption...");
				byte[] sha = CryptoUtils.getSHA(password);
				password = CryptoUtils.Encrypt(sha);
				user = adminService.createNewUser(username, password, accType);
				loginService.uploadNewLogin(user);
				log.info("Created new account: " + username + String.format(" (%s)", accType.toString()));
				System.out.println("\nCreated new account!");
			} catch (NoSuchAlgorithmException e) {
				log.warn("Password could not be encrypted.");
				log.warn(e.toString());
			}
		}
		return true;
	}
}
