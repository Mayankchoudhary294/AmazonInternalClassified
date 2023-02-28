package com.Amazon.models;

/*
	CREATE TABLE Users(
	userId INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
	username varchar(50),
	password varchar(30),
	email varchar(50),
	phone varchar(15), 
	isActive INT NOT NULL);
*/


public class User {
	
	private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private int isActive;
    private double balance;
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public int isActive() {
		return isActive;
	}

	public void setActive(int isActive) {
		this.isActive = isActive;
	}
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public User() {
		
	}

	public User(int id, String username, String password, String email, String phone, int isActive, int balance) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.isActive = isActive;
		this.balance = balance;
	}


	
	

	

    

}
