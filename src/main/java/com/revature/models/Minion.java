package com.revature.models;

public class Minion extends Card {

	protected int attack;
	protected int defense;
	
	public Minion(String name, int manaCost, String description, RarityType rarity, int attack, int defense) {
		super(name, manaCost, description, rarity);
		this.attack = attack;
		this.defense = defense;
	}
	
	public int getAttackValue()
	{
		return this.attack;
	}
	
	public int getDefenseValue()
	{
		return this.defense;
	}

}
