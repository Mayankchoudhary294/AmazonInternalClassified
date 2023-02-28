package com.Amazon.models;

public class Transactions {
    private int transactionId;
    private int productId;
    private int sellerId;
    private int buyerId;
    private double productAmount;
    private String dateTime;

    public Transactions(int productId, int sellerId, int buyerId, double productAmount, String dateTime) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.productAmount = productAmount;
        this.dateTime = dateTime;
    }
    
    public Transactions(int transactionId, int productId, int sellerId, int buyerId, double productAmount, String dateTime) {
    	this.transactionId = transactionId;
    	this.productId = productId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.productAmount = productAmount;
        this.dateTime = dateTime;
    }
    
    public Transactions() {
    	
    }

    // Getters and Setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public double getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(double productAmount) {
        this.productAmount = productAmount;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}

