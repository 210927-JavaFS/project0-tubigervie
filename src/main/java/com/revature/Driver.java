package com.revature;

import com.revature.controllers.AdminHomePageMenuController;
import com.revature.controllers.LoginMenuController;
import com.revature.controllers.ModeratorHomePageMenuController;
import com.revature.controllers.StandardUserHomePageMenuController;
import com.revature.models.User;

public class Driver {
	public static void main(String[] args) {
		System.out.println("Welcome to Hearthstone Deck Builder!");
		
		LoginMenuController menuController = new LoginMenuController();
		
		while(menuController.getApplicationStatus()) {
			User user = menuController.getUser();
			
			if(user == null) continue;
			switch(user.getAccountType()) 
			{
				case admin:
					AdminHomePageMenuController adminHomePage = new AdminHomePageMenuController();
					adminHomePage.enterHomePage(user);
					break;
				case moderator:
					ModeratorHomePageMenuController modHomePage = new ModeratorHomePageMenuController();
					modHomePage.enterHomePage(user);
					break;
				case standard:
					StandardUserHomePageMenuController standardHomePage = new StandardUserHomePageMenuController();
					standardHomePage.enterHomePage(user);	
					break;			
			}
		}
	}
}
