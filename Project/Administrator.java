import java.util.*;
import java.io.*;

public class Administrator extends Staff {

	/**
	 * 
	 * @param action
	 * @param staff
	 */
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

}