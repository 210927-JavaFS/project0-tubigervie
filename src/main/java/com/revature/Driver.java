package com.revature;

import com.revature.controllers.LoginMenuController;
import com.revature.models.StandardUser;
import com.revature.models.User;

public class Driver {
	public static void main(String[] args) {
		System.out.println("Welcome to Hearthstone Deck Builder!");
		
		LoginMenuController menuController = new LoginMenuController();

		User user = menuController.getUser();
		System.out.println("This user has " + ((StandardUser)user).getInventory().size() + " cards in the inventory");
	}
}
