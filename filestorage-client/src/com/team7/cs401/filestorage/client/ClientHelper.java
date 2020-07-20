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
	
	// user handling
	
	/*
	 * Generates login message to send to the server
	 * 
	 * @param username
	 * @param password
	 * @return message to be sent to server
	 */
	public static Message generateLogin(String username, String password) {
		System.out.println("Attempt to login");
		Message msg = new Message("login", "requesting", username, password);
		return msg;

	}
	
	
	/*
	 * Handles server response
	 * 
	 * @param Message
	 * @return true if successful login
	 */
	public static boolean handleLogin(Message msg) {
		if (msg.getStatus().equalsIgnoreCase("valid")) {
			return true;
		}
		return false;
	}
	
	/*
	 * Generates message to log user out
	 * 
	 * @return Message
	 */
	public static Message logout(CurrentUser user) {
		System.out.println("Logout method called");
		Message msg = new Message("logout", "requesting", user.getUserName());
		return msg;
	}
	
	/*
	 * Generates user sign up request message
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @return Message to be sent to server
	 */
	public static Message generateSignUp(String username, String password, String email) {
		System.out.println("Attempt to sign up");
		Message msg = new Message("signup", "requesting", username, password, email);
		return msg;
	}
	
	/*
	 * Handles server response to sign up message
	 * 
	 * @param Message
	 * @return true if user signed up properly (status <valid>)
	 */
	public static boolean handleSignUp(Message msg) {
		if (msg.getStatus().equals("valid")) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Generates message to request changes to account
	 * can only change password or email
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @return Message to be sent to server
	 */
	public static Message generatePasswordChange(String username, String password) {
		System.out.println("Attempt to edit accout password");
		Message msg = new Message("passwordChange", "requesting", username, password);
		return msg;
	}
	
	/*
	 * Handles the account setting response
	 * 
	 * @return true if account settings changed
	 */
	public static boolean handlePasswordChange(Message msg) {
		if (msg.getStatus().equals("valid")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Message generateEmailChange(String username, String email) {
		System.out.println("Attempt to edit accout email address");
		Message msg = new Message("emailChange", "requesting", username, email);
		return msg;
	}
	
	/*
	 * Handles the account setting response
	 * 
	 * @return true if account settings changed
	 */
	public static boolean handleEmailChange(Message msg) {
		if (msg.getStatus().equals("valid")) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Generates msg to request the main Dir
	 */
	public static Message generateViewUserFiles(CurrentUser user) {
		Message msg = new Message("mainDirRequest", "requesting", user.getUserName());
		return msg;
	}
	
	/*
	 * If dirMsg, shows the names of all files
	 * @param msg
	 * @return true if the message status is dirMsg
	 */
	public static boolean handleViewUserFiles(Message msg)  {
		if (msg.getStatus().equals("dirMsg")) {
			System.out.println(msg.getTextArray());
			return true;
		} else {
			return false;
		}
	}
	
	// Message generation to open a specific folder
	public static Message generateOpenFolder(CurrentUser user, String folderId) {
		System.out.println("generateOpenUserFiles method called");
		Message msg = new Message("openFolder", "requesting", user.getUserName(), folderId);
		return msg;
	}
	public static boolean handleOpenFolder(Message msg) {
		if (msg.getStatus().equals("valid")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Message generateReadFile(CurrentUser user, String fileId) {
		System.out.println("generateReadFile method called");
		Message msg = new Message("openFolder", "requesting", user.getUserName(), fileId);
		return msg;
	}
	
	public static boolean handleReadFile(Message msg) {	
		if (msg.getStatus().equals("valid")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Message generateUpload(CurrentUser user, String filename, byte[] fileBytes) {
		System.out.println("generateUpload method called");
		Message msg = new Message("file", "requesting", user.getUserName(), filename, null, fileBytes);
		return msg;
	}

	
	public static boolean handleUploadFile(Message msg) {
		if (msg.getStatus().equals("valid")) {
			return true;
		} else {
			return false;
		}
	}

	public static Message generateDeleteItem(CurrentUser user, String fileId) {
		System.out.println("generateDeleteItem method called");
		Message msg = new Message("deleteItem", "requesting", user.getUserName(), fileId);
		return msg;
	}
	public static boolean handleDeleteItem(Message msg) {
		if (msg.getStatus().equals("valid")) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Generates msg to be sent to server requesting a file download
	 * @param user
	 * @param fileName that includes file extension
	 */
	public static Message generateDownload(CurrentUser user, String fileName) {
		System.out.println("generateDownload method called");
		String path = user.getUserName() + "/" + fileName;
		Message msg = new Message("fileReq", "requesting", user.getUserName(), path, fileName);
		return msg;
	}
	
	
	
	public static boolean handleDownload(Message msg) {
		if (msg.getStatus().equals("valid")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Message generateShare(CurrentUser user, String fileId, String[] userList) {
		System.out.println("generateShare method called");
		Message msg = new Message("share", "requesting", user.getUserName(), fileId, userList);
		return msg;
	}
	
	public static boolean handleShare(Message msg) {
		if (msg.getStatus().equals("valid")) {
			return true;
		} else {
			return false;
		}
	}
	
}
