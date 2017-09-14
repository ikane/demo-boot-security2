package com.example.demobootsecurity.config;

public class ParamsDto {
	
	private String login;
	private String connectionTime;
	
	public ParamsDto() {
		//  Auto-generated constructor stub
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getConnectionTime() {
		return connectionTime;
	}

	public void setConnectionTime(String connectionTime) {
		this.connectionTime = connectionTime;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[login:" + login + " - connectionTime: " + connectionTime + "]";
	}
}
