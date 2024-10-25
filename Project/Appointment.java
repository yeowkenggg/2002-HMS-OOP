import java.util.*;
import java.io.*;

public class Appointment {

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
    }
	
	public String getAppointmentID() {
        return appointmentID;
    }

    public String getStatus() {
        return status;
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

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentID + ", Doctor ID: " + doctorID + ", TimeSlot: " + timeSlot + ", Status: " + status;
    }
}