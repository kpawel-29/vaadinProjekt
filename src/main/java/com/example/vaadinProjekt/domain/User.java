package com.example.vaadinProjekt.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class User {
	
	private int id = 0;
	@NotNull
	private String name = "Maciek";
	private Date loginTime = new Date();
	private String color = "#0085D5";
		
	public User(int id, String name, String color) {
		super();
		this.id = id;
		this.name = name;
		this.color = color;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
	
}
