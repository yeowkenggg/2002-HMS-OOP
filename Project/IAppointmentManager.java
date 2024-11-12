import java.util.List;

public interface IAppointmentManager {
    void scheduleAppointment(Patient patient, Doctor doctor, TimeSlot timeSlot);
    void rescheduleAppointment(Patient patient, Appointment appointment, TimeSlot newTimeSlot, Doctor doctor);
    void cancelAppointment(Appointment appointment, User caller);
    List<Appointment> getUpcomingAppointments(Patient patient);  
    List<Appointment> getPastAppointments(Patient patient);     
    Appointment getAppointmentDetails(Patient patient, String appointmentID);
    public void viewAvailableSlots(Doctor doctor);
    void viewAppointments(Patient patient);
    void viewAppointmentOutcome(Patient patient);
    void setAppList(List<Appointment> allAppointments);
    void setDoctorManager(IDoctorManager doctorManager);
    void setPatientManager(IPatientManager patientManager);
    void acceptAppointment(Doctor doctor, Appointment appointment);
    void declineAppointment(Doctor doctor, Appointment appointment);
    void recordAppointmentOutcome(Doctor doctor, String patientID, String appointmentID, String services, String notes,
            Prescription prescription);
}
