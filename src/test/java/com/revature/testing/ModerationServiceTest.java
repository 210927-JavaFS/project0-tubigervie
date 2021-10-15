package com.revature.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.revature.models.User;
import com.revature.models.User.AccountType;
import com.revature.services.ModerationService;

@TestMethodOrder(OrderAnnotation.class)
public class ModerationServiceTest {
	private ModerationService modService = new ModerationService();
	
	@Test
	@Order(1)
	public void findUserByNameTrueTest()
	{
		String testString = "adminuser";
		assertNotEquals(modService.findUserByName(testString), null);
	}
	
	@Test
	@Order(2)
	public void findUserByNameFalseTest()
	{
		String testString = "notreal";
		assertEquals(modService.findUserByName(testString), null);
	}
	
	@Test
	@Order(3)
	public void createStandardUserTest()
	{
		String testString = "user2";
		String testPass = "password";
		User newUser = modService.createNewUser(testString, testPass, AccountType.standard);
		assertEquals(newUser.getAccountType(), AccountType.standard);
	}
	
	@Test
	@Order(4)
	public void createNonStandardUserTest()
	{
		String testString = "adminuser2";
		String testPass = "password";
		User newUser = modService.createNewUser(testString, testPass, AccountType.admin);
		assertEquals(newUser.getAccountType(), AccountType.admin);
	}
	
	@Test
	@Order(5)
	public void findUsersByTypeTest()
	{
		assertEquals(modService.findUsersByType(AccountType.admin).size(), 1);
	}
	
	
}
