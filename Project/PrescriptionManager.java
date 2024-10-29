import java.util.ArrayList;
import java.util.List;

public class PrescriptionManager implements IPrescriptionManager {

    private List<Prescription> prescriptions;
    private MedicineManager medicineManager;

    public PrescriptionManager(MedicineManager medicineManager) {
        this.prescriptions = new ArrayList<>();
        this.medicineManager = medicineManager;
    }

    public void setMedicineManager(MedicineManager mm){
        this.medicineManager = mm;
    }

    @Override
    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
        System.out.println("Prescription added: " + prescription);
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        return new ArrayList<>(prescriptions);  // return a copy to prevent direct modification
    }

    @Override
    public List<Prescription> getPendingPrescriptions() {
        List<Prescription> pendingPrescriptions = new ArrayList<>();
        for (Prescription prescription : prescriptions) {
            if ("Pending".equalsIgnoreCase(prescription.getStatus())) {
                pendingPrescriptions.add(prescription);
            }
        }
        return pendingPrescriptions;
    }

    @Override
    public boolean updatePrescriptionStatus(String prescriptionID) {
        for (Prescription prescription : prescriptions) {
            if (prescription.getPrescriptionID().equals(prescriptionID)) {
                prescription.updateStatus();  
                System.out.println("Prescription status updated: " + prescription);
                return true;
            }
        }
        System.out.println("Prescription with ID " + prescriptionID + " not found.");
        return false;
    }

    @Override
    public Prescription findPrescriptionById(String prescriptionID) {
        for (Prescription prescription : prescriptions) {
            if (prescription.getPrescriptionID().equals(prescriptionID)) {
                return prescription;
            }
        }
        return null;
    }

    @Override
    public void removePrescription(String prescriptionID) {
        prescriptions.removeIf(prescription -> prescription.getPrescriptionID().equals(prescriptionID));
        System.out.println("Prescription with ID " + prescriptionID + " removed.");
    }
}