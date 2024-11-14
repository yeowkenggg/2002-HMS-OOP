import java.util.Scanner;
import java.util.List;

/**
 * Administrator Class 
 */
public class Administrator extends Staff implements IUser {
    private IStaffManager staffManager;
    private IMedicineManager medicineManager;
    private AdministratorManager adminService;

    /**
     * Constructor for administrator class
     * @param userId from superlcass
     * @param password from superlcass
     * @param name from superlcass
     * @param gender from superlcass
     * @param role from superlcass
     * @param age from superlcass
     * @param staffManager manager responsible for staff-related logic
     * @param medicineManager manger responsible for medicine-related logic
     */
    public Administrator(String userId, String password, String name, String gender, String role, int age, 
                         IStaffManager staffManager, IMedicineManager medicineManager) {
        super(userId, password, name, gender, role, age);
        this.staffManager = staffManager;
        this.medicineManager = medicineManager;
        this.adminService = new AdministratorManager(medicineManager);
    }

    /**
     * setting StaffManager (to prevent cyclic in Main)
     * @param sm set staffManager
     */
    public void setStaffManager(StaffManager sm){
        this.staffManager = sm;
    }

    /**
     * setting MedicineManager (to prevent cyclic in Main)
     * @param mm set medicineManager
     */
    public void setMedicineManager(MedicineManager mm){
        this.medicineManager = mm;
    }

    /**
     * Display menu for Administrator class
     */
    @Override
    public void displayMenu() {
        if (isLoggedIn()) {
                System.out.println("\n--- Administrator Menu ---");
                System.out.println("1. Manage Hospital Staff");
                System.out.println("2. Manage Medication Inventory");
                System.out.println("3. Approve Replenishment Requests");
                System.out.println("4. View Appointments Detail");
                System.out.println("5. Logout");
        } else {
            System.out.println("ERROR: Please log in first. (Admin)");
        }
    }

    /**
     * Manages the hospital staff by displaying the staff management menu.
     */
    public void manageHospitalStaff() {
        staffManager.displayStaffManagementMenu();
    }

    /**
     * Manages the medicine inventory by displaying the medicine management menu.
     */
    public void manageMedicationInventory() {
        medicineManager.displayMedicineManagementMenu();
    }

    /**
     * Manages the status update for replenishment requests
     */
    public void approveReplenishmentRequests() {
        adminService.approveReplenishmentRequests();
    }

    /**
     * Output to show appointment details
     */
    public void viewAppointmentDetails() {
        List<Appointment> appointments = Appointment.getAllAppointments();
        System.out.println("All Appointments in the System:\n");
        
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
    
            //Print out the outcome together with the appointment, if there isnt a outcome 
            //then just leave it as it is
            AppointmentOutcome outcome = appointment.getOutcome();
            if (outcome != null) {
                System.out.println("  --- Appointment Outcome ---");
                System.out.println("  Services Provided: " + outcome.getServices());
                System.out.println("  Additional Notes: " + outcome.getNotes());
                System.out.println("  Prescription ID: " + outcome.getPrescriptrion());
                System.out.println("");
            }
        }
    }
}
