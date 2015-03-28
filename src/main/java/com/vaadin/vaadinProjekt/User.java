package com.vaadin.vaadinProjekt;

import java.util.Date;

public class User {
	
	private String name = "Maciek";
	private Date loginTime = new Date();
		
	public User(String name, Date loginTime) {
		super();
		this.name = name;
		this.loginTime = loginTime;
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
