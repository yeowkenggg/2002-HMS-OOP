import java.util.Scanner;
import java.util.List;

public class Administrator extends Staff implements IUser {
    private StaffManagementService staffManagementService;
    private MedicineManagementService medicineManagementService;

    public Administrator(String userId, String password, String name, String gender, String role, int age, 
                         StaffManagementService staffManagementService, MedicineManagementService medicineManagementService) {
        super(userId, password, name, gender, role, age);
        this.staffManagementService = staffManagementService;
        this.medicineManagementService = medicineManagementService;
    }

    @Override
    public void displayMenu() {
        if (isLoggedIn()) {
            Scanner scanner = new Scanner(System.in);
            boolean running = true;
            while (running) {
                System.out.println("\n--- Administrator Menu ---");
                System.out.println("1. Manage Hospital Staff");
                System.out.println("2. Manage Medication Inventory");
                System.out.println("3. Approve Replenishment Requests");
                System.out.println("4. View Appointments Detail");
                System.out.println("5. Logout");
                System.out.print("Choose an option (1-5): ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> staffManagementService.displayStaffManagementMenu();
                    case 2 -> medicineManagementService.displayMedicineManagementMenu(); // Delegate to the service
                    case 3 -> approveReplenishmentMenu();
                    case 4 -> viewAppointmentDetails();
                    case 5 -> {
                        System.out.println("Logging out...");
                        logout();
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Please choose between 1 and 5.");
                }
            }
        } else {
            System.out.println("ERROR: Please log in first. (Admin)");
        }
    }

    private void approveReplenishmentMenu() {
        medicineManagementService.viewReplenishmentRequests();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Replenishment Request ID to approve: ");
        String requestId = scanner.nextLine();
        medicineManagementService.approveReplenishment(requestId);
    }

    private void viewAppointmentDetails() {
        List<Appointment> appointments = Appointment.getAllAppointments();
        System.out.println("All Appointments in the System:");
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }
}
