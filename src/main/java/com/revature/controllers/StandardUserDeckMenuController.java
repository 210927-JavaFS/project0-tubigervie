package com.revature.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

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
					inDecks = enterView(user);
					break;
				case "create":
					inDecks = enterCreate(user);
					break;
				case "edit":
					inDecks = enterEdit(user);
					break;
				case "delete":
					inDecks = enterDelete(user);
					break;
				case "return":
					inDecks = false;
					break;
				default:
					System.out.println("Invalid input. Try again.");
					break;
			}
		}
		return false;
	}
	
	public boolean enterEdit(StandardUser user)
	{
		ArrayList<Integer> decks = standardUserService.getDecks(user);
		int deckCount = decks.size();
		if(deckCount > 0) {
			while(true)
			{
				printDeckList(user, decks, deckCount);
				System.out.println("\nType in the number of the deck you would like to edit or type RETURN.");
				String response = scan.nextLine().trim();
				try {
					if(response.equals("return")) return true;
					int number = Integer.parseInt(response);
					Deck deck = deckService.getDeck(decks.get(number - 1));
					while(true)
					{
						System.out.println("Edit - What would you like to do? \n" + "ADD | " + "REMOVE | " + "RETURN");
						String response2 = scan.nextLine();
						switch(response2.toLowerCase())
						{
							case "add":
								enterAdd(user, deck);
								break;
							case "remove":
								enterRemove(user, deck);
								break;
							case "return":
								return true;
							default:
								break;
						}
					}	
				}
				catch(NumberFormatException | IndexOutOfBoundsException e){
					System.out.println("\nInvalid input. Try again.");
					continue;
				}
			}
		}
		return true;
	}
	
	public boolean enterDelete(StandardUser user)
	{
		ArrayList<Integer> decks = standardUserService.getDecks(user);
		int deckCount = decks.size();
		if(deckCount > 0)
		{
			while(true)
			{
				printDeckList(user, decks, deckCount);
				System.out.println("\nType in the number of the deck you would like to delete or type RETURN.");
				String response2 = scan.nextLine().trim();
				if(response2.equals("return")) return true;
				try {
					int number = Integer.parseInt(response2);
					Deck deck = deckService.getDeck(decks.get(number - 1));
					standardUserService.removeDeck(user, deck.getDeckID());
					deckService.deleteDeck(deck);
					return true;
				}
				catch(NumberFormatException | IndexOutOfBoundsException e){
					System.out.println("\nInvalid input. Try again.");
				}
			}
		}
		return true;
	}
	
	public void enterRemove(StandardUser user, Deck deck)
	{
		HashMap<Integer, Integer> editDeckMap = deckService.createDeckMapCopy(deck);
		TreeMap<Integer, Integer> sortedDeckMap = deckService.createDeckTreeMap(deck);
		
		if(editDeckMap.size() == 0) {
			System.out.println("\nNo cards in your deck to remove.\n");
			return;
		}
		while(true)
		{
			sortedDeckMap = deckService.createDeckTreeMap(deck);
			System.out.println(String.format("\n%s", deck.toString()));
			printDeck(user, sortedDeckMap);
			System.out.println("\nType in the number of the card you would like to remove or type RETURN.");
			String response2 = scan.nextLine().trim();
			
			if(response2.equalsIgnoreCase("return")) return;
			try {
				int number = Integer.parseInt(response2);
				if(editDeckMap.containsKey(number))
				{
					Card card = cardService.findCard(number);
					deckService.removeFromDeck(deck, card);
					editDeckMap.put(number, editDeckMap.get(number) - 1);
					if(editDeckMap.get(number) == 0)
						editDeckMap.remove(number);
					if(editDeckMap.isEmpty())
					{
						System.out.println("Deck is now empty.\n");
						return;
					}
					continue;
				}
				else 
				{
					System.out.println("\nInvalid input. Try again.");
					continue;
				}
			}
			catch(NumberFormatException | IndexOutOfBoundsException e){
				System.out.println("\nInvalid input. Try again.");
				continue;
			}
		}			
	}
	
	public void enterAdd(StandardUser user, Deck deck)
	{
		if(deck.isFull())
		{
			System.out.println(String.format("\n%s is full. Please remove atleast 1 card before attempting to add.\n", deck.getName()));
			return;
		}
		
		HashMap<Integer, Integer> inventoryMap = standardUserService.getInventory(user);
		TreeMap<Integer, Integer> subsetInventoryMap = deckService.getSubsetInventoryTreeMap(deck.getDeckMap(), standardUserService.getInventoryTreeMap(user, inventoryMap));
		if(subsetInventoryMap.size() == 0) {
			System.out.println("\nNo cards in your inventory to add.");
			return;
		}
		
		while(true)
		{
			TreeMap<Integer, Integer> sortedDeckMap = deckService.createDeckTreeMap(deck);
			System.out.println(String.format("\n%s", deck.toString()));
			printDeck(user, sortedDeckMap);			
			System.out.println("\nInventory");
			printDeck(user, subsetInventoryMap);
			System.out.println("\nType in the number of the card you would like to add or type RETURN.");
			String response2 = scan.nextLine().trim();
			
			if(response2.equalsIgnoreCase("return")) return;
			try {
				int number = Integer.parseInt(response2);
				if(subsetInventoryMap.containsKey(number))
				{
					Card card = cardService.findCard(number);
					deckService.addToDeck(deck, card);
					subsetInventoryMap.put(number, subsetInventoryMap.get(number) - 1);
					if(subsetInventoryMap.get(number) == 0)
						subsetInventoryMap.remove(number);
					if(subsetInventoryMap.isEmpty())
					{
						System.out.println("\nNo more cards capable of being added.");
						return;
					}
					else if(deck.isFull())
					{
						System.out.println(String.format("\n%s", deck.toString()));
						printDeck(user, deck.getDeckMap());
						System.out.println(String.format("\n%s is now full.", deck.getName()));
						return;
					}
					continue;
				}
				else 
				{
					System.out.println("\nInvalid input. Try again.");
					continue;
				}
			}
			catch(NumberFormatException | IndexOutOfBoundsException e){
				System.out.println("\nInvalid input. Try again.");
				continue;
			}
		}		
	}
	
	public boolean enterCreate(StandardUser user)
	{
		System.out.println("\nWhat would you like to name the deck?");
		String deckName = scan.nextLine().trim();
		Deck newDeck = deckService.createNewDeck(deckName, user);
		standardUserService.addToDecks(user, newDeck.getDeckID());
		while(true) {
			System.out.println("\nDeck has been created. Would you like to add cards from your inventory into the deck? Y/N");
			String response = scan.nextLine().trim();
			switch(response.toLowerCase()) 
			{
				case "y":
					enterAdd(user, newDeck);
					return true;
				case "n":
					return true;
				default:
					System.out.println("\nInvalid input. Try again.");
					continue;
			}
		}		
	}
	
	public boolean enterView(StandardUser user)
	{
		ArrayList<Integer> decks = standardUserService.getDecks(user);
		int deckCount = decks.size();
		if(deckCount > 0)
		{
			while(true)
			{
				printDeckList(user, decks, deckCount);
				System.out.println("\nType in the number of the deck you would like to examine or type RETURN.");
				String response2 = scan.nextLine().trim();
				if(response2.equals("return")) return true;
				try {
					int number = Integer.parseInt(response2);
					Deck deck = deckService.getDeck(decks.get(number - 1));
					System.out.println(String.format("\n%s", deck.toString()));
					TreeMap<Integer, Integer> sortedDeckMap = deckService.createDeckTreeMap(deck);
					for(Map.Entry<Integer, Integer> entry : sortedDeckMap.entrySet())
					{
						Card card = cardService.findCard(entry.getKey());
						System.out.println(String.format("%d) %s x%d", card.getIndex(), card.getName(), entry.getValue()));
					}
					return true;
				}
				catch(NumberFormatException | IndexOutOfBoundsException e){
					System.out.println("\nInvalid input. Try again.");
				}
			}
		}
		return true;
	}
	
	private void printDeckList(StandardUser user, ArrayList<Integer> decks, int deckCount)
	{
		System.out.println("\nYour account currently has " + deckCount + " deck(s).");
		if(deckCount > 0) 
		{
			for(int i = 0; i < deckCount; i++)
			{
				System.out.println(String.format("%d) %s", i + 1, deckService.getDeck(decks.get(i)).getName()));
			}

		}
	}
	
	private void printDeck(StandardUser user, Map<Integer, Integer> deckCardMap)
	{
		for(Map.Entry<Integer, Integer> entry : deckCardMap.entrySet())
		{
			Card card = cardService.findCard(entry.getKey());
			System.out.println(String.format("%d) %s x%d", card.getIndex(), card.getName(), entry.getValue()));
		}
	}
}
