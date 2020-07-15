package com.team7.cs401.filestorage.client;

public class CurrentUser {
	private String userName;
	private boolean loggedIn;
	
	public CurrentUser(String user) {
		this.setUserName(user);
		setLoggedIn(true);
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
	
}
