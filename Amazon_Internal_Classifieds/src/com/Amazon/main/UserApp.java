package com.Amazon.main;
import com.Amazon.dataStorage.*;
import java.util.Scanner;
import com.Amazon.models.User;
import com.Amazon.managers.*;

public class UserApp {
    Scanner scanner;
    private static int userId;
    private static UserApp app;
    
	public static int getUserId() {
		return userId;
	}
	
	public static UserApp getInstance() {
		if(app == null) {
			app = new UserApp();
		}
		return app;
	}
	
	private UserApp(){
		scanner = new Scanner(System.in);
	}
    
    
    public void startUserApp() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
	        while (true) {
	            System.out.println("\nWelcome to User Portal");
	            System.out.println("1. Register");
	            System.out.println("2. Login");
	            System.out.println("0. Back to Main Menu");
	            System.out.print("Enter your Choice: ");
	            try {
			        int choice = Integer.parseInt(scanner.nextLine());
				        switch (choice) {
		                case 1:
		                	registerUser();
		                	break;
		                case 2 : 
		                	loginUser();
		                	break;
		                case 0 : 
		                	return;
		                default :
		                	System.out.println("Invalid Choice. Please try again!\n");
				        }
	             }catch (Exception e) {
		        	System.out.println("Invalid Input. Please enter a valid value.");
		        	e.getMessage();
	          	 }
	        }
    }
    
    private void registerUser() {
    	User user = new User();
        System.out.print("\nEnter username: ");
        String username = scanner.next();

        System.out.print("\nEnter password: ");
        String password = scanner.next();

        System.out.print("\nEnter email: ");
        String email = scanner.next();

        String phone = "";
        boolean isValid = false;
        while (!isValid) {
            System.out.print("\nEnter phone: ");
            phone = scanner.next();
            isValid = UserManager.getInstance().isValidPhoneNumber(phone);
            if (!isValid) {
                System.out.println("Invalid phone number. Please enter a phone number containing only digits.");
            } else {
                user.setPhone(phone);
            }
        }

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setActive(1);

        UserDAO.getInstance().createUser(user);
        System.out.println("\nUser registered successfully!");
        userMenu();
    }
    
    private void loginUser() {
        System.out.print("\nEnter username: ");
        String username = scanner.next();

        System.out.print("\nEnter password: ");
        String password = scanner.next();
        
        try {
        User user = UserDAO.getInstance().findUserByUsername(username, password);
        if (user != null && user.getPassword().equals(password) && user.isActive()==1) {
            System.out.println("\nLogin successful!");
            userId = UserDAO.getInstance().getUserId(username, password);
            userMenu();
        } else {
            System.out.println("\nLogin failed, incorrect username or password.");
        }}catch (Exception e) {
        	System.out.println("Something went wrong, Please check the entered details correctly");
        }
    }

    
    public void userMenu() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n******* User Menu *******");
            System.out.println("1. Manage Profile");
            System.out.println("2. Post Classified");
            System.out.println("3. View All Classifieds");
            System.out.println("4. View My Classifieds");
            System.out.println("5. Buy Product");
            System.out.println("6. Payment Options");
            System.out.println("0. Back to previous menu");
            System.out.print("\nEnter your choice: ");
            try {
            	int choice = Integer.parseInt(scanner.nextLine());
	            switch (choice) {
	                case 1:
	                	UserManager.getInstance().manageProfile();
	                    break;
	                case 2:
	                	UserManager.getInstance().postClassified();
	                    break;
	                case 3:
	                	UserManager.getInstance().viewApprovedClassifieds();
	                    break;
	                case 4:
	                	UserManager.getInstance().viewMyClassifieds();
	                    break;
	                case 5:
	                	UserManager.getInstance().buyProduct();
	                    break;
	                case 6:
	                	UserManager.getInstance().paymentOptions();
	                    break;
	                case 0:
	                    return;
	                default:
	                    System.out.println("\nInvalid choice. Please try again.");
	            }
            }catch (Exception e) {
	        	System.out.println("Invalid Input. Please enter a valid value.");
	        	e.getMessage();
          	 }
        }
    }
    
    

}
