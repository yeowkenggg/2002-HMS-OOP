import java.util.*;
import java.io.*;

public class Administrator extends Staff {

	public Administrator(String userId, String password, String name, String gender, String role,
			int age) {
		super(userId, password, name, gender, role, age);
	}

	public List<Appointment> viewAppointment() {
        List<Appointment> appointments = Appointment.getAllAppointments();
        System.out.println("All Appointments in the System:");
        System.out.println("==========================");
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
        return appointments;
    }

	public void viewReplenishmentRequests() {
        System.out.println("Pending Replenishment Requests:");
        for (ReplenishmentRequest request : ReplenishmentRequest.getRequests()) {
            String status = request.isApproved() ? "Approved" : "Pending";
            System.out.println("Request ID: " + request.getRequestID() 
                               + " | Medicine: " + request.getMedicine().getName() 
                               + " | Amount: " + request.getRequestedAmount()
                               + " | Status: " + status);
        }
    }

	/**
	 * 
	 * @param requestID
	 */
	public void approveReplenishment(String requestID) {
        for (ReplenishmentRequest request : ReplenishmentRequest.getRequests()) {
            if (request.getRequestID().equals(requestID) && !request.isApproved()) {
                request.approve();
                System.out.println("Replenishment request " + requestID + " has been approved.");
                return;
            }
        }
        System.out.println("Replenishment request not found or already approved.");
    }
	
	//override displayMenu in User class
	@Override
    public void displayMenu() {
        if (isLoggedIn()) {
            Scanner scanner = new Scanner(System.in);
            boolean running = true;
            while (running) {
                System.out.println("\n--- Administrator Menu ---");
                System.out.println("1. View and Manage Hospital Staff");
                System.out.println("2. View Appointments detail");
                System.out.println("3. View and Manage Medication Inventory");
                System.out.println("4. Approve Replenishment Requests");
                System.out.println("5. Logout");
                System.out.print("Choose an option (1-5): ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        manageStaff();
                        break;
                    case 2:
                        viewAppointment();
                        break;
                    case 3:
                        manageMedicines();
                        break;
                    case 4:
                        viewReplenishmentRequests();
                        System.out.print("Enter Replenishment Request ID to approve: ");
                        String requestId = scanner.nextLine();
                        approveReplenishment(requestId);
                        break;
                    case 5:
                        System.out.println("Logging out...");
                        logout();   // Set isLoggedIn to false
                        running = false; // Exit the loop
                        break;
                    default:
                        System.out.println("Invalid option. Please choose between 1 and 5.");
                        break;
                }
            }
            scanner.close();
        } else {
            System.out.println("ERROR: Please log in first.");
        }
    }

 
   

    //menu for CRUD staff
    private void manageStaff() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("STAFF MANAGEMENT");
        System.out.println("1. Add New Staff");
        System.out.println("2. Update Staff");
        System.out.println("3. Remove Staff");
        System.out.println("4. View All Staff");
        System.out.println("5. Filter Staff");
        System.out.print("Enter an action (1-5): ");
        
