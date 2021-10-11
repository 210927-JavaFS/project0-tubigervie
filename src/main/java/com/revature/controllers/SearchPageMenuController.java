package com.revature.controllers;

import java.util.Scanner;

import com.revature.models.User;

public abstract class SearchPageMenuController {
	
	protected static Scanner scan = new Scanner(System.in);
	public abstract boolean enterSearchPage(User user);
}
