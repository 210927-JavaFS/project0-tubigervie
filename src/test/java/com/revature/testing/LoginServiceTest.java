package com.revature.testing;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.daos.LoginDAO;
import com.revature.daos.StandardUserDAO;
import com.revature.models.User;
import com.revature.models.User.AccountType;
import com.revature.services.LoginService;
import com.revature.utils.CryptoUtils;

@TestMethodOrder(OrderAnnotation.class)
public class LoginServiceTest 
{
	private static LoginDAO loginDAO = new LoginDAO();
	private static StandardUserDAO userDAO = new StandardUserDAO();
	
	private LoginService loginService = new LoginService();
	private static Logger log = LoggerFactory.getLogger(LoginService.class);
	
	private String testUsername = "test";
	private String testPassword = "password";
	
	@Test
	@Order(1)
	public void loginExistsTrueTest()
	{
		String testString = "adminuser";
		assertTrue(loginService.loginExists(testString));
	}
	
	@Test
	@Order(2)
	public void loginExistsFalseTest()
	{
		String testString = "notrealuser";
		assertFalse(loginService.loginExists(testString));
	}
	
	@Test
	@Order(3)
	public void retrieveLoginTest()
	{
		String testUsername = "adminuser";
		String testPassword = "secretpassword!";
		try 
		{
			byte[] sha = CryptoUtils.getSHA(testPassword);
			testPassword = CryptoUtils.Encrypt(sha);
		} catch (NoSuchAlgorithmException e) {
			log.info("Could not encrypt password.");
			fail();
		}
		User user = loginService.retrieveExistingLogin(testUsername, testPassword);
		assertNotEquals(user, null);
		User user2 = loginDAO.findExistingLogin(testUsername, testPassword);
		assertEquals(user.getUserID(), user2.getUserID());
	}
	
	@Test
	@Order(4)
	public void uploadNewLoginTest()
	{
		User testUser = new User(testUsername, testPassword, AccountType.standard);
		loginService.uploadNewLogin(testUser);
		assertTrue(loginService.loginExists(testUsername));
		userDAO.deleteStandardUser(testUser.getUserID());
		loginDAO.deleteLogin(testUser.getUserID());

	}
}
