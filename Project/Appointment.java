import java.util.*;
import java.io.*;
import java.time.LocalDateTime;

public class Appointment {

    private static List<Appointment> allAppointments = new ArrayList<>();

	private String appointmentID;
	private String patientID;
	private String doctorID;
	private TimeSlot timeSlot;
	private String status;
    private AppointmentOutcome outcome;


    public Appointment(String appointmentID, String patientID, String doctorID, TimeSlot timeSlot, String status) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.timeSlot = timeSlot;
        this.status = status;
        this.outcome = null;  //no outcome initially

        allAppointments.add(this);
    }
	
    
	public String getAppointmentID() {
        return appointmentID;
    }

    public String getStatus() {
        return status;
    }

    public String getPatientID() { 
        return patientID; 
    }

    public String getDoctorID(){
        return doctorID;
    }

    public TimeSlot getTimeSlot(){
        return timeSlot;
    }
    
    public void setTimeSlot(TimeSlot newTimeSlot) {
        this.timeSlot = newTimeSlot;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOutcome(AppointmentOutcome outcome) {
        if (this.outcome != null) {
            return;
        }
        this.outcome = outcome;
        
        MedicalRecord record = MedicalRecord.getRecordByPatientID(patientID);
        if (record != null) {
            if (!record.getAppointmentOutcomes().contains(outcome)) {
                record.addAppointmentOutcome(outcome);
            } else {
                System.out.println("Outcome already exists in the patient record for Patient ID: " + patientID);
            }
        }
    }
    

    public AppointmentOutcome getOutcome() { 
        return outcome; 
    }

    public void confirm() {
        this.status = "Confirmed";
    }

    public void cancel() {
        this.status = "Cancelled";
    }

    public static List<Appointment> getAllAppointments() {
        return allAppointments;  
    }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentID + 
               ", Time: " + timeSlot + 
               ", Status: " + status ;
    }

    public boolean isPast() {
        LocalDateTime appointmentDateTime = LocalDateTime.of(this.timeSlot.getDate(), this.timeSlot.getTime());
        return appointmentDateTime.isBefore(LocalDateTime.now());
    }
    
    public boolean isUpcoming() {
        LocalDateTime appointmentDateTime = LocalDateTime.of(this.timeSlot.getDate(), this.timeSlot.getTime());
        return appointmentDateTime.isAfter(LocalDateTime.now());
    }

}