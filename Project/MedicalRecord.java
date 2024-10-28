import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class MedicalRecord {

	private String patientID;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String bloodType;
    private String contactInfo;
    private List<AppointmentOutcome> pastAppointments;

	private static List<MedicalRecord> allRecords = new ArrayList<>();

	//constructor
    public MedicalRecord(String patientID, String name, LocalDate dateOfBirth, String gender, String bloodType, String contactInfo) {
        this.patientID = patientID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
        this.pastAppointments = new ArrayList<>();
    }
 	public static void addRecord(MedicalRecord record) {
        allRecords.add(record);
    }
	
	public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;

    }

    public void addAppointmentOutcome(AppointmentOutcome outcome) {
        pastAppointments.add(outcome);
    }

	public static MedicalRecord getRecordByPatientID(String patientID) {
        for (MedicalRecord record : allRecords) {
            if (record.getPatientID().equals(patientID)) {
                return record;
            }
        }
        return null;  // null if no record found
    }

    public String getPatientID() {
        return patientID;
    }

    public void viewMedicalRecord() {
    System.out.println("=== Medical Record ===");
    System.out.println("Patient ID: " + patientID);
    System.out.println("Name: " + name);
    System.out.println("Date of Birth: " + dateOfBirth);
    System.out.println("Gender: " + gender);
    System.out.println("Blood Type: " + bloodType);
    System.out.println("Contact Info: " + contactInfo);
    System.out.println("\n--- Past Diagnoses and Treatments ---");

    if (pastAppointments.isEmpty()) {
        System.out.println("N/A");
    } else {
        for (AppointmentOutcome outcome : pastAppointments) {
            System.out.println(outcome);
        }
    }
    System.out.println("");
}


}