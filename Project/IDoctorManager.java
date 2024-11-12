import java.util.List;

public interface IDoctorManager {
    void viewPatientRecord(Doctor doctor, String PatientID);
    boolean isAvailable(Doctor doctor, TimeSlot timeSlot);
    List<TimeSlot> getAvailability(Doctor doctor);
    
    void assignPatient(Doctor doctor, String patientID);
    
    List<String> getAssignedPatientIDs(Doctor doctor);
    void setStaffManager(IStaffManager staffManager);
    void setPrescriptionManager(IPrescriptionManager prescriptionManager);
    Doctor findDoctorById(String string);
    void addDiagnosis(String selectedPatientID, String diagnosisID, String details);
    void addTreatment(String selectedPatientID, String treatmentID, String details);
    void addPrescription(String selectedPatientID, String prescriptionID, IMedicineManager medicineManager);
    List<Doctor> getAllDoctors();
}
