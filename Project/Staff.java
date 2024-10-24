public class Staff extends User {

	public Staff(String userId, String password, String name, String gender, String role, int age) {
		super(userId, password, name, gender);  
        this.role = role;
        this.age = age;
    }
	
	private String role;
	private int age;


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    
}
