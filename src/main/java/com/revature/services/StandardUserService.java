package com.revature.services;

import java.util.ArrayList;

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
	
	public ArrayList<Integer> getDecks(StandardUser user)
	{
		return user.getDecks();
	}
	
	public ArrayList<Integer> getInventory(StandardUser user)
	{
		return user.getInventory();
	}	
}
