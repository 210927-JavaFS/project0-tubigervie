package com.revature.services;

import com.revature.models.Card;
import com.revature.models.StandardUser;
import com.revature.models.User;

public class StandardUserService extends UserService{

	public User createNewUser(String username, String password) {
		return new StandardUser(username, password);
	}
	
	public void addCard(StandardUser user, Card card) {
		user.addToInventory(card);
	}
	
}
