import java.util.List;

public interface IAppointmentManager {
    void viewAppointments();
    void scheduleAppointment(Doctor doctor, TimeSlot timeSlot);
    void cancelAppointment(Appointment appointment, Doctor doctor);
    void rescheduleAppointment(Appointment appointment, TimeSlot newTimeSlot, Doctor doctor);
}
