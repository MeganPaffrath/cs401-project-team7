package com.team7.cs401.filestorage.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class ServerCommunicator {
	protected static int threadCount = 15;
	
	public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(1234)) {
            System.out.println("The File Storage System is now running.");
            var pool = Executors.newFixedThreadPool(threadCount);
            while (true) {
                pool.execute(new Communicator(listener.accept()));
            }
        }
    }

    private static class Communicator implements Runnable {
        private Socket socket;

        Communicator(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Connected: " + socket);
            try {
            	// Turn into listening function? vvvvv
            	DataInputStream dIn = new DataInputStream(socket.getInputStream());
            	
            	int mLength = dIn.readInt();
            	System.out.println("mLength: " + mLength);
            	if(mLength>0) {
            		byte[] message = new byte[mLength];
            		dIn.readFully(message, 0, message.length);
            		System.out.println("Server recieved: " + new String(message) );
            	}
            	// Turn into listening function? ^^^^^
            } catch (Exception e) {
                System.out.println("Error:" + socket);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
                System.out.println("Closed: " + socket);
            }
        }
    }
}
