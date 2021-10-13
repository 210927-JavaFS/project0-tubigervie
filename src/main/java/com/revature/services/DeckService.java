package com.revature.services;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.revature.daos.DeckDAO;
import com.revature.models.Card;
import com.revature.models.Deck;
import com.revature.models.StandardUser;

public class DeckService 
{
	private static DeckDAO deckDAO = new DeckDAO();
	
	public Deck createNewDeck(String deckName, StandardUser user)
	{
		Deck deckTemplate = new Deck(deckName);
		deckDAO.uploadDeck(user, deckTemplate);
		Deck newDeck = deckDAO.findExistingDeck(deckTemplate.getName());
		deckDAO.addToDeckMap(newDeck);
		return newDeck;
	}
	
	public void addToDeck(Deck deck, Card card)
	{
		if(!deck.isFull()) 
		{
			deck.addCard(card);
			deckDAO.updateDeck(deck);	
		}
	}
	
	public void removeFromDeck(Deck deck, Card card)
	{
		deck.removeCard(card);
		deckDAO.updateDeck(deck);
	}
	
	public Deck getDeck(int id)
	{
		return deckDAO.findExistingDeck(id);
	}
	
	public void deleteDeck(Deck deck)
	{
		if(deckDAO.deckMap.containsKey(deck.getDeckID()))
			deckDAO.removeFromDeckMap(deck);
		deckDAO.deleteDeck(deck);
	}
	
	public HashMap<Integer, Integer> createDeckMapCopy(Deck deck)
	{
		HashMap<Integer, Integer> copy = new HashMap<Integer, Integer>();
		for(Map.Entry<Integer, Integer> entry : deck.getDeckMap().entrySet())
		{
			copy.put(entry.getKey(), entry.getValue());
		}
		return copy;
	}
	
	public TreeMap<Integer, Integer> createDeckTreeMap(Deck deck)
	{
		TreeMap<Integer, Integer> copy = new TreeMap<Integer, Integer>(deck.getDeckMap());
		return copy;
	}
	
	public HashMap<Integer, Integer> getSubsetInventoryMap(HashMap<Integer, Integer> deckMap, HashMap<Integer, Integer> inventoryMap) 
	{
		HashMap<Integer, Integer> subsetInventoryMap = new HashMap<Integer, Integer>();
		
		for(Map.Entry<Integer, Integer> entry : inventoryMap.entrySet())
		{
			subsetInventoryMap.put(entry.getKey(), entry.getValue());
		}
		
		for(Map.Entry<Integer, Integer> entry : deckMap.entrySet())
		{
			if(subsetInventoryMap.containsKey(entry.getKey())) {
				subsetInventoryMap.put(entry.getKey(), subsetInventoryMap.get(entry.getKey()) - deckMap.get(entry.getKey()));
				if(subsetInventoryMap.get(entry.getKey()) == 0)
					subsetInventoryMap.remove(entry.getKey());
			}
		}
		
		return subsetInventoryMap;
	}
	
	public TreeMap<Integer, Integer> getSubsetInventoryTreeMap(HashMap<Integer, Integer> deckMap, TreeMap<Integer, Integer> inventoryMap) //Refactor this
	{
		TreeMap<Integer, Integer> subsetInventoryMap = new TreeMap<Integer, Integer>();
		
		for(Map.Entry<Integer, Integer> entry : inventoryMap.entrySet())
		{
			subsetInventoryMap.put(entry.getKey(), entry.getValue());
		}
		
		for(Map.Entry<Integer, Integer> entry : deckMap.entrySet())
		{
			if(subsetInventoryMap.containsKey(entry.getKey())) {
				subsetInventoryMap.put(entry.getKey(), subsetInventoryMap.get(entry.getKey()) - deckMap.get(entry.getKey()));
				if(subsetInventoryMap.get(entry.getKey()) == 0)
					subsetInventoryMap.remove(entry.getKey());
			}
		}
		
		return subsetInventoryMap;
	}
	
	public void loadDecks(StandardUser user)
	{
		deckDAO.loadDecks(user);
	}
}
