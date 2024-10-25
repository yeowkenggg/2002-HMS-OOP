import java.util.*;

import javax.swing.text.View;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Patient extends User {

	private String patientID;
	private Date dateOfBirth;
	private String bloodType;
	private String contactInfo;
	private List<MedicalRecord> medicalRecords;
	private List<Appointment> appointments; 

	//constructor
    public Patient(String userId, String password, String name, String gender) {
        super(userId, password, name, gender);
		this.patientID = userId;
        this.appointments = new ArrayList<>();
    }


	/**
	 * 
	 * @param appointmentID
	 */
	public void scheduleAppointment(Doctor doctor, TimeSlot timeSlot) {
			
		// Check if the doctor is available for the given time slot
		if (doctor.isAvailable(timeSlot)) {
			//using time as a ID
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMHHmm"); //ddMMHHmmss
            String formattedDate = LocalDateTime.now().format(formatter);  
            String requestID = "R" + formattedDate;
			Appointment appointment = new Appointment(requestID, this.patientID, doctor.getUserId(), timeSlot, "Pending");
			appointments.add(appointment);
			doctor.addAppointment(appointment);
			System.out.println("Appointment scheduled with Dr. " + doctor.getName() + " on " + timeSlot);
		} else {
			System.out.println("The doctor is not available for the selected time slot.");
		}
	}
	
	public void viewAppointments() {
        System.out.println("Scheduled Appointments");
        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(patientID)) {
                System.out.println(appointment);
            }
        }
    }
	/**
	 * 
	 * @param appointment
	 */
	public void cancelAppointment(Appointment appointment) {
        if (appointments.contains(appointment)) {
            appointment.cancel();
            appointments.remove(appointment);
            System.out.println("Appointment with ID " + appointment.getAppointmentID() + " has been canceled.");
        } else {
            System.out.println("No such appointment found.");
        }
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