package com.team7.cs401.filestorage.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class FileStorageServer {
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
            	// CURRENT WORKING POS
            	DataInputStream dIn = new DataInputStream(socket.getInputStream());
            	
            	int mLength = dIn.readInt();
            	System.out.println("mLength: " + mLength);
            	if(mLength>0) {
            		byte[] message = new byte[mLength];
            		System.out.println(new String(message) );
            		dIn.readFully(message, 0, message.length);
            		System.out.println(new String(message) );
            		System.out.println(dIn);
            	}
//            	byte messageType = dIn.readByte();
//            	System.out.println("Server says: " + dIn.readUTF() + "done.");
//            	dIn.close();
//                var in = new Scanner(socket.getInputStream());
//                var out = new PrintWriter(socket.getOutputStream(), true);
//                while (in.hasNextLine()) {
//                    out.println("Server Response: " + in.nextLine().toUpperCase());
//                }
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
