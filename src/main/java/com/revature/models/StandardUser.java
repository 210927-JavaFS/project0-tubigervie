package com.revature.models;

import java.util.ArrayList;


public class StandardUser extends User
{
	private ArrayList<Card> inventory = new ArrayList<Card>();
	
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
	
	public void addToInventory(Card card)
	{
		this.inventory.add(card);
	}
}
