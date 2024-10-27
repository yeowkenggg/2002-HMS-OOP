import java.util.*;

public class Administrator extends Staff implements IUser {
    private IInventoryManager inventoryManager;
    private StaffManager staffManager;

    public Administrator(String userId, String password, String name, String gender, String role, int age, IInventoryManager inventoryManager, StaffManager staffManager) {
        super(userId, password, name, gender, role, age);
        this.inventoryManager = inventoryManager;
        this.staffManager = staffManager;
    }

    @Override
    public void displayMenu() {
        if (isLoggedIn()) {
            Scanner scanner = new Scanner(System.in);
            boolean running = true;
            while (running) {
                System.out.println("\n--- Administrator Menu ---");
                System.out.println("1. Manage Hospital Staff");
                System.out.println("2. View Appointments Detail");
                System.out.println("3. Manage Medication Inventory");
                System.out.println("4. Approve Replenishment Requests");
                System.out.println("5. Logout");
                System.out.print("Choose an option (1-5): ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> manageStaff();
                    case 2 -> viewAppointment();
                    case 3 -> manageMedicines();
                    case 4 -> approveReplenishmentMenu();
                    case 5 -> {
                        System.out.println("Logging out...");
                        logout();
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Please choose between 1 and 5.");
                }
            }
            scanner.close();
        } else {
            System.out.println("ERROR: Please log in first.");
        }
    }

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
        scanner.nextLine();

        switch (action) {
            case 1 -> addStaff();
            case 2 -> updateStaff();
            case 3 -> removeStaff();
            case 4 -> staffManager.viewAllStaff();
            case 5 -> filterStaffMenu();
            default -> System.out.println("Invalid action.");
        }
    }

    private void addStaff() {
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
    
        // create Staff object
        Staff newStaff = new Staff(userId, password, name, gender, role, age);
        staffManager.addStaff(newStaff);
    }
    

    private void updateStaff() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter User ID of Staff to Update: ");
        String userId = scanner.nextLine();

        System.out.print("Enter New Name (leave blank to keep current): ");
        String name = scanner.nextLine();

        System.out.print("Enter New Role (leave blank to keep current): ");
        String role = scanner.nextLine();

        System.out.print("Enter New Age (or 0 to keep current): ");
        int age = scanner.nextInt();

        staffManager.updateStaff(userId, name, role, age);
    }

    private void removeStaff() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter User ID of Staff to Remove: ");
        String userId = scanner.nextLine();
        staffManager.removeStaffById(userId);
    }

    private void filterStaffMenu() {
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
            case 1:
                System.out.print("Enter role to filter by (e.g., Doctor, Pharmacist): ");
                String role = scanner.nextLine();
                staffManager.filterStaff(staff -> staff.getRole().equalsIgnoreCase(role), "Role: " + role);
                break;
            case 2:
                System.out.print("Enter gender to filter by (Male/Female): ");
                String gender = scanner.nextLine();
                staffManager.filterStaff(staff -> staff.getGender().equalsIgnoreCase(gender), "Gender: " + gender);
                break;
            case 3:
                System.out.print("Enter age to filter by: ");
                int age = scanner.nextInt();
                staffManager.filterStaff(staff -> staff.getAge() == age, "Age: " + age);
                break;
            case 4:
                staffManager.viewAllStaff();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    

    private void manageMedicines() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Medicine Management ---");
        System.out.println("1. View All Medicines");
        System.out.println("2. Add New Medicine");
        System.out.println("3. Update Medicine Stock");
        System.out.println("4. Update Stock Alert Level");
        System.out.println("5. Remove Medicine");
        System.out.print("Choose an option (1-5): ");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1 -> inventoryManager.viewMedicines();
            case 2 -> addMedicine();
            case 3 -> updateMedicineStock();
            case 4 -> updateStockAlertLevel();
            case 5 -> removeMedicine();
            default -> System.out.println("Invalid option.");
        }
    }

    private void addMedicine() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Medicine Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Initial Stock: ");
        int stock = scanner.nextInt();

        System.out.print("Enter Stock Alert Level: ");
        int alertLevel = scanner.nextInt();

        inventoryManager.addMedicine(name, stock, alertLevel);
    }

    private void updateMedicineStock() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Medicine Name to Update Stock: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter New Stock Amount: ");
        int newStock = scanner.nextInt();

        inventoryManager.updateMedicineStock(name, newStock);
    }

    private void updateStockAlertLevel() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Medicine Name to Update Alert Level: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter New Stock Alert Level: ");
        int newAlertLevel = scanner.nextInt();

        inventoryManager.updateStockAlertLevel(name, newAlertLevel);
    }

    private void removeMedicine() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Medicine Name to Remove: ");
        String name = scanner.nextLine();

        inventoryManager.removeMedicine(name);
    }

    public List<Appointment> viewAppointment() {
        List<Appointment> appointments = Appointment.getAllAppointments();
        System.out.println("All Appointments in the System:");
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
        return appointments;
    }

    public void approveReplenishmentMenu() {
        viewReplenishmentRequests();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Replenishment Request ID to approve: ");
        String requestId = scanner.nextLine();
        approveReplenishment(requestId);
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
}
