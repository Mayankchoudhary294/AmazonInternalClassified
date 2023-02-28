package com.Amazon.main;
import java.util.Scanner;

public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
		    System.out.println("\nWelcome to the Amazon Classified");
		    System.out.println("1. Administrator");
		    System.out.println("2. User");
		    System.out.println("0. Exit\n");
		    System.out.print("Enter your choice: ");
		    try {
		        int choice = Integer.parseInt(scanner.nextLine());
		        switch (choice) {
		            case 1 :
		                AdminApp.getInstance().startAdminApp();
		                break;
		            case 2 : 
		                UserApp.getInstance().startUserApp();
		                break;
		            case 0 :
		                System.out.println("\nThank you for using the Amazon Classified. Goodbye!");
		                return;
		            default :
		                System.out.println("Invalid choice.\n");
		        }
		    } catch (NumberFormatException e) {
		        System.out.println("\nInvalid input. Please enter a valid value.");
		    }
		}

	}

}
