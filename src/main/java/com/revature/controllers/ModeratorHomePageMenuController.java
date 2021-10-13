package com.revature.controllers;

import com.revature.models.ModeratorUser;
import com.revature.models.User;

public class ModeratorHomePageMenuController extends HomePageMenuController
{

	@Override
	public boolean enterHomePage(User user) {
		ModeratorUser modUser = (ModeratorUser) user;
		boolean loggedIn = true;
		System.out.println(String.format("Welcome, %s!", modUser.getUserName()));
		while(loggedIn)
		{
			System.out.println("Home (MOD) - What would you like to do? \n" + "SEARCH | " + "INVENTORY | "
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
					inventory.enterInventoryPage(modUser);
					System.out.println();
					break;
				case "decks": //open DeckMenuController
					StandardUserDeckMenuController decks = new StandardUserDeckMenuController();
					decks.enterDeckPage(modUser);
					System.out.println();
					break;
				case "help":
					System.out.println("\nSEARCH: search for a card or account to view\n" + "INVENTORY: check your currently owned cards \n"
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
