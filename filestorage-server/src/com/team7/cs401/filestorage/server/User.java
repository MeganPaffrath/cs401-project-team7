package com.team7.cs401.filestorage.server;

public class User {
	private String userName;
	private String password;
	private String userEmail;
	
	public User(String UserName, String Password, String email){
		this.setUserName(UserName);
		this.setPassword(Password);
		this.setUserEmail(email);
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
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


}
