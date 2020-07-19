package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.team7.cs401.filestorage.client.Message;
import com.team7.cs401.filestorage.server.ServerHelper;

public class ServerHelperTest {
	
	@Test
	public void test_validateLogin_validLogin() {
		Message inMsg = new Message("login", "pending", "username", "password");
		Message msg = ServerHelper.validateLogin(inMsg);
		assertEquals("A valid login should set the status to <valid>", "valid", msg.getStatus());
	}
	
	@Test
	public void test_validateLogin_invalidLogin() {
		Message inMsg = new Message("login", "pending", "baduser", "badpass");
		Message msg = ServerHelper.validateLogin(inMsg);
		assertNotEquals("An invalid login should NOT set the status to <valid>", "valid", msg.getStatus());
	}
}
