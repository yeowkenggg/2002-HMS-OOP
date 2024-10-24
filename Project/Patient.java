import java.util.*;

import javax.swing.text.View;

import java.io.*;

public class Patient extends User {

	public Patient(String userId, String password, String name, String gender) {
		super(userId, password, name, gender);
	}

	private String patientID;
	private Date dateOfBirth;
	private String bloodType;
	private String contactInfo;
	private List<MedicalRecord> medicalRecords;

	/**
	 * 
	 * @param appointmentID
	 */
	public void scheduleAppointment(Appointment appointmentID) {
		// TODO - implement Patient.scheduleAppointment
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param appointmentID
	 */
	public void cancelAppointment(Appointment appointmentID) {
		// TODO - implement Patient.cancelAppointment
		throw new UnsupportedOperationException();
	}

	public AppointmentOutcome viewAppointmentOutcome() {
		// TODO - implement Patient.viewAppointmentOutcome
		throw new UnsupportedOperationException();
	}	
	
	public void updateContactInfo() {
		// TODO - implement Patient.updateContactInfo
		throw new UnsupportedOperationException();
	}
	
	//override displayMenu in User class
	@Override
	public void displayMenu() {
        if (isLoggedIn()) {
			System.out.println("1. View Medical Record");
			System.out.println("2. Update Personal Information");
			System.out.println("3. View Available Appointment Slots");
			System.out.println("4. Schedule an Appointment");
			System.out.println("5. Reschedule an Appointment");
			System.out.println("6. Cancel an Appointment");
			System.out.println("7. View Scheduled Appointments");
			System.out.println("8. View Past Appointment Outcome Records");
			System.out.println("9. Logout");
        } else {
            System.out.println("ERROR. PLEASE LOG IN!");
        }
    }

}