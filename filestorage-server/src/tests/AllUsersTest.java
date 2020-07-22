package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.team7.cs401.filestorage.server.AllUsers;
import com.team7.cs401.filestorage.server.User;

public class AllUsersTest {
	
	@Test
	public void simple() {
		AllUsers allUsers = new AllUsers();
		allUsers.loadData("AllUsers.txt");
		User result = allUsers.getUser("jim");
		assertEquals("12345", result.getPassword());
		assertEquals("jim@yahoo.com", result.getUserEmail());
	}

}
