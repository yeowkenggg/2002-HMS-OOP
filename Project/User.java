public abstract class User {

    private String userId;
    private String password;
    private String name;
    private String gender;
    private boolean isLogged;
    private boolean firstLogin = true;

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
        this.password = newPassword;
        this.firstLogin = false;
    }

    public void setLoggedIn(boolean status) {
        this.isLogged = status;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public boolean isFirstLogin() {
        return firstLogin;
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

     
    public String getPassword(){
        return password;
    }
}
