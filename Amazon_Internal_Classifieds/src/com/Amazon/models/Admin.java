package com.Amazon.models;

public class Admin {

	  private static Admin admin;

	  private String adminId;
	  private String password;

	  private Admin() {
	  }

	  public static Admin getAdminInstance() {
	    if (admin == null) {
	      admin = new Admin();

	      admin.adminId = "ADMIN";
	      admin.password = "ADMIN";
	    }
	    return admin;
	  }

	  public String getAdminId() {
	    return adminId;
	  }

	  public String getPassword() {
	    return password;
	  }
}
