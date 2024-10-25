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
    
    //Used for updating staff 
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
        } else {
            return false;
        }
    }

	public void loginMenu() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to the Hospital Management System");

        while (!isLogged) {
            System.out.print("Enter User ID: ");
            String inputUserId = scanner.nextLine();
            System.out.print("Enter Password: ");
            String inputPassword = scanner.nextLine();

            if (login(inputUserId, inputPassword)) {
                isLogged = true;
            }
        }
    }

    //method to change password
	public void changePassword(String password) {
		if(isLogged){
			this.password = password;
			System.out.println("Password changed.");
		}
		else{
			System.out.println("Please login.");
		}
	}

    //change bool of islogged into false to 'logout'
	public void logout(){
		if(isLogged) this.isLogged = false;
	}

    //check status of login
	public boolean isLoggedIn() {
        return isLogged;
    }
	
    //template for subclasses
	public void displayMenu() {
        if (isLogged) {
            System.out.println("Menu.");
        } else {
            System.out.println("Please log in.");
        }
    }
}