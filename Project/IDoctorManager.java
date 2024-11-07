import java.util.List;

public interface IDoctorManager {
    void viewPatientRecord(Doctor doctor, String PatientID);
    boolean isAvailable(Doctor doctor, TimeSlot timeSlot);
    List<TimeSlot> getAvailability(Doctor doctor);
    
    void assignPatient(Doctor doctor, String patientID);
    
    List<String> getAssignedPatientIDs(Doctor doctor);
}
