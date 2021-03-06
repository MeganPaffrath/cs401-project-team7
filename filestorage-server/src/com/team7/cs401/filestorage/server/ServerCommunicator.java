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
	
	// utilize this later to keep track of current users
	public String[] users; 
	public static List<Message> outMsg = new ArrayList<>();
    public static List<Message> inMsg = new ArrayList<>();
    private static ObjectOutputStream objOutStream;
    private static ObjectInputStream objInStream;

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
                objInStream = new ObjectInputStream(in);
                
                // Output
                // get output stream from connected socket & object output
                OutputStream out = socket.getOutputStream();
                objOutStream = new ObjectOutputStream(out);
                // Communication fully set up -----------------------------------------------^
                
                // Instantiate ServerHelper
                ServerHelper sHelper = new ServerHelper();

                boolean loggedOut = false;
                while (!loggedOut) { // run for user until they log out
                	inMsg = (List<Message>) objInStream.readObject();

                    System.out.println("Received [" + inMsg.size() + "] messages from: " + socket);
                    
                    System.out.println("All messages:");
                    inMsg.forEach(msg -> printMessage(msg));
                    // iterate
                	for (Message msg : inMsg) {
                    	System.out.println("Recieved: " + msg.getType());
                    	
                    	// Login, logout, or message
                    	if (msg.getType().equalsIgnoreCase("login")) { // LOGIN
                    		System.out.println("Recieved login msg");
                    		
                    		// handle login message
                    		Message msgR = sHelper.validateLogin(msg);
                    		
                			// send back response
                    		sendMsgToClient(msgR);
	                          
                    	} else if (msg.getType().equalsIgnoreCase("signup")) { // this is where the other msgs will go
                    		System.out.println("Recieved signup request");
                    		
                    		// Test if valid user
                    		sHelper.newUserValidation(msg);
                    		
                    		// make user if valid
                    		sHelper.newUser(msg);
                    		
                    		// if user created, make directory for them as well.
                    		File newFile = new File("allfiles/" + msg.getText1());
                    		newFile.mkdir();
                    		// within this folder make an example file
                    		File newInnerFile = new File("allfiles/" + msg.getText1() + "/firstFile.txt");
                    		String str = "This is your first file!";
                    	    BufferedWriter writer = new BufferedWriter(new FileWriter(newInnerFile));
                    	    writer.write(str);
                    	    writer.close();
                    		
                    		// send the msg back
                    		outMsg.add(msg);
		                    objOutStream.writeUnshared(outMsg);
		                    objOutStream.flush();
		                    System.out.println("Sending new user response msg");
                    	} else if (msg.getType().equalsIgnoreCase("file")) { // File message
                    		// generate response
                    		Message msgR = sHelper.uploadValidation(msg);
                    		// send response
                            outMsg.add(msgR);
                            objOutStream.writeUnshared(outMsg);
                            objOutStream.flush();
                    	} else if (msg.getType().equalsIgnoreCase("fileReq")) { // Request to download a file
                    		System.out.println("Recieved a file download request");
                    		try {
                    			// make response msg
                        		Message msgR =sHelper.grantDownloadRequest(msg);
                        		// send the message
                    			outMsg.clear();
                            	outMsg.add(msgR);
                            	objOutStream.writeUnshared(outMsg);
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
                    		outMsg.add(msgDir);
                            objOutStream.writeUnshared(outMsg);
                            objOutStream.flush();
                    	} else if (msg.getType().equalsIgnoreCase("OTHER")) { // this is where the other msgs will go
                    		
                    	} else if (msg.getType().equalsIgnoreCase("logout")) {
                    		System.out.println("Recieved logout");
                    		loggedOut = true;
                    		break;
                    	} else {
                    		System.out.println("Recieved bad request");
                    	}
                    }

                    
                    // empty recieved messages
                    inMsg.clear();
                    outMsg.clear();
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
    
    private static void sendMsgToClient(Message msg) throws IOException {
    	outMsg.add(msg);
        objOutStream.writeUnshared(outMsg);
        objOutStream.flush();
    }
    
    private static void recieveMsgFromClient(Message msg) {
    	// objInStream needs to be declared in diff location for this to work
    }
}