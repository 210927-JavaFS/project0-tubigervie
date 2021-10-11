package com.revature.services;

import java.util.ArrayList;
import java.util.HashMap;

import com.revature.daos.CardDAO;
import com.revature.models.Card;
import com.revature.models.Card.CardType;

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
	
	public Card findCard(String name)
	{
		return cardDAO.findCard(name);
	}
	
	public ArrayList<Card> findCardsByType(CardType cType)
	{
		return cardDAO.findCardsByType(cType);
	}
}
