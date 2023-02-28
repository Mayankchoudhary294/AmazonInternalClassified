package com.Amazon.models;

/*
 CREATE TABLE Classifieds (
  id INT AUTO_INCREMENT PRIMARY KEY,
  headline VARCHAR(100) NOT NULL,
  productName VARCHAR(50) NOT NULL,
  brand VARCHAR(25),
  condition ENUM('Brand New', 'Lightly Used', 'Moderately Used', 'Heavily Used', 'Damaged/Dented', 'Not Working') NOT NULL,
  description VARCHAR(500) NOT NULL,
  price DOUBLE NOT NULL,
  category VARCHAR(100) NOT NULL,
  userId INT NOT NULL,
  FOREIGN KEY (userId) REFERENCES User(id)
);
 */

public class Classified {
	private int id;
    private String headline;
    private String productName;
    private String brand;
    private Condition condition;
    private String description;
    private double price;
//    private String[] pictures;
    private String category;
    private int userId;
    private String approvalStatus;
    private String inventoryStatus;
    
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getHeadline() {
		return headline;
	}
	
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public Condition getCondition() {
		return condition;
	}
	
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getApprovalStatus() {
		return approvalStatus;
	}
	
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	public String getInventoryStatus() {
		return inventoryStatus;
	}
	
	public void setInventoryStatus(String inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}
        
	
	


}
