package com.team7.cs401.filestorage.server;
import java.util.List;

import com.team7.cs401.filestorage.client.ClientHelper;
import com.team7.cs401.filestorage.client.FileHandler;
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
	public static Message newUser(Message msg) {
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
	public static void logout(Message msg) {}
	
	
	public static Message upload(Message msg) {return null;}
	public static Message grantFileAccess(Message msg) {return null;}
	
	
	public static Message grantDownloadRequest(Message msg) {
		Message msgOut;
		// Get path and filename
		String user = msg.getText1();
		String path = "allfiles/" + msg.getText2();
//		String filename = msg.getText3();
		System.out.println("generate file from: " + path);
		
		// get the file
		// make msg, send, and rec response
		try {
			// convert file to byte array
			byte[] byteArr = FileHandler.fileToByteArr(path);
			
			// make the message (null UUID because UUID does not exist yet)
			msgOut = new Message("file", "fileMsg", user, null, byteArr);
			
			System.out.println("message made");

		} catch (Exception e) {
			System.out.println("File does not exist");
			// send failure message back.
			msgOut = new Message("file", "failure", user, null, path);
		}
		
		
		return msgOut;
	}
	
	
	public static Message setValidUser(Message msg) {return null;}
	public static void logEvent(String event) {}
	
	/*
	 * Lets user change password and email, not username
	 * 
	 * @param Message
	 * @return Message with status "success"
	 */
	public static Message changePassword(Message msg) {return null;}
	public static Message changeEmail(Message msg) {return null;}
}
