import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pharmacist extends Staff implements IUser {

    private PrescriptionManager prescriptionManager;
    private MedicineManagementService inventoryManager;
    private List<ReplenishmentRequest> replenishmentRequests;

    public Pharmacist(String userId, String password, String name, String gender, String role, int age, 
                  MedicineManagementService inventoryManager, PrescriptionManager prescriptionManager) {
		super(userId, password, name, gender, role, age);
		this.inventoryManager = inventoryManager;
		this.prescriptionManager = prescriptionManager;
		this.replenishmentRequests = new ArrayList<>();
	}
    public void viewPrescriptionRecords() {
        System.out.println("\nPrescription Records:");
        List<Prescription> prescriptions = prescriptionManager.getAllPrescriptions();

        if (prescriptions.isEmpty()) {
            System.out.println("No prescriptions available.");
        } else {
            for (Prescription prescription : prescriptions) {
                System.out.println(prescription);
            }
        }
    }

    public void viewPendingPrescriptionRecords() {
        System.out.println("\nPending Prescription Records:");
        List<Prescription> pendingPrescriptions = prescriptionManager.getPendingPrescriptions();

        if (pendingPrescriptions.isEmpty()) {
            System.out.println("No pending prescriptions available.");
        } else {
            for (Prescription prescription : pendingPrescriptions) {
                System.out.println(prescription);
            }
        }
    }

    public void updatePrescriptionStatus(String prescriptionID) {
        if (!prescriptionManager.updatePrescriptionStatus(prescriptionID)) {
            System.out.println("Prescription with ID " + prescriptionID + " not found.");
        }
    }

    public void replenishmentRequest(String medicineName, int amt) {
        Medicine medicine = inventoryManager.findMedicineByName(medicineName);

        if (medicine == null) {
            System.out.println("Medicine " + medicineName + " not found in inventory.");
            return;
        }

        if (inventoryManager.needsReplenishment(medicine)) {
            boolean alreadyRequested = false;
            for (ReplenishmentRequest request : replenishmentRequests) {
                if (request.getMedicine().equals(medicine) && !request.isApproved()) {
                    alreadyRequested = true;
                    break;
                }
            }

            if (alreadyRequested) {
                System.out.println("A replenishment request for " + medicine.getName() + " is already pending.");
                return;
            }

            // Creating a new request
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMHHmmss");
            String requestID = "R" + LocalDateTime.now().format(formatter);
            ReplenishmentRequest request = new ReplenishmentRequest(requestID, medicine, amt, getUserId(), getName());

            replenishmentRequests.add(request);  // Track this request locally for the pharmacist
            System.out.println("Replenishment Request Submitted: " + amt + " units of " + medicine.getName());
        } else {
            System.out.println("Replenishment is not needed for " + medicine.getName() + ".");
        }
    }

    public void viewInventory() {
        System.out.println("\n=== Current Inventory ===");
        inventoryManager.viewMedicines();
    }

    public void viewReplenishmentRequests() {
        System.out.println("Replenishment Requests by Pharmacist:");
        for (ReplenishmentRequest request : replenishmentRequests) {
            String status = request.isApproved() ? "Approved" : "Pending";
            System.out.println("Request ID: " + request.getRequestID() + ", Medicine: " 
                               + request.getMedicine().getName() + ", Amount: " + request.getRequestedAmount()
                               + ", Status: " + status);
        }
    }

    @Override
    public void displayMenu() {
        if (isLoggedIn()) {
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
