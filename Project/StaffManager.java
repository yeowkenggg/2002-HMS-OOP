import java.util.*;
import java.util.function.Predicate;

public class StaffManager {
    private List<Staff> staffList;

    public StaffManager() {
        this.staffList = new ArrayList<>();
    }

    public StaffManager(List<Staff> initialStaffList) {
        this.staffList = new ArrayList<>(initialStaffList); // initialize with existing staff
    }

    public void addStaff(Staff staff) {
        staffList.add(staff);
        System.out.println("Staff member added: " + staff.getName() + " (ID: " + staff.getUserId() + ")");
    }

    public void updateStaff(String userId, String name, String role, int age) {
        Staff staff = findStaffById(userId);
        if (staff != null) {
            if (!name.isEmpty()) staff.setName(name);
            if (!role.isEmpty()) staff.setRole(role);
            if (age > 0) staff.setAge(age);
            System.out.println("Updated staff member: " + staff.getName() + " (ID: " + staff.getUserId() + ")");
        } else {
            System.out.println("Staff member with ID " + userId + " not found.");
        }
    }

    public boolean removeStaffById(String userId) {
        Staff staff = findStaffById(userId);
        if (staff != null) {
            staffList.remove(staff);
            System.out.println("Staff member with ID " + userId + " removed.");
            return true;
        }
        System.out.println("Staff member with ID " + userId + " not found.");
        return false;
    }

    public void viewAllStaff() {
        System.out.println("\n--- All Staff Members ---");
        for (Staff staff : staffList) {
            System.out.println("ID: " + staff.getUserId() + ", Name: " + staff.getName() + ", Role: " + staff.getRole());
        }
    }

    public void filterStaff(Predicate<Staff> criteria, String filterDescription) {
        System.out.println("\nFiltered Staff by " + filterDescription + ":");
        for (Staff staff : staffList) {
            if (criteria.test(staff)) {
                System.out.println("Name: " + staff.getName() + ", ID: " + staff.getUserId() +
                                   ", Role: " + staff.getRole() + ", Gender: " + staff.getGender() +
                                   ", Age: " + staff.getAge());
            }
        }
    }

    public Staff findStaffById(String userId) {
        return staffList.stream()
                        .filter(staff -> staff.getUserId().equals(userId))
                        .findFirst()
                        .orElse(null);
    }

    public List<Staff> getAllStaff() {
        return new ArrayList<>(staffList);
    }
}
