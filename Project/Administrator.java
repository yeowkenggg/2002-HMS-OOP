import java.util.Scanner;
import java.util.List;

public class Administrator extends Staff implements IUser {
    private StaffManager staffManager;
    private MedicineManager medicineManager;
    private AdminService adminService;

    public Administrator(String userId, String password, String name, String gender, String role, int age, 
                         StaffManager staffManager, MedicineManager medicineManager) {
        super(userId, password, name, gender, role, age);
        this.staffManager = staffManager;
        this.medicineManager = medicineManager;
        this.adminService = new AdminService(medicineManager);
    }

    public void setStaffManager(StaffManager sm){
        this.staffManager = sm;
    }

    public void setMedicineManager(MedicineManager mm){
        this.medicineManager = mm;
    }

    @Override
    public void displayMenu() {
        if (isLoggedIn()) {
                System.out.println("\n--- Administrator Menu ---");
                System.out.println("1. Manage Hospital Staff");
                System.out.println("2. Manage Medication Inventory");
                System.out.println("3. Approve Replenishment Requests");
                System.out.println("4. View Appointments Detail");
                System.out.println("5. Logout");
                System.out.print("Choose an option (1-5): ");
        } else {
            System.out.println("ERROR: Please log in first. (Admin)");
        }
    }

    public void manageHospitalStaff() {
        staffManager.displayStaffManagementMenu();
    }

    public void manageMedicationInventory() {
        medicineManager.displayMedicineManagementMenu();
    }

    public void approveReplenishmentRequests() {
        adminService.approveReplenishmentRequests();
    }

    public void viewAppointmentDetails() {
        List<Appointment> appointments = Appointment.getAllAppointments();
        System.out.println("All Appointments in the System:");
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }
}
