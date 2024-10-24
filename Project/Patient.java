public class Patient extends User {

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


}