import java.util.*;

import javax.swing.text.View;

import java.io.*;

public class Administrator extends Staff {

	public Administrator(String userId, String password, String name, String gender, String role,
			int age) {
		super(userId, password, name, gender, role, age);
	}

	public void manageStaff(String action, Staff staff) {
		// TODO - implement Administrator.manageStaff
		throw new UnsupportedOperationException();
	}

	public List<Appointment> viewAppointment() {
		//return appointments;
	}

	public void viewReplenishmentRequests() {
        System.out.println("Pending Replenishment Requests:");
        for (ReplenishmentRequest request : ReplenishmentRequest.getRequests()) {
            if (!request.isApproved()) {
                System.out.println("Request ID: " + request.getRequestID() + " | Medicine: " 
                                    + request.getMedicine().getName() + " | Amount: " + request.getRequestedAmount()+ " | Status: "+request.isApproved());
            }
        }
    }

	/**
	 * 
	 * @param req
	 * @param amount
	 */
	public void approveReplenishment(String requestID) {
        for (ReplenishmentRequest request : ReplenishmentRequest.getRequests()) {
            if (request.getRequestID().equals(requestID) && !request.isApproved()) {
                request.approve();
                System.out.println("Replenishment request " + requestID + " has been approved.");
                return;
            }
        }
        System.out.println("Replenishment request not found or already approved.");
    }
	
	//override displayMenu in User class
	@Override
    public void displayMenu() {
        if (isLoggedIn()) {
            System.out.println("1. View and Manage Hospital Staff");
			System.out.println("2. View Appointments detail");
			System.out.println("3. View and Manage Medication Inventory");
			System.out.println("4. Approve Replenishment Requests");
			System.out.println("5. Logout");
        } else {
            System.out.println("ERROR. PLEASE LOG IN!");
        }
    }
}