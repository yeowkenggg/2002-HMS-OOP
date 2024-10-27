import java.util.*;

import javax.swing.text.View;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Patient extends User {

    
	private String patientID;
    private LocalDate dateOfBirth;
    private String bloodType;
    private String contactInfo;
    private MedicalRecord medicalRecord;
    private List<Appointment> appointments;

    // constructor
    public Patient(String userId, String password, String name, String gender, LocalDate dateOfBirth, String bloodType, String contactInfo) {
        super(userId, password, name, gender);
        this.patientID = userId;
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
        this.appointments = new ArrayList<>();
        this.medicalRecord = new MedicalRecord(userId, name, dateOfBirth, gender, bloodType, contactInfo);  // initialize medical record
        MedicalRecord.addRecord(medicalRecord);
    }


	/**
	 * 
	 * @param doctor
	 * @param timeSlot
	 */
	public void scheduleAppointment(Doctor doctor, TimeSlot timeSlot) {
			
		// Check if the doctor is available for the given time slot
		if (doctor.isAvailable(timeSlot)) {
			//using time as a ID
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMHHmms"); //ddMMHHmmss
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
        appointment.cancel();
        appointments.remove(appointment);
    
        System.out.println("Appointment with ID " + appointment.getAppointmentID() + " has been canceled.");
        
        // Find the doctor and return the slot to availability
        Doctor doctor = findDoctorById(appointment.getDoctorID());
        if (doctor != null) {
            doctor.cancelAppointment(appointment); 
        }
    }
    
    


	public void viewAppointmentOutcome() {
        List<AppointmentOutcome> outcomes = AppointmentOutcome.getOutcomesByPatientID(this.getUserId());

        if (outcomes.isEmpty()) {
            System.out.println("No appointment outcomes available.");
        } else {
            for (AppointmentOutcome outcome : outcomes) {
                System.out.println(outcome);  // toString() will format the output
            }
        }
    }
	

	public void updateContactInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new contact information (e.g., phone number, email): ");
        String newContactInfo = scanner.nextLine();

        if (!newContactInfo.isEmpty()) {
            this.contactInfo = newContactInfo;
            medicalRecord.setContactInfo(newContactInfo);  // sync with MedicalRecord
            System.out.println("Contact information updated successfully to: " + contactInfo);
        } else {
            System.out.println("Invalid input. Contact information not updated.");
        }
    }
	
    public void updateName(String newName) {
        if (!newName.isEmpty()) {
            this.setName(newName);
            medicalRecord.setName(newName);  // sync with MedicalRecord
            System.out.println("Name updated to: " + newName);
        } else {
            System.out.println("Name cannot be empty.");
        }
    }

    public void updateGender(String newGender) {
        if (newGender.equalsIgnoreCase("Male") || newGender.equalsIgnoreCase("Female")) {
            this.setGender(newGender);
            medicalRecord.setGender(newGender);  // sync with MedicalRecord
            System.out.println("Gender updated to: " + newGender);
        } else {
            System.out.println("Invalid gender input.");
        }
    }

    public void updateDateOfBirth(LocalDate newDateOfBirth) {
        this.dateOfBirth = newDateOfBirth;
        medicalRecord.setDateOfBirth(newDateOfBirth);  // sync with MedicalRecord
        System.out.println("Date of birth updated to: " + newDateOfBirth);
    }

    public void viewMedicalRecord() {
        medicalRecord.viewMedicalRecord();  // displays all details and outcomes
    }

    public String getContactInfo() {
        return contactInfo;
    }

	public void displayContactInfo() {
        System.out.println("Current Contact Info: " + contactInfo);
    }

    public void setGender(String gender) {
        super.setGender(gender);  // calling the superclass method
    }
	
    public void setName(String name){
        super.setName(name);
        this.medicalRecord.setName(name); //sync with medicalRecord
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }
    public void viewAvailableSlots(Doctor doctor) {
        System.out.println("Available Appointment Slots for Dr. " + doctor.getName() + ":");
        List<TimeSlot> availableSlots = doctor.getAvailability();
        for (TimeSlot slot : availableSlots) {
            if (doctor.isAvailable(slot)) {  // Check if the slot is truly available
                System.out.println(slot);
            }
        }
    }

    public void rescheduleAppointment(Appointment appointment, TimeSlot newTimeSlot) {
        if (appointments.contains(appointment)) {
            Doctor doctor = findDoctorById(appointment.getDoctorID());
            if (doctor != null) {
                // check doctor is available 
                if (doctor.isAvailable(newTimeSlot)) {
                    // cancel den schedule new
                    appointment.setStatus("Rescheduled");
                    appointments.remove(appointment);
    
                    Appointment newAppointment = new Appointment(
                        appointment.getAppointmentID(),
                        appointment.getPatientID(),
                        appointment.getDoctorID(),
                        newTimeSlot,
                        "Confirmed"
                    );
                    appointments.add(newAppointment);
                    doctor.addAppointment(newAppointment); // sync with doctor appts
    
                    System.out.println("Appointment rescheduled successfully to " + newTimeSlot);
                } else {
                    System.out.println("The selected time slot is not available for rescheduling.");
                }
            } else {
                System.out.println("Doctor not found.");
            }
        } else {
            System.out.println("No such appointment found to reschedule.");
        }
    }
    
    private Doctor findDoctorById(String doctorId) {
        for (Staff staff : Staff.getAllStaff()) {
            if (staff instanceof Doctor && staff.getUserId().equals(doctorId)) {
                return (Doctor) staff;
            }
        }
        return null;
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