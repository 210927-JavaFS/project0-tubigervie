package com.revature.models;

public class Card {
	public enum RarityType {common, rare, epic, legendary};
	
	protected String name;
	protected int manaCost;
	protected String description;
	protected RarityType rarity;
	
	public Card(String name, int manaCost, String description, RarityType rarity)
	{
		this.name = name;
		this.manaCost = manaCost;
		this.description = description;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getManaCost()
	{
		return this.manaCost;
	}
	
	public String getDescription()
	{
		return this.description;
	}
}
