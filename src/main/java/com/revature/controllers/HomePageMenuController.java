package com.revature.controllers;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.User;

public abstract class HomePageMenuController {

	protected static Logger log = LoggerFactory.getLogger(AdminCreatePageMenuController.class);
	
	protected static Scanner scan = new Scanner(System.in);
	
	public abstract boolean enterHomePage(User user);
}
