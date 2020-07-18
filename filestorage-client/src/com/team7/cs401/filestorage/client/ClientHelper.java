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
		return null;
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
		return false;
	}
	
	// File handling
	public static Message generateViewUserFiles() {return null;}
	public static boolean handleViewUserFiles(Message msg) {return false;}
	public static Message generateOpenFolder() {return null;}
	public static boolean handleOpenFolder(Message msg) {return false;}
	public static Message generateReadFile() {return null;}
	public static boolean handleReadFile(Message msg) {return false;}
	public static Message generateUploadFile() {return null;}
	public static boolean handleUploadFile(Message msg) {return false;}
	public static Message generateDeleteItem() {return null;}
	public static boolean handleDeleteItem(Message msg) {return false;}
	public static Message generateDownload() {return null;}
	public static boolean handleDownload(Message msg) {return false;}
	public static Message generateShare() {return null;}
	public static boolean handleShare(Message msg) {return false;}
	
}
