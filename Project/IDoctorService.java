import java.util.List;

public interface IDoctorService {
    void viewPatientRecord(Doctor doctor, String PatientID);
    void updatePatientRecord(Doctor doctor, String patientID, AppointmentOutcome outcome);
    void assignPatient(Doctor doctor, String patientID);

    void setAvailability(Doctor doctor, TimeSlot timeSlot);
    List<TimeSlot> getAvailability(Doctor doctor);
    
    void acceptAppointment(Doctor doctor, Appointment appointment);
    void declineAppointment(Doctor doctor, Appointment appointment);
    void cancelAppointment(Doctor doctor, Appointment appointment);
    boolean isAvailable(Doctor doctor, TimeSlot timeSlot);

    void viewAppointments(Doctor doctor);

    List<String> getAssignedPatientIDs(Doctor doctor);
}
