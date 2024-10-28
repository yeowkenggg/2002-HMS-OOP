import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class StaffManagementService {
    private List<Staff> staffList;

    public StaffManagementService() {
        this.staffList = new ArrayList<>();
    }

    // Initialize with an existing list of staff members
    public StaffManagementService(List<Staff> initialStaffList) {
        this.staffList = new ArrayList<>(initialStaffList);
    }

    public void displayStaffManagementMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("STAFF MANAGEMENT");
        System.out.println("1. Add New Staff");
        System.out.println("2. Update Staff");
        System.out.println("3. Remove Staff");
        System.out.println("4. View All Staff");
        System.out.println("5. Filter Staff");
        System.out.print("Enter an action (1-5): ");
        
        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1 -> addStaffMenu();
            case 2 -> updateStaff();
            case 3 -> removeStaff();
            case 4 -> viewAllStaff();
            case 5 -> filterStaffMenu();
            default -> System.out.println("Invalid action.");
        }
    }

    // CRUD Operations for Staff
    public void addStaff(Staff staff) {
        staffList.add(staff);
        System.out.println("Staff member added: " + staff.getName() + " (ID: " + staff.getUserId() + ")");
    }

    public void addStaffMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Gender (Male/Female): ");
        String gender = scanner.nextLine();

        System.out.print("Enter Role (Doctor/Pharmacist): ");
        String role = scanner.nextLine();

        System.out.print("Enter Age: ");
        int age = scanner.nextInt();

        Staff newStaff;
        if ("Doctor".equalsIgnoreCase(role)) {
            newStaff = new Doctor(userId, password, name, gender, "Doctor", age);
        } else if ("Pharmacist".equalsIgnoreCase(role)) {
            // Placeholder: assuming the pharmacist requires additional services (dependency injection)
            MedicineManagementService inventoryManager = new MedicineManagementService();
            PrescriptionManager prescriptionManager = new PrescriptionManager();
            newStaff = new Pharmacist(userId, password, name, gender, "Pharmacist", age, inventoryManager, prescriptionManager);
        } else {
            System.out.println("Invalid role, only Doctor or Pharmacist allowed.");
            return;
        }

        addStaff(newStaff);
    }


    public void updateStaff() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter User ID of Staff to Update: ");
        String userId = scanner.nextLine();

        Staff staff = findStaffById(userId);
        if (staff == null) {
            System.out.println("Staff member with ID " + userId + " not found.");
            return;
        }

        System.out.print("Enter New Name (leave blank to keep current): ");
        String name = scanner.nextLine();

        System.out.print("Enter New Role (leave blank to keep current): ");
        String role = scanner.nextLine();

        System.out.print("Enter New Age (or 0 to keep current): ");
        int age = scanner.nextInt();

        if (!name.isEmpty()) staff.setName(name);
        if (!role.isEmpty()) staff.setRole(role);
        if (age > 0) staff.setAge(age);
        System.out.println("Updated staff member: " + staff.getName() + " (ID: " + staff.getUserId() + ")");
    }

    public void removeStaff() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter User ID of Staff to Remove: ");
        String userId = scanner.nextLine();

        Staff staff = findStaffById(userId);
        if (staff != null) {
            staffList.remove(staff);
            System.out.println("Staff member with ID " + userId + " removed.");
        } else {
            System.out.println("Staff member with ID " + userId + " not found.");
        }
    }

    public void viewAllStaff() {
        System.out.println("\n--- All Staff Members ---");
        for (Staff staff : staffList) {
            System.out.println("ID: " + staff.getUserId() + ", Name: " + staff.getName() + ", Role: " + staff.getRole());
        }
    }

    public void filterStaffMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nFilter Staff By:");
        System.out.println("1. Role");
        System.out.println("2. Gender");
        System.out.println("3. Age");
        System.out.println("4. Show All");
        System.out.print("Choose an option (1-4): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter role to filter by (e.g., Doctor, Pharmacist): ");
                String role = scanner.nextLine();
                filterStaff(staff -> staff.getRole().equalsIgnoreCase(role), "Role: " + role);
            }
            case 2 -> {
                System.out.print("Enter gender to filter by (Male/Female): ");
                String gender = scanner.nextLine();
                filterStaff(staff -> staff.getGender().equalsIgnoreCase(gender), "Gender: " + gender);
            }
            case 3 -> {
                System.out.print("Enter age to filter by: ");
                int age = scanner.nextInt();
                filterStaff(staff -> staff.getAge() == age, "Age: " + age);
            }
            case 4 -> viewAllStaff();
            default -> System.out.println("Invalid choice.");
        }
    }

    // Utility functions for staff operations
    public Staff findStaffById(String userId) {
        for (Staff staff : staffList) {
            if (staff.getUserId().equals(userId)) {
                return staff;
            }
        }
        return null;
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
}
