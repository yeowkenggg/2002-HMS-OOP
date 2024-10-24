public class Staff extends User {

	public Staff(String userId, String password, String name, String gender, String staffID, String role, int age) {
		//TODO Auto-generated constructor stub
		super(userId, password, name, gender);  
        this.staffID = staffID;
        this.role = role;
        this.age = age;
    }
	
	private String staffID;
	private String role;
	private int age;

	public String getStaffID() {
        return staffID;
    }

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
