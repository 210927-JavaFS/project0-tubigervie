package com.revature.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import com.revature.daos.StandardUserDAO;
import com.revature.models.Deck;
import com.revature.models.StandardUser;
import com.revature.models.User;
import com.revature.models.User.AccountType;

public class StandardUserService extends UserService
{
	private static StandardUserDAO userDAO = new StandardUserDAO();
	
	private DeckService deckService = new DeckService();
	private CardService cardService = new CardService();
	
	public User createNewUser(String username, String password) {
		return new StandardUser(username, password, AccountType.standard);
	}
	
	public void addCardToInventory(StandardUser user, int cardID) 
	{
		user.addToInventory(cardID);
		updateAccountInfo(user);
	}
	
	public void removeCardFromInventory(StandardUser user, int cardID)
	{
		user.removeFromInventory(cardID);
		for(Integer i : user.getDecks())
		{
			Deck deck = deckService.getDeck(i);
			deck.removeCard(cardService.findCard(cardID));
		}
		updateAccountInfo(user);
	}
	
	public void addToDecks(StandardUser user, int deckID)
	{
		user.addToDecks(deckID);
		updateAccountInfo(user);
	}
	
	public void removeDeck(StandardUser user, int deckID)
	{
		user.removeDeck(deckID);
		updateAccountInfo(user);
	}
	
	public ArrayList<Integer> getDecks(StandardUser user)
	{
		return user.getDecks();
	}
	
	public HashMap<Integer, Integer> getInventory(StandardUser user)
	{
		return user.getInventory();
	}
	
	public ArrayList<Integer> getInventoryArray(StandardUser user)
	{
		return user.getInventoryArray();
	}
	
	public TreeMap<Integer, Integer> getInventoryTreeMap(StandardUser user, HashMap<Integer, Integer> inventoryMap)
	{
		TreeMap<Integer, Integer> sortedMap = new TreeMap<Integer, Integer>(inventoryMap);
		return sortedMap;
	}
	
	public void loadInventory(StandardUser user)
	{
		userDAO.load(user);
		deckService.loadDecks(user);
	}
	
	public void updateAccountInfo(User user)
	{
		userDAO.updateUser(user);
	}
}
