package com.team7.cs401.filestorage.client;

import java.io.PrintWriter;
import java.util.Scanner;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHelper {
	CurrentUser currUser = null;
	DataOutputStream dOut;
	Socket socket;
	
	public ClientHelper(Socket skt) throws IOException {
		this.socket = skt;
		dOut = new DataOutputStream(socket.getOutputStream());
	}
	
	// Support functions:
	
	/*
	 * Sends a string message to the Server
	 * @param message to be sent
	 */
	public void sendToServer(String msg) throws IOException {
		System.out.println("Trying to send message: " + msg);
		byte[] message = msg.getBytes("UTF-8");
		dOut.writeInt(message.length);
		dOut.write(message);
		dOut.flush();
	}
	
	public void sendFile() {
		System.out.println("Attempt to send file");
		// turn file into bytes to be sent to server
	}
	
	
	// user handling
	/*
	 * Attempts to log user in, sets currUser if valid
	 * 
	 * @param username
	 * @param password
	 * @return true if valid login and currUser set
	 */
	public boolean login(String username, String password) throws IOException {
		System.out.println("Attempt to login");
		sendToServer("LOGIN:" + username + ", " + password);
		this.currUser = new CurrentUser(username);      // saved for logout
		return false;
	}
	
	/*
	 * Logs user out
	 */
	public void logout() throws IOException {
		System.out.println("Attempt to logout");
		sendToServer("LOGOUT:" + this.currUser.getUserName());
	}
	
	/*
	 * User sign up request
	 * Sets currUser to this user if successful login created
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @return true if user created and currUser set
	 */
	public boolean signUp(String username, String password, String email) {
		System.out.println("Attempt to sign up");
		return false;
	}
	
	/*
	 * Allows user to change account settings
	 * @return true if changes are made
	 */
	public boolean accountSettings() {
		System.out.println("Attempt to edit accout Settings");
		return false;
	}
	
	
	// File handling
	public void viewUserFiles() {}
	public void openFolder() {}
	public void readFile() {}
	public void uploadFile() {}
	public void deleteItem() {}
	public void download() {}
	public void share() {}
	
}
