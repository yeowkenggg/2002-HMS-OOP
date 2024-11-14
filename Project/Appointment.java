import java.util.*;
import java.io.*;
import java.time.LocalDateTime;

/**
 * Appointment class
 */
public class Appointment {

    private static List<Appointment> allAppointments = new ArrayList<>();

	private String appointmentID;
	private String patientID;
	private String doctorID;
	private TimeSlot timeSlot;
	private String status;
    private AppointmentOutcome outcome;

    /**
     * Constructor for appointment class
     * @param appointmentID unique ID for appointment
     * @param patientID get Patient through ID
     * @param doctorID get Doctor through ID
     * @param timeSlot TimeSlot for appointment
     * @param status status indicator for the status of appointment
     */
    public Appointment(String appointmentID, String patientID, String doctorID, TimeSlot timeSlot, String status) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.timeSlot = timeSlot;
        this.status = status;
        this.outcome = null;  //no outcome initially

        allAppointments.add(this);
    }
	
    /**
     * get method for appointmentID
     * @return the appointmentID
     */
	public String getAppointmentID() {
        return appointmentID;
    }

    /**
     * get method for status
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * get method for patientID
     * @return the patientID
     */
    public String getPatientID() { 
        return patientID; 
    }

    /**
     * get method for doctorID
     * @return the patientID
     */
    public String getDoctorID(){
        return doctorID;
    }

    /**
     * get method for timeSlot
     * @return the timelot
     */
    public TimeSlot getTimeSlot(){
        return timeSlot;
    }
    
    /**
     * set method for timeslot
     * @param newTimeSlot gets a timeslot as parameter and set it
     */
    public void setTimeSlot(TimeSlot newTimeSlot) {
        this.timeSlot = newTimeSlot;
    }

    /**
     * set method for status
     * @param status gets a status as parameter and set it
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * sets a record outcome for the patient
     * @param services the services that was done for the patient
     * @param notes the notes for the outcome
     * @param prescription the prescriptions
     * @param patient the patient object this outcome is for
     */
    public void recordOutcome(String services, String notes, Prescription prescription, Patient patient) {
        if (this.outcome == null) {
            this.outcome = new AppointmentOutcome(this, services, notes, prescription, timeSlot.getDate());
        
            MedicalRecord record = MedicalRecord.getRecordByPatientID(patientID);
            record.addAppointmentOutcome(outcome);
            }   
        else {
                System.out.println("Outcome already exists in the patient record for Patient ID: " + patientID);
        }
    }
    
        
    /**
     * get method for outcome
     * @return the outcome
     */
    public AppointmentOutcome getOutcome() { 
        return outcome; 
    }

    /**
     * set status as confirmed
     */
    public void confirm() {
        this.status = "Confirmed";
    }

    /**
     * set status as cancelled
     */
    public void cancel() {
        this.status = "Cancelled";
    }

    /**
     * retrieves a list of appointment in ther system
     * @return a list of appointments
     */
    public static List<Appointment> getAllAppointments() {
        return allAppointments;  
    }

    /**
     * method for a representation of appointment
     * @return a formatted string containing the appointment ID, time, and status
     */
    @Override
    public String toString() {
        return "Appointment ID: " + appointmentID + 
               ", Time: " + timeSlot + 
               ", Status: " + status ;
    }

    /**
     * checks if an appointment is in the past
     * @return a bool indicating if the appointment is in the past or not
     */
    public boolean isPast() {
        LocalDateTime appointmentDateTime = LocalDateTime.of(this.timeSlot.getDate(), this.timeSlot.getTime());
        return appointmentDateTime.isBefore(LocalDateTime.now());
    }
    
    /**
     * checks if an appointment is upcoming (in the future)
     * @return a bool indicating if the appointment is in the future or not
     */
    public boolean isUpcoming() {
        LocalDateTime appointmentDateTime = LocalDateTime.of(this.timeSlot.getDate(), this.timeSlot.getTime());
        return appointmentDateTime.isAfter(LocalDateTime.now());
    }

}