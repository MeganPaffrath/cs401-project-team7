package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

import com.team7.cs401.filestorage.client.ClientHelper;
import com.team7.cs401.filestorage.client.CurrentUser;
import com.team7.cs401.filestorage.client.Message;


public class ClientHelperTest {
	CurrentUser user;
	
	@Before 
	public void initTest() {
		user = new CurrentUser();
		user.setUserName("username");
		user.setLoggedIn(true);
	}
	
	// START: LOGIN / SIGNUP / LOGOUT --------------------------------------------------------------------------------v

	@Test
	public void test_generateLogin_validMSG() {
		Message msg = ClientHelper.generateLogin("username", "password");
		assertEquals("Type should be <login>", "login", msg.getType());
		assertEquals("Status should be <requesting>" , "requesting", msg.getStatus());
		assertEquals("text1 should be <username>", "username", msg.getText1());
		assertEquals("text2 should be <password>", "password", msg.getText2());
		
	}
	
	@Test
	public void test_handleLogin_validLogin() {
		Message msg = new Message("login", "valid", "username", "password");
		assertTrue("Logins should be valid if status is <valid>", ClientHelper.handleLogin(msg));
	}
	
	@Test
	public void test_handleLogin_badLogin() {
		Message msg = new Message("login", "invalid", "username", "password");
		Boolean loggedIn = ClientHelper.handleLogin(msg);
		
		assertFalse("Login response of type `login` and status `invalid` should not work.", loggedIn);
	}
	
	@Test
	public void test_generateSignUp_validMSG() {
		Message msg = ClientHelper.generateSignUp("user", "pass", "mail");
		assertEquals("Type should be <signup>", "signup", msg.getType());
		assertEquals("Status should be <requesting>" , "requesting", msg.getStatus());
		assertEquals("text1 should be <username>", "user", msg.getText1());
		assertEquals("text2 should be <password>", "pass", msg.getText2());
		assertEquals("text3 should be <email>", "mail", msg.getText3());
	}
	
	@Test
	public void test_handleSignUp_goodSignup() {
		Message msg = new Message("signup", "valid", "user", "pass");
		boolean signUp = ClientHelper.handleSignUp(msg);
		assertTrue("Status <valid> should be a successful signup.", signUp);
	}
	
	@Test
	public void test_handleSignUp_badSignup() {
		Message msg = new Message("signup", "notvalid", "user", "pass");
		boolean signUp = ClientHelper.handleSignUp(msg);
		assertFalse("Status <valid> should be a successful signup.", signUp);
	}
	
	@Test
	public void test_logout_goodReq() {
		Message msg = ClientHelper.logout(user);
		
		assertEquals("Message type should be `logout`", "logout", msg.getType());
		assertEquals("Message status shoudl be `requesting`", "requesting", msg.getStatus());
		assertEquals("message text1 should be <username>", user.getUserName(), msg.getText1());
	}
	
	
	
	// END: LOGIN / SIGNUP / LOGOUT ----------------------------------------------------------------------------------^
	

	// START: ACCOUNT SETTINGS -------------------------------------------------------------------------------------v
	
	@Test
	public void test_generatePasswordChange_goodReq() {
		String newPass = "newpass";
		Message msg = ClientHelper.generatePasswordChange(user.getUserName(), newPass);
		
		assertEquals("Message type should be `passwordChange`", "passwordChange", msg.getType());
		assertEquals("Message status should be `requesting`", "requesting", msg.getStatus());
		assertEquals("Text1 should be <username>", user.getUserName(), msg.getText1());
		assertEquals("Text2 should be <pssword>", newPass, msg.getText2());
	}
	
	@Test
	public void test_handlePasswordChange_goodResp() {
		Message msgR = new Message("settings", "success", "username");
		boolean handled = ClientHelper.handlePasswordChange(msgR);
		
		assertTrue("For password change response type `settings` and status `success` password change should work.", handled);
	}
	
	@Test
	public void test_handlePasswordChange_badResp() {
		Message msgR = new Message("settings", "failure", "username");
		
		boolean handled = ClientHelper.handlePasswordChange(msgR);
		
		assertFalse("For password change response type `settings` and status `failure` password change should not work.", handled);
	}
	
	@Test
	public void test_generateEmailChange_goodReq() {
		String newMail = "newmail@gmail.com";
		Message msg = ClientHelper.generateEmailChange(user.getUserName(), newMail);
		
		assertEquals("Message type should be `emailChange`", "emailChange", msg.getType());
		assertEquals("Message status should be `requesting`", "requesting", msg.getStatus());
		assertEquals("Text1 should be <username>", user.getUserName(), msg.getText1());
		assertEquals("Text2 should be <email>", newMail, msg.getText2());
	}
	
	@Test
	public void test_handleEmailChange_goodResp() {
		Message msgR = new Message("settings", "success", "username");
		
		boolean handled = ClientHelper.handleEmailChange(msgR);
		
		assertTrue("For email change response type `settings` and status `success` email change should work", handled);
	}
	
	@Test
	public void test_handleEmailChange_badResp() {
		Message msgR = new Message("settings", "failure", "username");
		
		boolean handled = ClientHelper.handleEmailChange(msgR);
		
		assertFalse("For email change response type `settings` and status `failure` email change should not work", handled);
	}
	// END: ACCOUNT SETTINGS ------------------------------------------------------------------------------------^
	
	
	 // START: FILE HANDLING ----------------------------------------------------------------------------------v
	@Test
	public void test_generateDownload_goodReq() {
		String testFile = "test.txt";
		Message msg = ClientHelper.generateDownload(user, testFile);
		
		assertEquals("Message type should be `fileReq`", "fileReq", msg.getType());
		assertEquals("Message status should be `requesting`", "requesting", msg.getStatus());
		assertEquals("Text1 should be <username>", "username", msg.getText1());
		assertEquals("Text2 should be in format `username/test.txt`", "username/test.txt", msg.getText2());
		assertEquals("Text3 should be `test.txt`", "test.txt", msg.getText3());
	}
	
	
	
}

