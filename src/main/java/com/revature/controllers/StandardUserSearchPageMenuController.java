package com.revature.controllers; 


import java.util.ArrayList;

import com.revature.models.Card;
import com.revature.models.Minion;
import com.revature.models.StandardUser;
import com.revature.models.User;
import com.revature.models.Weapon;
import com.revature.models.Card.CardType;
import com.revature.services.CardService;
import com.revature.services.StandardUserService;

public class StandardUserSearchPageMenuController extends SearchPageMenuController
{
	private CardService cardService = new CardService();
	private StandardUserService userService = new StandardUserService();
	
	public boolean enterSearchPage(User user)
	{
		boolean inSearch = true;
		while(inSearch) {
			System.out.println("\nSearch - What would you like to search? \n" + "CARD | " + "RETURN");
			String response = scan.nextLine().trim();
			switch(response.toLowerCase())
			{
				case "card":		
					inSearch = enterCardSearch(user);
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
	
	private boolean enterCardSearch(User user)
	{
		boolean inCardSearch = true;
		while(inCardSearch)
		{
			System.out.println("\nSearch - How would you like to search by? \n" + "NAME | " + "TYPE | " + "RETURN");
			String response = scan.nextLine().trim();
			switch(response.toLowerCase())
			{
				case "name":
					while(true)
					{
						System.out.println("\nEnter the name of the card you would like to look up or type R to return.");
						String search = scan.nextLine().trim().toLowerCase();
						if(search.equals("r")) break;
						ArrayList<Card> foundCards = cardService.findCard(search);
						if(foundCards != null && foundCards.size() != 0)
						{
							foundCards.sort((c1, c2) -> ((Integer)c1.getIndex()).compareTo((Integer)c2.getIndex()));
							inCardSearch = false;
							while(true)
							{
								System.out.println();
								for(int i = 0; i < foundCards.size(); i++)
								{
									Card card = foundCards.get(i);
									System.out.println(String.format("%d) %s", i + 1, card.getName()));
								}
								System.out.println("\nType in the number of the card you would like to examine or type RETURN.");
								String response2 = scan.nextLine().trim();
								try {
									if(response2.equals("return")) break;
									int number = Integer.parseInt(response2);
									Card card = foundCards.get(number - 1);
									enterCardView(card, (StandardUser) user);
									break;
								}
								catch(NumberFormatException | IndexOutOfBoundsException e){
									System.out.println("\nInvalid input. Try again.");
									continue;
								}
							}
						}
						else 
							System.out.println("\nCould not find any results.");
					}
					continue;
				case "return":
					inCardSearch = false;
					break;
				case "type":
					while(true)
					{
						System.out.println("\nWhat type would you like to search by?\nSPELL | MINION | WEAPON | RETURN");
						String search = scan.nextLine().trim().toLowerCase();
						if(search.equals("return")) break;
						ArrayList<Card> cards = null;
						switch(search)
						{
							case "spell":
								cards = cardService.findCardsByType(CardType.spell);
								break;
							case "minion":
								cards = cardService.findCardsByType(CardType.minion);
								break;
							case "weapon":
								cards = cardService.findCardsByType(CardType.weapon);
								break;
							default:
								System.out.println("Invalid input. Try again.");
								continue;
						}
						if(cards != null) 
							cards.sort((c1, c2) -> ((Integer)c1.getIndex()).compareTo((Integer)c2.getIndex()));
						while(true)
						{
							System.out.println();
							for(int i = 0; i < cards.size(); i++)
							{
								Card card = cards.get(i);
								System.out.println(String.format("%d) %s", i + 1, card.getName()));
							}
							System.out.println("\nType in the number of the card you would like to examine or type RETURN.");
							String response2 = scan.nextLine().trim();
							try {
								if(response2.equals("return")) break;
								int number = Integer.parseInt(response2);
								Card card = cards.get(number - 1);
								enterCardView(card, (StandardUser) user);
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
	
	private void enterCardView(Card card, StandardUser user)
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
		while(true)
		{
			System.out.println("\nCard - What would you like to do? \n" + "ADD | " + "RETURN");
			String response = scan.nextLine().trim();
			switch(response.toLowerCase())
			{
				case "return":
					return;
				case "add":
					userService.addCardToInventory(user, card.getIndex());
					System.out.println(card.getName().toUpperCase() + " added into your inventory.");
					return;
				default:
					System.out.println("Invalid input. Try again.");
					break;
			}

		}
	}
	
}
