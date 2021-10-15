package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Card;
import com.revature.models.Card.CardType;
import com.revature.models.Card.ClassType;
import com.revature.models.Card.RarityType;
import com.revature.models.Minion;
import com.revature.models.Weapon;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.TokenizerUtil;

public class CardDAO {

	private static Logger log = LoggerFactory.getLogger(CardDAO.class);
	
	public HashMap<Integer, Card> cardMap;
	
	public HashMap<String, ArrayList<Integer>> tokenMap;
	
	public CardDAO() 
	{
		if(cardMap == null)
			initializeCards();
	}
	
	public Card findCard(int id) {
		return (cardMap.containsKey(id) ? cardMap.get(id) : retrieveCardByID(id));
	}
	
	public ArrayList<Card> findCardsByQuery(String name)
	{
		HashSet<Card> hits = new HashSet<Card>();
		ArrayList<String> tokens = TokenizerUtil.getTokens(name);
		HashSet<Integer> commonIDs = new HashSet<Integer>();
		for(String token : tokens)
		{
			if(tokenMap.containsKey(token))
				for(Integer id : tokenMap.get(token))
					commonIDs.add(id);
		}
		for(Integer id : commonIDs)
		{
			Card card = findCard(id);
			if(card != null)
				hits.add(card);
		}
		return new ArrayList<Card>(hits);
	}
	
