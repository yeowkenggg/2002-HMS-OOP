import java.time.LocalDate;
import java.util.List;

public interface IPatientService {
    void updateContactInfo(Patient patient, String newContactInfo);
    void updateName(Patient patient, String newName);
    void updateGender(Patient patient, String newGender);
    void updateDateOfBirth(Patient patient, LocalDate newDateOfBirth);

    void viewMedicalRecord(Patient patient);
    void viewAppointmentOutcome(Patient patient);
    void viewAppointments(Patient patient);
    void addPatient(Patient patient);
    void scheduleAppointment(Patient patient, DoctorService doctorService, Doctor doctor, TimeSlot timeSlot);
    void rescheduleAppointment(Patient patient, Appointment appointment, DoctorService doctorService, TimeSlot newTimeSlot, Doctor doctor);
    void cancelAppointment(Patient patient, Appointment appointment, DoctorService doctorService, Doctor doctor);
    List<Appointment> getUpcomingAppointments(Patient patient);  
    List<Appointment> getPastAppointments(Patient patient);     
    Appointment getAppointmentDetails(Patient patient, String appointmentID);

    void viewAvailableSlots(DoctorService doctorService, Doctor doctor);
}
