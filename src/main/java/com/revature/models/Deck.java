package com.revature.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Deck {
	static final int MAX_CARDS = 20;
	
	static int deckIDCounter = 0;
	
	private int deckID;
	private String name;
	private int currentCardCount = 0;
	private HashMap<Integer, Integer> deckMap = new HashMap<Integer, Integer>();
	
	public Deck(String name) 
	{
		this.deckID = deckIDCounter;
		this.name = name;
		this.currentCardCount = 0;
	}
	
	public Deck(int deckID, int cardCount, String name, HashMap<Integer, Integer> map) 
	{
		this.deckID = deckID;
		this.name = name;
		this.currentCardCount = cardCount;
		this.deckMap = map;
	}
	
	public HashMap<Integer, Integer> getDeckMap() 
	{
		return deckMap;
	}
	
	public ArrayList<Integer> createDeckArray()
	{
		ArrayList<Integer> deckArray = new ArrayList<Integer>();
		for(Map.Entry<Integer, Integer> entry : deckMap.entrySet())
		{
			for(int i = 0; i < entry.getValue(); i++)
				deckArray.add(entry.getKey());
		}
		return deckArray;
	}
	
	public int getDeckID()
	{
		return deckID;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getCardCount()
	{
		return currentCardCount;
	}
	
	public void addCard(Card card)
	{
		if(deckMap.containsKey(card.index))
			deckMap.put(card.index, deckMap.get(card.index) + 1);
		else
			deckMap.put(card.index, 1);
		currentCardCount++;
	}
	
	public void setID(int id)
	{
		this.deckID = id;
	}
	
	public boolean isFull()
	{
		return currentCardCount == MAX_CARDS;
	}
	
	public void removeCard(Card card)
	{
		if(deckMap.containsKey(card.index))
			deckMap.put(card.index, deckMap.get(card.index) - 1);
		if(deckMap.get(card.index) == 0)
			deckMap.remove(card.index);
		currentCardCount--;
	}
	
	@Override
	public String toString()
	{
		return String.format("%s (%d/%d)", name, currentCardCount, MAX_CARDS);
	}
}
