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
    private List<Diagnosis> diagnoses;
    private List<Treatment> treatments;
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
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
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

    public void removeAppointmentOutcome(AppointmentOutcome outcome) {
        if (pastAppointments.contains(outcome)) {
            pastAppointments.remove(outcome);
            System.out.println("Appointment outcome removed from medical record for Patient ID: " + patientID);
        } else {
            System.out.println("Outcome not found in the medical record for Patient ID: " + patientID);
        }
    }

    public void addDiagnosis(Diagnosis diagnosis) {
        diagnoses.add(diagnosis);
        System.out.println("Diagnosis added to medical record for Patient ID: " + patientID);
    }

    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
        System.out.println("Treatment added to medical record for Patient ID: " + patientID);
    }

    public void removeDiagnosis(String diagnosisID) {
        diagnoses.removeIf(diagnosis -> diagnosis.getDiagnosisID().equals(diagnosisID));
        System.out.println("Diagnosis with ID " + diagnosisID + " removed from medical record for Patient ID: " + patientID);
    }

    public void removeTreatment(String treatmentID) {
        treatments.removeIf(treatment -> treatment.getTreatmentID().equals(treatmentID));
        System.out.println("Treatment with ID " + treatmentID + " removed from medical record for Patient ID: " + patientID);
    }

    public List<AppointmentOutcome> getAppointmentOutcomes() {
        return new ArrayList<>(pastAppointments); 
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
        System.out.println("--- Diagnoses ---");
        if (diagnoses.isEmpty()) {
            System.out.println("N/A");
        } else {
            for (Diagnosis diagnosis : diagnoses) {
                System.out.println(diagnosis);
            }
        }
        System.out.println("\n--- Treatments ---");
        if (treatments.isEmpty()) {
            System.out.println("N/A");
        } else {
            for (Treatment treatment : treatments) {
                System.out.println(treatment);
            }
        }
        System.out.println("");
    }


}