package com.revature.controllers;

import java.util.ArrayList;

import com.revature.models.User;
import com.revature.models.User.AccountType;
import com.revature.services.ModerationService;

public class ModeratorSearchPageMenuController extends StandardUserSearchPageMenuController
{
	private ModerationService modService = new ModerationService();
	
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
					inSearch = enterAccountSearch();
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
	
	protected boolean enterAccountSearch()
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
						System.out.println("\nWhat type would you like to search by?\n STANDARDUSER | RETURN");
						String search = scan.nextLine().trim().toLowerCase();
						if(search.equals("return")) break;
						ArrayList<User> users = null;
						switch(search)
						{
							case "standarduser":
								users = modService.findUsersByType(AccountType.standard);
								break;
							case "moderator":
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
	
	protected boolean enterAccountView(User user)
	{
		return false;
	}
}
