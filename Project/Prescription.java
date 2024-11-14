import java.util.ArrayList;
import java.util.List;

/**
 * Prescription Class
 */
public class Prescription {

    private String prescriptionID;
    private List<Medicine> medicines;
    private List<Integer> quantities;
    private String status;

    private static List<Prescription> allPrescriptions = new ArrayList<>();

    /**
     * Constructor for Prescription
     * @param prescriptionID unique identifier for prescription
     * @param medicines the medicine for the prescription
     * @param quantities the quantity of medicine for the prescription
     * @param status the status of the prescription
     */
    public Prescription(String prescriptionID, List<Medicine> medicines, List<Integer> quantities, String status) {
        this.prescriptionID = prescriptionID;
        this.medicines = medicines;
        this.quantities = quantities;
        this.status = status;
        allPrescriptions.add(this);
    }

    /**
     * A check if the inventory has such a medicine
     * @param med the medicine to be checked for
     * @return a boolean to indicate if the medicine exists
     */
    public boolean hasMedicine(Medicine med) {
        return medicines.contains(med);
    }
    
    /**
     * get method to get prescription ID
     * @return the prescription iD
     */
    public String getPrescriptionID() {
        return prescriptionID;
    }

    /**
     * get method for status
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * get method to get a list of all prescrptions
     * @return a list of all prescriptions
     */
    public static List<Prescription> getAllPrescriptions() {
        return allPrescriptions;
    }

    /**
     * update method to change the method from "Pending" to "Dispensed"
     */
    public void updateStatus() {
        if ("Pending".equals(this.status)) {
            this.status = "Dispensed";
            System.out.println("Prescription " + prescriptionID + " status updated to Dispensed.");
        } else {
            System.out.println("This prescription has already been dispensed.");
        }
    }
    
    /**
     * Method to retrieve all medicines
     * @return a list of medicines
     */
    public List<Medicine> getMedicines() {
        return medicines;
    }

    /**
     * Method to retrieve the quantity of medicines
     * @return a list of quantities of medicines
     */
    public List<Integer> getQuantities() {
        return quantities;
    }

    /**
     * String representation of prescription 
     */
    public String toString() {
        String result = prescriptionID + " Medicines: ";
        
        for (int i = 0; i < medicines.size(); i++) {
            result += medicines.get(i).getName() + " x " + quantities.get(i);
            if (i < medicines.size() - 1) {
                result += ", "; 
            }
        }
        
        result += " Status: " + status;
        return result;
    }
}
