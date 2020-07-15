package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

import com.team7.cs401.filestorage.client.ClientHelper;
import com.team7.cs401.filestorage.client.Message;


public class ClientHelperTest {

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
	public void test_generateAccountSettings_goodMSG() {
		Message msg = ClientHelper.generateAccountSettings("user", "pass", "email");
		assertEquals("text1 should be <username>", "user", msg.getText1());
		assertEquals("text2 should be <password>", "pass", msg.getText2());
		assertEquals("text3 should be <email>", "email", msg.getText3());
	}
	
}
