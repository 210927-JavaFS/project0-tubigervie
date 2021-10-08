package com.revature.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.revature.models.Card;
import com.revature.models.StandardUser;
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
			System.out.println("\nInventory - What would you like to do? \n" + "VIEW | " + "EDIT | " + "RETURN");
			String response = scan.nextLine().trim();
			switch(response.toLowerCase())
			{
				case "view":
					HashMap<Integer, Integer> inventoryMap = standardUserService.getInventoryHashMap(user);
					int cardCount = standardUserService.getInventory(user).size();
					
					System.out.println("\nYour account currently has " + cardCount + " card(s) in your inventory.");					
					if(cardCount > 0)
						inInventory = enterView(inventoryMap, cardCount, user);		
					break;
				case "edit":
					//inInventory = enterEdit(user);
					break;
				case "return":
					inInventory = false;
					break;
				default:
					System.out.println("Invalid input. Try again.");
					break;
			}
		}
		return false;
	}
	
	private boolean enterView(HashMap<Integer, Integer> inventoryMap, int cardCount, StandardUser user)
	{
		while(true)
		{
			TreeMap<Integer, Integer> sortedInventoryMap = standardUserService.getInventoryTreeMap(user, inventoryMap);
			for(Map.Entry<Integer, Integer> entry : sortedInventoryMap.entrySet())
			{
				Card card = cardService.findCard(entry.getKey());
				System.out.println(String.format("%d) %s x%d", card.getIndex(), card.getName(), entry.getValue()));
			}
			
			System.out.println("\nType in the number of the card you would like to examine.");
			String response2 = scan.nextLine().trim();
			try {
				int number = Integer.parseInt(response2);
				if(!inventoryMap.containsKey(number)) {
					System.out.println("Invalid input. Try again. \n");
					continue;
				}
				System.out.println(cardService.findCard(number).toString());
				return true;
			}
			catch(NumberFormatException e){
				System.out.println("Invalid input. Try again. \n");
				continue;
			}
		}
	}
}
