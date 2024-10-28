import java.time.LocalDate;
import java.util.List;

public interface IPatientService {
    void updateContactInfo(Patient patient, String newContactInfo);
    void updateName(Patient patient, String newName);
    void updateGender(Patient patient, String newGender);
    void updateDateOfBirth(Patient patient, LocalDate newDateOfBirth);

    void viewMedicalRecord(Patient patient);
    void viewAppointmentOutcome(Patient patient);
    
    void scheduleAppointment(Patient patient, Doctor doctor, TimeSlot timeSlot);
    void rescheduleAppointment(Patient patient, Appointment appointment, TimeSlot newTimeSlot, Doctor doctor);
    void cancelAppointment(Patient patient, Appointment appointment, Doctor doctor);
    void viewAppointments(Patient patient);
    List<Appointment> getUpcomingAppointments(Patient patient);  
    List<Appointment> getPastAppointments(Patient patient);     
    Appointment getAppointmentDetails(Patient patient, String appointmentID);

    void viewAvailableSlots(Doctor doctor);
}
