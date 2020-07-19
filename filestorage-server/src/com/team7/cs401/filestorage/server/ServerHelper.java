package com.team7.cs401.filestorage.server;
import com.team7.cs401.filestorage.client.Message;

public class ServerHelper {
	/*
	 * Checks if user is valid
	 * 
	 * @param Message
	 * @return msg with staus "valid" if valid
	 */
	public static Message validateLogin(Message msg) {
		String username = msg.getText1();
		String password = msg.getText2();
		
		// if valid user:
		msg.setStatus("valid");
		// otherwise dont change status
		return msg;
	}
	
	/*
	 * Tries to generate new user
	 * If user generated, sets status to "valid"
	 * 
	 * @param Message
	 * @return msg with status "valid" if valid
	 * 
	 */
	public Message newUser(Message msg) {
		// if valid user generated:
		msg.setStatus("valid");
		// otherwise dont change status
		return msg;
	}
	
	/*
	 * logs the user out
	 * 
	 * @param Message
	 */
	public void logout(Message msg) {}
	
	
	public Message upload(Message msg) {return null;}
	public Message grantFileAccess(Message msg) {return null;}
	public Message grantDownloadRequest(Message msg) {return null;}
	public Message setValidUser(Message msg) {return null;}
	public void logEvent(String event) {}
	
	/*
	 * Lets user change password and email, not username
	 * 
	 * @param Message
	 * @return Message with status "success"
	 */
	public Message changePassword(Message msg) {return null;}
	public Message changeEmail(Message msg) {return null;}
}
