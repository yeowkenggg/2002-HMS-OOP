import java.util.List;

public interface IAppointmentManager {

    void setAppList(List<Appointment> allAppointments);

    void setDoctorManager(IDoctorManager doctorManager);

    void setPatientManager(IPatientManager patientManager);

    void cancelAppointment(Appointment appointment, User patient);

    void viewAppointments(Patient patient);

    void viewAppointmentOutcome(Patient patient);

    void rescheduleAppointment(Patient patient, Appointment appointment, TimeSlot newTimeSlot, Doctor doctor);

    void scheduleAppointment(Patient patient, Doctor doctor, TimeSlot timeSlot);

    void viewAvailableSlots(Doctor doctor);

    void acceptAppointment(Doctor doctor, Appointment appointment);

    void declineAppointment(Doctor doctor, Appointment appointment);

    void recordAppointmentOutcome(Doctor doctor, String patientID, String appointmentID, String services, String notes,
            Prescription prescription);

}
