package com.revature.models;

import java.util.ArrayList;


public class StandardUser extends User
{
	private ArrayList<Integer> inventory = new ArrayList<Integer>();
	
	private ArrayList<Integer> decks = new ArrayList<Integer>();
	
	public StandardUser(String username, String password, AccountType type) {
		super(username, password, type);
		// TODO Auto-generated constructor stub
	}
	
	public StandardUser(String username, String password, AccountType type, ArrayList<Integer> inventory) {
		super(username, password, type);
		this.inventory = inventory;
	}
	
	public ArrayList<Integer> getInventory()
	{
		return this.inventory;
	}
	
	public ArrayList<Integer> getDecks()
	{
		return this.decks;
	}
	
	public void addToInventory(int cardID)
	{
		this.inventory.add(cardID);
	}
	
	public void addToDecks(int deckID)
	{
		this.decks.add(deckID);
	}
	
	public void removeDeck(int deckID)
	{
		this.decks.remove((Integer)deckID);
	}
	
	public void setInventory(ArrayList<Integer> newInv)
	{
		this.inventory = newInv;
	}
	
	public void setDeckList(ArrayList<Integer> newDecks)
	{
		this.decks = newDecks;
	}
}
