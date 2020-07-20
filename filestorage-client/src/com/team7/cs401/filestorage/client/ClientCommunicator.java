package com.team7.cs401.filestorage.client;

import java.awt.Desktop;
import java.io.*;
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
            	System.out.println("\n\t1: LOGOUT \n\t2: VIEWFILES \n\t3: UPLOAD \n\t4: DOWNLOAD \n\t5:SHARE \n\t6: DELETE \n\t7: CHANGE SETTINGS ");
            	String select = myScnr.nextLine();
            	if (select.equals("1")) {
            		selection = UserSelection.LOGOUT;
            	} else if (select.equals("2")) {
            		selection = UserSelection.VIEWFILES; 
	            } else if (select.equals("3")) {
	        		selection = UserSelection.UPLOAD;
	        	} else if (select.equals("4")) {
            		selection = UserSelection.DOWNLOAD;
            	} else if (select.equals("5")) {
            		selection = UserSelection.SHARE;
            	} else if (select.equals("6")) {
            		selection = UserSelection.DELETE;
            	} else if (select.equals("7")) {
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
	            		System.out.println("Get a list of all files");
	            		// generate msg to be sent
	            		Message msg = ClientHelper.generateViewUserFiles(user);
	            		
	            		// send message
	            		messagesOut.clear();
	                	messagesOut.add(msg);
	                	objOutStream.writeUnshared(messagesOut);
	                    objOutStream.flush();
	            		
	            		// Receive response
	                    messagesIn = (List<Message>) objInStream.readObject();
	                    System.out.println("Received [" + messagesIn.size() + "] response messages from: " + socket);
	                    
	                    // go through recieved msgs
	                    for (Message m : messagesIn) {
	                    	System.out.println("Recieved msg type: " + m.getType());
	                    	Boolean found = ClientHelper.handleViewUserFiles(m);
	                    	
	                    	// if valid msg
	                    	if (found) {
	                    		break;
	                    	}
	                    }
	                    messagesIn.removeAll(messagesIn);
	            		break;
	            	case UPLOAD:
	            		System.out.println("TRY TO UPLOAD A FILE");
	            		
	            		// Get the file
	            		System.out.println("Input file name: ");
	            		String filename = myScnr.nextLine();
	            		String filePath = "userdir/" + filename;
	            		
	            		File file = null;
	            		
	            		// make msg, send, and rec response
	            		try {
	            			// convert file to byte array
	            			byte[] byteArr = FileHandler.fileToByteArr(filePath);
	            			
	            			// make the message (null UUID because UUID does not exist yet)
//	            			Message msg = new Message("file", "requesting", user.getUserName(), filename, null, byteArr);
	            			Message msgUpload =  ClientHelper.generateUpload(user, filename, byteArr);
	            			System.out.println("message made");
	            			
	            			// send the message
	            			messagesOut.clear();
		                	messagesOut.add(msgUpload);
		                	objOutStream.writeUnshared(messagesOut);
		                    objOutStream.flush();
		                    
		                    // rec response
		                    messagesIn = (List<Message>) objInStream.readObject();
		                    System.out.println("Received [" + messagesIn.size() + "] response messages from: " + socket);
		                    
		                    // for each recieved msg, check if valid
		                    for (Message m : messagesIn) {
		                    	// if valid login
		                    	if (m.getType().equals("upload")) {
		                    		if (m.getStatus().equals("valid")) {
		                    			System.out.println("Server got file");
		                    		} else {
		                    			System.out.println("Server failed to get file");
		                    		}
		                    		
		                    		messagesIn.removeAll(messagesIn);
		                    		break;
		                    	}
		                    }
	            			
	            			
	            		} catch (Exception e) {
	            			System.out.println("File does not exist");
	            		}
	            		
	            		break;
	            	case DOWNLOAD:
	            		System.out.println("TRY TO DOWNLOAD A FILE");
	            		
	            		// build request of file path
	            		System.out.println("Input file name: "); // this works for individual files, not files withind subdirs
	            		String fileName = myScnr.nextLine();
	            		String path = user.getUserName() + "/" + fileName;
	            		
	            		// request file by 
	            		Message msgDownload = ClientHelper.generateDownload(user, fileName);
	            		
	            		// send the message
	            		messagesOut.clear();
	                	messagesOut.add(msgDownload);
	                	objOutStream.writeUnshared(messagesOut);
	                    objOutStream.flush();
	            		
	            		// recieve response
	            		messagesIn = (List<Message>) objInStream.readObject();
	                    System.out.println("Received [" + messagesIn.size() + "] response messages from: " + socket);
	                    
	                    for (Message m : messagesIn) {
	                    	if (m.getStatus().equals("fileMsg")) {
		                    	System.out.println("File recieved!");
		                    } else {
		                    	System.out.println("File not recieved.");
		                    	System.out.println("Message response status was: " + m.getStatus());
		                    }
		                    
		                    // generate the file
		                    File recFile = new File("userdir/" + fileName);
		                    byte[] fileBytes = m.getFileBytes();
		                    FileHandler.byteArrToFile(recFile, fileBytes);
		                    
		                 // open the file
	        				Desktop desktop = Desktop.getDesktop();
	        				desktop.open(recFile);
		                    
		                    messagesIn.removeAll(messagesIn);
                    		break;
	                    }
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



            

