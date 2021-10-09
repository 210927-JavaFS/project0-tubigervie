package com.revature.daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.revature.models.Card;
import com.revature.models.Card.CardType;
import com.revature.models.Card.ClassType;
import com.revature.models.Card.RarityType;
import com.revature.models.Minion;
import com.revature.models.Weapon;
import com.revature.utils.ConnectionUtil;

public class CardDAO {

	public HashMap<Integer, Card> cardMap;
	
	public CardDAO() 
	{
		if(cardMap == null)
			initializeCards();
	}
	
	public Card findCard(int id) {
		return (cardMap.containsKey(id)) ? cardMap.get(id) : null;
	}
	
	private void initializeCards()
	{
		System.out.println("Card database being initialized");
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
				
				if(card != null && !cardMap.containsKey(card.getIndex()))
					cardMap.put(card.getIndex(), card);
			}
		}
		catch(SQLException e)
		{
			System.out.println("Could not connect to database instance.");
		}
	}
}
