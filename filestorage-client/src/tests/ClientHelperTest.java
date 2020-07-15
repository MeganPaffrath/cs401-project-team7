package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

import com.team7.cs401.filestorage.client.ClientHelper;

public class ClientHelperTest {
	ClientHelper cHelper;
	
	@Before
	public void initEach() throws Exception {
		Socket testSocket = new Socket() {
			public OutputStream getOutputStream() throws IOException {
				return null;
			}
		};
		cHelper = new ClientHelper(testSocket);
	}

//	@Test
//	public void test_login_validLogin() {
//		assertTrue("Should be able to login user: <username> with password <password>", cHelper.login("username", "password"));
//	}
}
