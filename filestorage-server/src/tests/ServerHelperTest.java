package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.team7.cs401.filestorage.client.Message;
import com.team7.cs401.filestorage.server.AllUsers;
import com.team7.cs401.filestorage.server.ServerHelper;

public class ServerHelperTest {
	ServerHelper sHelper;
	@Before
	public void testInit() {
		sHelper = new ServerHelper();
	}
	
	// START: LOGIN / SIGNUP / LOGOUT --------------------------------------------------------------------------------v
	// Login
	@Test
	public void test_validateLogin_validLogin() {
		Message inMsg = new Message("login", "pending", "username", "password");
		ServerHelper serverHelper = new ServerHelper();
		Message msg = serverHelper.validateLogin(inMsg);
		assertEquals("A valid login should set the status to <valid>", "valid", msg.getStatus());
	}
	
	@Test
	public void test_validateLogin_invalidLogin() {
		Message inMsg = new Message("login", "pending", "baduser", "badpass");
		ServerHelper serverHelper = new ServerHelper();
		Message msg = serverHelper.validateLogin(inMsg);
		assertNotEquals("An invalid login should NOT set the status to <valid>", "valid", msg.getStatus());
	}
	
	// Signup
	@Test
	public void test_newUserValidation_lookForNew() {
		Message inMsg = new Message("signup", "requesting", "usernameDNE", "password", "email@email.com");
		sHelper.newUserValidation(inMsg);
		
		assertEquals("There should not be a username of `usernameDNE` in the database, should produce `signup` msg with status `valid`", "valid", inMsg.getStatus());

	}
	
	@Test
	public void test_newUserValidation_userExists() {
		Message inMsg = new Message("signup", "requesting", "username", "password", "goodemail@email.com");
		sHelper.newUserValidation(inMsg);
		
		assertEquals("There should already be a username of `username` in the database, should produce `signup` msg with status `invalid`", "invalid", inMsg.getStatus());
	}
	
	// Logout
	@Test
	public void test_logout_works() {
		fail("Test not yet implemented");
	}

	
	// END: LOGIN / SIGNUP / LOGOUT ----------------------------------------------------------------------------------^
	
	// START: ACCOUNT SETTINGS -------------------------------------------------------------------------------------v
	// password change
	@Test
	public void test_changeAccountSettings_changePassword() {
		// generate new user if user DNE yet
		AllUsers allUsers = new AllUsers();
		allUsers.addOrModifyUser("username", "password", "usernameemail@email.com");
		
		Message inMsg = new Message("passwordChange", "requesting", "username", "newPassword");
		
		// change the password
		Message msgR = sHelper.changeAccountSettings(inMsg);
		
		assertEquals("Response type should be `settings`", "settings", msgR.getType());
		assertEquals("response status should be `success`", "success", msgR.getStatus());
		assertEquals("Text1 should be <username>", "username", msgR.getText1());
		assertEquals("Users password should have been changed in txt file", "newPassword", allUsers.getUser("username").getPassword());
		
		// change password back to what it was (reset test)
		allUsers.addOrModifyUser("username", "password", "usernameemail@email.com");
	}
	
	// email change
	@Test
	public void test_changeAccountSettings_changeEmail() {
		// generate new user if user DNE yet
				AllUsers allUsers = new AllUsers();
				allUsers.addOrModifyUser("username", "password", "usernameemail@email.com");
				
				Message inMsg = new Message("emailChange", "requesting", "username", "newemail@email.com");
				
				// change the password
				Message msgR = sHelper.changeAccountSettings(inMsg);
				
				assertEquals("Response type should be `settings`", "settings", msgR.getType());
				assertEquals("response status should be `success`", "success", msgR.getStatus());
				assertEquals("Text1 should be <username>", "username", msgR.getText1());
				assertEquals("User's email should have been changed in txt file", "newemail@email.com", allUsers.getUser("username").getUserEmail());
				
				// change password back to what it was (reset test)
				allUsers.addOrModifyUser("username", "password", "usernameemail@email.com");
	}
	// END: ACCOUNT SETTINGS -------------------------------------------------------------------------------------^
}
