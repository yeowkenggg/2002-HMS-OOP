import java.util.*;
import java.io.*;

public class Prescription {

	private String prescriptionID;
	private List<Medicine> medicine;
	private String status;

	//constructor
	public Prescription(String prescriptionID, String status) {
        this.prescriptionID = prescriptionID;
        this.medicine = new ArrayList<>();  
        this.status = status;
    }

	// to return the list of medicine
	public List<Medicine> getMedicines() {
        return medicine;
    }

	//adding medicine, checks if there are duplicated as well
	public void addMedicine(Medicine med) {
        if (!medicine.contains(med)) {
            medicine.add(med);
            System.out.println(med.getName() + " added to the prescription.");
        } else {
            System.out.println(med.getName() + " is already in the prescription.");
        }
    }

	//removing medicine, check if it even exists in the list of medicine as as well
	public void removeMedicine(Medicine med) {
        if (medicine.contains(med)) {
            medicine.remove(med);
            System.out.println(med.getName() + " has been removed from the prescription.");
        } else {
            System.out.println(med.getName() + " is not in the prescription.");
        }
    }

	//change the status from pending to dispensed
	public void updateStatus(String status) {
		if(status.equals("Pending")){
			this.status = "Dispensed";
		}
		else{
			System.out.println("This prescription has already been dispensed.");
		}
		System.out.println("Prescription status updated to: " + this.status);
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
}