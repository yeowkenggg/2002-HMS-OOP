import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class PharmacistManager implements IPharmacistManager {

    private PrescriptionManager prescriptionManager;
    private MedicineManager medicineManager;
    private List<ReplenishmentRequest> replenishmentRequests;

    public PharmacistManager(PrescriptionManager prescriptionManager, MedicineManager medicineManager) {
        this.prescriptionManager = prescriptionManager;
        this.medicineManager = medicineManager;
        this.replenishmentRequests = new ArrayList<>();
    }

    public void setPrescriptionManager(PrescriptionManager pm){
        this.prescriptionManager = pm;   
    }

    public void setMedicineManager(MedicineManager mm){
        this.medicineManager = mm;
    }
    
    @Override
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

    @Override
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

    @Override
    public void updatePrescriptionStatus(String prescriptionID) {
        if (!prescriptionManager.updatePrescriptionStatus(prescriptionID)) {
            System.out.println("Prescription with ID " + prescriptionID + " not found.");
        }
    }

    @Override
    public void replenishmentRequest(String medicineName, int amt) {
        Medicine medicine = medicineManager.findMedicineByName(medicineName);

        if (medicine == null) {
            System.out.println("Medicine " + medicineName + " not found in inventory.");
            return;
        }

        if (medicineManager.needsReplenishment(medicine.getName())) {
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
            ReplenishmentRequest request = new ReplenishmentRequest(requestID, medicine, amt, "pharmacistID", "pharmacistName");

            replenishmentRequests.add(request);  // Track this request locally for the pharmacist
            System.out.println("Replenishment Request Submitted: " + amt + " units of " + medicine.getName());
        } else {
            System.out.println("Replenishment is not needed for " + medicine.getName() + ".");
        }
    }

    @Override
    public void viewInventory() {
        medicineManager.viewMedicines();
    }

    @Override
    public void viewReplenishmentRequests() {
        System.out.println("Replenishment Requests by Pharmacist:");
        for (ReplenishmentRequest request : replenishmentRequests) {
            String status = request.isApproved() ? "Approved" : "Pending";
            System.out.println("Request ID: " + request.getRequestID() + ", Medicine: " 
                               + request.getMedicine().getName() + ", Amount: " + request.getRequestedAmount()
                               + ", Status: " + status);
        }
    }
}