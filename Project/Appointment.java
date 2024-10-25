import java.util.*;
import java.io.*;

public class Appointment {

    private static List<Appointment> allAppointments = new ArrayList<>();

	private String appointmentID;
	private String patientID;
	private String doctorID;
	private TimeSlot timeSlot;
	private String status;

	public Appointment(String appointmentID, String patientID, String doctorID, TimeSlot timeSlot, String status) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.timeSlot = timeSlot;
        this.status = status;

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

    public void setStatus(String status) {
        this.status = status;
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
        return "Appointment ID: " + appointmentID + ", Doctor ID: " + doctorID + ", Time: " + timeSlot + " - Status: " + status;
    }
}