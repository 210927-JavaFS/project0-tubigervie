package com.revature.controllers;

import java.util.ArrayList;

import com.revature.models.Card;
import com.revature.models.Deck;
import com.revature.models.Minion;
import com.revature.models.StandardUser;
import com.revature.models.User;
import com.revature.models.Weapon;
import com.revature.models.User.AccountType;

public class AdminSearchPageMenuController extends ModeratorSearchPageMenuController
{
	@Override
	protected boolean enterAccountSearch(User self)
	{
		boolean inAccountSearch = true;
		while(inAccountSearch)
		{
			System.out.println("\nSearch - How would you like to search by? \n" + "NAME | " + "TYPE | " + "RETURN");
			String response = scan.nextLine().trim();
			switch(response.toLowerCase())
			{
				case "name":
					while(true)
					{
						System.out.println("\nEnter the name of the account you would like to look up or type R to return.");
						String search = scan.nextLine().trim().toLowerCase();
						if(search.equals("r")) break;
						User user = modService.findUserByName(search);
						if(user != null)
							enterAccountView(user);
						else 
							System.out.println("\nCould not find any results.");
					}
					continue;
				case "return":
					inAccountSearch = false;
					break;
				case "type":
					while(true)
					{
						System.out.println("\nSearch - What type of account would you like to search by?\nSTANDARD | MOD | ADMIN | RETURN");
						String search = scan.nextLine().trim().toLowerCase();
						if(search.equals("return")) break;
						ArrayList<User> users = null;
						switch(search)
						{
							case "standard":
								users = modService.findUsersByType(AccountType.standard);
								break;
							case "mod":
								users = modService.findUsersByType(AccountType.moderator);
								break;
							case "admin":
								users = modService.findUsersByType(AccountType.admin);
								break;
							default:
								System.out.println("Invalid input. Try again.");
								continue;
						}
						if(users != null) 
							users.sort((c1, c2) -> ((Integer)c1.getUserID()).compareTo((Integer)c2.getUserID()));
						while(true)
						{
							System.out.println();
							for(User user : users)
							{
								if(user.getUserID() == self.getUserID())
								{
									users.remove(user);
									break;
								}
							}
							if(users.size() == 0)
							{
								System.out.println("No other users found!");
								break;
							}
							for(int i = 0; i < users.size(); i++)
							{
								User user = users.get(i);
								System.out.println(String.format("%d) %s", i + 1, user.getUserName()));
							}
							System.out.println("\nType in the number of the account you would like to examine or type RETURN.");
							String response2 = scan.nextLine().trim();
							try {
								if(response2.equals("return")) break;
								int number = Integer.parseInt(response2);
								User user = users.get(number - 1);
								enterAccountView(user);
								break;
							}
							catch(NumberFormatException | IndexOutOfBoundsException e){
								System.out.println("Invalid input. Try again. \n");
								continue;
							}
						}
						continue;
					}
					break;
				default:
					System.out.println("Invalid input. Try again.");
					break;
			}
		}
		return true;
	}
	
	@Override
	protected void enterAccountView(User user)
	{
		while(true)
		{
			System.out.println(String.format("\n%s", user.getUserName()));
			System.out.println("Account - What would you like to do? \n" + "INFO | " + "DELETE | " + "RETURN");
			String response = scan.nextLine().trim();
			switch(response.toLowerCase())
			{
				case "return":
					return;
				case "info":
					System.out.println(String.format("\nUsername: %s", user.getUserName()));
					System.out.println("Account Type: " + user.getAccountType());
					if(user.hasAnInventory())
					{
						ArrayList<Integer> deckIDs = userService.getDecks((StandardUser)user);
						if(deckIDs.size() > 0)
						{
							System.out.println(String.format("Inventory: %d card(s)", userService.getInventory((StandardUser) user).size()));
							System.out.println("Decks:");
							for(int i = 0; i < deckIDs.size(); i++)
							{
								Deck deck = deckService.getDeck(deckIDs.get(i));
								System.out.println(String.format("%d) %s", i+1, deck.getName()));
							}
						}	
					}
					break;
				case "delete":
					boolean inDelete = true;
					while(inDelete)
					{
						System.out.println("\nAre you sure you would like to permanently delete this account? Y/N");
						String response2 = scan.nextLine().trim();
						switch(response2.toLowerCase())
						{
							case "y":
								if(modService.deleteUser(user.getUserID()))
									System.out.println("\nAccount successfully deleted.");	
								return;
							case "n":
								inDelete = false;
								break;
						}
					}
					break;
				default:
					System.out.println("Invalid input. Try again.");
					break;
			}
		}		
	}
	
	@Override
	protected void enterCardView(Card card, StandardUser user)
	{
		switch(card.getCardType())
		{
			case minion:
				System.out.println(((Minion)card).toString());
				break;
			case weapon:
				System.out.println(((Weapon)card).toString());
				break;
			default:
				System.out.println(card.toString());
				break;
		}
	}
}
