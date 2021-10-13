package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.AdminUser;
import com.revature.models.ModeratorUser;
import com.revature.models.StandardUser;
import com.revature.models.User;
import com.revature.models.User.AccountType;
import com.revature.utils.ConnectionUtil;

public class LoginDAO 
{
	public boolean addNewLogin(User user)
	{
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
			e.printStackTrace();
		}
		return false;
	}
	
	public User findExistingLogin(String username, String password)
	{
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
			e.printStackTrace();
		}
		return null;
	}
}
