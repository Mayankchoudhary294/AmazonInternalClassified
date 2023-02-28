package com.Amazon.main;
import java.util.Scanner;
import com.Amazon.managers.AdminManager;
import com.Amazon.models.Admin;


public class AdminApp {
	
    Scanner scanner = new Scanner(System.in);
    private String adminID = Admin.getAdminInstance().getAdminId();
    private String password = Admin.getAdminInstance().getPassword();
    private static int loginTries = 1;
    private final static int maxLoginTries = 3;
    
    private static AdminApp app;
	
	public static AdminApp getInstance() {
		if(app == null) {
			app = new AdminApp();
		}
		return app;
	}
	
	private AdminApp(){
		scanner = new Scanner(System.in);
	}
    
    
    public void startAdminApp() {
    	try {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nWelcome to Admin Portal");
            System.out.println("1. Login");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your Choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 :
                	setLoginDetails();
                	break;
                case 0 : 
                	return;
                default :
                	System.out.println("\nInvalid Choice. Please try again!");
            }
        }
    	}catch (Exception e) {
    		System.out.println("Invalid Input");
			e.getMessage();
		}
    }
    
    private void login(String adminId, String password){
        if(adminId.trim().equals(this.adminID) && password.equals(this.password)) {
          System.out.println("\nAdmin Login Successful!");
          loginTries = 0;
          adminMenu();
        } else {
          System.out.println("\nPlease enter correct ID and Password.[Only 3 tries are allowed] \n");
          setLoginDetails();
        }
      }

      private boolean setLoginDetails(){
        System.out.println("\nAdmin Login ");
        loginTries += 1;

        System.out.println("Enter Login ID : ");
        String userId = scanner.next();

        System.out.println("\nEnter Password : ");
        String password = scanner.next();

        if (loginTries > maxLoginTries) {
          System.out.println("\nMaximum Login Tries Exceeded! \n Returning to Home.");

          loginTries = 0;

          return false;
        }

        login(userId, password);

        return true;
      }

	public void adminMenu() {
		while(true) {
			System.out.println("\nWelcome to the Admin Menu");
		      System.out.println("\nPlease Select an Option : ");

		      System.out.println("\n1. Approve Classified\n" +
		              "2. Reject Classified\n" +
		              "3. Activate User\n" +
		              "4. Deactivate User\n" +
		              "5. Add Classified\n" +
		              "6. Remove Classified\n" +
		              "7. Manage Category of Classified\n" +
		              "8. Generate Reports\n" +
		              "0. Exit to Main Menu");

		      int choice = scanner.nextInt();
		      
		      switch (choice) {
		        case 1:
		        	AdminManager.getInstance().approveClassified();
		          break;

		        case 2:
		        	AdminManager.getInstance().rejectClassified();
		          break;

		        case 3:
		        	AdminManager.getInstance().activateUser();
		          break;

		        case 4:
		        	AdminManager.getInstance().deactivateUser();
		          break;
		          
		        case 5:
		        	AdminManager.getInstance().addClassified();
			          break;
			          
		        case 6:
		        	AdminManager.getInstance().removeClassified();
			          break;
			    
		        case 7:
		        	AdminManager.getInstance().manageCategory();
			          break;
			      
		        case 8:
		        	AdminManager.getInstance().generateReports();
			          break;

		        case 0:
		        	return;

		        default:
		          System.out.println("\nPlease Enter Valid Option\n");
		      }

			
		}
		
	}
    

}
