package com.revature.models;

import java.util.ArrayList;


public class StandardUser extends User
{
	private ArrayList<Card> inventory = new ArrayList<Card>();
	
	private ArrayList<Deck> decks = new ArrayList<Deck>();
	
	public StandardUser(String username, String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}
	
	public StandardUser(String username, String password, ArrayList<Card> inventory) {
		super(username, password);
		this.inventory = inventory;
	}
	
	public ArrayList<Card> getInventory()
	{
		return this.inventory;
	}
	
	public ArrayList<Deck> getDecks()
	{
		return this.decks;
	}
	
	public void addToInventory(Card card)
	{
		this.inventory.add(card);
	}
	
	public Deck createNewDeck(String deckName)
	{
		Deck newDeck = new Deck(deckName);
		decks.add(newDeck);
		return newDeck;
	}
	
	public void addToDeck(Deck deck, Card card)
	{
		if(!deck.isFull())
			deck.addCard(card);
	}
}
