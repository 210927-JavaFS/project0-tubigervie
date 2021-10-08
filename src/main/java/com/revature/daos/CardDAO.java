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
			String sql = "SELECT * From cards;";
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			cardMap = new HashMap<Integer, Card>();
			
			while(result.next())
			{
				Card card = new Card(
						result.getInt("card_id"),
						result.getString("card_name"),
						result.getInt("mana_cost"),
						result.getString("description"),
						RarityType.valueOf(result.getString("rarity")),
						CardType.valueOf(result.getString("card_type")),
						ClassType.valueOf(result.getString("class_type")));
				if(!cardMap.containsKey(card.getIndex()))
					cardMap.put(card.getIndex(), card);
			}
		}
		catch(SQLException e)
		{
			System.out.println("Could not connect to database instance.");
		}
	}
}
