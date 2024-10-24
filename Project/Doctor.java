import java.util.*;
import java.io.*;

public class Doctor extends Staff {

	private List<TimeSlot> availability;

	/**
	 * 
	 * @param patientID
	 */
	public MedicalRecord viewPatientRecord(String patientID) {
		// TODO - implement Doctor.viewPatientRecord
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param patientID
	 * @param record
	 */
	public void updatePatientRecord(String patientID, MedicalRecord record) {
		// TODO - implement Doctor.updatePatientRecord
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param timeSlot
	 */
	public void setAvailability(TimeSlot timeSlot) {
		// TODO - implement Doctor.setAvailability
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param req
	 */
	public void acceptAppointment(Appointment req) {
		// TODO - implement Doctor.acceptAppointment
		throw new UnsupportedOperationException();
	}
	
	public void displayMenu() {
        if (isLoggedIn()) {
            System.out.println("View Patient Medical Records");
			System.out.println("Update Patient Medical Records");
			System.out.println("View Personal Schedule");
			System.out.println("Set Availability for Appointments");
			System.out.println("Accept or Decline Appointment Requests");
			System.out.println("View Upcoming Appointments");
			System.out.println("Record Appointment Outcome");
        } else {
            System.out.println("ERROR. PLEASE LOG IN!");
        }
    }
	

}