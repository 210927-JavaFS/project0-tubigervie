package com.revature.controllers;

import java.util.Scanner;

import com.revature.models.User;

public abstract class HomePageMenuController {

	protected static Scanner scan = new Scanner(System.in);
	
	public abstract boolean enterHomePage(User user);
}
