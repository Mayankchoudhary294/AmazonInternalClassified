package com.Amazon.managers;

import java.sql.*;
import java.util.Scanner;
import com.Amazon.dataStorage.*;


public class AdminManager {
	
	private Scanner scanner ;
    private DatabaseConnection connectionManager;

	private static AdminManager adminManager = null;
	public static AdminManager getInstance() {
		if (adminManager == null) {
			adminManager = new AdminManager();
		}
		return adminManager;
	}
	
	public AdminManager() {
        try {
        	scanner = new Scanner(System.in);
        	connectionManager = new DatabaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
	
	public void approveClassified() {
		System.out.print("\nEnter the classified ID: ");
        int classifiedId = scanner.nextInt();

        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement("SELECT * FROM Classifieds WHERE classifiedId = ?")) {
            stmt.setInt(1, classifiedId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String status = rs.getString("approvalStatus");
                if (status.equalsIgnoreCase("Pending")) {
                    PreparedStatement updateStmt = connectionManager.getConnection().prepareStatement("UPDATE classifieds SET approvalStatus = 'approved' WHERE classifiedId = ?");
                    updateStmt.setInt(1, classifiedId);
                    updateStmt.executeUpdate();
                    System.out.println("\nClassified approved successfully!");
                } else {
                    System.out.println("\nClassified has already been approved or rejected!");
                }
            } else {
                System.out.println("\nClassified ID not found!, Please enter Valid Classified Id");
            }
        } catch (SQLException e) {
            System.out.println("Error: Invalid Input" );
        }		
	}
	
	public void rejectClassified() {
		System.out.print("\nEnter the classified ID: ");
        int classifiedId = scanner.nextInt();

        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement("SELECT * FROM Classifieds WHERE classifiedId = ?")) {
            stmt.setInt(1, classifiedId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String status = rs.getString("approvalStatus");
                if (status.equalsIgnoreCase("Pending")) {
                    PreparedStatement updateStmt = connectionManager.getConnection().prepareStatement("UPDATE classifieds SET approvalStatus = 'rejected' WHERE classifiedId = ?");
                    updateStmt.setInt(1, classifiedId);
                    updateStmt.executeUpdate();
                    System.out.println("\nClassified rejected successfully!");
                } else {
                    System.out.println("\nClassified has already been approved or rejected!");
                }
            } else {
                System.out.println("\nClassified ID not found!, Please enter Valid Classified Id");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
		
	}
	
	public void activateUser() {
		System.out.print("\nEnter the User ID: ");
        int userId = scanner.nextInt();

        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement("SELECT * FROM Users WHERE userId = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int isActive = rs.getInt("isActive");
                if (isActive == 0) {
                    PreparedStatement updateStmt = connectionManager.getConnection().prepareStatement("UPDATE Users SET isActive = 1 WHERE userId = ?");
                    updateStmt.setInt(1, userId);
                    updateStmt.executeUpdate();
                    System.out.println("\nUser activated successfully!");
                } else {
                    System.out.println("\nUser is already active!");
                }
            } else {
                System.out.println("\nUser ID not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error: Invalid Input" );
        }
	}
	
	public void deactivateUser() {
		System.out.print("\nEnter the User ID: ");
        int userId = scanner.nextInt();

        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement("SELECT * FROM Users WHERE userId = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int isActive = rs.getInt("isActive");
                if (isActive == 1) {
                    PreparedStatement updateStmt = connectionManager.getConnection().prepareStatement("UPDATE Users SET isActive = 0 WHERE userId = ?");
                    updateStmt.setInt(1, userId);
                    updateStmt.executeUpdate();
                    System.out.println("\nUser deactivated successfully!");
                } else {
                    System.out.println("\nUser is already deactivated!");
                }
            } else {
                System.out.println("\nUser ID not found!");
            }
        } catch (SQLException e) {
            System.out.println("Error: Invalid Input");
        }		
	}
	
	
	public void addClassified() {
		UserManager.getInstance().postClassified();
	}
	
	
	public void removeClassified() {
		System.out.println("\nEnter classified ID : ");
		int id = scanner.nextInt();
		try {
		ClassifiedDAO.getInstance().removeClassified(id);
		System.out.println("\nClassified removed Successfully!");
		}catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public void manageCategory() {
		System.out.print("\nEnter the classified ID to update the category: ");
        int classifiedId = scanner.nextInt();
        System.out.print("\nEnter the new category name: ");
        String newCategory = scanner.next();

        try {
            String checkQuery = "SELECT * FROM Classifieds WHERE classifiedId = ?";
            PreparedStatement checkStmt = connectionManager.getConnection().prepareStatement(checkQuery);
            checkStmt.setInt(1, classifiedId);
            ResultSet checkResult = checkStmt.executeQuery();
            
            if (checkResult.next()) {
                String updateQuery = "UPDATE Classifieds SET category = ? WHERE classifiedId = ?";
                PreparedStatement updateStmt = connectionManager.getConnection().prepareStatement(updateQuery);
                updateStmt.setString(1, newCategory);
                updateStmt.setInt(2, classifiedId);
                int rowsUpdated = updateStmt.executeUpdate();
                
                if (rowsUpdated > 0) {
                    System.out.println("\nCategory updated successfully!");
                } else {
                    System.out.println("\nError updating category.");
                }
            } else {
                System.out.println("\nClassified not found.");
            }
        } catch (SQLException e) {
            System.out.println("\nSQL Exception: " + e.getMessage());
        }
	}
	
	public void generateReports() {
		while(true) {
			System.out.println("\nEnter your choice");
			System.out.println("\n1. View all Classifieds\n"
					+ "2. View all Users Details\n"
					+ "3. View all Transaction Details\n"
					+ "4. View Items In Stock\n"
					+ "5. View Sold Items\n"
					+ "0. Back to previous Menu\n");
			
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				viewAllClassifieds();
				break;
			case 2:
				viewAllUsers();
				break;
			case 3:
				TransactionDAO.getInstance().getAllTransactions();
				break;
			case 4:
				viewInStockItems();
				break;
			case 5:
				viewSoldItems();
				break;
			case 0:
				return;
			default:
				System.out.println("\nInvalid Options");
			}
		}
	}
	
	public void viewAllClassifieds() {
    	try {
            String sql = "SELECT * FROM Classifieds";
            Statement statement = connectionManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("classifiedId");
                String headline = resultSet.getString("headline");
                String productName = resultSet.getString("productName");
                String brand = resultSet.getString("brand");
                String condition = resultSet.getString("condition");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
//                String[] pictures = (String[]) resultSet.getArray("pictures").getArray();
                String category = resultSet.getString("category");
                int userId = resultSet.getInt("userId");
                String approvalStatus = resultSet.getString("approvalStatus");
                String inventoryStatus = resultSet.getString("inventoryStatus");
                System.out.println("\nClassified ID: " + id);
                System.out.println("Headline: " + headline);
                System.out.println("Product Name: " + productName);
                System.out.println("Brand: " + brand);
                System.out.println("Condition: " + condition);
                System.out.println("Description: " + description);
                System.out.println("Price: " + price);
//                System.out.println("Pictures: " + String.join(", ", pictures));
                System.out.println("Category: " + category);
                System.out.println("User ID: " + userId);
                System.out.println("Status: " + approvalStatus);
                System.out.println("Stock Status: " + inventoryStatus);
                System.out.println("-------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unused")
	public void viewAllUsers() {
    	try {
            String sql = "SELECT * FROM Users";
            Statement statement = connectionManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("userId");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                int isActive = resultSet.getInt("isActive");
                System.out.println("\nUser ID: " + id);
                System.out.println("username: " + username);
                System.out.println("password: *********" );
                System.out.println("email: " + email);
                System.out.println("phone: " + phone);
                System.out.println("isActive: " + isActive);
                System.out.println("-------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void viewInStockItems() {
    	try {
            String sql = "SELECT * FROM Classifieds WHERE inventoryStatus LIKE '%In Stock%'";
            Statement statement = connectionManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                int id = resultSet.getInt("classifiedId");
                String headline = resultSet.getString("headline");
                String productName = resultSet.getString("productName");
                String brand = resultSet.getString("brand");
                String condition = resultSet.getString("condition");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
//                String[] pictures = (String[]) resultSet.getArray("pictures").getArray();
                String category = resultSet.getString("category");
                int userId = resultSet.getInt("userId");
                String approvalStatus = resultSet.getString("approvalStatus");
                String inventoryStatus = resultSet.getString("inventoryStatus");
                System.out.println("\nClassified ID: " + id);
                System.out.println("Headline: " + headline);
                System.out.println("Product Name: " + productName);
                System.out.println("Brand: " + brand);
                System.out.println("Condition: " + condition);
                System.out.println("Description: " + description);
                System.out.println("Price: " + price);
//                System.out.println("Pictures: " + String.join(", ", pictures));
                System.out.println("Category: " + category);
                System.out.println("User ID: " + userId);
                System.out.println("Status: " + approvalStatus);
                System.out.println("Stock Status: " + inventoryStatus);
                System.out.println("-------------------------------------");
            }
            if (!resultSet.next()) {
				System.out.println("\nNo item in Stock.");
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void viewSoldItems() {
    	try {
            String sql = "SELECT * FROM Classifieds WHERE inventoryStatus = 'Sold'";
            Statement statement = connectionManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("classifiedId");
                String headline = resultSet.getString("headline");
                String productName = resultSet.getString("productName");
                String brand = resultSet.getString("brand");
                String condition = resultSet.getString("condition");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
//                String[] pictures = (String[]) resultSet.getArray("pictures").getArray();
                String category = resultSet.getString("category");
                int userId = resultSet.getInt("userId");
                String approvalStatus = resultSet.getString("approvalStatus");
                String inventoryStatus = resultSet.getString("inventoryStatus");
                System.out.println("\nClassified ID: " + id);
                System.out.println("Headline: " + headline);
                System.out.println("Product Name: " + productName);
                System.out.println("Brand: " + brand);
                System.out.println("Condition: " + condition);
                System.out.println("Description: " + description);
                System.out.println("Price: " + price);
//                System.out.println("Pictures: " + String.join(", ", pictures));
                System.out.println("Category: " + category);
                System.out.println("User ID: " + userId);
                System.out.println("Status: " + approvalStatus);
                System.out.println("Stock Status: " + inventoryStatus);
                System.out.println("-------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        
        }
    }

}
