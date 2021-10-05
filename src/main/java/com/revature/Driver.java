package com.revature;

import com.revature.controllers.LoginMenuController;
import com.revature.controllers.StandardUserHomePageMenuController;
import com.revature.models.User;

public class Driver {
	public static void main(String[] args) {
		System.out.println("Welcome to Hearthstone Deck Builder!");
		
		LoginMenuController menuController = new LoginMenuController();
		
		while(menuController.getApplicationStatus()) {
			User user = menuController.getUser();
			
			StandardUserHomePageMenuController homePageMenuController = new StandardUserHomePageMenuController();
			if(user != null) {
				homePageMenuController.enterHomePage(user);	
			}
		}
	}
}
