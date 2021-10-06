package com.revature.services;

import java.util.ArrayList;
import java.util.HashMap;

import com.revature.daos.CardDAO;
import com.revature.models.Card;
import com.revature.models.Deck;
import com.revature.models.StandardUser;
import com.revature.models.User;

public class StandardUserService extends UserService{
	
	private static CardDAO cardDAO = new CardDAO(); //move this over to a CardServiceObject

	public User createNewUser(String username, String password) {
		return new StandardUser(username, password);
	}
	
	public void addCardToInventory(StandardUser user, Card card) 
	{
		user.addToInventory(card);
	}
	
	public void addCardToDeck(StandardUser user, Card card, Deck deck) 
	{
		user.addToDeck(deck, card);
	}
	
	
	public Deck createDeck(StandardUser user, String deckName)
	{
		return user.createNewDeck(deckName);
	}
	
	public ArrayList<Deck> getDecks(StandardUser user)
	{
		return user.getDecks();
	}
	
	public Deck getDeck(StandardUser user, int id)
	{
		return user.getDecks().get(id);
	}
	
	public ArrayList<Card> getInventory(StandardUser user)
	{
		return user.getInventory();
	}
	
	public static HashMap<Integer, Card> getAllCards()
	{
		return CardDAO.cardMap;
	}
	
}
