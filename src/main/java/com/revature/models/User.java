package com.revature.models;

public abstract class User {
	protected String username;
	protected String password;
	
	public User(String username, String password) 
	{
		this.username = username;
		this.password = password;
	}
	
	public String getUserName()
	{
		return username;
	}
}
