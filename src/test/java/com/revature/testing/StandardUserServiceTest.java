package com.revature.testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.ArrayList;
import java.util.TreeMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Card.RarityType;
import com.revature.models.StandardUser;
import com.revature.services.LoginService;
import com.revature.services.StandardUserService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class StandardUserServiceTest 
{
	private static Logger log = LoggerFactory.getLogger(StandardUserService.class);
	private StandardUserService userService = new StandardUserService();
	private LoginService loginService = new LoginService();
	
	private String testUsername = "test";
	private String testPassword = "password";
	private StandardUser testUser;
	
	@BeforeAll
	public void createTestUser() {
		testUser = (StandardUser)userService.createNewUser(testUsername, testPassword);
		loginService.uploadNewLogin(testUser);
		log.info("A test account has been created and uploaded to database to be used for JUnit tests.");
	}
	
	@Test
	@Order(1)
	public void testGetInventory()
	{
		assertTrue(userService.getInventory(testUser).size() == 0);
	}
	
	@Test
	@Order(2)
	public void testAddCard()
	{
		int cardID = 1;
		RarityType rarity = RarityType.rare;
		assertTrue(userService.addCardToInventory(testUser, cardID, rarity));
		assertEquals(userService.getInventory(testUser).size(), 1);
	}
	
	@Test
	@Order(3)
	public void testRemoveCard()
	{
		int cardID = 1;
		userService.removeCardFromInventory(testUser, cardID);
		assertEquals(userService.getInventory(testUser).size(), 0);
	}
	
	@Test
	@Order(4)
	public void testGetDecks()
	{
		assertTrue(userService.getDecks(testUser).size() == 0);
	}
	
	@Test
	@Order(5)
	public void testAddDeck()
	{
		userService.addToDecks(testUser, 0);
		assertEquals(userService.getDecks(testUser).size(), 1);
	}
	
	@Test
	@Order(6)
	public void testRemoveDeck()
	{
		userService.removeDeck(testUser, 0);
		assertEquals(userService.getDecks(testUser).size(), 0);
	}
	
	@Test
	@Order(7)
	public void testGetInventoryArray()
	{
		ArrayList<Integer> invArray = userService.getInventoryArray(testUser, true);
		assertEquals(userService.getInventory(testUser).size(), invArray.size());
	}
	
	@Test
	@Order(8)
	public void testGetInventoryTreeMap()
	{
		TreeMap<Integer, Integer> sortedMap = userService.getInventoryTreeMap(testUser, userService.getInventory(testUser));
		assertEquals(userService.getInventory(testUser).size(), sortedMap.size());
	}
	
	@AfterAll
	public void deleteTestUser()
	{
		userService.deleteStandardUser(testUser.getUserID());
		loginService.deleteLogin(testUser.getUserID());
		log.info("Testing complete. Test account successfully removed from database.");
	}
	
	
}
