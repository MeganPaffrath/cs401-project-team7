package com.team7.cs401.filestorage.client;

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
		if (msg.getStatus() == "valid") {
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
	public static Message generateAccountSettings(String username, String password, String email) {
		System.out.println("Attempt to edit accout Settings");
		Message msg = new Message("settings", "requesting", username, password, email);
		return msg;
	}
	
	/*
	 * Handles the account setting response
	 * 
	 * @return true if account settings changed
	 */
	public static boolean handleAccountSettings(Message msg) {
		if (msg.getStatus() == "valid") {
			return true;
		} else {
			return false;
		}
	}
	
	// Message generation to display list of user files
	public static Message generateViewUserFiles(CurrentUser user) {
		System.out.println("generateViewUserFiles method called");
		Message msg = new Message("viewUserFiles", "requesting", user.getUserName());
		return msg;
	}
	
	public static boolean handleViewUserFiles(Message msg)  {
		if (msg.getStatus() == "valid") {
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
		if (msg.getStatus() == "valid") {
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
		if (msg.getStatus() == "valid") {
			return true;
		} else {
			return false;
		}
	}
	
	// NOTE: I'm not sure on how to work on this part --------------!
	public static Message generateUploadFile() {
		return null;
	}
	//--------------------------------------------------------------!
	
	public static boolean handleUploadFile(Message msg) {
		if (msg.getStatus() == "valid") {
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
		if (msg.getStatus() == "valid") {
			return true;
		} else {
			return false;
		}
	}
	
	public static Message generateDownload(CurrentUser user, String fileId) {
		System.out.println("generateDownload method called");
		Message msg = new Message("downloadFile", "requesting", user.getUserName(), fileId);
		return msg;
	}
	public static boolean handleDownload(Message msg) {
		if (msg.getStatus() == "valid") {
			return true;
		} else {
			return false;
		}
	}
	
	public static Message generateShare(CurrentUser user, String fileId, String[] userList) {
		System.out.println("generateShare method called");
		Message msg = new Message("Share", "requesting", user.getUserName(), fileId, userList);
		return msg;
	}
	
	public static boolean handleShare(Message msg) {
		if (msg.getStatus() == "valid") {
			return true;
		} else {
			return false;
		}
	}
	
}
