package com.revature.daos;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import com.revature.models.Card;
import com.revature.models.Card.CardType;
import com.revature.models.Card.ClassType;
import com.revature.models.Card.RarityType;

public class CardDAO {

	public HashMap<Integer, Card> cardMap;
	
	public CardDAO() 
	{
		if(cardMap == null)
			initializeCards();
	}
	
	public Card findCard(int id) {
		return (cardMap.containsKey(id)) ? cardMap.get(id) : null;
	}
	
	private void initializeCards()
	{
		System.out.println("Card database being initialized");
		cardMap = new HashMap<Integer, Card>();
		try 
		{
			Scanner scan = new Scanner(new File("C:\\Users\\ervie\\Documents\\Work Stuff\\Repos\\project0-tubigervie\\src\\main\\resources\\CardDatabase.txt"));
			scan.nextLine();
			while(scan.hasNextLine())
			{
				String cardString = scan.nextLine();
				String[] cardArray = cardString.split(";");
				Card card = new Card(Integer.valueOf(cardArray[0]), cardArray[1], Integer.valueOf(cardArray[2]), cardArray[3], RarityType.valueOf(cardArray[4]), CardType.valueOf(cardArray[5]), ClassType.valueOf(cardArray[6]));
				
				if(!cardMap.containsKey(card.getIndex()))
					cardMap.put(card.getIndex(), card);
			}
		}
		catch(IOException e)
		{
			System.out.println("Could not open cards file.");
		}
		catch(NumberFormatException e)
		{
			System.out.println("Card in card database not properly formatted.");
		}
	}
}
