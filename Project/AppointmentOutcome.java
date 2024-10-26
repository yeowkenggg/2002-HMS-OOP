import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class AppointmentOutcome {
	
	private static List<AppointmentOutcome> allOutcomes = new ArrayList<>();  

	private Appointment appointment;
	private String services;
	private String notes;
	private Prescription prescription;
	private LocalDate appointmentDate;

	//constructor
	public AppointmentOutcome(Appointment appointment, String services, String notes, Prescription prescription, LocalDate localDate) {
        this.appointment = appointment;
        this.services = services;
        this.notes = notes;
        this.prescription = prescription;
        this.appointmentDate = localDate;

        allOutcomes.add(this); 
    }

	public Appointment getAppointment() {
        return appointment;
    }

	public static List<AppointmentOutcome> getAllOutcomes() {
        return allOutcomes;
    }

	public static List<AppointmentOutcome> getOutcomesByPatientID(String patientID) {
        List<AppointmentOutcome> patientOutcomes = new ArrayList<>();
        for (AppointmentOutcome outcome : allOutcomes) {
            if (outcome.getAppointment().getPatientID().equals(patientID)) {
                patientOutcomes.add(outcome);
            }
        }
        return patientOutcomes;
    }
	/**
	 * 
	 * @param services
	 */
	public void setServices(String services) {
		this.services = services;
	}

	public String getServices() {
        return services;
    }
	/**
	 * 
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes() {
        return notes;
    }

	public Prescription getPrescriptrion() {
		return prescription;
	}

	public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

	/**
	 * 
	 * @param prescription
	 */
	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

	public String toString() {
		return "======= Appointment Outcome =======\n" +
			   "Appointment ID: " + appointment.getAppointmentID() +
			   "\nServices: " + services +
			   "\nNotes: " + notes +
			   "\nPrescription: " + (prescription != null ? prescription : "No Prescription") +
			   "\nAppointment Date: " + appointmentDate +
			   "\n=================================";
	}
}