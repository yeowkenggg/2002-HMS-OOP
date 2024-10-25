import java.util.ArrayList;
import java.util.List;

public class Staff extends User {

	public Staff(String userId, String password, String name, String gender, String role, int age) {
		super(userId, password, name, gender);  
        this.role = role;
        this.age = age;
        staffList.add(this);
    }
	
	private String role;
	private int age;
    private static List<Staff> staffList = new ArrayList<>();


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
    public static List<Staff> getAllStaff() {
        return staffList;
    }

    public static Staff findStaffById(String userId) {
        for (Staff staff : staffList) {
            if (staff.getUserId().equals(userId)) {
                return staff;
            }
        }
        return null;
    }

    public static void removeStaffById(String userId) {
        Staff staffToRemove = findStaffById(userId);
        if (staffToRemove != null) {
            staffList.remove(staffToRemove);
            System.out.println("Removed staff member: " + staffToRemove.getName() + " (ID: " + userId + ")");
        } else {
            System.out.println("Staff member with ID " + userId + " not found.");
        }
    }
    

    
}
