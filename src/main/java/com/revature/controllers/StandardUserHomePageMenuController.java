package com.revature.controllers;

import com.revature.models.StandardUser;
import com.revature.models.User;

public class StandardUserHomePageMenuController extends HomePageMenuController
{
	
	@Override
	public boolean enterHomePage(User user) {
		StandardUser standardUser = (StandardUser) user;
		boolean loggedIn = true;
		System.out.println(String.format("Welcome, %s!", standardUser.getUserName()));
		while(loggedIn)
		{
			System.out.println("Home - What would you like to do? \n" + "SEARCH | " + "INVENTORY | "
							+ "DECKS | " + "LOGOUT | " + "HELP");
			String response = (scan.nextLine()).trim();
			switch(response.toLowerCase())
			{
				case "search": // open SearchMenuController
					StandardUserSearchPageMenuController search = new StandardUserSearchPageMenuController();
					search.enterSearchPage(user);
					System.out.println();
					break;
				case "inventory": //open InventoryMenuController
					StandardUserInventoryMenuController inventory = new StandardUserInventoryMenuController();
					inventory.enterInventoryPage(standardUser);
					System.out.println();
					break;
				case "decks": //open DeckMenuController
					StandardUserDeckMenuController decks = new StandardUserDeckMenuController();
					decks.enterDeckPage(standardUser);
					System.out.println();
					break;
				case "help":
					System.out.println("\nSEARCH: search for a card to view or add to your inventory \n" + "INVENTORY: check your currently owned cards \n"
							+ "DECK: check, create, or delete your current decks \n" + "LOGOUT: log out of your account\n");
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
