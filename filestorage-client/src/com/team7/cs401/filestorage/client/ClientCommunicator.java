package com.team7.cs401.filestorage.client;

import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/*
 * THIS FILE MIGHT NEED TO BE CHANGED COMPLETELY
 * Should we be separating client helper from the UI or combining it?
 */

public class ClientCommunicator {
	// might need to change based on UI implementation ------v
	private enum UserSelection {
		LOGIN, SIGNUP, LOGOUT, VIEWFILES,
		UPLOAD, DOWNLOAD, SHARE,
		DELETE, SETTING	
		}
	// -------------------------------------------------------^
	
	


	public static void main(String[] args) throws Exception {
    	CurrentUser user = new CurrentUser();
    	
        if (args.length != 1) {
            System.err.println("Failed to pass server IP as command line argument.");
            return;
        }
        try (var socket = new Socket(args[0], 1234)) {
        	System.out.println("Connected to " + args[0] + ":" + 1234);
        	
        	// Set up communication ----------------------------------------------------v
        	// Output stream socket
            OutputStream outputStream = socket.getOutputStream();
            
            // Create object output stream
            ObjectOutputStream objOutStream = new ObjectOutputStream(outputStream);
            
            // have input
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objInStream = new ObjectInputStream(inputStream);
            
            // List of Message objs
            List<Message> messagesOut = new ArrayList<>();
            List<Message> messagesIn = new ArrayList<>();
            // Communication fully set up -----------------------------------------------^
            
            // User input collector
            Scanner myScnr = new Scanner(System.in);
        	
        	// User input loop:
            Boolean running = true;
            boolean loggedIn = false;
            String signupLogin;
            while(running) {
            	// First make user log in:
            	while(!user.isLoggedIn()) {
            		while(true) {
	            		System.out.println(":: FILE STORAGE SYSTEM ::\n1) Sign Up\n2) Login\n:\t");
	            		signupLogin = myScnr.nextLine();
	            		if(signupLogin.equals("1")  || signupLogin.equals("2"))
	            			break;
            		}
            		if(signupLogin.equals("1") ) { // THIS DOES NOT WORK YET. NEED TO ADD IN ERROR CHECKING AS WELL AS FIX
            			System.out.println("This doesn't work yet. This will need to be fixed.");
            			System.out.println("Username:\t");
            			String username = myScnr.nextLine();
            			String email;
            			while(true) {
	            			System.out.println("Email:\t");
	            			email = myScnr.nextLine();
	            			if(email.contains("@") && email.contains(".com") && email.indexOf("@") < email.indexOf(".com"))
	            				break;
            			}
            			System.out.println("password:\t");
            			String password = myScnr.nextLine();
	                	messagesOut.clear();
	                	Message signup_msg = ClientHelper.generateSignUp(username, password, email);
	                	messagesOut.add(signup_msg);
	                	objOutStream.writeUnshared(messagesOut);
	                    objOutStream.flush();
	                    messagesIn = (List<Message>) objInStream.readObject();
	                    System.out.println("Received [" + messagesIn.size() + "] response messages from: " + socket);
	                    
	                    for (Message msg : messagesIn) {
	                    	// if valid login
	                    	if (ClientHelper.handleSignUp(msg) ) {
	                    		messagesIn.removeAll(messagesIn);
	                    		System.out.println("\nAccount successfully created!");
	                    		break;
	                    	}
	                    	else {
	                    		System.out.println("\nFailed to Create an account!");
	                    	}
	                    }
            		} else { // signupLogin.equals("2")
            		
	            		// Get username and password
	            		System.out.println("username:");
	                	String username = myScnr.nextLine();
	                	System.out.println("password:");
	                	String password = myScnr.nextLine();
	                	
	                	// pass a login message to server
	                	messagesOut.clear();
	                	Message loginMsg = ClientHelper.generateLogin(username, password);
	                	messagesOut.add(loginMsg);
	                	//messagesOut.add(new Message("login", "incoming", username, password));
	                	objOutStream.writeUnshared(messagesOut);
	                    objOutStream.flush();
	                    
	                    // rec response
	                    messagesIn = (List<Message>) objInStream.readObject();
	                    System.out.println("Received [" + messagesIn.size() + "] response messages from: " + socket);
	                    
	                    // for each recieved msg, process and test for successful login
	                    for (Message msg : messagesIn) {
	                    	// if valid login
	                    	if (ClientHelper.handleLogin(msg) ) {
	                    		user.setUserName(msg.getText1());
	                    		user.login();
	                    		messagesIn.removeAll(messagesIn);
	                    		break;
	                    	}
	                    }
            		}
//                    messagesIn.removeAll(messagesIn);
            	}
            	
            	// if failure to select properly, it will just logout
            	UserSelection selection = UserSelection.LOGOUT;
            	
            	System.out.println("Input text to enter next test loop:");
            	System.out.println("\n\t1: LOGOUT \n\t2: UPLOAD \n\t3: DOWNLOAD \n\t4:SHARE \n\t5: DELETE \n\t6: CHANGE SETTINGS ");
            	String select = myScnr.nextLine();
            	if (select == "1") {
            		selection = UserSelection.LOGOUT;
            	}else if (select == "2") {
            		selection = UserSelection.VIEWFILES;
            	}else if (select == "3") {
            		selection = UserSelection.UPLOAD;
            	} else if (select == "4") {
            		selection = UserSelection.DOWNLOAD;
            	} else if (select == "5") {
            		selection = UserSelection.SHARE;
            	} else if (select == "6") {
            		selection = UserSelection.DELETE;
            	} else if (select == "7") {
            		selection = UserSelection.SETTING;
            	}
            	
            	// Current test selection: 
            	switch(selection) {
	            	case LOGOUT:
	            		System.out.println("LOGOUT");
	            		// pass a logout message to server
	                	messagesOut.clear();
	                	Message logoutMsg = ClientHelper.logout(user);
	                	messagesOut.add(logoutMsg);
	                	objOutStream.writeUnshared(messagesOut);
	                    objOutStream.flush();
	                    
	                    // end
	                    // receive "response"
	                    try {
	                    	messagesIn = (List<Message>) objInStream.readObject();
	                    	messagesIn.removeAll(messagesIn);
	                    } catch (Exception e) {
	                    	System.out.println("Logging out");
	                    	running = false;
	                    }
	            		break;
	            	case VIEWFILES:
	            		System.out.println("TRY TO VIEW ALL THE FILES");
	            		break;
	            	case UPLOAD:
	            		System.out.println("TRY TO UPLOAD A FILE");
	            		break;
	            	case DOWNLOAD:
	//            		cHelper.sendToServer("test send");
	            		System.out.println("TRY TO DOWNLOAD A FILE");
	            		break;
	            	case SHARE:
	//            		cHelper.sendToServer("test send");
	            		System.out.println("TRY TO SHARE A FILE");
	            		break;
	            	case DELETE:
	//            		cHelper.sendToServer("test send");
	            		System.out.println("TRY TO DELETE A FILE");
	            		break;
	            	case SETTING:
	//            		cHelper.sendToServer("test send");
	            		System.out.println("CHANGE SETTING OF THE USER");
	            		String acc_selection = "";
	            		String password = "";
	            		String email = "";
	            		while(true) {
		            		System.out.println("Account Settings:\n1) Change Password\n2) Change Email\n:\t");
		            		acc_selection = myScnr.nextLine();
		            		if(acc_selection == "1" || acc_selection == "2")
		            			break;
	            		}
	            		if(acc_selection == "1") {
	            			System.out.println("Enter the new password: ");
	            			password = myScnr.nextLine();
	            			messagesOut.clear();
		                	Message password_msg = ClientHelper.generatePasswordChange(user.getUserName(), password);
		                	messagesOut.add(password_msg);
	            		}else {
	            			// loops till the proper email address is entered by the user
	            			while(true) {
		            			System.out.println("Enter the new email address: ");
		            			email = myScnr.nextLine();
		            			if(email.contains("@") && email.contains(".com") && email.indexOf("@") < email.indexOf(".com"))
		            				break;
		            			else
		            				System.out.println("\nPlease enter the correct email address!\n");
	            			}
	            			messagesOut.clear();
		                	Message email_msg = ClientHelper.generateEmailChange(user.getUserName(), email);
		                	messagesOut.add(email_msg);
	            		}
	            		break;
	            	default:
	            		System.out.println("Something went wrong.");
	            	myScnr.close();
            	}
            }
        }
    }
    
    private static void printMessage(Message msg){
        System.out.println("Type: " + msg.getType());
        System.out.println("Status: " + msg.getStatus());
        System.out.println("Text: " + msg.getText1());
    }
}



            

