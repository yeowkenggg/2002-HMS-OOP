import java.util.*;

import javax.swing.text.View;

import java.io.*;

public class Administrator extends Staff {

	public Administrator(String userId, String password, String name, String gender, String staffID, String role,
			int age) {
		super(userId, password, name, gender, staffID, role, age);
	}

	public void manageStaff(String action, Staff staff) {
		// TODO - implement Administrator.manageStaff
		throw new UnsupportedOperationException();
	}

	public List<Appointment> viewAppointment() {
		// TODO - implement Administrator.viewAppointment
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param req
	 */
	public void approveReplenishment(Medicine req, int amount) {
		// TODO - implement Administrator.approveReplenishment
		req.replenish(amount);

		throw new UnsupportedOperationException();
	}
	
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