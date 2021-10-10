package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.models.StandardUser;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class StandardUserDAO implements UserDAO
{
	
	@Override
	public User findUser(int id)
	{
		return null;
	}
	
	@Override
	public boolean addUser(User user)
	{
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "INSERT INTO standard_users (user_id, inventory, decks)"
							+ "VALUES (?, ?, ?)";
			
			StringBuilder invBuilder = new StringBuilder("");
			ArrayList<Integer> inv = ((StandardUser) user).getInventory();
			for(int i = 0; i < inv.size(); i++)
			{
				if(i == inv.size() - 1) 
					invBuilder.append(inv.get(i));
				else
					invBuilder.append(inv.get(i)+",");
			}
			
			StringBuilder deckBuilder = new StringBuilder("");
			ArrayList<Integer> decks = ((StandardUser) user).getDecks();
			for(int i = 0; i < decks.size(); i++)
			{
				if(i == decks.size() - 1) 
					deckBuilder.append(decks.get(i));
				else
					deckBuilder.append(decks.get(i)+",");
			}			
			
			int count = 0;
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(++count, user.getUserID());
			statement.setString(++count, invBuilder.toString());
			statement.setString(++count, deckBuilder.toString());
			
			statement.execute();			
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateUser(User user)
	{
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "UPDATE standard_users SET inventory = ?, decks = ? WHERE user_id = ?";
			
			StringBuilder invBuilder = new StringBuilder("");
			ArrayList<Integer> inv = ((StandardUser) user).getInventory();
			for(int i = 0; i < inv.size(); i++)
			{
				if(i == inv.size() - 1) 
					invBuilder.append(inv.get(i));
				else
					invBuilder.append(inv.get(i)+",");
			}
			
			StringBuilder deckBuilder = new StringBuilder("");
			ArrayList<Integer> decks = ((StandardUser) user).getDecks();
			for(int i = 0; i < decks.size(); i++)
			{
				if(i == decks.size() - 1) 
					deckBuilder.append(decks.get(i));
				else
					deckBuilder.append(decks.get(i)+",");
			}			
			
			int count = 0;
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(++count, invBuilder.toString());
			statement.setString(++count, deckBuilder.toString());
			statement.setInt(++count, user.getUserID());
			statement.execute();			
			return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean load(StandardUser user)
	{
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * from standard_users WHERE user_id = ?";
			
			int count = 0;
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setInt(++count, user.getUserID());
			
			statement.execute();
			
			ResultSet result = statement.executeQuery();
			
			if(result.next())
			{
				System.out.println("successfully found information from database");
				
				String inventoryString = result.getString("inventory");
				ArrayList<Integer> inventory = new ArrayList<Integer>();
				
				if(!inventoryString.isBlank()) 
				{
					String[] inventoryParts = inventoryString.split(",");
					
					for(String cardID : inventoryParts)
						inventory.add(Integer.valueOf(cardID));
				
					user.setInventory(inventory);
				}

				
				String deckString = result.getString("decks");
				ArrayList<Integer> decks = new ArrayList<Integer>();
				
				if(!deckString.isBlank()) 
				{
					String[] deckParts = deckString.split(",");
					
					for(String deckID : deckParts)
						inventory.add(Integer.valueOf(deckID));
				
					user.setDeckList(decks);
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