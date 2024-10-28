import java.util.ArrayList;
import java.util.List;

public class PrescriptionManager implements IPrescriptionManager {

    private List<Prescription> prescriptions;

    public PrescriptionManager() {
        this.prescriptions = new ArrayList<>();
    }

    // Add a new prescription to the system
    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
        System.out.println("Prescription added: " + prescription);
    }

    // Retrieve all prescriptions
    public List<Prescription> getAllPrescriptions() {
        return new ArrayList<>(prescriptions);  // return a copy to prevent direct modification
    }

    // Retrieve only pending prescriptions
    public List<Prescription> getPendingPrescriptions() {
        List<Prescription> pendingPrescriptions = new ArrayList<>();
        for (Prescription prescription : prescriptions) {
            if ("Pending".equalsIgnoreCase(prescription.getStatus())) {
                pendingPrescriptions.add(prescription);
            }
        }
        return pendingPrescriptions;
    }

    // Update the status of a prescription by ID
    public boolean updatePrescriptionStatus(String prescriptionID) {
        for (Prescription prescription : prescriptions) {
            if (prescription.getPrescriptionID().equals(prescriptionID)) {
                prescription.updateStatus();  // Assuming updateStatus handles status transition
                System.out.println("Prescription status updated: " + prescription);
                return true;
            }
        }
        System.out.println("Prescription with ID " + prescriptionID + " not found.");
        return false;
    }

    // Additional methods if needed for more functionality
    public Prescription findPrescriptionById(String prescriptionID) {
        for (Prescription prescription : prescriptions) {
            if (prescription.getPrescriptionID().equals(prescriptionID)) {
                return prescription;
            }
        }
        return null;
    }

    public void removePrescription(String prescriptionID) {
        prescriptions.removeIf(prescription -> prescription.getPrescriptionID().equals(prescriptionID));
        System.out.println("Prescription with ID " + prescriptionID + " removed.");
    }
}
