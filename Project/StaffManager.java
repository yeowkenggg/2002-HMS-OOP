import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * StaffManager - logic implementation for Staff class
 */
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

    /**
     * Constructor for StaffManager
     * @param initialStaffList  initalize a new staffList to populate staff
     * @param userList shared list of all users
     * @param userManager  the manager for handling user-related operations
     * @param inventoryManager the manager for handling inventory-related operations
     * @param prescriptionManager the manager for handling prescrpition-related operations
     * @param doctorManager the manager for handling doctor-related operations
     */
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

    /**
     * Load staff data from staff CSV file.
     * @param filePath path to the CSV file
     */
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

    /**
     * Displays the staff management menu.
     * Allows the user to perform actions related to staff management.
     */
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
            return;
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

    /**
     * Add new staff member to the system.
     * @param staff staff member to add
     */
    public void addStaff(Staff staff) {
        userList.add(staff);
        staffList.add(staff);
    }
    
   
    /**
     * Entering of details of the new staff member
     */
    public void addStaffMenu() {
    Scanner scanner = new Scanner(System.in);

    try {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        if (userList.stream().anyMatch(user -> user.getUserId().equalsIgnoreCase(userId))) {
            System.out.println("Error: User ID already exists. Please use a different ID.");
            return; 
        }

        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();
        if (password.isEmpty()) {
            System.out.println("Error: Password cannot be empty.");
            return; 
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Error: Name cannot be empty.");
            return; 
        }

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



    /**
     * Updates details of an existing staff member.
     */
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
    
    
    
    
    /**
     * Removes a staff member from the system.
     * @param userManager UserManager to remove staff from system
     */
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
    
    
    /**
     * Displays all staff members in the system.
     */
    public void viewAllStaff() {
        System.out.println("\n--- All Staff Members ---");
        for (Staff staff : staffList) {
            System.out.println("ID: " + staff.getUserId() + ", Name: " + staff.getName() + ", Role: " + staff.getRole() + ", Gender: " + staff.getGender() + ", Age: " + staff.getAge());
        }
    }

    /**
     * Filter staff by criteria such as role, gender, or age.
     */
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
    

    /**
     * Finds a staff member by their user ID.
     * @param userId user ID to search for
     * @return staff member if found, otherwise null
     */
    public Staff findStaffById(String userId) {
        for (Staff staff : staffList) {
            if (staff.getUserId().equals(userId)) {
                return staff;
            }
        }
        return null;
    }
    
    /**
     * Filters staff based on a specified criterion and displays matching results.
     * @param criteria the filter criterion
     * @param filterDescription name of the filter
     */
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

    /**
     * Exception for invalid roles.
     */
    public class InvalidRoleException extends Exception {
        /**
         * Custom exception for role validation
         * @param message the message for exception
         */
        public InvalidRoleException(String message) {
            super(message);
        }
    }
    
    /**
     * Exception for invalid genders.
     */
    public class InvalidGenderException extends Exception {
        /**
         * Custom exception for gender validation
         * @param message the message for exception
         */
        public InvalidGenderException(String message) {
            super(message);
        }
    }

    /**
     * Validates if gender provided is valid.
     * @param gender gender to validate
     * @throws InvalidGenderException if the gender is not valid
     */
    private void validateGender(String gender) throws InvalidGenderException {
        if (!"Male".equalsIgnoreCase(gender) && !"Female".equalsIgnoreCase(gender)) {
            throw new InvalidGenderException("Invalid gender. Only 'Male' or 'Female' is allowed.");
        }
    }

    /**
     * Validates if role provided is valid.
     * @param role role to validate
     * @throws InvalidRoleException if the role is not valid
     */
    private void validateRole(String role) throws InvalidRoleException {
        if (!"Doctor".equalsIgnoreCase(role) && !"Pharmacist".equalsIgnoreCase(role)) {
            throw new InvalidRoleException("Invalid role. Only 'Doctor' or 'Pharmacist' is allowed.");
        }
    }


    
}
