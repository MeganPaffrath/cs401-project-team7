package com.team7.cs401.filestorage.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class FileStorageClient {
	private enum UserSelection {
		LOGIN, SIGNUP, LOGOUT,
		UPLOAD,
		OTHER
	}

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Failed to pass IP as command line argument.");
            return;
        }
        try (var socket = new Socket(args[0], 1234)) {
            System.out.println("Enter lines of text then Ctrl+D or Ctrl+C to quit");
            
            // Set up scanner to use w/ server
//            var scanner = new Scanner(System.in);
//            var in = new Scanner(socket.getInputStream());
//            var out = new PrintWriter(socket.getOutputStream(), true);
            
            ClientHelper cHelper = new ClientHelper(socket);
            
            // User input and function calls
            Boolean running = true;
//            while (running) {
            	/*
                 * HAVE USER MAKE NEXT SELECTION
                 * MAKE A LOOP OF THIS SECTION AND THE SWITCH STATEMENT BELOW
                 */
                
                // Allow for various actions based on user input?
                UserSelection selection = UserSelection.OTHER;
                switch(selection) {
                	case LOGIN:
                		System.out.println("LOGIN");
                		break;
                	case LOGOUT:
                		System.out.println("LOGOUT");
                		break;
                	case UPLOAD:
                		System.out.println("TRY TO UPLOAD A FILE");
                		break;
                	case OTHER:
                		System.out.println("DO SOMETHING ELSE");
                		cHelper.sendToServer("test send");
                		selection = UserSelection.LOGIN;
                		break;
                	default:
                		System.out.println("Something went wrong.");
                }
//            }
            
        }
    }
}



            