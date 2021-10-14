package com.revature.controllers;

import com.revature.models.AdminUser;
import com.revature.models.User;

public class AdminHomePageMenuController extends HomePageMenuController
{
	public boolean enterHomePage(User user) 
	{
		AdminUser admin = (AdminUser) user;
		boolean loggedIn = true;
		System.out.println(String.format("Welcome, %s!", admin.getUserName()));
		while(loggedIn)
		{
			System.out.println("Home (ADMIN) - What would you like to do? \n" + "SEARCH | " + "CREATE | " + "LOGOUT | " + "HELP");
			String response = (scan.nextLine()).trim();
			switch(response.toLowerCase())
			{
				case "search": // open SearchMenuController
					AdminSearchPageMenuController search = new AdminSearchPageMenuController();
					search.enterSearchPage(user);
					System.out.println();
					break;
				case "create":
					AdminCreatePageMenuController create = new AdminCreatePageMenuController();
					create.enterCreatePage(user);
					System.out.println();
					break;
				case "help":
					System.out.println("\nSEARCH: search for an account to view or delete \n" + 
							"CREATE: register a new admin, moderator or standard user account\n" +
							"LOGOUT: log out of your account\n");
					break;
				case "logout":
					System.out.println("\nLogged out.\n");
					loggedIn = false;
					break;
				default:
					System.out.println("Invalid input. Try again. \n");
					break;
			}
		}
		return false;
	}
}
