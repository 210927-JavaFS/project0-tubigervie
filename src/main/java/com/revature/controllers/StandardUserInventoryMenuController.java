package com.revature.controllers;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.models.Card;
import com.revature.models.StandardUser;
import com.revature.services.StandardUserService;

public class StandardUserInventoryMenuController {
	
	private StandardUserService standardUserService = new StandardUserService();
	protected static Scanner scan = new Scanner(System.in);
	
	public boolean enterInventoryPage(StandardUser user)
	{
		boolean inInventory = true;
		while(inInventory) {
			System.out.println("\nInventory - What would you like to do? \n" + "VIEW | " + "RETURN");
			String response = scan.nextLine();
			switch(response.toLowerCase())
			{
				case "view":
					ArrayList<Card> cards = standardUserService.getInventory(user);
					int cardCount = cards.size();
					
					System.out.println("\nYour account currently has " + cardCount + " card(s) in your inventory.");

					for(int i = 0; i < cardCount; i++)
						System.out.println(String.format("%d) %s", i + 1, cards.get(i).getName()));
					
					if(cardCount > 0)
						inInventory = enterView(cards, cardCount);		
					break;
				case "return":
					inInventory = false;
					break;
				default:
					break;
			}
		}
		return false;
	}
	
	private boolean enterView(ArrayList<Card> cards, int cardCount)
	{
		while(true)
		{
			System.out.println("Type in the number of the card you would like to examine.");
			String response2 = scan.nextLine();
			try {
				int number = Integer.parseInt(response2);
				if(number > cardCount) {
					System.out.println("Invalid input. Try again. \n");
					continue;
				}
				System.out.println(cards.get(number - 1).toString());
				return true;
			}
			catch(NumberFormatException e){
				System.out.println("Invalid input. Try again. \n");
				continue;
			}
		}
	}
}
