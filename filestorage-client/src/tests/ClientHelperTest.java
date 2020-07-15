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
	ClientHelper cHelper;
	
	@Before
	public void initEach() throws Exception {
		cHelper = new ClientHelper();
	}

	@Test
	public void test_generateLogin_validMSG() {
		Message msg = cHelper.generateLogin("username", "password");
		assertEquals("text1 should be <username>", "username", msg.getText1());
		assertEquals("text2 should be <password>", "password", msg.getText2());
		
	}
	
	@Test
	public void test_handleLogin_validLogin() {
		Message msg = new Message("login", "success", "username", "password");
		assertTrue("Logins should be valid if status is <success>", cHelper.handleLogin(msg));
	}
	
	@Test
	public void test_generateSignUp_validMSG() {
		
	}
	
}