        int action = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (action) {
            case 1:
                addStaff();
                break;
            case 2:
                updateStaff();
                break;
            case 3:
                removeStaff();
                break;
            case 4:
                viewAllStaff();
                break;
            case 5:
                filterStaffMenu();
                break;
            default:
                System.out.println("Invalid action.");
                break;
        }
    }

    private void addStaff() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Add New Staff ---");
    
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
    
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
    
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
    
        // input gender, check against custom exceptions
        String gender = "";
        boolean validGender = false;
        while (!validGender) {
            System.out.print("Enter Gender (Male/Female): ");
            gender = scanner.nextLine();
            try {
                if (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female")) {
                    throw new InvalidGenderException("Invalid input for gender. Please enter 'Male' or 'Female'.");
                }
                validGender = true;
            } catch (InvalidGenderException e) {
                System.out.println(e.getMessage());
            }
        }
    
        // input role, check against custom exceptions
        String role = "";
        boolean validRole = false;
        while (!validRole) {
            System.out.print("Enter Role (Doctor/Pharmacist): ");
            role = scanner.nextLine();
            try {
                if (!role.equalsIgnoreCase("Doctor") && !role.equalsIgnoreCase("Pharmacist")) {
                    throw new InvalidRoleException("Invalid input for role. Please enter 'Doctor' or 'Pharmacist'.");
                }
                validRole = true;
            } catch (InvalidRoleException e) {
                System.out.println(e.getMessage());
            }
        }
    
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
    
        //create staff
        new Staff(userId, password, name, gender, role, age);
        System.out.println("Staff member added: " + name + " (ID: " + userId + ")");
    }

    private void updateStaff() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Update Staff ---");
        System.out.print("Enter User ID of Staff to Update: ");
        String userId = scanner.nextLine();

        Staff staffToUpdate = Staff.findStaffById(userId);

        if (staffToUpdate == null) {
            System.out.println("Staff member with ID " + userId + " not found.");
            return;
        }

        System.out.print("Enter New Name (leave blank to keep current): ");
        String name = scanner.nextLine();
        System.out.print("Enter New Role (leave blank to keep current): ");
        String role = scanner.nextLine();
        System.out.print("Enter New Age (or 0 to keep current): ");
        int age = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (!name.isEmpty()) staffToUpdate.setName(name);
        if (!role.isEmpty()) staffToUpdate.setRole(role);
        if (age > 0) staffToUpdate.setAge(age);

        System.out.println("Updated staff member: " + staffToUpdate.getName() + " (ID: " + staffToUpdate.getUserId() + ")");
    }

    private void removeStaff() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter User ID of Staff to Remove: ");
        String userId = scanner.nextLine();
        Staff.removeStaffById(userId);
    }

    public void viewAllStaff() {
        System.out.println("\n--- All Staff Members ---");
        for (Staff staff : Staff.getAllStaff()) {
            System.out.println("ID: " + staff.getUserId() + ", Name: " + staff.getName() + ", Role: " + staff.getRole());
        }
    }

    // Menu for filtering staff
    private void filterStaffMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nFilter Staff By:");
        System.out.println("1. Role");
        System.out.println("2. Gender");
        System.out.println("3. Age");
        System.out.println("4. Show All");
        System.out.print("Choose an option (1-4): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter role to filter by (e.g., Doctor, Pharmacist): ");
                String role = scanner.nextLine();
                filterStaff(role, null, null);
                break;
            case 2:
                System.out.print("Enter gender to filter by (Male/Female): ");
                String gender = scanner.nextLine();
                filterStaff(null, gender, null);
                break;
            case 3:
                System.out.print("Enter age to filter by: ");
                int age = scanner.nextInt();
                filterStaff(null, null, age);
                break;
            case 4:
                filterStaff(null, null, null);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void filterStaff(String role, String gender, Integer age) {
        System.out.println("\nFiltered Staff List:");
        for (Staff staff : Staff.getAllStaff()) {
            if ((role == null || staff.getRole().equalsIgnoreCase(role)) &&
                (gender == null || staff.getGender().equalsIgnoreCase(gender)) &&
                (age == null || staff.getAge() == age)) {
                System.out.println("Name: " + staff.getName() + ", ID: " + staff.getUserId() +
                                   ", Role: " + staff.getRole() + ", Gender: " + staff.getGender() +
                                   ", Age: " + staff.getAge());
            }
        }
    }


    public void viewMedicines() {
        System.out.println("\n=== All Medicines ===");
        for (Medicine med : Medicine.getAllMedicines()) {
            System.out.println("Name: " + med.getName() + ", Stock: " + med.getStock() + ", Alert Level: " + med.getAlertLevel());
        }
    }

    public void addMedicine() {
        Scanner scanner = new Scanner(System.in);
    
        try {
            System.out.print("Enter Medicine Name: ");
            String name = scanner.nextLine().trim();
    
            // Check if the medicine already exists in the inventory
            if (Medicine.findMedicineByName(name) != null) {
                System.out.println("Error: Medicine with the name '" + name + "' already exists in the inventory.");
                return;
            }
    
            System.out.print("Enter Initial Stock: ");
            int stock = scanner.nextInt();
    
            System.out.print("Enter Stock Alert Level: ");
            int alertLevel = scanner.nextInt();
    
            // checking values
            if (stock < 0 || alertLevel < 0) {
                System.out.println("Error: Stock and alert levels must be non-negative.");
                return;
            }
    
            Medicine newMedicine = new Medicine(name, stock, alertLevel);
            System.out.println("Medicine added: " + newMedicine.getName() + " with stock " + newMedicine.getStock() + " and alert level " + newMedicine.getAlertLevel());
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter numeric values for stock and alert level.");
            scanner.nextLine(); // clear away invalid input
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    

    public void updateMedicineStock() {
        Scanner scanner = new Scanner(System.in);
    
        try {
            System.out.print("Enter Medicine Name to Update Stock: ");
            String name = scanner.nextLine().trim();
    
            Medicine medicine = Medicine.findMedicineByName(name);
    
            if (medicine == null) {
                System.out.println("Error: Medicine '" + name + "' not found in inventory.");
                return;
            }
    
            System.out.print("Enter New Stock Amount: ");
            int newStock = scanner.nextInt();
    
            if (newStock < 0) {
                System.out.println("Error: Stock amount cannot be negative.");
                return;
            }
    
            // Set the stock directly to the input amount
            medicine.setStock(newStock);
            System.out.println("Updated stock for " + medicine.getName() + " to " + newStock + ".");
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter a numeric value for stock.");
            scanner.nextLine(); // Clear the invalid input
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    

    public void updateStockAlertLevel() {
        Scanner scanner = new Scanner(System.in);
    
        try {
            System.out.print("Enter Medicine Name to Update Alert Level: ");
            String name = scanner.nextLine().trim();
    
            Medicine medicine = Medicine.findMedicineByName(name);
    
            if (medicine == null) {
                System.out.println("Error: Medicine '" + name + "' not found in inventory.");
                return;
            }
    
            System.out.print("Enter New Stock Alert Level: ");
            int newAlertLevel = scanner.nextInt();
    
            if (newAlertLevel < 0) {
                System.out.println("Error: Alert level cannot be negative.");
                return;
            }
    
            medicine.adjustAlert(newAlertLevel);
            System.out.println("Updated alert level for " + medicine.getName() + " to " + newAlertLevel + ".");
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter a numeric value for the alert level.");
            scanner.nextLine(); 
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    

    public void removeMedicine() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Medicine Name to Remove: ");
        String removeName = scanner.nextLine();
        Medicine.removeMedicine(removeName);
    }

    // Display menu for managing medicines with options calling individual methods
    private void manageMedicines() {
        Scanner scanner = new Scanner(System.in);
        boolean managing = true;

        while (managing) {
            System.out.println("\n--- Medicine Management ---");
            System.out.println("1. View All Medicines");
            System.out.println("2. Add New Medicine");
            System.out.println("3. Update Medicine Stock");
            System.out.println("4. Update Stock Alert Level");
            System.out.println("5. Remove Medicine");
            System.out.println("6. Return to Main Menu");
            System.out.print("Choose an option (1-6): ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    viewMedicines();
                    break;
                case 2:
                    addMedicine();
                    break;
                case 3:
                    updateMedicineStock();
                    break;
                case 4:
                    updateStockAlertLevel();
                    break;
                case 5:
                    removeMedicine();
                    break;
                case 6:
                    managing = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    //custom exceptions
    public class InvalidGenderException extends Exception {
        public InvalidGenderException(String message) {
            super(message);
        }
    }
    
    public class InvalidRoleException extends Exception {
        public InvalidRoleException(String message) {
            super(message);
        }
    }
}