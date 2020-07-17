package com.team7.cs401.filestorage.server;

import java.io.BufferedReader;
import java.io.FileReader;
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

	public void addOrModifyUser(String username, String password) {
		this.users.add(new User(username,password));
		
	}
	
	public void loadData(String filename) {
		try {
			// put file in project main folder, not the src folder
			FileReader fin = new FileReader(filename);
			BufferedReader bis = new BufferedReader(fin);
			int line=1;
			String data;
			while ((data = bis.readLine()) != null) {
				String[] values = data.split(",");
				if (values.length != 2) {
					System.out.println("Warning: Invalid DVD entry line \"" + line + "\"");
					return;
				}
				addOrModifyUser(values[0], values[1]);
				line++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("File is not found.");
		}
	}

	public static void main(String[] args) {
		AllUsers allUsers = new AllUsers();
		allUsers.loadData("I:\\Summer2020\\Project\\Phase 3\\cs401-project-team7\\filestorage-server\\src\\AllUsers.txt");
		User result = allUsers.getUser("jim");
		System.out.println(result.getPassword());
		
	}
}
