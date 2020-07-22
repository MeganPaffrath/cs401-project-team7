package com.team7.cs401.filestorage.server;

import java.awt.Desktop;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.Executors;

import com.team7.cs401.filestorage.client.ClientHelper;
import com.team7.cs401.filestorage.client.FileHandler;
import com.team7.cs401.filestorage.client.Message;


public class ServerCommunicator {
	private static int threadCount = 25;

    public static void main(String[] args) throws Exception {
    	
        try (var listener = new ServerSocket(1234)) {
            System.out.println("The server is running...");
            var pool = Executors.newFixedThreadPool(threadCount);
            while (true) {
            	// Server listens for incoming connections from clients
                pool.execute(new MessagePasser(listener.accept()));
            }
        }
    }

    private static class MessagePasser implements Runnable {
    	// on receiving connection, create a new thread
        private Socket socket;

        MessagePasser(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Connected: " + socket);
            try {
            	
            	// Set up communication ----------------------------------------------------v
            	// Input
            	// get input stream from connected socket & object input
                InputStream in = socket.getInputStream();
                ObjectInputStream objInStream = new ObjectInputStream(in);
                
                // Output
                // get output stream from connected socket & object output
                OutputStream out = socket.getOutputStream();
                ObjectOutputStream objOutStream = new ObjectOutputStream(out);
                
                
                // Read list of msgs from the socket
                List<Message> messagesOut = new ArrayList<>();
                List<Message> messagesIn = new ArrayList<>();
             // Communication fully set up -----------------------------------------------^
                
                // Instantiate ServerHelper
                ServerHelper sHelper = new ServerHelper();

                boolean loggedOut = false;
                while (!loggedOut) { // run for user until they log out
                	messagesIn = (List<Message>) objInStream.readObject();

                    System.out.println("Received [" + messagesIn.size() + "] messages from: " + socket);
                    
                    System.out.println("All messages:");
                    messagesIn.forEach(msg -> printMessage(msg));
                    // iterate
                	for (Message msg : messagesIn) {
                    	System.out.println("Recieved: " + msg.getType());
                    	
                    	// Login, logout, or message
                    	if (msg.getType().equalsIgnoreCase("login")) { // LOGIN
                    		System.out.println("Recieved login msg");
                    		
                    		// handle login message
                    		Message msgR = sHelper.validateLogin(msg);
                    		
                    		// send back response
	                          messagesOut.add(msg);
	                          
	                          objOutStream.writeUnshared(messagesOut);
	                          objOutStream.flush();
	                          System.out.println("Sending login response");
                    		
                    	} else if (msg.getType().equalsIgnoreCase("file")) { // File message
                    		// generate response
                    		Message msgR = sHelper.uploadValidation(msg);
                    		// send response
                            messagesOut.add(msgR);
                            objOutStream.writeUnshared(messagesOut);
                            objOutStream.flush();
                    	} else if (msg.getType().equalsIgnoreCase("fileReq")) { // Request to download a file
                    		System.out.println("Recieved a file download request");
                    		try {
                    			// make response msg
                        		Message msgR =sHelper.grantDownloadRequest(msg);
                        		// send the message
                    			messagesOut.clear();
                            	messagesOut.add(msgR);
                            	objOutStream.writeUnshared(messagesOut);
                                objOutStream.flush();
                    		} catch (Exception e) {
                    			System.out.println("server could not find file");
                    		}
                    		
                    	} else if (msg.getType().equalsIgnoreCase("mainDirRequest")) { //  returns the users main dir
                    		// generate response message
                    		String user = msg.getText1();
                    		String[] fileNames = {"this.txt", "method.txt", "isnt.txt", "made.txt", "yet.txt" };
                    		Message msgDir = new Message("mainDir", "dirMsg", user, fileNames);
                    		
                    		// send msg back
                    		messagesOut.add(msgDir);
                            objOutStream.writeUnshared(messagesOut);
                            objOutStream.flush();
                    	} else if (msg.getType().equalsIgnoreCase("OTHER")) { // this is where the other msgs will go
                    		
                    	} else if (msg.getType().equalsIgnoreCase("logout")) {
                    		System.out.println("Recieved logout");
                    		
                    		// log out
                    		/*
                    		 * IF KEEPING TRACK OF CURRENT USERS, REMOVE THIS USER
                    		 */
                    		loggedOut = true;
                    		break;
                    	} else {
                    		System.out.println("Recieved bad request");
                    	}
                    }

                    
                    // empty recieved messages
                    messagesIn.clear();
                    messagesOut.clear();
                    objOutStream.reset();
               
                    System.out.println("Clearing messages");
                }
              
                System.out.println("Logged out!!!!!!!!!");
        
            } catch (Exception e) {
                System.out.println("Error:" + socket);
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
                System.out.println("Closed: " + socket);
            }
        }
    }
    
    private static void printMessage(Message msg){
        System.out.println("Type: " + msg.getType());
        System.out.println("Text: " + msg.getText1());
    }
}