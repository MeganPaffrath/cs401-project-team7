package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.team7.cs401.filestorage.client.Message;
import com.team7.cs401.filestorage.server.ServerHelper;

public class ServerHelperTest {
	
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
		Message newUser = ServerHelper.newUserValidation(inMsg);
		
		assertEquals("There should not be a username of `usernameDNE` in the database, should produce `signup` msg with status `valid`", "valid", newUser.getStatus());

	}
	
	@Test
	public void test_newUserValidation_userExists() {
		Message inMsg = new Message("signup", "requesting", "username", "password", "goodemail@email.com");
		Message newUser = ServerHelper.newUserValidation(inMsg);
		
		assertEquals("There should already be a username of `username` in the database, should produce `signup` msg with status `invalid`", "invalid", newUser.getStatus());
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
		
		// change their password
		fail("test not yet implemented");
		
		// change password back to what it was (reset test)
	}
	
	// email change
	@Test
	public void test_changeAccountSettings_changeEmail() {
		// generate new user if user DNE yet
		
		// change their email
		fail("test not yet implemented");
		
		// change email back to what it was (reset test)
	}
	// END: ACCOUNT SETTINGS -------------------------------------------------------------------------------------^
}
