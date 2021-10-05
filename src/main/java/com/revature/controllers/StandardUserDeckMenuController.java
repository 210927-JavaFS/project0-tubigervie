package com.revature.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.revature.models.Card;
import com.revature.models.Deck;
import com.revature.models.StandardUser;
import com.revature.services.StandardUserService;

public class StandardUserDeckMenuController {
	private StandardUserService standardUserService = new StandardUserService();
	protected static Scanner scan = new Scanner(System.in);
	
	public boolean enterDeckPage(StandardUser user)
	{
		boolean inDecks = true;
		while(inDecks) {
			System.out.println("What would you like to do? \n" + "VIEW | " + "CREATE | " + "EDIT | " + "DELETE | " + "RETURN");
			String response = scan.nextLine();
			switch(response.toLowerCase())
			{
				case "view":		
					ArrayList<Deck> decks = standardUserService.getDecks(user);
					int deckCount = decks.size();
					System.out.println("\nYour account currently has " + deckCount + " deck(s) created.\n");
					if(deckCount > 0) {
						for(int i = 0; i < deckCount; i++)
							System.out.println(String.format("%d) %s", i + 1, decks.get(i).getName()));
						if(deckCount > 0)
							inDecks = enterView(decks, deckCount, user);
					}
					break;
				case "create":
					inDecks = enterCreate(user);
					break;
				case "edit":
					break;
				case "delete":
					break;
				case "return":
					inDecks = false;
					break;
				default:
					break;
			}
		}
		return false;
	}
	
	public boolean enterCreate(StandardUser user)
	{
		System.out.println("What would you like to name the deck?");
		String deckName = scan.nextLine();
		Deck deck = standardUserService.createDeck(user, deckName); //create a deck service object and create that object there
		System.out.println("\nDeck has been created. Would you like to add cards from your inventory into the deck? Y/N");
		String response = scan.nextLine();
		switch(response.toLowerCase()) {
			case "y":
				ArrayList<Card> inventory = standardUserService.getInventory(user);
				if(inventory.size() == 0) {
					System.out.println("No cards in your inventory to add.");
					return true;
				}
				HashMap<String, Integer> inventoryMap = new HashMap<String, Integer>(); //replace this with cardID once i have a working DAO for cards
				for(int i = 0; i < inventory.size(); i++)
				{
					String itemIndex = inventory.get(i).getName();
					if(inventoryMap.containsKey(itemIndex))
						inventoryMap.put(itemIndex.toLowerCase(), inventoryMap.get(itemIndex) + 1);
					else
						inventoryMap.put(itemIndex.toLowerCase(), 1);
				}
				
				while(true)
				{
					int i = 0;
					for(Map.Entry<String, Integer> entry : inventoryMap.entrySet())
					{
						System.out.println(String.format("%d) %s x%d", i + 1, entry.getKey(), entry.getValue()));
						i++;
					}
					while(true)
					{
						System.out.println("Type in the name of the card you would like to add or RETURN to go back to the decks page.");
						String response2 = scan.nextLine();
						
						if(response2.equalsIgnoreCase("return")) return true;
						if(inventoryMap.containsKey(response2.toLowerCase()))
						{
							for(int j = 0; j < inventory.size(); j++)
							{
								if(inventory.get(j).getName().equalsIgnoreCase(response2)) { //this might be prone to buggy behavior will replace string with int id once database is built
									standardUserService.addCardToDeck(user, inventory.get(j), deck);
									break;
								}
							}
							inventoryMap.put(response2.toLowerCase(), inventoryMap.get(response2) - 1);
							if(inventoryMap.get(response2) == 0)
								inventoryMap.remove(response2);
							if(inventoryMap.isEmpty())
							{
								System.out.println("No more cards capable of being added.");
								return true;
							}
						}
						else 
						{
							System.out.println("Invalid input. Try again. \n");
							continue;
						}
						break;
					}				
				}
			case "n":
				return true;
			default:
				return true;
		}
		
	}
	
	public boolean enterView(ArrayList<Deck> decks, int deckCount, StandardUser user)
	{
		while(true)
		{
			System.out.println("Type in the number of the deck you would like to examine.");
			String response2 = scan.nextLine();
			try {
				int number = Integer.parseInt(response2);
				if(number > deckCount) {
					System.out.println("Invalid input. Try again. \n");
					continue;
				}
				System.out.println(decks.get(number - 1).toString());
				return true;
			}
			catch(NumberFormatException e){
				System.out.println("Invalid input. Try again. \n");
				continue;
			}
		}
	}
}
