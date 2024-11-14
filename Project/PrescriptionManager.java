import java.util.ArrayList;
import java.util.List;

/**
 * PrescriptionManager class, implementing logic for Prescription class
 */
public class PrescriptionManager implements IPrescriptionManager {

    private List<Prescription> prescriptions;
    private IMedicineManager medicineManager;

    /**
     * Constructor for PrescriptionManager
     * @param medicineManager the manager responsible for handling medicine-related operations
     */
    public PrescriptionManager(IMedicineManager medicineManager) {
        this.prescriptions = new ArrayList<>();
        this.medicineManager = medicineManager;
    }

    /**
     * set medicine manager
     * @param mm the manager responsible for handling medicine-related operations
     */
    public void setMedicineManager(IMedicineManager mm){
        this.medicineManager = mm;
    }

    /**
     * adds prescription to a list of all prescription
     * @param prescription the prescription to be added
     */
    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
        System.out.println("Prescription added: " + prescription);
    }

    /**
     * Retrieves the list of prescrpition in the system
     * @return the list of prescriptions
     */
    public List<Prescription> getAllPrescriptions() {
        return new ArrayList<>(prescriptions);  // return a copy to prevent direct modification
    }


    /**
     * Retrieves all pending prescrpition in the system
     * @return the list of pending prescriptions
     */
    public List<Prescription> getPendingPrescriptions() {
        List<Prescription> pendingPrescriptions = new ArrayList<>();
        for (Prescription prescription : prescriptions) {
            if ("Pending".equalsIgnoreCase(prescription.getStatus())) {
                pendingPrescriptions.add(prescription);
            }
        }
        return pendingPrescriptions;
    }

    /**
     * Updates a prescription status
     * @param prescriptionID the ID of prescription that is to be updated
     * @return a boolean indicating if the status has been successfully updated
     */
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
                        if(medicine.alertReplenishment()){
                            System.out.println(medicine.getName() + " requires replenishment.");
                        };
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


    /**
     * Retrieve prescription based on prescription ID
     * @param prescriptionID the ID of prescription that is to be retrieved
     * @return the prescription
     */
    public Prescription findPrescriptionById(String prescriptionID) {
        for (Prescription prescription : prescriptions) {
            if (prescription.getPrescriptionID().equals(prescriptionID)) {
                return prescription;
            }
        }
        return null;
    }

}