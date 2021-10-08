package com.revature.services;

import java.util.ArrayList;
import java.util.HashMap;

import com.revature.models.StandardUser;
import com.revature.models.User;

public class StandardUserService extends UserService{

	public User createNewUser(String username, String password) {
		return new StandardUser(username, password);
	}
	
	public void addCardToInventory(StandardUser user, int cardID) 
	{
		user.addToInventory(cardID);
	}
	
	public void addToDecks(StandardUser user, int deckID)
	{
		user.addToDecks(deckID);
	}
	
	public void removeDeck(StandardUser user, int deckID)
	{
		user.removeDeck(deckID);
	}
	
	public ArrayList<Integer> getDecks(StandardUser user)
	{
		return user.getDecks();
	}
	
	public ArrayList<Integer> getInventory(StandardUser user)
	{
		return user.getInventory();
	}
	
	public HashMap<Integer, Integer> getInventoryMap(StandardUser user)
	{
		ArrayList<Integer> inventory = getInventory(user);
		HashMap<Integer, Integer> inventoryMap = new HashMap<Integer, Integer>();
		for(int i = 0; i < inventory.size(); i++)
		{
			Integer itemIndex = inventory.get(i);
			if(inventoryMap.containsKey(itemIndex))
				inventoryMap.put(itemIndex, inventoryMap.get(itemIndex) + 1);
			else
				inventoryMap.put(itemIndex, 1);
		}
		return inventoryMap;
	}
}
