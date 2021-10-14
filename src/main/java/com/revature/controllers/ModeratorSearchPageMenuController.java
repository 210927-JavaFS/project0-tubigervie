package com.revature.controllers;

import java.util.ArrayList;

import com.revature.models.Deck;
import com.revature.models.StandardUser;
import com.revature.models.User;
import com.revature.models.User.AccountType;
import com.revature.services.DeckService;
import com.revature.services.ModerationService;

public class ModeratorSearchPageMenuController extends StandardUserSearchPageMenuController
{
	protected ModerationService modService = new ModerationService();
	protected DeckService deckService = new DeckService();
	
	@Override
	public boolean enterSearchPage(User user)
	{
		boolean inSearch = true;
		while(inSearch) {
			System.out.println("\nSearch - What would you like to search? \n" + "CARD | " + "ACCOUNT | " + "RETURN");
			String response = scan.nextLine().trim();
			switch(response.toLowerCase())
			{
				case "card":		
					inSearch = enterCardSearch(user);
					break;
				case "account":
					inSearch = enterAccountSearch(user);
					break;
				case "return":
					inSearch = false;
					break;
				default:
					System.out.println("\nInvalid input. Try again.");
					break;
			}
		}
		return false;
	}
	
	protected boolean enterAccountSearch(User user)
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
						User result = modService.findUserByName(search);
						if(user != null)
							enterAccountView(result);
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
						System.out.println("\nSearch - What type of account would you like to search by?\nSTANDARD | RETURN");
						String search = scan.nextLine().trim().toLowerCase();
						if(search.equals("return")) break;
						ArrayList<User> users = null;
						switch(search)
						{
							case "standard":
								users = modService.findUsersByType(AccountType.standard);
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
							if(users.size() == 0)
							{
								System.out.println("\nNo other users found!");
								break;
							}
							for(int i = 0; i < users.size(); i++)
							{
								User result = users.get(i);
								System.out.println(String.format("%d) %s", i + 1, result.getUserName()));
							}
							System.out.println("\nType in the number of the account you would like to examine or type RETURN.");
							String response2 = scan.nextLine().trim();
							try {
								if(response2.equals("return")) break;
								int number = Integer.parseInt(response2);
								User result = users.get(number - 1);
								enterAccountView(result);
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
					ArrayList<Integer> deckIDs = userService.getDecks((StandardUser)user);
					if(deckIDs.size() > 0)
					{
						System.out.println(String.format("\nUsername - %s", user.getUserName()));
						System.out.println("Account Type - " + user.getAccountType());
						System.out.println(String.format("Inventory - %d card(s)", userService.getInventory((StandardUser) user).size()));
						System.out.println("Decks - ");
						for(int i = 0; i < deckIDs.size(); i++)
						{
							Deck deck = deckService.getDeck(deckIDs.get(i));
							System.out.println(String.format("%d) %s", i+1, deck.getName()));
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
								{
									System.out.println("\nAccount successfully deleted.");	
								}
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
}
