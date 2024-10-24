import java.util.*;
import java.io.*;

public class Pharmacist extends Staff {

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
	 */
	public void replenishmentRequest(Medicine med) {
		// TODO - implement Pharmacist.replenishmentRequest
		throw new UnsupportedOperationException();
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