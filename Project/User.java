import java.util.*;
import java.io.*;

public class User {

	private String userId;
	private String password;
	private String name;
	private String gender;
	private boolean isLogged;

    //constructor
    public User(String userId, String password, String name, String gender) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.isLogged = false;  // Default 
    }

    //get infos
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    //Used for updating names in sublclasses 
    public void setName(String name) {
        this.name = name;
    }

    //login implementation
	public boolean login(String inputUser, String inputPass) {
		//Implement next time when we decide how to handle user accounts
		//for now just default login codes
		if (inputUser.equals(this.userId) && inputPass.equals(this.password)) {
            isLogged = true;
            System.out.println("Login successful for: " + name);
            return true;
        }
        return false;
    }

	public void loginMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Hospital Management System");

        while (!isLogged) {
            System.out.print("Enter User ID: ");
            String inputUserId = scanner.nextLine();
            System.out.print("Enter Password: ");
            String inputPassword = scanner.nextLine();

            if (!login(inputUserId, inputPassword)) {
                System.out.print("Would you like to try again? (y/n): ");
                String retry = scanner.nextLine();
                if (retry.equalsIgnoreCase("n")) {
                    System.out.println("Exiting system..");
                    break;
                }
            }
        }
    }

    //method to change password
	public void changePassword(String newPassword) {
        if (isLogged) {
            if (newPassword == null || newPassword.trim().isEmpty()) {
                System.out.println("Password cannot be empty. Try again.");
                return;
            }
            this.password = newPassword;
            System.out.println("Password successfully changed.");
        } else {
            System.out.println("Please log in to change your password.");
        }
    }

    public void logout() {
        if (isLogged) {
            isLogged = false;
            System.out.println("You have been logged out.");
        } else {
            System.out.println("You are not logged in.");
        }
    }

    //check status of login
	public boolean isLoggedIn() {
        return isLogged;
    }
	
}