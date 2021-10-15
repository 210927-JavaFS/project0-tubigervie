package com.revature.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.revature.models.Card;
import com.revature.services.CardService;

@TestMethodOrder(OrderAnnotation.class)
public class CardServiceTest 
{
	private CardService cardService = new CardService();
	
	@Test
	@Order(1)
	public void getAllCardsTest()
	{
		assertEquals(CardService.getAllCards().size(), Card.TOTAL_CARDS);
	}
	
	@Test
	@Order(2)
	public void findCardByIDTest()
	{
		Card card = cardService.findCard(1);
		assertNotEquals(card, null);
		assertEquals(card.getName(), "ABOMINATION");
	}
	
	@Test
	@Order(3)
	public void findCardsByNameTest()
	{
		ArrayList<Card> cards = cardService.findCard("ashbringer");
		assertEquals(cards.size(), 1);
	}
	
	@Test
	@Order(4)
	public void findCardByType()
	{
		ArrayList<Card> cards = cardService.findCardsByType(Card.CardType.minion);
		assertNotEquals(cards.size(), 0);
	}
	
}
