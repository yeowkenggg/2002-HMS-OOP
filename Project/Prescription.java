import java.util.ArrayList;
import java.util.List;

public class Prescription {

    private String prescriptionID;
    private List<Medicine> medicines;
    private List<Integer> quantities;
    private String status;

    private static List<Prescription> allPrescriptions = new ArrayList<>();

    //constructor
    public Prescription(String prescriptionID, List<Medicine> medicines, List<Integer> quantities, String status) {
        this.prescriptionID = prescriptionID;
        this.medicines = medicines;
        this.quantities = quantities;
        this.status = status;
        allPrescriptions.add(this);
    }

    //check if medicine exists in the list
    public boolean hasMedicine(Medicine med) {
        return medicines.contains(med);
    }
    
    //get prescription id 
    public String getPrescriptionID() {
        return prescriptionID;
    }

    //get status
    public String getStatus() {
        return status;
    }

    public static List<Prescription> getAllPrescriptions() {
        return allPrescriptions;
    }

    //change the status from pending to dispensed
    public void updateStatus() {
        if ("Pending".equals(this.status)) {
            this.status = "Dispensed";
            System.out.println("Prescription " + prescriptionID + " status updated to Dispensed.");
        } else {
            System.out.println("This prescription has already been dispensed.");
        }
    }
    
    public List<Medicine> getMedicines() {
        return medicines;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    @Override
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
