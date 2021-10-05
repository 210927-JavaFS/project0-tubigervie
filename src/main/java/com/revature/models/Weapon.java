package com.revature.models;

public class Weapon extends Card
{
	protected int attackValue;
	protected int charges;
	
	public Weapon(String name, int manaCost, String description, RarityType rarity, int attackValue, int charges) {
		super(name, manaCost, description, rarity);
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
