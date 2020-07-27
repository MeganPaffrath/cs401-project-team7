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
	
	private static List<Message> outMsg = new ArrayList<>();
    private static List<Message> inMsg = new ArrayList<>();
    private static ObjectOutputStream objOutStream;
    private static ObjectInputStream objInStream;


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
            objOutStream = new ObjectOutputStream(outputStream);
            
            // have input
            InputStream inputStream = socket.getInputStream();
            objInStream = new ObjectInputStream(inputStream);
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
	                	outMsg.clear();
	                	Message signup_msg = ClientHelper.generateSignUp(username, password, email);
	                	outMsg.add(signup_msg);
	                	objOutStream.writeUnshared(outMsg);
	                    objOutStream.flush();
	                    inMsg = (List<Message>) objInStream.readObject();
	                    System.out.println("Received [" + inMsg.size() + "] response messages from: " + socket);
	                    
	                    for (Message msg : inMsg) {
	                    	// if valid login
	                    	if (ClientHelper.handleSignUp(msg) ) {
	                    		inMsg.removeAll(inMsg);
	                    		System.out.println("\nAccount successfully created!");
	                    		user.setUserName(username);
	                    		user.login();
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
	                	Message loginMsg = ClientHelper.generateLogin(username, password);
	                	
	                	// send the message
	                	sendMsgToServer(loginMsg);
	                    
	                    // rec response
	                    inMsg = (List<Message>) objInStream.readObject();
	                    System.out.println("Received [" + inMsg.size() + "] response messages from: " + socket);
	                    
	                    // for each recieved msg, process and test for successful login
	                    for (Message msg : inMsg) {
	                    	// if valid login
	                    	if (ClientHelper.handleLogin(msg) ) {
	                    		user.setUserName(msg.getText1());
	                    		user.login();
	                    		inMsg.removeAll(inMsg);
	                    		break;
	                    	}
	                    }
            		}
//                    inMsg.removeAll(inMsg);
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
	                	outMsg.clear();
	                	Message logoutMsg = ClientHelper.logout(user);
	                	outMsg.add(logoutMsg);
	                	objOutStream.writeUnshared(outMsg);
	                    objOutStream.flush();
	                    
	                    // end
	                    // receive "response"
	                    try {
	                    	inMsg = (List<Message>) objInStream.readObject();
	                    	inMsg.removeAll(inMsg);
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
	            		
	            		outMsg.clear();
	                	outMsg.add(msg);
	                	objOutStream.writeUnshared(outMsg);
	                    objOutStream.flush();
	            		
	            		// Receive response
	                    inMsg = (List<Message>) objInStream.readObject();
	                    System.out.println("Received [" + inMsg.size() + "] response messages from: " + socket);
	                    
	                    // go through recieved msgs
	                    for (Message m : inMsg) {
	                    	System.out.println("Recieved msg type: " + m.getType());
	                    	Boolean found = ClientHelper.handleViewUserFiles(m);
	                    	
	                    	// if valid msg
	                    	if (found) {
	                    		break;
	                    	}
	                    }
	                    inMsg.removeAll(inMsg);
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
	            			outMsg.clear();
		                	outMsg.add(msgUpload);
		                	objOutStream.writeUnshared(outMsg);
		                    objOutStream.flush();
		                    
		                    // rec response
		                    inMsg = (List<Message>) objInStream.readObject();
		                    System.out.println("Received [" + inMsg.size() + "] response messages from: " + socket);
		                    
		                    // for each recieved msg, check if valid
		                    for (Message m : inMsg) {
		                    	// if valid login
		                    	if (m.getType().equals("upload")) {
		                    		if (m.getStatus().equals("valid")) {
		                    			System.out.println("Server got file");
		                    		} else {
		                    			System.out.println("Server failed to get file");
		                    		}
		                    		
		                    		inMsg.removeAll(inMsg);
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
	            		outMsg.clear();
	                	outMsg.add(msgDownload);
	                	objOutStream.writeUnshared(outMsg);
	                    objOutStream.flush();
	            		
	            		// recieve response
	            		inMsg = (List<Message>) objInStream.readObject();
	                    System.out.println("Received [" + inMsg.size() + "] response messages from: " + socket);
	                    
	                    for (Message m : inMsg) {
	                    	if (m.getStatus().equals("fileMsg")) {
		                    	System.out.println("File recieved!");
		                    	// generate the file
			                    File recFile = new File("userdir/" + fileName);
			                    byte[] fileBytes = m.getFileBytes();
			                    FileHandler.byteArrToFile(recFile, fileBytes);
			                    
			                    // open the file
		        				Desktop desktop = Desktop.getDesktop();
		        				desktop.open(recFile);
		                    } else {
		                    	System.out.println("File not recieved.");
		                    	System.out.println("Message response status was: " + m.getStatus());
		                    }
	                    }
	                    inMsg.removeAll(inMsg);
	            		break;
	            	case SHARE:
	//            		cHelper.sendToServer("test send");
	            		System.out.println("TRY TO SHARE A FILE");
	            		Boolean endList = false;
	            		int shareCount = 0;
	            		// Hard-coded shareList array size as 10,000
	            		String[] shareList = new String[10000];
	            		
	            		System.out.println("Enter the file name:\t");
	            		String file_name = myScnr.nextLine();
	            		while(endList == false) {
		            		System.out.println("Enter the username of the account to be shared with *OR* Hit ENTER to END\n\t:");   
		            		String inputUsername = myScnr.nextLine();
		            		if (inputUsername.isEmpty()){
		            			endList = true;
		            		}else {
			            		shareList[shareCount] = inputUsername;
			            		shareCount++;
		            		}
	            		}
	            		if(shareCount > 0) {
	            			Message msgShare = ClientHelper.generateShare(user, file_name, shareList);
	            			outMsg.clear();
		                	outMsg.add(msgShare);
		                	objOutStream.writeUnshared(outMsg);
		                    objOutStream.flush();
		                    
		            		inMsg = (List<Message>) objInStream.readObject();
		                    System.out.println("Received [" + inMsg.size() + "] response messages from: " + socket);
		                    
		                    for (Message m : inMsg) {
		                    	System.out.println("Recieved msg type: " + m.getType());
		                    	Boolean found = ClientHelper.handleShare(m);
		                    	
		                    	// if valid msg
		                    	if (found) {
		                    		System.out.println("File: '" +  file_name + "' has been granted to share with other user accounts.\n");
		                    		break;
		                    	}
		                    }
	            		}

	            		
	            		break;
	            	case DELETE:
	//            		cHelper.sendToServer("test send");
	            		System.out.println("TRY TO DELETE A FILE\n");
	            		System.out.println("Enter the file name:\t");
	            		String userFile = myScnr.nextLine();
	            		if(!userFile.isEmpty()) {
		            		Message msgDelete = ClientHelper.generateDeleteItem(user, userFile);
	            			outMsg.clear();
		                	outMsg.add(msgDelete);
		                	objOutStream.writeUnshared(outMsg);
		                    objOutStream.flush();
		                    
		            		inMsg = (List<Message>) objInStream.readObject();
		                    System.out.println("Received [" + inMsg.size() + "] response messages from: " + socket);
		                    
		                    for (Message m : inMsg) {
		                    	System.out.println("Recieved msg type: " + m.getType());
		                    	Boolean found = ClientHelper.handleDeleteItem(m);
		                    	
		                    	// if valid msg
		                    	if (found) {
		                    		System.out.println("File: '" +  userFile + "' has been deleted.\n");
		                    		break;
		                    	}
		                    }
	            		}
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
	            			outMsg.clear();
		                	Message password_msg = ClientHelper.generatePasswordChange(user.getUserName(), password);
		                	outMsg.add(password_msg);
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
	            			outMsg.clear();
		                	Message email_msg = ClientHelper.generateEmailChange(user.getUserName(), email);
		                	outMsg.add(email_msg);
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
    
    private static void sendMsgToServer(Message msg) throws IOException {
    	outMsg.clear();
    	outMsg.add(msg);
    	objOutStream.writeUnshared(outMsg);
        objOutStream.flush();
    }
    
    private static void recieveMsgFromServer(Message msg) {
    	// might need to change plan here?
    }
  
}



            

