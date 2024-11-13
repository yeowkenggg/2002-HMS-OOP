import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class StaffManager implements IStaffManager{
    private List<Staff> staffList;
    private List<User> userList;
    private IMedicineManager inventoryManager;
    private IPrescriptionManager prescriptionManager;
    private IPharmacistManager pharmacistManager; 
    private UserManager userManager;
    private IDoctorManager doctorManager;


    public List<Staff> getAllStaff() {
        return new ArrayList<>(staffList); 
    }

    // Initialize with an existing list of staff members
    public StaffManager(List<Staff> initialStaffList, List<User> userList, UserManager userManager, IMedicineManager inventoryManager, IPrescriptionManager prescriptionManager, IDoctorManager doctorManager) {
        this.staffList = new ArrayList<>(initialStaffList);
        this.userList = userList; 
        this.inventoryManager = inventoryManager;
        this.prescriptionManager = prescriptionManager;
        this.pharmacistManager = pharmacistManager;
        this.userManager = userManager;
        this.doctorManager = doctorManager;

        for (Staff staff : initialStaffList) {
            this.userList.add(staff);
        }
    }


    public void loadStaffFromCSV(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                if (data.length >= 6) {
                    String userId = data[0];
                    String name = data[1];
                    String password = data[2];
                    String role = data[3];
                    String gender = data[4];
                    int age = Integer.parseInt(data[5]);

                    Staff newStaff;
                    if ("Doctor".equalsIgnoreCase(role)) {
                        newStaff = new Doctor(userId, password, name, gender, role, age, doctorManager);
                    } else {
                        newStaff = new Pharmacist(userId, password, name, gender, role, age, pharmacistManager, prescriptionManager);
                    }

                    addStaff(newStaff);

                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            e.printStackTrace();
        }
    }
    public void displayStaffManagementMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Staff Manaagement Menu ---");
        System.out.println("1. Add New Staff");
        System.out.println("2. Update Staff");
        System.out.println("3. Remove Staff");
        System.out.println("4. View All Staff");
        System.out.println("5. Filter Staff");
        System.out.println("6. Return");
        System.out.print("Enter an action (1-6): ");
        
        
        int action = scanner.nextInt();
        scanner.nextLine();

        if (action == (6)) {
            return; // Exit the method
        }

        switch (action) {
            case 1 -> addStaffMenu();
            case 2 -> updateStaff();
            case 3 -> removeStaff(userManager);
            case 4 -> viewAllStaff();
            case 5 -> filterStaffMenu();
            default -> System.out.println("Invalid action.");
        }
    }

    // CRUD Operations for Staff
    public void addStaff(Staff staff) {
        userList.add(staff);
        staffList.add(staff);
    }
    
   

    public void addStaffMenu() {
    Scanner scanner = new Scanner(System.in);

    try {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Gender (Male/Female): ");
        String gender = scanner.nextLine().trim();
        validateGender(gender);

        System.out.print("Enter Role (Doctor/Pharmacist): ");
        String role = scanner.nextLine().trim();
        validateRole(role);

        System.out.print("Enter Age: ");
        int age = scanner.nextInt();

        Staff newStaff;
        if ("Doctor".equalsIgnoreCase(role)) {
            newStaff = new Doctor(userId, password, name, gender, "Doctor", age, doctorManager);
        } else if ("Pharmacist".equalsIgnoreCase(role)) {
            newStaff = new Pharmacist(userId, password, name, gender, "Pharmacist", age, pharmacistManager, prescriptionManager);
        } else {
            throw new InvalidRoleException("Invalid role. Only 'Doctor' or 'Pharmacist' is allowed.");
        }

        addStaff(newStaff);
        System.out.println("Staff member added: " + newStaff.getName() + " (ID: " + newStaff.getUserId() + ")");

        } catch (InvalidRoleException | InvalidGenderException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the correct data type.");
            scanner.nextLine(); // Clear the buffer
        }
    }




    public void updateStaff() {
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("\n--- All Staff Members ---");
        for (int i = 0; i < staffList.size(); i++) {
            Staff staff = staffList.get(i);
            System.out.println(i + ": ID: " + staff.getUserId() + ", Name: " + staff.getName() + ", Role: " + staff.getRole());
        }
    
        System.out.print("Enter the index of the Staff to Update: ");
        int index;
        try {
            index = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return;
        }
    
        if (index < 0 || index >= staffList.size()) {
            System.out.println("Invalid index. Please select a valid staff member.");
            return;
        }
    
        Staff staff = staffList.get(index);
    
        try {
            System.out.print("Enter New Name (leave blank to keep current): ");
            String name = scanner.nextLine();
    
            System.out.print("Enter New Role (leave blank to keep current): ");
            String role = scanner.nextLine();
    
            if (!role.isEmpty() && !role.equalsIgnoreCase(staff.getRole())) {
                validateRole(role);
    
                // if changing role, create a new instance of the appropriate subclass
                // assuming you cannot just randomly change to an admin class
                Staff newStaff;
                if ("Doctor".equalsIgnoreCase(role)) {
                    newStaff = new Doctor(staff.getUserId(), staff.getPassword(), name.isEmpty() ? staff.getName() : name, staff.getGender(), "Doctor", staff.getAge(), doctorManager);
                } else if ("Pharmacist".equalsIgnoreCase(role)) {
                    newStaff = new Pharmacist(staff.getUserId(), staff.getPassword(), name.isEmpty() ? staff.getName() : name, staff.getGender(), "Pharmacist", staff.getAge(), pharmacistManager, prescriptionManager);
                } else {
                    throw new InvalidRoleException("Invalid role provided.");
                }
    
                staffList.set(index, newStaff);
                userList.set(userList.indexOf(staff), newStaff);
    
                System.out.println("Role updated. Staff member changed to: " + newStaff.getName() + " (ID: " + newStaff.getUserId() + ", Role: " + newStaff.getRole() + ")");
            }
    
            if (!name.isEmpty()) {
                staff.setName(name);
            }
    
            System.out.print("Enter New Age (or empty/0 to keep current): ");
            String ageInput = scanner.nextLine();
            if (!ageInput.isEmpty()) {
                int age = Integer.parseInt(ageInput);
                if (age > 0) {
                    staff.setAge(age);
                }
            }
    
            System.out.println("Updated staff member: " + staff.getName() + " (ID: " + staff.getUserId() + ")");
        } catch (InvalidRoleException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid age input. Please enter a valid number.");
        }
    }
    
    
    
    

    public void removeStaff(UserManager userManager) {
            Scanner scanner = new Scanner(System.in);
        
            System.out.println("\n--- All Staff Members ---");
            for (int i = 0; i < staffList.size(); i++) {
                Staff staff = staffList.get(i);
                System.out.println(i + ": ID: " + staff.getUserId() + ", Name: " + staff.getName() + ", Role: " + staff.getRole());
            }
        
            System.out.print("Enter the index of the Staff to Remove: ");
            int index;
            try {
                index = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                return;
            }
        
            if (index < 0 || index >= staffList.size()) {
                System.out.println("Invalid index. Please select a valid staff member.");
                return;
            }
        
            Staff staff = staffList.get(index);
            String userId = staff.getUserId();
        
            staffList.remove(index);
            userManager.getUsers().removeIf(user -> user.getUserId().equals(userId));
    
        System.out.println("Staff member with ID " + userId + " removed.");
    }
    
    

    public void viewAllStaff() {
        System.out.println("\n--- All Staff Members ---");
        for (Staff staff : staffList) {
            System.out.println("ID: " + staff.getUserId() + ", Name: " + staff.getName() + ", Role: " + staff.getRole() + ", Gender: " + staff.getGender() + ", Age: " + staff.getAge());
        }
    }

    public void filterStaffMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) { 
            try {
                System.out.println("\nFilter Staff By:");
                System.out.println("1. Role");
                System.out.println("2. Gender");
                System.out.println("3. Age");
                System.out.println("4. Show All");
                System.out.println("5. Return");
                System.out.print("Choose an option (1-5): ");
                
                String input = scanner.nextLine(); 
    
                if (input.equalsIgnoreCase("5")) {
                    return; 
                }
    
                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                    continue; 
                }
    
                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter role to filter by (e.g., Doctor, Pharmacist): ");
                        String role = scanner.nextLine();
                        filterStaff(staff -> staff.getRole().equalsIgnoreCase(role), "Role: " + role);
                    }
                    case 2 -> {
                        System.out.print("Enter gender to filter by (Male/Female): ");
                        String gender = scanner.nextLine();
                        
                        if (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female")) {
                            System.out.println("Invalid gender. Please enter 'Male' or 'Female'.");
                            continue; 
                        }
                        filterStaff(staff -> staff.getGender().equalsIgnoreCase(gender), "Gender: " + gender);
                    }
                    case 3 -> {
                        System.out.print("Enter age to filter by: ");
                        int age;
                        try {
                            age = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid age.");
                            continue; 
                        }
                        filterStaff(staff -> staff.getAge() == age, "Age: " + age);
                    }
                    case 4 -> viewAllStaff();
                    default -> System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); 
            }
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

    public class InvalidRoleException extends Exception {
        public InvalidRoleException(String message) {
            super(message);
        }
    }
    
    public class InvalidGenderException extends Exception {
        public InvalidGenderException(String message) {
            super(message);
        }
    }

    private void validateGender(String gender) throws InvalidGenderException {
        if (!"Male".equalsIgnoreCase(gender) && !"Female".equalsIgnoreCase(gender)) {
            throw new InvalidGenderException("Invalid gender. Only 'Male' or 'Female' is allowed.");
        }
    }

    private void validateRole(String role) throws InvalidRoleException {
        if (!"Doctor".equalsIgnoreCase(role) && !"Pharmacist".equalsIgnoreCase(role)) {
            throw new InvalidRoleException("Invalid role. Only 'Doctor' or 'Pharmacist' is allowed.");
        }
    }


    
}