	public ArrayList<Card> findCardsByType(CardType c_type)
	{
		ArrayList<Card> cardList = new ArrayList<Card>();
		log.info(String.format("Attempting to load cards of type %s from cards table in database.", c_type.toString()));
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT c.card_id, c.card_name,\r\n"
					+ "	c.mana_cost, c.description, c.rarity, c.card_type,\r\n"
					+ "	c.class_type, m.attack, m.health, w.wepAttack, w.charges\r\n"
					+ "FROM cards c \r\n"
					+ "	FULL JOIN minions m ON (c.card_id = m.card_id)\r\n"
					+ "	FULL JOIN weapons w ON (c.card_id = w.card_id)\r\n"
					+ " WHERE c.card_type = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			int count = 0;
			statement.setString(++count, c_type.toString());
			ResultSet result = statement.executeQuery();
			while(result.next())
			{
				Card card;
				CardType cardType = CardType.valueOf(result.getString("card_type"));
				switch(cardType) {
				case minion:
					card = new Minion(
							result.getInt("card_id"),
							result.getString("card_name"),
							result.getInt("mana_cost"),
							result.getString("description"),
							RarityType.valueOf(result.getString("rarity")),
							CardType.valueOf(result.getString("card_type")),
							ClassType.valueOf(result.getString("class_type")), 
							result.getInt("attack"), 
							result.getInt("health"));
					break;
				case weapon:
					card = new Weapon(
							result.getInt("card_id"),
							result.getString("card_name"),
							result.getInt("mana_cost"),
							result.getString("description"),
							RarityType.valueOf(result.getString("rarity")),
							CardType.valueOf(result.getString("card_type")),
							ClassType.valueOf(result.getString("class_type")), 
							result.getInt("wepAttack"), 
							result.getInt("charges"));
					break;
				default:
					card = new Card(
							result.getInt("card_id"),
							result.getString("card_name"),
							result.getInt("mana_cost"),
							result.getString("description"),
							RarityType.valueOf(result.getString("rarity")),
							CardType.valueOf(result.getString("card_type")),
							ClassType.valueOf(result.getString("class_type")));
					break;		
				}
				if(card != null && !cardMap.containsKey(card.getIndex()))
					cardMap.put(card.getIndex(), card);
				cardList.add(card);
			}
		}
		catch(SQLException e)
		{
			log.error("Could not execute SQL statement.");
			log.error(e.toString());
		}
		return cardList;
	}
	
	private Card retrieveCardByID(int id)
	{
		Card card = null;
		log.info(String.format("Attempting to load card (ID: %d) from cards table in database.", id));
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT c.card_id, c.card_name,\r\n"
					+ "	c.mana_cost, c.description, c.rarity, c.card_type,\r\n"
					+ "	c.class_type, m.attack, m.health, w.wepAttack, w.charges\r\n"
					+ "FROM cards c \r\n"
					+ "	FULL JOIN minions m ON (c.card_id = m.card_id)\r\n"
					+ "	FULL JOIN weapons w ON (c.card_id = w.card_id)\r\n"
					+ " WHERE LOWER(c.card_id) = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			int count = 0;
			statement.setInt(++count, id);
			ResultSet result = statement.executeQuery();
			while(result.next())
			{
				CardType cardType = CardType.valueOf(result.getString("card_type"));
				switch(cardType) {
				case minion:
					card = new Minion(
							result.getInt("card_id"),
							result.getString("card_name"),
							result.getInt("mana_cost"),
							result.getString("description"),
							RarityType.valueOf(result.getString("rarity")),
							CardType.valueOf(result.getString("card_type")),
							ClassType.valueOf(result.getString("class_type")), 
							result.getInt("attack"), 
							result.getInt("health"));
					break;
				case weapon:
					card = new Weapon(
							result.getInt("card_id"),
							result.getString("card_name"),
							result.getInt("mana_cost"),
							result.getString("description"),
							RarityType.valueOf(result.getString("rarity")),
							CardType.valueOf(result.getString("card_type")),
							ClassType.valueOf(result.getString("class_type")), 
							result.getInt("wepAttack"), 
							result.getInt("charges"));
					break;
				default:
					card = new Card(
							result.getInt("card_id"),
							result.getString("card_name"),
							result.getInt("mana_cost"),
							result.getString("description"),
							RarityType.valueOf(result.getString("rarity")),
							CardType.valueOf(result.getString("card_type")),
							ClassType.valueOf(result.getString("class_type")));
					break;		
				}
				
				if(card != null && !cardMap.containsKey(card.getIndex()))
					cardMap.put(card.getIndex(), card);
			}
		}
		catch(SQLException e)
		{
			log.error("Could not execute SQL statement.");
			log.error(e.toString());
		}
		return card;
	}
	
	private void initializeCards()
	{
		log.info("Initializing cards from database...");
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT c.card_id, c.card_name,\r\n"
					+ "	c.mana_cost, c.description, c.rarity, c.card_type,\r\n"
					+ "	c.class_type, m.attack, m.health, w.charges\r\n"
					+ "FROM cards c \r\n"
					+ "	FULL JOIN minions m ON (c.card_id = m.card_id)\r\n"
					+ "	FULL JOIN weapons w ON (c.card_id = w.card_id);";
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			cardMap = new HashMap<Integer, Card>();
			tokenMap = new HashMap<String, ArrayList<Integer>>();
			while(result.next())
			{
				CardType cardType = CardType.valueOf(result.getString("card_type"));
				Card card;
				switch(cardType) {
				case minion:
					card = new Minion(
							result.getInt("card_id"),
							result.getString("card_name"),
							result.getInt("mana_cost"),
							result.getString("description"),
							RarityType.valueOf(result.getString("rarity")),
							CardType.valueOf(result.getString("card_type")),
							ClassType.valueOf(result.getString("class_type")), 
							result.getInt("attack"), 
							result.getInt("health"));
					break;
				case weapon:
					card = new Weapon(
							result.getInt("card_id"),
							result.getString("card_name"),
							result.getInt("mana_cost"),
							result.getString("description"),
							RarityType.valueOf(result.getString("rarity")),
							CardType.valueOf(result.getString("card_type")),
							ClassType.valueOf(result.getString("class_type")), 
							result.getInt("attack"), 
							result.getInt("charges"));
					break;
				default:
					card = new Card(
							result.getInt("card_id"),
							result.getString("card_name"),
							result.getInt("mana_cost"),
							result.getString("description"),
							RarityType.valueOf(result.getString("rarity")),
							CardType.valueOf(result.getString("card_type")),
							ClassType.valueOf(result.getString("class_type")));
					break;		
				}
				if(card != null)
				{
					ArrayList<String> cardTokens = TokenizerUtil.getTokens(card.getName());
					for(String token : cardTokens)
					{
						if(!tokenMap.containsKey(token))
						{
							ArrayList<Integer> cardIDs = new ArrayList<Integer>();
							cardIDs.add(card.getIndex());
							tokenMap.put(token, cardIDs);
						}
						else
							tokenMap.get(token).add(card.getIndex());
					}
					if(!cardMap.containsKey(card.getIndex()))
						cardMap.put(card.getIndex(), card);
				}
			}
		}
		catch(SQLException e)
		{
			log.error("Could not execute SQL statement.");
			log.error(e.toString());
		}
	}
}
