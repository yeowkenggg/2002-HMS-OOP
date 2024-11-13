import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MedicalRecord {

	private Patient patient;
    private List<Diagnosis> diagnoses;
    private List<Treatment> treatments;
    private List<Prescription> prescriptions;
    private List<AppointmentOutcome> pastAppointments;
    private List<LocalDateTime> entryTimestamps;

	private static List<MedicalRecord> allRecords = new ArrayList<>();

	//constructor
    public MedicalRecord(Patient patient) {
        this.patient = patient; // Reference to existing Patient object
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
        this.pastAppointments = new ArrayList<>();
        this.entryTimestamps = new ArrayList<>();
    }

    public String getPatientName(){
        return patient.getName();
    }
    public String getPatientBloodType(){
        return patient.getBloodType();
    }
    public String getContactInfo(){
        return patient.getContactInfo();
    }
    public LocalDate getDateOfBirth(){
        return patient.getDateOfBirth();
    }
    public int getPhoneNumber(){
        return patient.getPhoneNumber();
    }
    public String getGender(){
        return patient.getGender();
    }

 	public static void addRecord(MedicalRecord record) {
        allRecords.add(record);
    }

    public void addAppointmentOutcome(AppointmentOutcome outcome) {
        if (!pastAppointments.contains(outcome)) {  // Check to prevent duplicates
            pastAppointments.add(outcome);
            System.out.println("Appointment outcome added to medical record for Patient ID: " + patient.getPatientID());
        } else {
            System.out.println("Appointment outcome already exists in the record for Patient ID: " + patient.getPatientID());
        }
    }
    

    public void removeAppointmentOutcome(AppointmentOutcome outcome) {
        if (pastAppointments.contains(outcome)) {
            pastAppointments.remove(outcome);
            System.out.println("Appointment outcome removed from medical record for Patient ID: " + patient.getPatientID());
        } else {
            System.out.println("Outcome not found in the medical record for Patient ID: " + patient.getPatientID());
        }
    }

    public void addDiagnosis(Diagnosis diagnosis) {
        diagnoses.add(diagnosis);
        entryTimestamps.add(LocalDateTime.now());
        System.out.println("Diagnosis added to medical record for Patient ID: " + patient.getPatientID());
    }

    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
        entryTimestamps.add(LocalDateTime.now());
        System.out.println("Prescription added to medical record for Patient ID: " + patient.getPatientID());
    }

    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
        entryTimestamps.add(LocalDateTime.now());
        System.out.println("Treatment added to medical record for Patient ID: " + patient.getPatientID());
    }

    public void removeDiagnosis(String diagnosisID) {
        diagnoses.removeIf(diagnosis -> diagnosis.getDiagnosisID().equals(diagnosisID));
        System.out.println("Diagnosis with ID " + diagnosisID + " removed from medical record for Patient ID: " + patient.getPatientID());
    }

    public void removeTreatment(String treatmentID) {
        treatments.removeIf(treatment -> treatment.getTreatmentID().equals(treatmentID));
        System.out.println("Treatment with ID " + treatmentID + " removed from medical record for Patient ID: " + patient.getPatientID());
    }

    public void removePrescription(String prescriptionID) {
        prescriptions.removeIf(treatment -> treatment.getPrescriptionID().equals(prescriptionID));
        System.out.println("Prescription with ID " + prescriptionID + " removed from medical record for Patient ID: " + patient.getPatientID());
    }

    public List<AppointmentOutcome> getAppointmentOutcomes() {
        return new ArrayList<>(pastAppointments); 
    }
	public static MedicalRecord getRecordByPatientID(String patientID) {
        for (MedicalRecord record : allRecords) {
            if (record.patient.getPatientID().equals(patientID)) {
                return record;
            }
        }
        return null;  // null if no record found
    }

    
    public void viewMedicalRecord() {
        System.out.println("=== Medical Record ===");
        System.out.println("Patient ID: " + patient.getPatientID());
        System.out.println("Name: " + patient.getName());
        System.out.println("Date of Birth: " + patient.getDateOfBirth());
        System.out.println("Gender: " + patient.getGender());
        System.out.println("Blood Type: " + patient.getBloodType());
        System.out.println("Contact Info: " + patient.getContactInfo());
        System.out.println("Phone Number: " + patient.getPhoneNumber());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        System.out.println("\n--- Diagnoses History ---");
        if (diagnoses.isEmpty()) {
            System.out.println("N/A");
        } else {
            for (int i = 0; i < diagnoses.size(); i++) {
                String formattedTimestamp = entryTimestamps.get(i).format(formatter);
                System.out.println(formattedTimestamp + " - " + diagnoses.get(i));
            }
        }

        System.out.println("\n--- Treatments History ---");
        if (treatments.isEmpty()) {
            System.out.println("N/A");
        } else {
            for (int i = 0; i < treatments.size(); i++) {
                String formattedTimestamp = entryTimestamps.get(i).format(formatter);
                System.out.println(formattedTimestamp + " - " + treatments.get(i));
            }
        }

        System.out.println("\n--- Past Appointments ---");
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