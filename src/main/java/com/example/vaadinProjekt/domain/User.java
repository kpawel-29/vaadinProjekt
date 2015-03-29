package com.example.vaadinProjekt.domain;

import java.util.Date;

public class User {
	
	private int id = 0;
	private String name = "Maciek";
	private Date loginTime = new Date();
		
	public User(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		//this.loginTime = loginTime;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	
}
