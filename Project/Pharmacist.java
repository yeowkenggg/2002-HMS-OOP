import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pharmacist extends Staff {

	private List<ReplenishmentRequest> replenishmentReq = new ArrayList<>();
	
	public Pharmacist(String userId, String password, String name, String gender, String role,
			int age) {
		super(userId, password, name, gender, role, age);
	}

	public void viewPrescriptionRecords() {
        System.out.println("\nPrescription Records:");
        List<Prescription> prescriptions = Prescription.getAllPrescriptions();

        if (prescriptions.isEmpty()) {
            System.out.println("No prescriptions available.");
        } else {
            for (Prescription prescription : prescriptions) {
                System.out.println(prescription);
            }
        }
    }

	public void viewPendingPrescriptionRecords() {
        System.out.println("\nPrescription Records:");
        List<Prescription> prescriptions = Prescription.getAllPrescriptions();

        if (prescriptions.isEmpty()) {
            System.out.println("No prescriptions available.");
        } else {
            for (Prescription prescription : prescriptions) {
				if(prescription.getStatus() == "Pending"){                
					System.out.println(prescription);
				}
            }
        }
    }

	/**
	 * 
	 * 
	 * @param prescriptionID
	 */
	public void updatePrescriptionStatus(String prescriptionID) {
        // find the prescription by ID
        for (Prescription prescription : Prescription.getAllPrescriptions()) {
            if (prescription.getPrescriptionID().equals(prescriptionID)) {
                // update the status only if it’s pending
                if (prescription.getStatus().equalsIgnoreCase("Pending")) {
                    prescription.updateStatus("Dispensed");
                    System.out.println("Prescription " + prescriptionID + " status updated to Dispensed.");
                } else {
                    System.out.println("Prescription " + prescriptionID + " has already been dispensed.");
                }
                return;
            }
        }
        System.out.println("Prescription with ID " + prescriptionID + " not found.");
    }
	/**
	 * 
	 * @param medicine
	 * @param amt
	 */
	public void replenishmentRequest(Medicine med, int amt) {
		//stock < alertLine
		if(med.alertReplenishment()){
			for (ReplenishmentRequest request : ReplenishmentRequest.getRequests()) {
				if (request.getMedicine().equals(med) && !request.isApproved()) {
					System.out.println("A replenishment request for " + med.getName() + " is already pending by " + request.getPharmacistName() + ".");
					return;  
				}
			}
			//using time as a ID
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMHHmm"); //ddMMHHmmss
            String formattedDate = LocalDateTime.now().format(formatter);  
            String requestID = "R" + formattedDate;
			//use getStaffID in staff class to get the pharmacist's ID
			ReplenishmentRequest req = new ReplenishmentRequest(requestID, med, amt, getUserId(), getName());

			replenishmentReq.add(req);
			System.out.println("===================");
			System.out.println("Replenishment Req: "+ med.getName() +" has been submitted.");
			System.out.println("Summary:");
			System.out.printf("%d %s\n", amt, med.getName());
			System.out.println("===================");
		}
		else{
			System.out.println("Replenishment is not needed.");
		}
	}

	//view all replenishment made by pharmacist
	public List<ReplenishmentRequest> getReplenishmentRequests(){
		return replenishmentReq;
	}

	public void displayMenu() {
        if (isLoggedIn()) {
            System.out.println("1. View Appointment Outcome Record ");
			System.out.println("2. Update Prescription Status");
			System.out.println("3. View Medication Inventory");
			System.out.println("4. Submit Replenishment Request ");
			System.out.println("5. Logout");
        } else {
            System.out.println("ERROR. PLEASE LOG IN!");
        }
    }
}