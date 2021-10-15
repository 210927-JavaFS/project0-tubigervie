package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.AdminUser;
import com.revature.models.ModeratorUser;
import com.revature.models.StandardUser;
import com.revature.models.User;
import com.revature.models.User.AccountType;
import com.revature.utils.ConnectionUtil;

public class LoginDAO 
{
	private static Logger log = LoggerFactory.getLogger(LoginDAO.class);
	
	public boolean addNewLogin(User user)
	{
		log.info(String.format("Attempting to insert a new login (ID: %d) to logins table in database.", user.getUserID()));
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "INSERT INTO logins (user_name, user_pass, acc_type) "
							+ "VALUES (?, ?, ?)";
			int count = 0;
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(++count, user.getUserName());
			statement.setString(++count, user.getPassword());
			statement.setString(++count, user.getAccountType().toString());
			
			statement.execute();
			
			user.setUserID(findExistingLogin(user.getUserName(), user.getPassword()).getUserID()); //kind of a hack find a way to do this better
			
			return true;
		}
		catch(SQLException e)
		{
			log.error("Could not execute SQL statement.");
			log.error(e.toString());
		}
		return false;
	}
	
	public User findExistingLogin(String username, String password)
	{
		log.info(String.format("Attempting to find login (NAME: %s) from logins table in database.", username));
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * from logins WHERE user_name = ? and user_pass = ?";
			
			int count = 0;
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(++count, username);
			statement.setString(++count, password);
			
			statement.execute();
			
			ResultSet result = statement.executeQuery();
			
			User loadedUser = null;
			
			if(result.next())
			{
				switch(AccountType.valueOf(result.getString("acc_type"))) 
				{
				case admin:
					loadedUser = new AdminUser(result.getString("user_name"), result.getString("user_pass"), AccountType.valueOf(result.getString("acc_type")));
					break;
				case moderator:
					loadedUser = new ModeratorUser(result.getString("user_name"), result.getString("user_pass"), AccountType.valueOf(result.getString("acc_type")));
					break;
				case standard:
					loadedUser = new StandardUser(result.getString("user_name"), result.getString("user_pass"), AccountType.valueOf(result.getString("acc_type")));
					break;
				default:
					loadedUser = new StandardUser(result.getString("user_name"), result.getString("user_pass"), AccountType.valueOf(result.getString("acc_type")));
					break;
				}
				loadedUser.setUserID(result.getInt("user_id"));
			}	
			return loadedUser;
		}
		catch(SQLException e)
		{
			log.error("Could not execute SQL statement.");
			log.error(e.toString());
		}
		return null;
	}
	
	public boolean deleteLogin(int id)
	{
		log.info(String.format("Attempting to delete login (ID: %d) from logins table in database.", id));
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "DELETE from logins WHERE user_id = ?";
			int count = 0;
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setInt(++count, id);
			statement.execute();
		}
		catch(SQLException e)
		{
			log.error("Could not execute SQL statement.");
			log.error(e.toString());
			return false;
		}
		return true;
	}
	
	public boolean checkIfExists(String username)
	{
		log.info(String.format("Attempting to find login (NAME: %s) from logins table in database.", username));
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * from logins WHERE user_name = ?";
			
			int count = 0;
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(++count, username);
			
			statement.execute();
			
			ResultSet result = statement.executeQuery();
			
			if(result.next())
			{
				return true;
			}	
		}
		catch(SQLException e)
		{
			log.error("Could not execute SQL statement.");
			log.error(e.toString());
			return true;
		}
		return false;
	}
}
