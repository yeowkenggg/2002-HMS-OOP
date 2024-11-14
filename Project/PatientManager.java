import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that manages patient related operations
 */
public class PatientManager implements IPatientManager {

    private List<Patient> patientList;
    private IAppointmentManager appointmentManager;

    /**
     * Constructor for PatientManager
     * Construct a new patient list and assigns the appointment manager
     * @param appointmentManager the manager used for managing appointment-related operations
     */
    public PatientManager(AppointmentManager appointmentManager) {
        this.patientList = new ArrayList<>();
        this.appointmentManager = appointmentManager;
    }

    /**
     * set the appointment manager
     * @param am the manager to set
     */
    public void setAppointmentManager(IAppointmentManager am){
        this.appointmentManager = am;
    }
    
    /**
     * Display medical record of a patient
     * @param patient the patient which medical record is being retrieved
     */
    public void viewMedicalRecord(Patient patient) {
        MedicalRecord record = MedicalRecord.getRecordByPatientID(patient.getUserId());
        if (record != null) {
            record.viewMedicalRecord();
        } else {
            System.out.println("No medical record found for Patient ID: " + patient.getUserId());
        }
    }

    /**
     * update a contact information of a patient
     * @param patient the patient to be updated
     * @param newContactInfo the contact information of the patient (email)
     * @param phone the phone number of the patient
     */
    public void updateContactInfo(Patient patient, String newContactInfo, int phone) {
        if (newContactInfo != null && !newContactInfo.trim().isEmpty()) {
            patient.setContactInfo(newContactInfo);
            patient.setPhoneNumber(phone);
            System.out.println("Updated contact information.");
        } else {
            System.out.println("Invalid contact information.");
        }
    }
    
    /**
     * Retrieve a list of all patient
     * @param caller the caller must be either a doctor or administrator
     * @return a list of patient
     */
    public List<Patient> getAllPatients(Staff caller) { //
    if ("Doctor".equals(caller.getRole()) || "Administrator".equals(caller.getRole())) {
        return new ArrayList<>(patientList);
    } else {
        System.out.println("Access denied. Only doctors or administrators can access all patient records.");
        return Collections.emptyList();
    }
    }

    /**
     * retrieves a list of all patients (used internally, no restrictions)
     * @return a list of patients
     */
    public List<Patient> getAllPatientsInternal() {
        return patientList;
    }

    /**
     * Finds a patient based on its ID
     * @param patientID the ID of the patient to find
     * @return the patient
     */
    public Patient findPatientById(String patientID) {
        for (Patient patient : getAllPatientsInternal()) {
            if (patient.getPatientID().equals(patientID)) {
                return patient;
            }
        }
        return null;
    }
    
    /**
     * Adds a new patient to the patient list
     * @param patient the patient to be added
     */
    public void addPatient(Patient patient) {
        if (!patientList.contains(patient)) {
            patientList.add(patient);
            //System.out.println("Patient added: " + patient.getName() + " (ID: " + patient.getUserId() + ")");
        }
    }
    
}
