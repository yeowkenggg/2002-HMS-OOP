import java.util.*;
import java.io.*;

public class User {

	private String userId;
	private String password;
	private String name;
	private String gender;
	private boolean isLogged;

    public User(String userId, String password, String name, String gender) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.isLogged = false;  // Default 
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

	public boolean login(String inputUser, String inputPass) {
		// TODO - implement User.login 	
		//Implement next time when we decide how to handle user accounts
		//for now just default login codes
		if (inputUser.equals(this.userId) && inputPass.equals(this.password)) {
            isLogged = true;  
            System.out.println("Login successful for user: " + name);
            return true;
        } else {
            System.out.println("Invalid credentials.");
            return false;
        }
    }
	

	public void changePassword(String password) {
		// TODO - implement User.changePassword
		if(isLogged){
			this.password = password;
			System.out.println("Password changed.");
		}
		else{
			System.out.println("Please login.");
		}
	}

	public void logout(){
		if(isLogged) this.isLogged = false;
	}

	public boolean isLoggedIn() {
        return isLogged;
    }
	
	public void displayMenu() {
        if (isLogged) {
            System.out.println("Menu.");
        } else {
            System.out.println("Please log in.");
        }
    }
}