import java.util.*;
import java.io.*;

public class Doctor extends Staff implements IUser {

	private List<TimeSlot> availability;
    private List<Appointment> appointments;
    private List<String> assignedPatientIDs = new ArrayList<>(); // to view patients that are under their care

	//constructor
    public Doctor(String userId, String password, String name, String gender, String role, int age) {
        super(userId, password, name, gender, role, age);
        this.availability = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }

    public void assignPatient(String patientID) {
        if (!assignedPatientIDs.contains(patientID)) {
            assignedPatientIDs.add(patientID);
        }
    }

	/**
	 * 
	 * @param patientID
	 */
	public void viewPatientRecord(String patientID) {
        if (!assignedPatientIDs.contains(patientID)) {
            System.out.println("Access denied. Patient is not under your care.");
            return;
        }

        // Retrieve and print all appointment outcomes linked to this patient’s record
        MedicalRecord record = MedicalRecord.getRecordByPatientID(patientID);
        if (record != null) {
            System.out.println("Medical Record for Patient ID: " + patientID);
            record.viewMedicalRecord();
        } else {
            System.out.println("No medical record found for Patient ID: " + patientID);
        }
    }

	/**
	 * 
	 * @param patientID
	 * @param record
	 */
	public void updatePatientRecord(String patientID, AppointmentOutcome outcome) {
        if (!assignedPatientIDs.contains(patientID)) {
            System.out.println("Access denied. Patient is not under your care.");
            return;
        }

        MedicalRecord record = MedicalRecord.getRecordByPatientID(patientID);
        if (record != null) {
            record.addAppointmentOutcome(outcome);
            System.out.println("Patient record updated successfully for Patient ID: " + patientID);
        } else {
            System.out.println("No medical record found for Patient ID: " + patientID);
        }
    }

    public void cancelAppointment(Appointment appointment) {
        appointments.remove(appointment);  
        addAvailability(appointment.getTimeSlot());  
        System.out.println("Appointment canceled and slot " + appointment.getTimeSlot() + " returned to availability.");
    }
    

	public List<Appointment> getAppointments() {
        return appointments;
    }
	
    public boolean isAvailable(TimeSlot timeSlot) {
        // Check if time slot is in availability and not already booked
        if (!availability.contains(timeSlot)) {
            return false;
        }
        for (Appointment appointment : appointments) {
            if (appointment.getTimeSlot().isSameTimeSlot(timeSlot)) {
                return false;
            }
        }
        return true;
    }
    

	/**
	 * 
	 * @param timeSlot
	 */
	public void setAvailability(TimeSlot timeSlot) {
        for (TimeSlot slot : availability) {
            if (slot.isSameTimeSlot(timeSlot)) {
                System.out.println("Time slot " + timeSlot + " is already in the availability list.");
                return;  // Exit if a duplicate slot is found
            }
        }
        availability.add(timeSlot);
        System.out.println("Availability added for Dr. " + getName() + ": " + timeSlot);
        System.out.println("Current availability for Dr. " + getName() + ": " + availability);
    }
    

	/**
	 * 
	 * @param appointment
	 */
	public void acceptAppointment(Appointment appointment) {
        if (appointment.getDoctorID().equals(this.getUserId())) {  // only doctor of their own ID can modify their own appointments
            if (appointments.contains(appointment)) {
                appointment.confirm();
                System.out.println("Appointment " + appointment.getAppointmentID() + " accepted.");
            } else {
                System.out.println("No such appointment found.");
            }
        } else {
            System.out.println("Access Denied: You are not authorized to accept this appointment.");
        }
    }

    /**
	 * 
	 * @param appointment
	 */
    public void declineAppointment(Appointment appointment) {
        if (appointment.getDoctorID().equals(this.getUserId())) {  // only doctor of their own ID can modify their own appointments
            if (appointments.contains(appointment)) {
                appointment.setStatus("Declined");
                System.out.println("Appointment " + appointment.getAppointmentID() + " declined.");
            } else {
                System.out.println("No such appointment found.");
            }
        } else {
            System.out.println("Access Denied: You are not authorized to decline this appointment.");
        }
    }
    

	public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }
    
    public void addAvailability(TimeSlot timeSlot) {
        if (!availability.contains(timeSlot)) {
            availability.add(timeSlot);
            System.out.println("Availability added for Dr. " + getName() + ": " + timeSlot);
        }
    }
    
    

	public void viewAppointments() {
        System.out.println("Scheduled Appointments for Doctor ID: " + getUserId());
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

	public List<TimeSlot> getAvailability() {
        return availability;
    }

	//override displayMenu in User class
	@Override
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
            System.out.println("ERROR. PLEASE LOG IN! (Doctor)");
        }
    }
	

}