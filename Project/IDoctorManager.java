import java.util.List;

public interface IDoctorManager {
    void viewPatientRecord(Doctor doctor, String PatientID);
    void updatePatientRecord(Doctor doctor, String patientID, AppointmentOutcome outcome);

    boolean isAvailable(Doctor doctor, TimeSlot timeSlot);
    void setAvailability(Doctor doctor, TimeSlot timeSlot);
    List<TimeSlot> getAvailability(Doctor doctor);
    
    void addAvailability(Doctor doctor, TimeSlot timeSlot);
    void assignPatient(Doctor doctor, String patientID);
    
    List<String> getAssignedPatientIDs(Doctor doctor);
}
