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
		LOGIN, SIGNUP, LOGOUT,
		UPLOAD,
		OTHER
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
            while(running) {
            	// First make user log in:
            	while(!user.isLoggedIn()) {
            		// Get username and password
            		System.out.println("username:");
                	String username = myScnr.nextLine();
                	System.out.println("password:");
                	String password = myScnr.nextLine();
                	
                	// pass a login message to server
                	messagesOut.clear();
                	Message loginMsg = ClientHelper.generateLogin(username, password);
                	messagesOut.add(loginMsg);
//                	messagesOut.add(new Message("login", "incoming", username, password));
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
//                    messagesIn.removeAll(messagesIn);
            	}
            	
            	// if failure to select properly, it will just logout
            	UserSelection selection = UserSelection.LOGOUT;
            	
            	System.out.println("Input text to enter next test loop:");
            	System.out.println("1: LOGOUT, 2: UPLOAD, 3: OTHER");
            	String select = myScnr.nextLine();
            	if (select == "1") {
            		selection = UserSelection.LOGOUT;
            	} else if (select == "2") {
            		selection = UserSelection.UPLOAD;
            	} else if (select == "3") {
            		selection = UserSelection.OTHER;
            	}
            	
            	// Current test selection: 
            	switch(selection) {
	            	case LOGOUT:
	            		System.out.println("LOGOUT");
	            		// pass a logout message to server
//	                	messagesOut.clear();
//	                	messagesOut.add(new Message("logout", "incoming", username, password));
//	                	objOutStream.writeUnshared(messagesOut);
//	                    objOutStream.flush();
	                    
	            		break;
	            	case UPLOAD:
	            		System.out.println("TRY TO UPLOAD A FILE");
	            		break;
	            	case OTHER:
	//            		cHelper.sendToServer("test send");
	            		System.out.println("other section");
	            		break;
	            	default:
	            		System.out.println("Something went wrong.");
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



            