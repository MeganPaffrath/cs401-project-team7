package com.team7.cs401.filestorage.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


/*
 * THIS FILE MIGHT NEED TO BE CHANGED COMPLETELY
 * Should we be separating client helper from the UI or combining it?
 */

public class ClientCommunicator {
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
            
            ClientHelper cHelper = new ClientHelper(socket);
            Scanner scan = new Scanner(System.in);
            
            // User input and function calls
            Boolean running = true;
            while (running) {
           
            	scan = new Scanner(System.in);
            	int choice = scan.nextInt();
				scan.nextLine();
				 
                UserSelection selection = UserSelection.values()[choice];
                switch(selection) {
                	case LOGIN:
                		System.out.println("LOGIN");
                		cHelper.login("jim", "12345");
                		break;
                		
                	case SIGNUP:
                		System.out.println("SIGNUP");
                		cHelper.signUp("kim", "12345", "kim@gmail.com");
                		break;
                	
                	case LOGOUT:
                		System.out.println("LOGOUT");
                		cHelper.logout();
                		break;
                	case UPLOAD:
                		System.out.println("TRY TO UPLOAD A FILE");
                		cHelper.uploadFile();
                		break;
                	case OTHER:
                		cHelper.sendToServer("test send");
                		break;
                	default:
                		System.out.println("Something went wrong.");
                }
           }
            
        }
    }
}



            