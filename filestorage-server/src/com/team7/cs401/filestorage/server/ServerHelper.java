package com.team7.cs401.filestorage.server;
import java.awt.Desktop;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	private AllUsers allUsers = new AllUsers();

	
	public ServerHelper() {
		allUsers.loadData("I:\\Summer2020\\Project\\Phase 3\\cs401-project-team7\\filestorage-server\\src\\AllUsers.txt");
		
		
	}
	
	
	public Message validateLogin(Message msg) {  //go back
		String username = msg.getText1();
		String password = msg.getText2();
		
		allUsers.getUser(username);
		User user = allUsers.getUser(username);
		if (user != null && user.getPassword().equals(password)) {
			msg.setStatus("valid");
		}
		
		/* create allusers instance
		 * get user passing from clients
		*/
		// if valid user:
		// otherwise dont change status
		return msg;
	}
	
	/*
	 * If user can be generated, sets msg status to "valid"
	 * 
	 * @param Message
	 * @return msg with status "valid" if valid
	 * 
	 */
	public static Message newUserValidation(Message msg) {
		// if user does not yet exist:
		msg.setStatus("valid");
		// otherwise dont change status
		return msg;
	}
	
	/*
	 * Reads newUserValidation message
	 * Generate new user if the status is valid
	 * 
	 * @param Message from newUserValidation
	 * 
	 */
	public static void newUser(Message msg) {
		// if message status is valid, generate new user
	}
	
	/*
	 * logs the user out
	 * 
	 * @param Message
	 */
	public static void logout(Message msg) {}
	
	/*
	 * @param msg
	 * @return Message showing if upload was vailid/invalid
	 */
	public static Message uploadValidation(Message msg) {
		Message msgR;
		try {
			// get received file
			byte[] fileBytes = msg.getFileBytes();
			// plan the file path
			Path path = Paths.get("allfiles/" + msg.getText1());
			Files.createDirectories(path);
			File recFile = new File(path + "/" + msg.getText2());
			// convert bytes to file
			FileHandler.byteArrToFile(recFile, fileBytes);

			// opens the file
			Desktop desktop = Desktop.getDesktop();
			desktop.open(recFile);
			
			// Send msg back : upload validation
			msgR = new Message("upload", "valid", "user", "UUID");
		} catch (Exception e) {
			msgR = new Message("upload", "invalid", "user", "UUID");
		}
		
		return msgR;
	}
	
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
