package com.revature.controllers;

import com.revature.models.Card;
import com.revature.models.Card.RarityType;
import com.revature.models.Deck;
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
			System.out.println("What would you like to do? \n" + "SEARCH | " + "INVENTORY | "
							+ "DECK | " + "ADD | " + "LOGOUT | " + "HELP");
			String response = scan.nextLine();
			switch(response.toLowerCase())
			{
				case "search": // open SearchMenuController
					break;
				case "inventory": //open InventoryMenuController
					StandardUserInventoryMenuController inventory = new StandardUserInventoryMenuController();
					inventory.enterInventoryPage(standardUser);
					break;
				case "deck": //open DeckMenuController
					int deckCount = standardUser.getDecks().size();
					System.out.println("\nYour account currently has " + deckCount + " deck(s) created.\n");
					if(deckCount > 0) {
						Deck deck = standardUserService.getDeck(standardUser, 0);
						System.out.println(deck.toString());
					}
						
					break;
				case "add":
					Card testCard = new Card(1, "test", 1, "this is a test card", RarityType.common);
					standardUserService.addCardToInventory(standardUser, testCard);
					System.out.println("\nAdding card: \n");
					System.out.println(testCard.toString());
					
					System.out.println("Would you like to create a new deck to add this into? Y/N");
					String response2 = scan.nextLine();
					switch(response2.toLowerCase()) {
						case "y":
							System.out.println("What would you like to name the deck?");
							String deckName = scan.nextLine();
							Deck deck = standardUserService.createDeck(standardUser, deckName); //create a deck service object and create that object there
							deck.addCard(testCard); //MUST DELETE THIS, JUST FOR TESTING
							System.out.println("Deck has been created and card has been added into.");
							break;
						case "n":
						default:
							break;
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
					break;
			}
		}
		return false;
	}
	
}
