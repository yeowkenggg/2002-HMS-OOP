import java.util.*;
import java.io.*;

public class AppointmentOutcome {

	private String appointmentID;
	private String services;
	private String notes;
	private Prescription prescription;
	private Date appointmentDate;

	public String getInfo() {
		// TODO - implement AppointmentOutcome.getInfo
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param services
	 */
	public void setServices(String services) {
		this.services = services;
	}

	/**
	 * 
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Prescription getPrescriptrion() {
		// TODO - implement AppointmentOutcome.getPrescriptrion
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param prescription
	 */
	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

}