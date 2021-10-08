package com.revature.models;

public class Card {
	static final int TOTAL_CARDS = 238;
	
	public enum CardType {spell, minion, weapon};
	public enum RarityType {common, rare, epic, legendary};
	public enum ClassType {shaman, mage, warrior, rogue, neutral, paladin, hunter, warlock, druid, priest}
	
	protected int index;
	
	protected String name;
	protected int manaCost;
	protected String description;
	protected RarityType rarity;
	protected CardType type;
	protected ClassType classType;
	
	public Card(int index, String name, int manaCost, String description, RarityType rarity, CardType type, ClassType classType)
	{
		this.index = index;
		this.name = name;
		this.manaCost = manaCost;
		this.description = description;
		this.rarity = rarity;
		this.type = type;
		this.classType = classType;
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	public String getName()
	{
		return this.name.toUpperCase();
	}
	
	public int getManaCost()
	{
		return this.manaCost;
	}
	
	public ClassType getClassType()
	{
		return this.classType;
	}
	
	public CardType getCardType()
	{
		return this.type;
	}
	
	public RarityType getRarityType()
	{
		return this.rarity;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	@Override
	public String toString()
	{
		return String.format("\nCard - %S (%d/%d)\nClass - %S\nType - %S\nRarity - %S\nMana Cost - %d\nDescription - %s", name, index, TOTAL_CARDS, classType, type, rarity, manaCost, description);
	}
}
