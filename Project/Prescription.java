import java.util.ArrayList;
import java.util.List;

public class Prescription {

    private String prescriptionID;
    private List<Medicine> medicine;
    private String status;

    private static List<Prescription> allPrescriptions = new ArrayList<>();

    //constructor
    public Prescription(String prescriptionID, List<Medicine> medicine, String status) {
        this.prescriptionID = prescriptionID;
        this.medicine = medicine; 
        this.status = status;
        allPrescriptions.add(this);
    }

    //check if medicine exists in the list
    public boolean hasMedicine(Medicine med) {
        return medicine.contains(med);
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

    @Override
    public String toString() {
        return "Medicines: " + medicine + " Status: " + status;
    }
}
