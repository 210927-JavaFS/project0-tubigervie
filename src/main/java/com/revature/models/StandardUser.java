package com.revature.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StandardUser extends User
{
	private static Logger log = LoggerFactory.getLogger(StandardUser.class);
	
	private HashMap<Integer, Integer> inventory = new HashMap<Integer, Integer>();
	
	private ArrayList<Integer> decks = new ArrayList<Integer>();
	
	public StandardUser(String username, String password, AccountType type) {
		super(username, password, type);
		// TODO Auto-generated constructor stub
	}
	
	public StandardUser(String username, String password, AccountType type, HashMap<Integer, Integer> inventory) {
		super(username, password, type);
		this.inventory = inventory;
	}
	
	public ArrayList<Integer> getInventoryArray(boolean noDuplicates)
	{
		if(noDuplicates)
		{
			HashSet<Integer> inventorySet = new HashSet<Integer>();
			for(Map.Entry<Integer, Integer> entry : inventory.entrySet())
			{
				for(int i = 0; i < entry.getValue(); i++)
					inventorySet.add(entry.getKey());
			}
			return new ArrayList<>(inventorySet);
		}
		else
		{
			ArrayList<Integer> inventoryArray = new ArrayList<Integer>();
			for(Map.Entry<Integer, Integer> entry : inventory.entrySet())
			{
				for(int i = 0; i < entry.getValue(); i++)
					inventoryArray.add(entry.getKey());
			}
			return inventoryArray;
		}	
	}
	
	public HashMap<Integer, Integer> getInventory()
	{
		return inventory;
	}
	
	public ArrayList<Integer> getDecks()
	{
		return this.decks;
	}
	
	public boolean addToInventory(int cardID, boolean isLegendary)
	{
		log.info(String.format("Attempting to add card (ID: %d) to user (ID: %d) inventory.", cardID, this.userID));
		int maxValue = (isLegendary) ? 1 : 2;	
		if(this.inventory.containsKey(cardID))
		{
			if(this.inventory.get(cardID) < maxValue)
				this.inventory.put(cardID, this.inventory.get(cardID) + 1);
			else
			{
				System.out.println("\nCannot have more than " + maxValue + " of these cards.");
				return false;
			}
		}
		else
			this.inventory.put(cardID, 1);
		return true;
	}
	
	public void removeFromInventory(int cardID)
	{
		log.info(String.format("Attempting to remove card (ID: %d) from user (ID: %d) inventory.", cardID, this.userID));
		if(this.inventory.containsKey(cardID))
			this.inventory.put(cardID, this.inventory.get(cardID) - 1);
		if(this.inventory.get(cardID) == 0)
			this.inventory.remove(cardID);
	}
	
	public void addToDecks(int deckID)
	{
		log.info(String.format("Adding deck (ID: %d) to user (ID: %d) deck list.", deckID, this.userID));
		this.decks.add(deckID);
	}
	
	public void removeDeck(int deckID)
	{
		log.info(String.format("Removing deck (ID: %d) from user (ID: %d) deck list.", deckID, this.userID));
		this.decks.remove((Integer)deckID);
	}
	
	public void setInventory(HashMap<Integer, Integer> newInv)
	{
		this.inventory = newInv;
	}
	
	public void setDeckList(ArrayList<Integer> newDecks)
	{
		this.decks = newDecks;
	}
}
