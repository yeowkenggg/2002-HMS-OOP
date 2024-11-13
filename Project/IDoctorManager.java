import java.util.List;

public interface IDoctorManager {

    Doctor findDoctorById(String doctorID);

    List<TimeSlot> getAvailability(Doctor doctor);

    boolean isAvailable(Doctor doctor, TimeSlot timeSlot);

    void viewPatientRecord(Doctor doctor, String patientID);

    void setStaffManager(IStaffManager staffManager);

    void setPrescriptionManager(IPrescriptionManager prescriptionManager);

    void addDiagnosis(String selectedPatientID, String diagnosisID, String details);

    void addTreatment(String selectedPatientID, String treatmentID, String details);

    void addPrescription(String selectedPatientID, String prescriptionID, IMedicineManager medicineManager);

    List<Doctor> getAllDoctors();

}
