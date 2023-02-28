package com.Amazon.managers;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import com.Amazon.dataStorage.*;
import com.Amazon.main.UserApp;
import com.Amazon.models.*;


public class UserManager {
	User user;
	private Scanner scanner;
	private static UserManager userManager;
    private DatabaseConnection connectionManager;

    public UserManager() {
        try {
        	scanner = new Scanner(System.in);
        	scanner.useDelimiter(System.getProperty("line.separator"));
        	connectionManager = new DatabaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    public static UserManager getInstance() {
    	if (userManager == null) {
    		userManager = new UserManager();
    	}
    	return userManager;
    }
	
	public void postClassified() {
        try {
        	scanner = new Scanner(System.in);
        	Classified classified = new Classified();
        	System.out.println("\nEnter the headline of Classified (maximum 100 characters): ");
            String headline = scanner.nextLine();
            while (headline.length() > 100) {
                System.out.println("Headline is too long. Please enter a headline with a maximum of 100 characters: ");
                headline = scanner.nextLine();
            }
            classified.setHeadline(headline);
            
            System.out.println("\nEnter the name of the product (maximum 50 characters): ");
            String productName = scanner.nextLine();
            while (productName.length() > 50) {
                System.out.println("Product name is too long. Please enter a name with a maximum of 50 characters: ");
                productName = scanner.nextLine();
            }
            classified.setProductName(productName);
            
            System.out.println("\nEnter the brand of Classified (if any, maximum 25 characters): ");
            String brand = scanner.nextLine();
            while (brand.length() > 25) {
                System.out.println("Brand is too long. Please enter a brand with a maximum of 25 characters: ");
                brand = scanner.nextLine();
            }
            classified.setBrand(brand);
            
        	Condition condition = null;
        	while (condition == null) {
        	    System.out.println("\nEnter the condition of Classified: ");
        	    System.out.println("Condition can be Brand new, Lightly Used, Moderately Used, Heavily Used, Damaged, Dented, Not Working");
        	    String conditionInput = scanner.nextLine();
        	    switch(conditionInput) {
        	        case "Brand new":
        	            condition = Condition.BRAND_NEW;
        	            break;
        	        case "Lightly Used":
        	            condition = Condition.LIGHTLY_USED;
        	            break;
        	        case "Moderately Used":
        	            condition = Condition.MODERATELY_USED;
        	            break;
        	        case "Heavily Used":
        	            condition = Condition.HEAVILY_USED;
        	            break;
        	        case "Damaged":
        	            condition = Condition.DAMAGED;
        	            break;
        	        case "Dented":
        	        	condition = Condition.DENTED;
        	        	break;
        	        case "Not Working":
        	            condition = Condition.NOT_WORKING;
        	            break;
        	        default:
        	            System.out.println("Invalid condition input. Please try again.");
        	            break;
        	    }
        	}
        	classified.setCondition(condition);
        	
        	System.out.println("\nEnter the description of Classified (maximum 500 characters): ");
            String description = scanner.nextLine();
            while (description.length() > 500) {
                System.out.println("Description is too long. Please enter a description with a maximum of 500 characters: ");
                description = scanner.nextLine();
            }
            classified.setDescription(description);
            while (true) {
                System.out.println("\nEnter the price of Classified: ");
                try {
                    double price = Double.parseDouble(scanner.nextLine());
                    classified.setPrice(price);
                    break; // Exit the loop if input is valid
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid double or int value.");
                }
            }
        	System.out.println("\nEnter the category of Classified: ");
        	classified.setCategory(scanner.nextLine());
        	classified.setUserId(UserApp.getUserId());
        	classified.setApprovalStatus("Pending");
        	classified.setInventoryStatus("In Stock");

        	
            String sql = "INSERT INTO Classifieds (headline, productName, brand, condition, description, price, category, userId, approvalStatus, inventoryStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql);
            statement.setString(1, classified.getHeadline());
            statement.setString(2, classified.getProductName());
            statement.setString(3, classified.getBrand());
            statement.setString(4, classified.getCondition().name());
            statement.setString(5, classified.getDescription());
            statement.setDouble(6, classified.getPrice());
//            statement.setObject(7, pictures);
            statement.setString(7, classified.getCategory());
            statement.setInt(8, classified.getUserId());
            statement.setString(9, classified.getApprovalStatus());
            statement.setString(10, classified.getInventoryStatus());
            statement.executeUpdate();
            System.out.println("Classified posted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewApprovedClassifieds() {
        try {
            String sql = "SELECT * FROM Classifieds WHERE approvalStatus='approved' AND inventoryStatus='In Stock'";
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
//            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void viewMyClassifieds() {
            try {
	            PreparedStatement statement = connectionManager.getConnection().prepareStatement("SELECT * FROM Classifieds WHERE userId = ?");
	            statement.setInt(1, UserApp.getUserId());

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("classifiedId");
                    String headline = resultSet.getString("headline");
                    String productName = resultSet.getString("productName");
                    String brand = resultSet.getString("brand");
                    String condition = resultSet.getString("condition");
                    String description = resultSet.getString("description");
                    double price = resultSet.getDouble("price");
//                    String[] pictures = (String[]) resultSet.getArray("pictures").getArray();
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
//                    System.out.println("Pictures: " + String.join(", ", pictures));
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
    
    @SuppressWarnings("resource")
	public void manageProfile() {
    	boolean exitCode = false;
    	Scanner scanner = new Scanner(System.in);
        System.out.println("\nWe need to verify the details again to make any changes in profile");
        while(!exitCode) {
	        System.out.println("\nEnter your username: ");
	        String username = scanner.nextLine();
	        System.out.println("\nEnter Password");
	        String password = scanner.nextLine();
	        User user = UserDAO.getInstance().findUserByUsername(username, password);
	        
	        if (user.getId() == 0) {
				System.out.println("\nDetails not found. Please Enter correct Details");
//				return;
			}else {
				while(true) {
			        System.out.println("\nUser ID: " + user.getId());
			        System.out.println("Username: " + user.getUsername());
			        System.out.println("Email: " + user.getEmail());
			        System.out.println("Phone: " + user.getPhone());
		        
			        System.out.println("\n1. Edit Email");
			        System.out.println("2. Edit Phone");
			        System.out.println("3. Edit Password");
			        System.out.println("0. Exit");
			        System.out.print("Enter your choice: ");
					scanner = new Scanner(System.in);
					try {
			        int choice = Integer.parseInt(scanner.nextLine());
			        switch (choice) {
			            case 1:
			                System.out.print("\nEnter new email: ");
			                String email = scanner.next();
			                UserDAO.getInstance().updateEmail(username, email, password);
			                System.out.println("Updated to "+ email+ " successfully");
			                break;
			            case 2:
			                String phone = "";
			                boolean isValid = false;
			                while (!isValid) {
			                    System.out.print("\nEnter phone: ");
			                    phone = scanner.next();
			                    isValid = isValidPhoneNumber(phone);
			                    if (!isValid) {
			                        System.out.println("Invalid phone number. Please enter a phone number containing only digits.");
			                    } else {
			                        user.setPhone(phone);
			                    }
			                }
			                UserDAO.getInstance().updatePhone(username, phone, password);
			                System.out.println("Updated to "+ phone+ " successfully");
			                break;
			            case 3:
			                System.out.print("\nEnter new password: ");
			                String newPassword = scanner.next();
			                UserDAO.getInstance().updatePassword(username, newPassword, password);
			                System.out.println("Updated to "+ newPassword+ " successfully");
			                break;
			            case 0:
			                return;
			            default:
			                System.out.println("\nInvalid choice!");
			        }
					}catch (Exception e) {
						System.err.println("\nPlease enter Valid Input.");
					}
				}
			}
      }
  
    }
    
    public void buyProduct() {
    	boolean exitcode = false;
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
        while (!exitcode) {
            System.out.println("\nEnter the classified ID:");
            try {
            int classifiedId = Integer.parseInt(scanner.nextLine());
            int userId = UserApp.getUserId();
            
            if (!ClassifiedDAO.getInstance().isClassifiedIdValid(classifiedId)) {
                System.out.println("\nInvalid classified ID. Please try again.");
	        }else {    	
			    Classified classified = ClassifiedDAO.getInstance().getClassifiedById(classifiedId);
			    int sellerId = classified.getUserId();
			    double amount = classified.getPrice();
			    if (userId == sellerId) {
			    	System.out.println("\nYou cannot buy your own product.");
			    	break;
			    }
			    if (classified.getInventoryStatus().equals("Sold")) {
					System.out.println("\nItem is already Sold");
					break;
				}
		
			    User user = UserDAO.getInstance().findUserById(userId);
			    double balance = user.getBalance();
		
			    if (balance < amount) {
			        System.out.println("\nInsufficient balance");
			        break;
			    }else {
			    balance -= amount;
			    UserDAO.getInstance().updateUserBalance(userId, balance);
			    
		        LocalDateTime currentDateTime = LocalDateTime.now();
		        String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
			    Transactions transaction = new Transactions(classifiedId, sellerId, userId, amount, formattedDateTime);
			    TransactionDAO.getInstance().insertTransaction(transaction);
			    System.out.println("\nTransaction successfully done.");
			    ClassifiedDAO.getInstance().updateInvertoryStatus(classifiedId);
			    exitcode = true;
			    }
	    	}
            }catch (Exception e) {
	        	System.out.println("Invalid Input. Please enter a valid value.");
	        	e.getMessage();
          	 }
        }
	}
    
    public void paymentOptions() {
    	while(true) {
	    	System.out.println("\n1. Check Available Balance");
	    	System.out.println("2. Add Balance");
	    	System.out.println("0. Back to previous Menu");
	    	int choice = scanner.nextInt();
	    	
	    	switch(choice) {
	    		case 1 : 
	    			UserDAO.getInstance().checkBalance(UserApp.getUserId());
	    			break;
	    		case 2:
	    			System.out.println("\nEnter the amount to add : ");
	    			double price = scanner.nextDouble();
	    			UserDAO.getInstance().addBalance(price);
	    			System.out.println("Amount Added Successfully!!");
	    			break;
	    		case 0:
	    			return;
	    		default:
	    			System.out.println("\nInvalid Input! Please try again");
	    			break;
	    	}
    	}
    }
    
    public boolean isValidPhoneNumber(String phone) {
        String regex = "^[0-9]+$";
        return phone.matches(regex);
    }
    
   
}