public abstract class User {

    private String userId;
    private String password;
    private String name;
    private String gender;
    private boolean isLogged;

    // Constructor
    public User(String userId, String password, String name, String gender) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.isLogged = false;  // Default status is not logged in
    }

    // Getters for user info
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    // Setters for user info
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void displayMenu();

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

    public void setLoggedIn(boolean status) {
        this.isLogged = status;
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
    
    public void logout() {
        if (isLogged) {
            isLogged = false;
            System.out.println("You have been logged out.");
        } else {
            System.out.println("You are not logged in.");
        }
    }

    public boolean isLoggedIn() {
        return isLogged;
    }

    protected String getPassword() {
        return password;
    }
}
