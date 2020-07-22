package com.team7.cs401.filestorage.server;

import java.io.BufferedReader;
import java.io.FileReader;
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

	public void addOrModifyUser(String username, String password,String email) {
		
		this.users.add(new User(username,password, email));
		
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

	
}
