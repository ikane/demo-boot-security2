package com.example.demobootsecurity.config;

public class ParamsDto {
	
	private String login;
	private String connectionTime;
	private String connectionTimeFormat;
	
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
	
	public String getConnectionTimeFormat() {
		return connectionTimeFormat;
	}

	public void setConnectionTimeFormat(String connectionTimeFormat) {
		this.connectionTimeFormat = connectionTimeFormat;
	}

	@Override
	public String toString() {
		return "[login:" + login + " - connectionTime: " + connectionTime + " - connectionTimeFormat: " + connectionTimeFormat + "]";
	}
}
