package com.revature.models;

public class User {
	public enum AccountType {standard, moderator, admin};
	
	protected int userID = -1;
	protected String username;
	protected String password;
	protected AccountType accountType;
	
	public User(String username, String password, AccountType accountType) 
	{
		this.username = username;
		this.password = password;
		this.accountType = accountType;
	}
	
	public void setUserID(int id)
	{
		if(userID != -1)
		{
			System.out.println("Warning: attempt made to assign an id to an account that already has one.");
			return;
		}
		userID = id;
	}
	
	public int getUserID()
	{
		return userID;
	}
	
	public String getUserName()
	{
		return username;
	}
	
	public AccountType getAccountType()
	{
		return accountType;
	}
	
	public String getPassword()
	{
		return password;
	}
}
