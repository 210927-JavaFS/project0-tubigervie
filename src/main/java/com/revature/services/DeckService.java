package com.revature.services;

import com.revature.daos.DeckDAO;
import com.revature.models.Card;
import com.revature.models.Deck;

public class DeckService 
{
	private static DeckDAO deckDAO = new DeckDAO();
	
	public Deck createNewDeck(String deckName)
	{
		Deck newDeck = new Deck(deckName);
		deckDAO.addToDeckMap(newDeck);
		return newDeck;
	}
	
	public void addToDeck(Deck deck, Card card)
	{
		if(!deck.isFull())
			deck.addCard(card);
	}
	
	public Deck getDeck(int id)
	{
		return deckDAO.findDeck(id);
	}
}
