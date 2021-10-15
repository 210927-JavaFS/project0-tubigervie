package com.revature.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;
import java.util.TreeMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Card;
import com.revature.models.Deck;
import com.revature.models.StandardUser;
import com.revature.services.CardService;
import com.revature.services.DeckService;
import com.revature.services.LoginService;
import com.revature.services.StandardUserService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class DeckServiceTest 
{
	private static Logger log = LoggerFactory.getLogger(StandardUserService.class);
	
	private DeckService deckService = new DeckService();
	private StandardUserService userService = new StandardUserService();
	private LoginService loginService = new LoginService();
	private CardService cardService = new CardService();
	
	StandardUser testUser;
	Deck testDeck;
	
	private String testUsername = "test";
	private String testPassword = "password";
	private String testDeckName = "test deck";
	
	@BeforeAll
	public void createTestDeck() 
	{
		testUser = (StandardUser)userService.createNewUser(testUsername, testPassword);
		loginService.uploadNewLogin(testUser);
		testDeck = deckService.createNewDeck(testDeckName, testUser);
		log.info("A test account and deck has been created and uploaded to database to be used for JUnit tests.");
		assertNotEquals(testDeck, null);
		assertEquals(testDeck.getCardCount(), 0);
	}
	
	@Test
	@Order(1)
	public void addToDeckTest()
	{
		Card testCard = cardService.findCard(1);
		deckService.addToDeck(testDeck, testCard);
		assertEquals(testDeck.getCardCount(), 1);
		Card testCard2 = cardService.findCard(2);
		deckService.addToDeck(testDeck, testCard2);
		assertEquals(testDeck.getCardCount(), 2);
	}
	
	@Test
	@Order(2)
	public void removeFromDeckTest()
	{
		Card testCard = cardService.findCard(1);
		deckService.removeFromDeck(testDeck, testCard);
		assertEquals(testDeck.getCardCount(), 1);
	}
	
	@Test
	@Order(3)
	public void getDeckTest()
	{
		Deck newDeck = deckService.getDeck(testDeck.getDeckID());
		assertEquals(newDeck.getDeckID(), testDeck.getDeckID());
	}
	
	@Test
	@Order(4)
	public void getDeckHashMapTest()
	{
		HashMap<Integer, Integer> deckMap = deckService.createDeckMapCopy(testDeck);
		assertEquals(deckMap.size(), testDeck.getDeckMap().size());
	}
	
	@Test
	@Order(5)
	public void getDeckTreeMapTest()
	{
		TreeMap<Integer, Integer> deckMap = deckService.createDeckTreeMap(testDeck);
		assertEquals(deckMap.size(), testDeck.getDeckMap().size());
	}
	
	@Test
	@Order(6)
	public void getSubsetHashMapTest()
	{
		HashMap<Integer, Integer> subsetInventoryMap = new HashMap<Integer, Integer>();
		subsetInventoryMap.put(2, 2);
		assertEquals(subsetInventoryMap.get(2), 2);
		subsetInventoryMap = deckService.getSubsetInventoryMap(testDeck.getDeckMap(), subsetInventoryMap);
		assertEquals(subsetInventoryMap.get(2), 1);
	}
	
	@Test
	@Order(7)
	public void getSubsetTreeMapTest()
	{
		TreeMap<Integer, Integer> subsetInventoryMap = new TreeMap<Integer, Integer>();
		subsetInventoryMap.put(2, 2);
		assertEquals(subsetInventoryMap.get(2), 2);
		subsetInventoryMap = deckService.getSubsetInventoryTreeMap(testDeck.getDeckMap(), subsetInventoryMap);
		assertEquals(subsetInventoryMap.get(2), 1);
	}
	
	@AfterAll
	public void removeTestDeck()
	{
		int deckID = testDeck.getDeckID();
		deckService.deleteDeck(testDeck);
		userService.deleteStandardUser(testUser.getUserID());
		loginService.deleteLogin(testUser.getUserID());
		assertEquals(deckService.getDeck(deckID), null);
		log.info("Testing complete. Test deck and account successfully removed from database.");
	}
}
