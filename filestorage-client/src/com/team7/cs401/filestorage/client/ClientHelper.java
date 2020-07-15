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
	public Message generateLogin(String username, String password) {
		System.out.println("Attempt to login");
		return null;
	}
	
	
	/*
	 * Handles server response
	 * 
	 * @param Message
	 * @return true if successful login
	 */
	public boolean handleLogin(Message msg) {
		return false;
	}
	
	/*
	 * Generates message to log user out
	 * 
	 * @return Message
	 */
	public Message logout() {
		System.out.println("Attempt to logout");
		return null;
	}
	
	/*
	 * Generates user sign up request message
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @return Message to be sent to server
	 */
	public Message generateSignUp(String username, String password, String email) {
		System.out.println("Attempt to sign up");
		return null;
	}
	
	/*
	 * Handles server response to sign up message
	 * 
	 * @param Message
	 * @return true if user signed up properly
	 */
	public boolean handleSignUp(Message msg) {
		return false;
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
	public Message generateAccountSettings() {
		System.out.println("Attempt to edit accout Settings");
		return null;
	}
	
	/*
	 * Handles the account setting response
	 * 
	 * @return true if account settings changed
	 */
	public boolean handleAccountSettings(Message msg) {
		return false;
	}
	
	// File handling
	public Message generateViewUserFiles() {return null;}
	public boolean handleViewUserFiles(Message msg) {return false;}
	public Message generateOpenFolder() {return null;}
	public boolean handleOpenFolder(Message msg) {return false;}
	public Message generateReadFile() {return null;}
	public boolean handleReadFile(Message msg) {return false;}
	public Message generateUploadFile() {return null;}
	public boolean handleUploadFile(Message msg) {return false;}
	public Message generateDeleteItem() {return null;}
	public boolean handleDeleteItem(Message msg) {return false;}
	public Message generateDownload() {return null;}
	public boolean handleDownload(Message msg) {return false;}
	public Message generateShare() {return null;}
	public boolean handleShare(Message msg) {return false;}
	
}
