package com.team7.cs401.filestorage.server;

import java.awt.Desktop;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.Executors;
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
                    		// check that username = username and password = password
                    		if (msg.getText1().contentEquals("username") && msg.getText2().contentEquals("password")) {
                    			System.out.println("Valid login");
                    			
                    			// set message status
                    			msg.setStatus("valid");
                    			
                    			// Send msg back
                                messagesOut.add(msg);
                                objOutStream.writeUnshared(messagesOut);
                                objOutStream.flush();
                                System.out.println("Success message sent.");
                    			
                    		} else {
                    			System.out.println("Invalid login");
                    			msg.setStatus("failure");
                    			
                    			// Send msg back
                                messagesOut.add(msg);
                                
                                objOutStream.writeUnshared(messagesOut);
                                objOutStream.flush();
                                System.out.println("Failure message sent.");
                    		}
                    	} else if (msg.getType().equalsIgnoreCase("file")) { // File message
                    		System.out.println("Recieved a file");
                			
                			// try to open the recieved file
                    		byte[] fileBytes = msg.getFileBytes();
                    		// plan the filepath
                    		File recFile = new File("allfiles/recText.txt");
                    		
                    		try {
                    			OutputStream os = new FileOutputStream(recFile);
                    			// write bytes to recFile
                    			os.write(fileBytes);
                    			os.close();
                    		} catch (Exception e) {
                    			System.out.print("Exception: " + e);
                    		}
                    		
                    		// turn byte array into a file
                    		
                    		
            				// opens the file
            				Desktop desktop = Desktop.getDesktop();
            				desktop.open(recFile);
                			
                			// Send msg back : upload validation
                			Message msgR = new Message("upload", "valid", "user", "UUID:");
                            messagesOut.add(msgR);
                            objOutStream.writeUnshared(messagesOut);
                            objOutStream.flush();
                            System.out.println("Upload msg sent");

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