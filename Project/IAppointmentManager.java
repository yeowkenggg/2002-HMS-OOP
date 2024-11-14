import java.util.List;

/**
 * Interface class for mangaging of appointments
 */
public interface IAppointmentManager {

    /**
     * Sets the list of all appointment 
     * @param allAppointments list of all appointments
     */
    void setAppList(List<Appointment> allAppointments);

    /**
     * Sets the manager responsible for doctor-related operations.
     * @param doctorManager the doctormanager to set
     */
    void setDoctorManager(IDoctorManager doctorManager);

    /**
     * Sets the manager responsible for patient-related operations.
     * @param patientManager the patientmanager to set
     */
    void setPatientManager(IPatientManager patientManager);

    /**
     * Cancels a specified appointment
     * @param appointment the appointment to be canceled
     * @param user the user who requested for the cancellation
     */
    void cancelAppointment(Appointment appointment, User user);

    /**
     * Views all appointment for a patient
     * @param patient patient whose appointment to be retrieved
     */
    void viewAppointments(Patient patient);

    /**
     * Views all appointment outcome for a patient
     * @param patient the patient whose appointmentoutcome to be retrieved
     */
    void viewAppointmentOutcome(Patient patient);

    /**
     * reschedules an appointment to a new timeslot
     * @param patient the patient of the appointment
     * @param appointment the appointment to be rescheduled
     * @param newTimeSlot the new timeslot of the appointment
     * @param doctor the doctor of the appointment
     */
    void rescheduleAppointment(Patient patient, Appointment appointment, TimeSlot newTimeSlot, Doctor doctor);

    /**
     * schedules an appointment between a patient and doctor
     * @param patient the patient of the appointment
     * @param doctor the doctor of the appointment
     * @param timeSlot the timeslot of the appointment
     */
    void scheduleAppointment(Patient patient, Doctor doctor, TimeSlot timeSlot);

    /**
     * retrieves all available slots of the doctor
     * @param doctor the doctor whose available slots to be retrieved
     */
    void viewAvailableSlots(Doctor doctor);

    /**
     * accepts an appointment for a doctor
     * @param doctor doctor accepting the appointment
     * @param appointment the appointment to be accepted
     */
    void acceptAppointment(Doctor doctor, Appointment appointment);

    /**
     * declines an appointment for a doctor
     * @param doctor doctor declining the appointment
     * @param appointment the appointment to be declined
     */
    void declineAppointment(Doctor doctor, Appointment appointment);

    /**
     * records the outcome of an appointment
     * @param doctor the doctor who provided the information
     * @param patientID the patient associated to this outcome
     * @param appointmentID the appointment associated to this outcome
     * @param services the services provided to this outcome
     * @param notes the notes of this outcome
     * @param prescription the prescription issued to this outcome
     */
    void recordAppointmentOutcome(Doctor doctor, String patientID, String appointmentID, String services, String notes,
            Prescription prescription);

}
