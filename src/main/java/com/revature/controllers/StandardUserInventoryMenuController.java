package com.revature.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.revature.models.Card;
import com.revature.models.Minion;
import com.revature.models.StandardUser;
import com.revature.models.Weapon;
import com.revature.services.CardService;
import com.revature.services.StandardUserService;

public class StandardUserInventoryMenuController {
	
	private StandardUserService standardUserService = new StandardUserService();
	private CardService cardService = new CardService();
	protected static Scanner scan = new Scanner(System.in);
	
	public boolean enterInventoryPage(StandardUser user)
	{
		boolean inInventory = true;
		while(inInventory) {
			System.out.println("\nInventory - What would you like to do? \n" + "VIEW | " + "REMOVE | " + "RETURN");
			String response = scan.nextLine().trim();
			if(response.equalsIgnoreCase("return")) return false;
			HashMap<Integer, Integer> inventoryMap = standardUserService.getInventory(user);
			int cardCount = standardUserService.getInventoryArray(user, false).size();
			System.out.println("\nYour account currently has " + cardCount + " card(s) in your inventory.");	
			switch(response.toLowerCase())
			{
				case "view":				
					if(cardCount > 0)
						inInventory = enterView(inventoryMap, cardCount, user, false);		
					break;
				case "remove":
					if(cardCount > 0)
						inInventory = enterView(inventoryMap, cardCount, user, true);		
					//inInventory = enterEdit(user);
					break;
				default:
					System.out.println("\nInvalid input. Try again.");
					break;
			}
		}
		return false;
	}
	
	private boolean enterView(HashMap<Integer, Integer> inventoryMap, int cardCount, StandardUser user, boolean isRemoving)
	{
		while(true)
		{
			ArrayList<Integer> inventoryArray = standardUserService.getInventoryArray(user, true);
			inventoryArray.sort((c1, c2) -> ((Integer)c1).compareTo((Integer)c2));
			for(int i = 0; i < inventoryArray.size(); i++)
			{
				Card card = cardService.findCard(inventoryArray.get(i));
				System.out.println(String.format("%d) %s x%d", i + 1, card.getName(), standardUserService.getInventory(user).get(inventoryArray.get(i))));
			}
			
			System.out.println("\nType in the number ID of the card you would like to examine or type RETURN.");
			String response2 = scan.nextLine().trim();
			try {
				if(response2.equals("return")) return true;
				int number = Integer.parseInt(response2);
				if(number > inventoryArray.size() || number <= 0) {
					System.out.println("\nInvalid input. Try again. \n");
					continue;
				}
				Card card = cardService.findCard(inventoryArray.get( number - 1));
				switch(card.getCardType())
				{
					case minion:
						if(!isRemoving)
							System.out.println(((Minion)card).toString());
						else
							standardUserService.removeCardFromInventory(user, card.getIndex());
						break;
					case weapon:
						if(!isRemoving)
							System.out.println(((Weapon)card).toString());
						else
							standardUserService.removeCardFromInventory(user, card.getIndex());
						break;
					default:
						if(!isRemoving)
							System.out.println(card.toString());
						else
							standardUserService.removeCardFromInventory(user, card.getIndex());
						break;	
				}
				return true;
			}
			catch(NumberFormatException | IndexOutOfBoundsException e){
				System.out.println("\nInvalid input. Try again. \n");
				continue;
			}
		}
	}
}
