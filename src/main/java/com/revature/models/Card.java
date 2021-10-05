package com.revature.models;

public class Card {
	static final int TOTAL_CARDS = 238;
	
	public enum CardType {spell, minion, weapon};
	public enum RarityType {common, rare, epic, legendary};
	
	protected int index;
	
	protected String name;
	protected int manaCost;
	protected String description;
	protected RarityType rarity;
	protected CardType type;
	
	public Card(int index, String name, int manaCost, String description, RarityType rarity, CardType type)
	{
		this.index = index;
		this.name = name;
		this.manaCost = manaCost;
		this.description = description;
		this.rarity = rarity;
		this.type = type;
	}
	
	public int getIndex()
	{
		return this.index;
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
	
	@Override
	public String toString()
	{
		return String.format("%s (%d/%d)\nMana Cost: %d\nRarity: %s\nDescription: %s\n", name, index, TOTAL_CARDS, manaCost, rarity.toString(), description);
	}
}
