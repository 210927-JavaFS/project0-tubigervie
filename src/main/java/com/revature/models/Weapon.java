package com.revature.models;

public class Weapon extends Card
{
	protected int attackValue;
	protected int charges;
	
	public Weapon(int index, String name, int manaCost, String description, RarityType rarity, CardType type, ClassType classType, int attackValue, int charges) {
		super(index, name, manaCost, description, rarity, type, classType);
		this.attackValue = attackValue;
		this.charges = charges;
		// TODO Auto-generated constructor stub
	}
	
	public int getAttackValue()
	{
		return this.attackValue;
	}
	
	public int getCharges()
	{
		return this.charges;
	}

}
