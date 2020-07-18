package com.team7.cs401.filestorage.client;

public class CurrentUser {
	// note: this is not a secure way of handling a user
	private String userName;
	private boolean loggedIn;
	

	public CurrentUser() {
		this.userName = null;
		this.loggedIn = false;
	}
	
	public CurrentUser(String user) {
		this.userName = user;
		this.loggedIn = true;

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public void login() {
		this.loggedIn = true;
	}
}
