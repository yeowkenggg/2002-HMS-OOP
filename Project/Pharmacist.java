import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pharmacist extends Staff implements IUser {

    private PharmacistManager pharmacistManager;

    public Pharmacist(String userId, String password, String name, String gender, String role, int age, 
                      PharmacistManager pharmacistManager) {
        super(userId, password, name, gender, role, age);
        this.pharmacistManager = pharmacistManager;
    }

    public void setPharmacistManager(PharmacistManager pm){
        this.pharmacistManager = pm;
    }

    public void viewPrescriptionRecords() {
        pharmacistManager.viewPrescriptionRecords();
    }

    public void viewPendingPrescriptionRecords() {
        pharmacistManager.viewPendingPrescriptionRecords();
    }

    public void updatePrescriptionStatus(String prescriptionID) {
        pharmacistManager.updatePrescriptionStatus(prescriptionID);
    }

    public void replenishmentRequest(String medicineName, int amt) {
        pharmacistManager.replenishmentRequest(medicineName, amt);
    }

    public void viewInventory() {
        pharmacistManager.viewInventory();
    }

    public void viewReplenishmentRequests() {
        pharmacistManager.viewReplenishmentRequests();
    }

    @Override
    public void displayMenu() {
        if (isLoggedIn()) {
            
            System.out.println("\n--- Pharmacist Menu ---");
            System.out.println("1. View Prescription Records");
            System.out.println("2. View Pending Prescription Records");
            System.out.println("3. Update Prescription Status");
            System.out.println("4. View Medication Inventory");
            System.out.println("5. Submit Replenishment Request");
            System.out.println("6. View Replenishment Requests");
            System.out.println("7. Logout");
        } else {
            System.out.println("ERROR. PLEASE LOG IN! (Pharmacist)");
        }
    }
}
