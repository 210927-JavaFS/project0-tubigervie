package com.revature.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class StandardUser extends User
{
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
	
	public ArrayList<Integer> getInventoryArray()
	{
		HashSet<Integer> inventorySet = new HashSet<Integer>();
		for(Map.Entry<Integer, Integer> entry : inventory.entrySet())
		{
			for(int i = 0; i < entry.getValue(); i++)
				inventorySet.add(entry.getKey());
		}
		return new ArrayList<>(inventorySet);
	}
	
	public HashMap<Integer, Integer> getInventory()
	{
		return inventory;
	}
	
	public ArrayList<Integer> getDecks()
	{
		return this.decks;
	}
	
	public void addToInventory(int cardID)
	{
		if(this.inventory.containsKey(cardID))
			this.inventory.put(cardID, this.inventory.get(cardID) + 1);
		else
			this.inventory.put(cardID, 1);
	}
	
	public void removeFromInventory(int cardID)
	{
		if(this.inventory.containsKey(cardID))
			this.inventory.put(cardID, this.inventory.get(cardID) - 1);
		if(this.inventory.get(cardID) == 0)
			this.inventory.remove(cardID);
	}
	
	public void addToDecks(int deckID)
	{
		this.decks.add(deckID);
	}
	
	public void removeDeck(int deckID)
	{
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
