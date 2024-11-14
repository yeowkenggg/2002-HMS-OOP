import java.time.LocalDate;
import java.util.Collection;

/**
 * An interface class managing patient related operations
 */
public interface IPatientManager {

    /**
     * Adds a new patient to the system
     * @param newPatient the patient to be added
     */
    void addPatient(Patient newPatient);

    /**
     * Finds a patient based on their ID
     * @param patientID the ID of patient to be found
     * @return the patient with the specified ID
     */
    Patient findPatientById(String patientID);

    /**
     * set method for manager responsible for managing patient appointments
     * @param appointmentManager the appointment manager to be set
     */
    void setAppointmentManager(IAppointmentManager appointmentManager);

    /**
     * Retrieves a collection of all patient for internal use
     * @return a collection of all patient
     */
    Collection<? extends User> getAllPatientsInternal();

    /**
     * Updates a patient contact information
     * @param patient the patient to be updated
     * @param newContactInfo the new contact information to be updated
     * @param phone the phone number to be updated
     */
    void updateContactInfo(Patient patient, String newContactInfo, int phone);

    /**
     * View the medical record for the patient
     * @param patient the patient who record is being retrieved
     */
    void viewMedicalRecord(Patient patient);
}
