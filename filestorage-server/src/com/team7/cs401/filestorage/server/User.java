package com.team7.cs401.filestorage.server;

public class User {
	private String userName;
	private String password;

	public User(String UserName, String Password){
		this.setUserName(UserName);
		this.setPassword(Password);
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}




}
