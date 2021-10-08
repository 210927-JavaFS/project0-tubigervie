package com.revature.models;

public class Minion extends Card {

	protected int attack;
	protected int defense;
	
	public Minion(int index, String name, int manaCost, String description, RarityType rarity, CardType type, ClassType classType, int attack, int defense) {
		super(index, name, manaCost, description, rarity, type, classType);
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
