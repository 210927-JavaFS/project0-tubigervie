package com.revature.daos;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import com.revature.models.Deck;

public class DeckDAO {
	public HashMap<Integer, Deck> deckMap;
	
	public DeckDAO() 
	{
		if(deckMap == null)
			initializeDecks();
	}
	
	public Deck findDeck(int id) {
		return (deckMap.containsKey(id)) ? deckMap.get(id) : null;
	}
	
	public void addToDeckMap(Deck deck)
	{
		if(!deckMap.containsKey(deck.getDeckID()))
			deckMap.put(deck.getDeckID(), deck);
	}
	
	public void removeFromDeckMap(Deck deck)
	{
		if(deckMap.containsKey(deck.getDeckID()))
			deckMap.remove(deck.getDeckID());
	}
	
	private void initializeDecks()
	{
		System.out.println("Deck database being initialized");
		deckMap = new HashMap<Integer, Deck>();
		try 
		{
			Scanner scan = new Scanner(new File("C:\\Users\\ervie\\Documents\\Work Stuff\\Repos\\project0-tubigervie\\src\\main\\resources\\DeckDatabase.txt"));
			scan.nextLine();
			while(scan.hasNextLine())
			{
				String deckString = scan.nextLine();
				String[] deckArray = deckString.split(";");
				String[] cardArray = deckArray[3].split(",");
				HashMap<Integer, Integer> cardMap = new HashMap<Integer, Integer>();
				for(String cardIDString : cardArray) {
					try {
						int cardID = Integer.parseInt(cardIDString);
						if(!cardMap.containsKey(cardID))
							cardMap.put(cardID, 1);
						else
							cardMap.put(cardID, cardMap.get(cardID) + 1);
					}
					catch(NumberFormatException e){
						System.out.println("Invalid card ID parsing. Check database.");
						continue;
					}
				}				
				Deck deck = new Deck(Integer.valueOf(cardArray[0]), Integer.valueOf(cardArray[1]), cardArray[2], cardMap);
				if(!deckMap.containsKey(deck.getDeckID()))
					deckMap.put(deck.getDeckID(), deck);
			}
		}
		catch(IOException e)
		{
			System.out.println("Could not open decks file.");
		}
		catch(NumberFormatException e)
		{
			System.out.println("Deck in deck database not properly formatted.");
		}
	}
}
