package com.revature.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.revature.models.Card;
import com.revature.models.Deck;
import com.revature.models.StandardUser;
import com.revature.services.CardService;
import com.revature.services.DeckService;
import com.revature.services.StandardUserService;

public class StandardUserDeckMenuController {
	private StandardUserService standardUserService = new StandardUserService();
	private CardService cardService = new CardService();
	private DeckService deckService = new DeckService();
	
	protected static Scanner scan = new Scanner(System.in);
	
	public boolean enterDeckPage(StandardUser user)
	{
		boolean inDecks = true;
		while(inDecks) {
			System.out.println("\nDecks - What would you like to do? \n" + "VIEW | " + "CREATE | " + "EDIT | " + "DELETE | " + "RETURN");
			String response = scan.nextLine().trim();
			switch(response.toLowerCase())
			{
				case "view":		
					ArrayList<Integer> decks = standardUserService.getDecks(user);
					int deckCount = decks.size();
					System.out.println("\nYour account currently has " + deckCount + " deck(s) created.");
					if(deckCount > 0) {
						for(int i = 0; i < deckCount; i++)
							System.out.println(String.format("%d) %s", i + 1, deckService.getDeck(decks.get(i)).getName()));
						if(deckCount > 0)
							inDecks = enterView(decks, deckCount, user);
					}
					break;
				case "create":
					inDecks = enterCreate(user);
					break;
				case "edit":
					inDecks = enterEdit(user);
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
	
	public boolean enterEdit(StandardUser user)
	{
		ArrayList<Integer> decks = standardUserService.getDecks(user);
		int deckCount = decks.size();
		System.out.println("\nYour account currently has " + deckCount + " deck(s) created.\n");
		if(deckCount > 0) {
			for(int i = 0; i < deckCount; i++)
				System.out.println(String.format("%d) %s", i + 1, deckService.getDeck(i).getName()));
			while(true)
			{
				System.out.println("\nType in the number of the deck you would like to edit.");
				String response = scan.nextLine().trim();
				try {
					int number = Integer.parseInt(response);
					if(number > deckCount) {
						System.out.println("Invalid input. Try again. \n");
						continue;
					}
					//Deck deck = decks.get(number - 1);
					while(true)
					{
						System.out.println("What would you like to do? \n" + "ADD | " + "DELETE | " + "RETURN");
						String response2 = scan.nextLine();
						switch(response2.toLowerCase())
						{
							case "add":
								break;
							case "delete":
								break;
							case "return":
								return true;
							default:
								break;
						}
					}	
				}
				catch(NumberFormatException e){
					System.out.println("Invalid input. Try again.");
					continue;
				}
			}
		}
		return true;
	}
	
	public boolean enterCreate(StandardUser user)
	{
		System.out.println("What would you like to name the deck?");
		String deckName = scan.nextLine().trim();
		Deck newDeck = deckService.createNewDeck(deckName);
		standardUserService.addToDecks(user, newDeck.getDeckID());
		System.out.println("\nDeck has been created. Would you like to add cards from your inventory into the deck? Y/N");
		String response = scan.nextLine().trim();
		switch(response.toLowerCase()) {
			case "y":
				ArrayList<Integer> inventory = standardUserService.getInventory(user);
				if(inventory.size() == 0) {
					System.out.println("No cards in your inventory to add.");
					return true;
				}
				HashMap<Integer, Integer> inventoryMap = new HashMap<Integer, Integer>();
				for(int i = 0; i < inventory.size(); i++)
				{
					Integer itemIndex = inventory.get(i);
					if(inventoryMap.containsKey(itemIndex))
						inventoryMap.put(itemIndex, inventoryMap.get(itemIndex) + 1);
					else
						inventoryMap.put(itemIndex, 1);
				}
				
				while(true)
				{
					for(Map.Entry<Integer, Integer> entry : inventoryMap.entrySet())
					{
						Card card = cardService.findCard(entry.getKey());
						System.out.println(String.format("%d) %s x%d", card.getIndex(), card.getName(), entry.getValue()));
					}
					while(true)
					{
						System.out.println("Type in the number of the card you would like to add or RETURN to go back to the decks page.");
						String response2 = scan.nextLine().trim();
						
						if(response2.equalsIgnoreCase("return")) return true;
						try {
							int number = Integer.parseInt(response2);
							if(inventoryMap.containsKey(number))
							{
								Card card = cardService.findCard(number);
								deckService.addToDeck(newDeck, card);
								inventoryMap.put(number, inventoryMap.get(number) - 1);
								if(inventoryMap.get(number) == 0)
									inventoryMap.remove(number);
								if(inventoryMap.isEmpty())
								{
									System.out.println("No more cards capable of being added.");
									return true;
								}
								break;
							}
							else 
							{
								System.out.println("Invalid input. Try again. \n");
								continue;
							}
						}
						catch(NumberFormatException e){
							System.out.println("Invalid input. Try again. \n");
							continue;
						}
					}				
				}
			case "n":
				return true;
			default:
				return true;
		}
		
	}
	
	public boolean enterView(ArrayList<Integer> decks, int deckCount, StandardUser user)
	{
		while(true)
		{
			System.out.println("Type in the number of the deck you would like to examine.");
			String response2 = scan.nextLine().trim();
			try {
				int number = Integer.parseInt(response2);
				if(number > deckCount) {
					System.out.println("Invalid input. Try again. \n");
					continue;
				}
				Deck deck = deckService.getDeck(decks.get(number - 1));
				System.out.println(String.format("\n%s", deck.toString()));
				HashMap<Integer, Integer> deckCardMap = deck.getDeckMap();
				for(Map.Entry<Integer, Integer> entry : deckCardMap.entrySet())
				{
					Card card = cardService.findCard(entry.getKey());
					System.out.println(String.format("%d) %s x%d", card.getIndex(), card.getName(), entry.getValue()));
				}
				return true;
			}
			catch(NumberFormatException e){
				System.out.println("Invalid input. Try again. \n");
				continue;
			}
		}
	}
}
