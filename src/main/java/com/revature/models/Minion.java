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
	
	@Override
	public String toString()
	{
		return String.format("\nCard - %S (%d/%d)\nClass - %S\nType - %S\nRarity - %S\nMana Cost - %d\nAttack - %d\nHealth - %d\nDescription - %s", 
				name, index, TOTAL_CARDS, classType, type, rarity, manaCost, attack, defense, (description.isBlank()) ? "NONE" : description);
	}

}
