package com.revature.services;

import java.util.ArrayList;
import java.util.HashMap;

import com.revature.daos.CardDAO;
import com.revature.models.Card;
import com.revature.models.Card.CardType;

public class CardService {
	
	private static CardDAO cardDAO = new CardDAO();
	
	public static HashMap<Integer, Card> getAllCards() //tested
	{
		return cardDAO.cardMap;
	}
	
	public Card findCard(int id) //tested
	{
		return cardDAO.findCard(id);
	}
	
	public ArrayList<Card> findCard(String name) //tested
	{
		return cardDAO.findCardsByQuery(name);
	}
	
	public ArrayList<Card> findCardsByType(CardType cType) //tested
	{
		return cardDAO.findCardsByType(cType);
	}
}
