package com.team7.cs401.filestorage.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileHandler {
	public static byte[] fileToByteArr(String filePath) throws IOException {
		File file = new File(filePath);
		if (file.exists() ) {
			// opens the file
//			Desktop desktop = Desktop.getDesktop();
//			desktop.open(file);
		} else {
			System.out.println("THE FILE DOES NOT EXIST: FileHandler.fileToByteArr failed");
		}
		
		// turn file into byte array
		byte[] byteArr = null;
		try {
			byteArr = new byte[ (int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(byteArr); // convert to bytes
			fis.close();
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			System.out.println("from fileToByteArr()");
		}
		
		
		return byteArr;
	}
	
	/*
	 * @param loc: a File made by the new location
	 */
	public static void byteArrToFile(File loc, byte[] bytes) {
		try {
			OutputStream os = new FileOutputStream(loc);
			// write bytes to recFile
			os.write(bytes);
			os.close();
		} catch (Exception e) {
			System.out.print("Exception: " + e);
		}
	}
	
}
