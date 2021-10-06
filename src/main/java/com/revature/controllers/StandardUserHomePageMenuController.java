package com.revature.controllers;

import java.util.HashMap;
import java.util.Map;

import com.revature.models.Card;
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
					HashMap<Integer, Card> cardMap = StandardUserService.getAllCards();
					for(Map.Entry<Integer, Card> entry : cardMap.entrySet())
					{
						System.out.println(String.format("%d) %s", (int)entry.getKey(), entry.getValue().getName()));
					}
					while(true)
					{
						System.out.println("Type in the number of the card you would like to add to your inventory.");
						String response2 = scan.nextLine();
						try {
							int number = Integer.parseInt(response2);
							if(number <= 0 || number > cardMap.size()) {
								System.out.println("Invalid input. Try again. \n");
								continue;
							}
							standardUserService.addCardToInventory(standardUser, cardMap.get(number));
							System.out.println("Added \"" + cardMap.get(number).getName() + "\" to your inventory!");
							break;
						}
						catch(NumberFormatException e){
							System.out.println("Invalid input. Try again. \n");
							continue;
						}
					}
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
