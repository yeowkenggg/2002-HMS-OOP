import java.util.List;

/**
 * Interface for managing doctor-related operations
 */
public interface IDoctorManager {

    /**
     * Finds a doctor by their unique ID
     * @param doctorID the ID of the doctor to find
     * @return the doctor with the specified ID
     */
    Doctor findDoctorById(String doctorID);

    /**
     * Retrieves a list of available time slots for a doctor.
     * @param doctor the doctor whose availability is being retrieved
     * @return a list of available time slots for the doctor
     */
    List<TimeSlot> getAvailability(Doctor doctor);

    /**
     * Checks if a specified time slot is available for a doctor.
     * @param doctor the doctor whose availability is being checked
     * @param timeSlot the time slot to check
     * @return a boolean indicating if the timeslot is available
     */
    boolean isAvailable(Doctor doctor, TimeSlot timeSlot);

    /**
     * Views a patient's medical record
     * @param doctor the doctor requesting access to the patient's record
     * @param patientID the ID of the patient whose record is to be viewed
     */
    void viewPatientRecord(Doctor doctor, String patientID);

    /**
     * Sets the manager responsible for managing staff-related operations.
     * @param staffManager the staff manager to set
     */
    void setStaffManager(IStaffManager staffManager);

    /**
     * Sets the manager responsible for handling prescription-related operations.
     * @param prescriptionManager the prescription manager to set
     */
    void setPrescriptionManager(IPrescriptionManager prescriptionManager);

    /**
     * Adds a diagnosis to a patient's medical record.
     * @param selectedPatientID the ID of the patient to add the diagnosis to
     * @param diagnosisID the ID of the diagnosis
     * @param details the details of the diagnosis
     */
    void addDiagnosis(String selectedPatientID, String diagnosisID, String details);

    /**
     * Adds a treatment to a patient's medical record.
     * @param selectedPatientID the ID of the patient to add the treatment to
     * @param treatmentID the ID of the treatment
     * @param details the details of the treatment
     */
    void addTreatment(String selectedPatientID, String treatmentID, String details);

    /**
     * Adds a prescription to a patient's medical record.
     * @param selectedPatientID the ID of the patient to add the prescription to
     * @param prescriptionID the ID of the prescription
     * @param medicineManager the medicine manager responsible for handling medicine inventory
     */
    void addPrescription(String selectedPatientID, String prescriptionID, IMedicineManager medicineManager);

    /**
     * Retrieves a list of all doctors in the system.
     * @return a list of all doctors
     */
    List<Doctor> getAllDoctors();
}
