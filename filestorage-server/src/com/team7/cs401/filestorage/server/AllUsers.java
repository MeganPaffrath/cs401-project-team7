package com.team7.cs401.filestorage.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AllUsers {
	private ArrayList<User> users = new ArrayList<User>();
	
	public User getUser(String username) {
		
		for(User user:users){
			//System.out.println(user.getUserName() + "," + user.getPassword());
			if ( user.getUserName().equals(username)){
				//System.out.println(username + "," + user.getPassword());
				System.out.println("Found User: "+ username);
				
				return user;
			}
		}
		return null;		
	}

	public void addOrModifyUser(String username, String password, String email) {
		// check to see if the user exists
		User existingUser = getUser(username);
		if (existingUser == null) {
			this.users.add(new User(username,password, email));
		} else {
			// update existing user
			for (User user: users) {
				if ( user.getUserName().equals(username)) {
					user.setPassword(password);
					user.setUserEmail(email);
				}
			}
		}
	}
	

	
	public void save(String sourceName) {
		try {
			Path path = Paths.get("src/" + sourceName);
			FileWriter fw = new FileWriter(path.toFile().getAbsolutePath());
			if (!users.isEmpty()) {
				for (int i = 0; i < users.size(); i++) {
					User user = users.get(i);
					fw.write(user.getUserName() + "," + user.getPassword() + "," + user.getUserEmail());
					fw.write(System.lineSeparator());
				}
			}

			fw.close();
			System.out.println("Changes to " + sourceName + " saved.");
		} catch (IOException e) {
			System.err.println("Error saving: " + e);
		}
	}
	
	public void loadData(String filename) {
		try {
			Path path = Paths.get("src/" + filename);
			// put file in project main folder, not the src folder
			FileReader fin = new FileReader(path.toFile().getAbsolutePath());
			BufferedReader bis = new BufferedReader(fin);
			int line=1;
			String data;
			while ((data = bis.readLine()) != null) {
				String[] values = data.split(",");
				if (values.length != 3) {
					System.out.println("Warning: Invalid User entry line \"" + line + "\"");
					return;
				}
				addOrModifyUser(values[0], values[1],values[2]);
				line++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("File is not found.");
		}
	}
	
	//modify account 
	public void changeAccountSettings(String password, String email,String username) {
		User existingUser = getUser(username);
		for (User user: users) {
		if ( user.getUserName().equals(username)) {
			user.setPassword(password);
			user.setUserEmail(email);
		}}
		
	}
	// logging user that are loged in 
	public void logEvent(String username, String logIN) {
		
		
	}

	
}
