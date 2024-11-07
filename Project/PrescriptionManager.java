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
        Prescription prescription = findPrescriptionById(prescriptionID);
        if (prescription != null) {
            if ("Pending".equalsIgnoreCase(prescription.getStatus())) {
                //dispensed
                prescription.updateStatus();

                List<Medicine> medicines = prescription.getMedicines();
                List<Integer> quantities = prescription.getQuantities();

                for (int i = 0; i < medicines.size(); i++) {
                    Medicine medicine = medicines.get(i);
                    int quantity = quantities.get(i);
                    //check stock and deduct when dispensed
                    if (medicine.getStock() >= quantity) {
                        medicine.deductStock(quantity);
                        System.out.println("Deducted " + quantity + " units of " + medicine.getName() + ". Remaining stock: " + medicine.getStock());
                    } else {
                        System.out.println("Insufficient stock for " + medicine.getName() + ". Prescription update aborted.");
                        return false;
                    }
                }

                return true;
            } else {
                System.out.println("This prescription has already been dispensed.");
                return false;
            }
        } else {
            return false;
        }
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
        if (prescriptions.removeIf(prescription -> prescription.getPrescriptionID().equals(prescriptionID))) {
            System.out.println("Prescription with ID " + prescriptionID + " removed.");
        } else {
            System.out.println("Prescription with ID " + prescriptionID + " not found.");
        }
    }
}