import java.util.*;
import java.io.*;

public class Doctor extends Staff {

	private List<TimeSlot> availability;
    private List<Appointment> appointments;

	//constructor
    public Doctor(String userId, String password, String name, String gender, String role, int age) {
        super(userId, password, name, gender, role, age);
        this.availability = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }

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

	public List<Appointment> getAppointments() {
        return appointments;
    }
	
    public boolean isAvailable(TimeSlot timeSlot) {
        System.out.println("Checking availability for Dr. " + getName() + " at " + timeSlot);
        // check if the time slot is in the availability list
        boolean isAvailableInList = false;
        for (TimeSlot slot : availability) {
            if (slot.isSameTimeSlot(timeSlot)) {
                isAvailableInList = true;
                break;
            }
        }
        if (!isAvailableInList) {
            System.out.println("Time slot is not in the availability list.");
            return false;
        }
        // check if the time slot is already booked in appointments
        for (Appointment appointment : appointments) {
            if (appointment.getTimeSlot().isSameTimeSlot(timeSlot)) {
                System.out.println("Time slot is already booked.");
                return false;
            }
        }

        System.out.println("Time slot is available.");
        return true;
    }

	/**
	 * 
	 * @param timeSlot
	 */
	public void setAvailability(TimeSlot timeSlot) {
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

	public void viewAppointments() {
        System.out.println("Scheduled Appointments for Doctor ID: " + getUserId());
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
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
            System.out.println("ERROR. PLEASE LOG IN!");
        }
    }
	

}