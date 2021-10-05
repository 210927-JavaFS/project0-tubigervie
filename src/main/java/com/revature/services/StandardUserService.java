package com.revature.services;

import com.revature.models.Card;
import com.revature.models.Deck;
import com.revature.models.StandardUser;
import com.revature.models.User;

public class StandardUserService extends UserService{

	public User createNewUser(String username, String password) {
		return new StandardUser(username, password);
	}
	
	public void addCard(StandardUser user, Card card) 
	{
		user.addToInventory(card);
	}
	
	public Deck createDeck(StandardUser user, String deckName)
	{
		return user.createNewDeck(deckName);
	}
	
	public Deck getDeck(StandardUser user, int index)
	{
		return user.getDecks().get(index);
	}
	
}
