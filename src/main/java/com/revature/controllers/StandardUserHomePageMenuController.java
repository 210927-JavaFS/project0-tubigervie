package com.revature.controllers;

import com.revature.models.Card;
import com.revature.models.Card.CardType;
import com.revature.models.Card.RarityType;
import com.revature.models.StandardUser;
import com.revature.models.User;
import com.revature.services.StandardUserService;

public class StandardUserHomePageMenuController extends HomePageMenuController
{

	private StandardUserService standardUserService = new StandardUserService();
	
	@Override
	public boolean enterHomePage(User user) {
		StandardUser standardUser = (StandardUser) user;
		boolean loggedIn = true;
		System.out.println(String.format("Welcome, %s!", standardUser.getUserName()));
		while(loggedIn)
		{
			System.out.println("Home - What would you like to do? \n" + "SEARCH | " + "INVENTORY | "
							+ "DECKS | " + "ADD | " + "LOGOUT | " + "HELP");
			String response = scan.nextLine();
			switch(response.toLowerCase())
			{
				case "search": // open SearchMenuController
					break;
				case "inventory": //open InventoryMenuController
					StandardUserInventoryMenuController inventory = new StandardUserInventoryMenuController();
					inventory.enterInventoryPage(standardUser);
					break;
				case "decks": //open DeckMenuController
					StandardUserDeckMenuController decks = new StandardUserDeckMenuController();
					decks.enterDeckPage(standardUser);
					break;
				case "add":
					Card testCard = new Card(1, "test", 1, "this is a test card", RarityType.common, CardType.spell);
					standardUserService.addCardToInventory(standardUser, testCard);
					System.out.println("\nAdding card: \n");
					System.out.println(testCard.toString());
					break;
				case "help":
					System.out.println("\nSEARCH: search for a card, deck, or account \n" + "INVENTORY: check your cards \n"
							+ "DECK: check your current decks \n" + "ADD: this is a test function \n" + "LOGOUT: log out of your account\n");
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
