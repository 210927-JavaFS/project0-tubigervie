package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.revature.models.Deck;
import com.revature.models.StandardUser;
import com.revature.utils.ConnectionUtil;

public class DeckDAO {
	public HashMap<Integer, Deck> deckMap = new HashMap<Integer, Deck>();
	
	public void addToDeckMap(Deck deck)
	{
		if(!deckMap.containsKey(deck.getDeckID()))
			deckMap.put(deck.getDeckID(), deck);
	}
	
	public void removeFromDeckMap(Deck deck)
	{
		if(deckMap.containsKey(deck.getDeckID()))
			deckMap.remove(deck.getDeckID());
	}
	
	public boolean uploadDeck(StandardUser user, Deck deck)
	{
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "INSERT INTO decks (user_id, deck_name, card_list, card_count)"
							+ "VALUES (?, ?, ?, ?)";
			int count = 0;
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(++count, user.getUserID());
			statement.setString(++count, deck.getName());
			
			StringBuilder cards = new StringBuilder();
			ArrayList<Integer> deckArray = deck.createDeckArray();
			for(int i = 0; i < deckArray.size(); i++)
			{
				if(i == deckArray.size() - 1) 
					cards.append(deckArray.get(i));
				else
					cards.append(deckArray.get(i)+",");
			}	
			statement.setString(++count, cards.toString());
			statement.setInt(++count, deck.getCardCount());
			
			statement.execute();
			
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public Deck findExistingDeck(String deck_name)
	{
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * from decks WHERE deck_name = ?";
			
			int count = 0;
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(++count, deck_name);
			
			statement.execute();
			
			ResultSet result = statement.executeQuery();
			
			Deck newDeck = null;
			
			HashMap<Integer, Integer> deckCardMap = new HashMap<Integer, Integer>();
			if(result.next())
			{
				String deckString = result.getString("card_list");
				
				if(deckString != null && !deckString.isBlank()) 
				{
					String[] deckParts = deckString.split(",");
					
					for(String cardID : deckParts)
					{
						Integer id = Integer.valueOf(cardID);
						if(deckCardMap.containsKey(id))
							deckCardMap.put(id, (deckCardMap.get(id)) + 1);
						else
							deckCardMap.put(id, 1);
					}
				}
			}
			newDeck = new Deck(result.getInt("deck_id"), result.getInt("card_count"), result.getString("deck_name"), deckCardMap);
			return newDeck;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public Deck findExistingDeck(int deck_id) {
		if(deckMap.containsKey(deck_id) && deckMap.get(deck_id) != null)
			return deckMap.get(deck_id);
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * from decks WHERE deck_id = ?";
			
			int count = 0;
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setInt(++count, deck_id);
			
			statement.execute();
			
			ResultSet result = statement.executeQuery();
			
			Deck newDeck = null;
			
			HashMap<Integer, Integer> deckCardMap = new HashMap<Integer, Integer>();
			if(result.next())
			{
				String deckString = result.getString("card_list");
				
				if(deckString != null && !deckString.isBlank()) 
				{
					String[] deckParts = deckString.split(",");
					
					for(String cardID : deckParts)
					{
						Integer id = Integer.valueOf(cardID);
						if(deckCardMap.containsKey(id))
							deckCardMap.put(id, (deckCardMap.get(id)) + 1);
						else
							deckCardMap.put(id, 1);
					}
				}
			}
			newDeck = new Deck(result.getInt("deck_id"), result.getInt("card_count"), result.getString("deck_name"), deckCardMap);
			deckMap.put(newDeck.getDeckID(), newDeck);
			return newDeck;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean updateDeck(Deck deck)
	{
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "UPDATE decks SET card_list = ?, card_count = ? WHERE deck_id = ?";
			
			int count = 0;
			PreparedStatement statement = conn.prepareStatement(sql);
			
			StringBuilder cards = new StringBuilder();
			ArrayList<Integer> deckArray = deck.createDeckArray();
			for(int i = 0; i < deckArray.size(); i++)
			{
				if(i == deckArray.size() - 1) 
					cards.append(deckArray.get(i));
				else
					cards.append(deckArray.get(i)+",");
			}	
			statement.setString(++count, cards.toString());
			statement.setInt(++count, deck.getCardCount());
			statement.setInt(++count, deck.getDeckID());
			
			statement.execute();			
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteDeck(Deck deck)
	{
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "DELETE FROM decks WHERE deck_id = ?";
			
			int count = 0;
			PreparedStatement statement = conn.prepareStatement(sql);
		
			statement.setInt(++count, deck.getDeckID());
			statement.execute();			
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean loadDecks(StandardUser user)
	{
		try(Connection conn = ConnectionUtil.getConnection())
		{
			HashMap<Integer, Integer> deckCardMap = new HashMap<Integer, Integer>();
			for(Integer deckID : user.getDecks())
			{
				String sql = "SELECT * from decks WHERE deck_id = ?";
				
				int count = 0;
				
				PreparedStatement statement = conn.prepareStatement(sql);
				
				statement.setInt(++count, deckID);
				statement.execute();
				
				ResultSet result = statement.executeQuery();
				Deck newDeck = null;
				if(result.next())
				{
					String deckString = result.getString("card_list");
					
					if(deckString != null && !deckString.isBlank()) 
					{
						String[] deckParts = deckString.split(",");
						
						for(String cardID : deckParts)
						{
							Integer id = Integer.valueOf(cardID);
							if(deckCardMap.containsKey(id))
								deckCardMap.put(id, (deckCardMap.get(id)) + 1);
							else
								deckCardMap.put(id, 1);
						}
					}	
					newDeck = new Deck(result.getInt("deck_id"), result.getInt("card_count"), result.getString("deck_name"), deckCardMap);
					newDeck.setID(findExistingDeck(newDeck.getName()).getDeckID());
					deckMap.put(newDeck.getDeckID(), newDeck);
				}	
			}
				
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
