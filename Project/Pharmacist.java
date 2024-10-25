import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pharmacist extends Staff {

	//to be able to store replenishmentRequest
	 private List<ReplenishmentRequest> replenishmentReq;

	public Pharmacist(String userId, String password, String name, String gender, String role,
			int age) {
		super(userId, password, name, gender, role, age);
		this.replenishmentReq = new ArrayList<>();
	}

	public List<Prescription> viewPrescriptionRecords() {
		// TODO - implement Pharmacist.viewPrescriptionRecords
		throw new UnsupportedOperationException();
	}

	

	/**
	 * 
	 * 
	 * @param prescriptionID
	 * @param status
	 */
	public void updateStatus(String prescriptionID, String status) {
		// TODO - implement Pharmacist.updateStatus
		throw new UnsupportedOperationException();
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