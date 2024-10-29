import java.util.List;

public interface IAppointmentManager {
    void scheduleAppointment(Patient patient, Doctor doctor, TimeSlot timeSlot);
    void rescheduleAppointment(Patient patient, Appointment appointment, TimeSlot newTimeSlot, Doctor doctor);
    void cancelAppointment(Doctor doctor, Patient patient, Appointment appointment);
    List<Appointment> getUpcomingAppointments(Patient patient);  
    List<Appointment> getPastAppointments(Patient patient);     
    Appointment getAppointmentDetails(Patient patient, String appointmentID);
    public void viewAvailableSlots(Doctor doctor);
    void viewAppointments(Patient patient);
    void viewAppointmentOutcome(Patient patient);
}
