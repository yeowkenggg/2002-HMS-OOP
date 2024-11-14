import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Pharmacist class, which is a subclass of Staff, and implements the abstract methods of IUser
 */
public class Pharmacist extends Staff implements IUser {

    private IPharmacistManager pharmacistManager;
    private IPrescriptionManager prescriptionManager;

    /**
     * Constructor for pharmacist
     * @param userId from superclass
     * @param password from superclass
     * @param name from superclass
     * @param gender from superclass
     * @param role from superclass
     * @param age from superclass
     * @param pharmacistManager the manager instance for managing pharmacist-specific operations
     * @param prescriptionManager the manager instance for managing prescription-specific operations
     */
    public Pharmacist(String userId, String password, String name, String gender, String role, int age, 
                      IPharmacistManager pharmacistManager, IPrescriptionManager prescriptionManager) {
        super(userId, password, name, gender, role, age);
        this.pharmacistManager = pharmacistManager;
        this.prescriptionManager = prescriptionManager; 
    }

    /**
     * get method to get the prescription manager
     * @return the prescription manager
     */
    public IPrescriptionManager getPrescriptionManager() {
        return this.prescriptionManager;
    }

    /**
     * set method to set the manager that manages the pharmacist-specific operations
     * @param pm the pharmacist manager
     */
    public void setPharmacistManager(PharmacistManager pm){
        this.pharmacistManager = pm;
    }

    /**
     * Retrieves all the prescription record to be viewed
     */
    public void viewPrescriptionRecords() {
        pharmacistManager.viewPrescriptionRecords();
    }

    /**
     * Retrieves all the pending prescription record to be viewed
     */
    public void viewPendingPrescriptionRecords() {
        pharmacistManager.viewPendingPrescriptionRecords();
    }

    /**
     * update a status of a prescription 
     * @param prescriptionID the ID of the prescription to be updated
     */
    public void updatePrescriptionStatus(String prescriptionID) {
        pharmacistManager.updatePrescriptionStatus(prescriptionID);
    }

    /**
     * Submits a replenishment request for a specified medicine and its amount
     * @param medicineName the medicine name
     * @param amt the amount to be replenished
     */
    public void replenishmentRequest(String medicineName, int amt) {
        pharmacistManager.replenishmentRequest(medicineName, amt);
    }

    /**
     * View the current medication inventory
     */
    public void viewInventory() {
        pharmacistManager.viewInventory();
    }

    /**
     * View all pending replensihment request
     */
    public void viewReplenishmentRequests() {
        pharmacistManager.viewReplenishmentRequests();
    }

    /**
     * the implementation of abstract method of IUser
     */
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
