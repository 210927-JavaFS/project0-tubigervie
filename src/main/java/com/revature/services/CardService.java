package com.revature.services;

import java.util.HashMap;

import com.revature.daos.CardDAO;
import com.revature.models.Card;

public class CardService {
	
	private static CardDAO cardDAO = new CardDAO();
	
	public static HashMap<Integer, Card> getAllCards()
	{
		return cardDAO.cardMap;
	}
	
	public Card findCard(int id)
	{
		return cardDAO.findCard(id);
	}
}
