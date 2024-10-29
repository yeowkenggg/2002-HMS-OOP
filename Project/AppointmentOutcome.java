import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class AppointmentOutcome {
	
	
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

    }

	public Appointment getAppointment() {
        return appointment;
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
	
	public String getDetails() {
		StringBuilder details = new StringBuilder();
		details.append("======= Appointment Outcome =======\n");
		details.append("Appointment ID: ").append(appointment.getAppointmentID()).append("\n");
		details.append("Services: ").append(services != null && !services.isEmpty() ? services : "None").append("\n");
		details.append("Notes: ").append(notes != null && !notes.isEmpty() ? notes : "None").append("\n");
	
		if (prescription != null) {
			details.append("Prescription: ").append(prescription).append("\n");
		} else {
			details.append("Prescription: No Prescription Issued\n");
		}
	
		details.append("Appointment Date: ").append(appointmentDate).append("\n");
		details.append("=================================");
		return details.toString();
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