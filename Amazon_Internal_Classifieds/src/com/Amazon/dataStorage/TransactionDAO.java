package com.Amazon.dataStorage;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.Amazon.models.Transactions;

public class TransactionDAO {
	
	Transactions transaction;
    private DatabaseConnection connectionManager;	
	private static TransactionDAO transactionDAO = null;
	public static TransactionDAO getInstance() {
		if (transactionDAO == null) {
			transactionDAO = new TransactionDAO();
		}
		return transactionDAO;
	}
	
	public TransactionDAO() {
        try {
        	connectionManager = new DatabaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean insertTransaction(Transactions transaction) {
        try {
            String query = "INSERT INTO Transactions ( productId, sellerId, buyerId, amount, transactionDate) VALUES ( ?, ?, ?, ?, ?)";
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(query);
//            statement.setString(1, transaction.getTransactionId());
            statement.setInt(1, transaction.getProductId());
            statement.setInt(2, transaction.getSellerId());
            statement.setInt(3, transaction.getBuyerId());
            statement.setDouble(4, transaction.getProductAmount());

            // get current date and time
            LocalDateTime currentDateTime = LocalDateTime.now();
            String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            statement.setString(5, formattedDateTime);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @SuppressWarnings("unused")
	public Transactions getTransactionById(String transactionId) {
    	
        try {
            String sql = "SELECT * FROM Transactions WHERE transactionId = ?";
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql);
            statement.setString(1, transactionId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
            	transaction = new Transactions();
                int productId = rs.getInt("productId");
                int sellerId = rs.getInt("sellerId");
                int buyerId = rs.getInt("buyerId");
                double productAmount = rs.getDouble("productAmount");
                String dateTime = rs.getString("dateTime");
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error getting transaction: " + e.getMessage());
            return null;
        }
        return transaction;

    }
    
    public void getAllTransactions() {
        try {
            String sql = "SELECT * FROM Transactions";
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int transactionId = rs.getInt("transactionId");
                int productId = rs.getInt("productId");
                int sellerId = rs.getInt("sellerId");
                int buyerId = rs.getInt("buyerId");
                double productAmount = rs.getDouble("amount");
                String dateTime = rs.getString("transactionDate");
                System.out.println("\nTransaction Id : " + transactionId);
                System.out.println("Classified Id : " + productId);
                System.out.println("Seller Id : " + sellerId);
                System.out.println("Buyer Id : " + buyerId);
                System.out.println("Product Amount : " + productAmount);
                System.out.println("Date and Time : " + dateTime);
                System.out.println("_____________________________________________");
            }
        } catch (SQLException e) {
            System.out.println("Error getting transactions: " + e.getMessage());
        }
    }

    
}
