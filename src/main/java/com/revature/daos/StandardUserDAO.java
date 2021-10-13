package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.revature.models.AdminUser;
import com.revature.models.ModeratorUser;
import com.revature.models.StandardUser;
import com.revature.models.User;
import com.revature.models.User.AccountType;
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
		/*System.out.println("should not be called");
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
		}*/
		return false;
	}
	
	public boolean updateUser(User user)
	{
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "UPDATE standard_users SET inventory = ?, decks = ? WHERE user_id = ?";
			
			StringBuilder invBuilder = new StringBuilder("");
			ArrayList<Integer> inv = ((StandardUser) user).getInventoryArray(false);
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
				String inventoryString = result.getString("inventory");
				HashMap<Integer, Integer> inventory = new HashMap<Integer, Integer>();
				
				if(inventoryString != null && !inventoryString.isBlank()) 
				{
					String[] inventoryParts = inventoryString.split(",");
					
					for(String cardID : inventoryParts)
					{
						Integer num = Integer.valueOf(cardID);
						if(inventory.containsKey(num))
							inventory.put(num, inventory.get(num) + 1);
						else
							inventory.put(num, 1);
					}
					user.setInventory(inventory);
				}

				
				String deckString = result.getString("decks");
				ArrayList<Integer> decks = new ArrayList<Integer>();
				
				if(deckString != null && !deckString.isBlank()) 
				{
					String[] deckParts = deckString.split(",");					
					for(String deckID : deckParts) {
						decks.add(Integer.valueOf(deckID));
					}				
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
	
	public User findUser(String username)
	{
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * from logins WHERE user_name = ? and acc_type = ?";
			
			int count = 0;
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(++count, username);
			statement.setString(++count, User.AccountType.standard.toString());
			
			statement.execute();
			
			ResultSet result = statement.executeQuery();
			
			User loadedUser = null;
			
			if(result.next())
			{
				loadedUser = new StandardUser(result.getString("user_name"), result.getString("user_pass"), AccountType.valueOf(result.getString("acc_type")));
				loadedUser.setUserID(result.getInt("user_id"));
			}	
			return loadedUser;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<User> findUsers(AccountType acc_type)
	{
		ArrayList<User> users = new ArrayList<User>();
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * from logins WHERE acc_type = ?";
			
			int count = 0;
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(++count, acc_type.toString());
			
			statement.execute();
			
			ResultSet result = statement.executeQuery();
			
			User loadedUser = null;
			
			while(result.next())
			{
				switch(acc_type) {
					case admin:
						loadedUser = new AdminUser(result.getString("user_name"), result.getString("user_pass"), AccountType.valueOf(result.getString("acc_type")));
						break;
					case moderator:
						loadedUser = new ModeratorUser(result.getString("user_name"), result.getString("user_pass"), AccountType.valueOf(result.getString("acc_type")));
						break;
					case standard:
						loadedUser = new StandardUser(result.getString("user_name"), result.getString("user_pass"), AccountType.valueOf(result.getString("acc_type")));
						break;				
				}
				loadedUser.setUserID(result.getInt("user_id"));
			}	
			users.add(loadedUser);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return users;
	}

}