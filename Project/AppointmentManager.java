import java.util.ArrayList;
import java.util.List;

/**
 * AppointmentManager, logic for appointments
 */
public class AppointmentManager implements IAppointmentManager {

    private IDoctorManager doctorManager;
    private IPatientManager patientManager;
    private List<Appointment> allAppointments;

    /**
     * Constructor for appointment manager
     * @param doctorManager  manager responsible for doctor-related logic
     * @param patientManager manager responsible for patient-related logic
     * @param allAppointments a list of appointments
     */
    public AppointmentManager(IDoctorManager doctorManager, IPatientManager patientManager,List<Appointment> allAppointments) {
        this.doctorManager = doctorManager;
        this.patientManager = patientManager;    
        this.allAppointments = allAppointments;
    }

    /**
     * setting doctorManager (to prevent cyclic in Main)
     * @param dm set doctorManager
     */
    public void setDoctorManager(IDoctorManager dm){
        this.doctorManager = dm;
    }

    /**
     * setting patientManager (to prevent cyclic in Main)
     * @param pm set patientManager
     */
    public void setPatientManager(IPatientManager pm){
        this.patientManager = pm;
    }

    /**
     * setting allAppointment (to prevent cyclic in Main)
     * @param app set list
     */
    public void setAppList(List<Appointment> app){
        this.allAppointments = app;
    }

    /**
     * Outputs all available slots for a particular doctor based on the parameters
     * @param doctor specified doctor for this method to output his/her available slots
     */
    public void viewAvailableSlots(Doctor doctor) {
        System.out.println("Available Slots for Dr. " + doctor.getName() + ":");
        List<TimeSlot> availableSlots = doctorManager.getAvailability(doctor);
        for (TimeSlot slot : availableSlots) {
            System.out.println(slot);
        }
    }

    /**
     * Method to schedule an appointment between patient and doctor
     * @param patient indicating which patient is involved with the appointment
     * @param doctor indicating which doctor is involved with the appointment
     * @param timeSlot the time which the appointment is scheduled
     */
    public void scheduleAppointment(Patient patient, Doctor doctor, TimeSlot timeSlot) {
        if (doctorManager.isAvailable(doctor, timeSlot)) {
            String appointmentID = "APT" + System.currentTimeMillis();
            Appointment appointment = new Appointment(appointmentID, patient.getUserId(), doctor.getUserId(), timeSlot, "Pending");

            patient.addAppointment(appointment);
            doctor.addAppointment(appointment);
            doctor.removeAvailability(timeSlot);
            allAppointments.add(appointment);
            
        } else {
            System.out.println("Doctor is unavailable at the selected time slot.");
        }
    }

    /**
     * Method to reschedule an appointment between patient and doctor
     * @param patient indicating which patient is involved with the appointment
     * @param appointment indicating which appointment is involved with the rescheduling
     * @param newTimeSlot the chosen new timeslot to be rescheduled
     * @param doctor indicating which doctor is involved with the appointment
     */
    public void rescheduleAppointment(Patient patient, Appointment appointment, TimeSlot newTimeSlot, Doctor doctor) {
        TimeSlot oldTimeSlot = appointment.getTimeSlot();
        if (doctorManager.isAvailable(doctor, newTimeSlot)) {
            appointment.setTimeSlot(newTimeSlot); //set the appt to new
            doctor.addAvailability(oldTimeSlot); // add the old time to avail
            doctor.removeAvailability(newTimeSlot); // remove the new time from avail
        } else {
            System.out.println("The selected slot is not available.");
        }
    }

    /**
     * Retrieves a list of upcoming appointments for a specific patient
     * @param patient the patient whose upcoming appointments are to be retrieved 
     * @return a list of appointments that are scheduled for a future date and time
     */
    public List<Appointment> getUpcomingAppointments(Patient patient) {
        List<Appointment> upcomingAppointments  = new ArrayList<>();
        for (Appointment appointment : patient.getAppointments()) {
            if (appointment.isUpcoming()) { 
                upcomingAppointments .add(appointment);
            }
        }
        return upcomingAppointments ;
    }

    /**
     * Displays the upcoming appointments for a specified patient in the console
     * @param patient the patient whose upcoming appointments are to be retrieved 
     */
    public void viewUpcomingAppointments(Patient patient) {
        System.out.println("\nViewing Upcoming Appointments for Patient ID: " + patient.getUserId());
        List<Appointment> upcomingAppointments = getUpcomingAppointments(patient);
    
        if (upcomingAppointments.isEmpty()) {
            System.out.println("No upcoming appointments found for Patient ID: " + patient.getUserId());
        } else {
            for (Appointment appointment : upcomingAppointments) {
                System.out.println("Appointment ID: " + appointment.getPatientID() + 
                                   ", Doctor ID: " + appointment.getDoctorID() + 
                                   ", Time: " + appointment.getTimeSlot().getDate() + " @ " + appointment.getTimeSlot().getTime() + 
                                   ", Status: " + appointment.getStatus());
            }
        }
    }

    /**
     * Retrieves a list of upcoming appointments for a specific doctor
     * @param doctor the doctor whose upcoming appointments are to be retrieved 
     * @return a list of appointments that are scheduled for a future date and time
     */
    //method overloading
    public List<Appointment> getUpcomingAppointments(Doctor doctor) {
        List<Appointment> upcomingAppointments  = new ArrayList<>();
        for (Appointment appointment : doctor.getAppointments()) {
            if (appointment.isUpcoming()) { 
                upcomingAppointments .add(appointment);
            }
        }
        return upcomingAppointments ;
    }

    /**
     * Displays the upcoming appointments for a specified doctor in the console
     * @param doctor the doctor whose upcoming appointments are to be retrieved 
     */
    public void viewUpcomingAppointments(Doctor doctor) {
        System.out.println("\nViewing Upcoming Appointments for Doctor ID: " + doctor.getUserId());
        List<Appointment> upcomingAppointments = getUpcomingAppointments(doctor);
    
        if (upcomingAppointments.isEmpty()) {
            System.out.println("No upcoming appointments found for Doctor ID: " + doctor.getUserId());
        } else {
            for (Appointment appointment : upcomingAppointments) {
                System.out.println("Appointment ID: " + appointment.getPatientID() + 
                                   ", Doctor ID: " + appointment.getDoctorID() + 
                                   ", Time: " + appointment.getTimeSlot().getDate() + " @ " + appointment.getTimeSlot().getTime() + 
                                   ", Status: " + appointment.getStatus());
            }
        }
    }

    /**
     * Retrieves a list of past appointments for a specific patient
     * @param patient the patient whose upcoming appointments are to be retrieved 
     * @return a list of appointments that are scheduled for a past date and time
     */
    public List<Appointment> getPastAppointments(Patient patient) {
        List<Appointment> pastAppointments = new ArrayList<>();
        for (Appointment appointment : patient.getAppointments()) {
            if (appointment.isPast() || "Completed".equalsIgnoreCase(appointment.getStatus())) {
                pastAppointments.add(appointment);
            }
        }

        if (pastAppointments.isEmpty()) {
            System.out.println("No available past appointments for the patient.");
        }
        return pastAppointments;
    }

    /**
     * Retrieves a list of past appointments for a specific doctor
     * @param doctor the doctor whose upcoming appointments are to be retrieved 
     * @return a list of appointments that are scheduled for a past date and time
     */
    //method overloading
    public List<Appointment> getPastAppointments(Doctor doctor) {
        List<Appointment> pastAppointments = new ArrayList<>();
        for (Appointment appointment : doctor.getAppointments()) {
            if (appointment.isPast() || "Completed".equalsIgnoreCase(appointment.getStatus())) { 
                pastAppointments.add(appointment);
            }
        }

        if (pastAppointments.isEmpty()) {
            System.out.println("No available past appointments for the patient.");
        }
        return pastAppointments;
    }

    /**
     * Retrieves appointment details
     * @param patient the patient whose appointments are to be retrieved 
     * @param appointmentID the appointment that is specified for retrival 
     * @return the appointment
     */
    public Appointment getAppointmentDetails(Patient patient, String appointmentID) {
        for (Appointment appointment : patient.getAppointments()) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                return appointment;
            }
        }

        
        return null; 
    }

    /**
     * Method to record a new appointment outcome for an appointment
     * This allows the doctor to record the outcome of a patient's appointment
     * @param doctor the doctor who wrote the appointment outcome
     * @param patientID patient associated to this appointment outcome
     * @param appointmentID the appointment associated to this appointment outcome
     * @param services a description of the services provided during the appointment
     * @param notes additional notes regarding the outcome
     * @param prescription prescription issued for the appointment
     */
    public void recordAppointmentOutcome(Doctor doctor, String patientID, String appointmentID, String services, String notes, Prescription prescription) {
        Appointment appointment = findAppointmentById(appointmentID);
        if (appointment == null || !appointment.getDoctorID().equals(doctor.getUserId())) {
            System.out.println("Error: Invalid appointment or unauthorized access.");
            return;
        }
        
        Patient patient = patientManager.findPatientById(patientID);
        if (patient != null) {
            appointment.recordOutcome(services, notes, prescription, patient);
        } else {
            System.out.println("Error: Patient not found.");
        }
    }
    
    /**
     * Finds an appointment based on the appointmentID
     * @param appointmentID specified appointmentID to find
     * @return the appointment with the specified appointmentID
     */
    public Appointment findAppointmentById(String appointmentID) {
        for (Appointment appointment : allAppointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                return appointment;
            }
        }
        System.out.println("Error: Appointment with ID " + appointmentID + " not found.");
        return null;
    }
    
   /**
    * Views all appointment for a specific patient
    * @param patient the patient appointment to be viewed
    */
    public void viewAppointments(Patient patient) {
        List<Appointment> appointments = patient.getAppointments();

        if (appointments.isEmpty()) {
            System.out.println("No available appointments for the patient.");
        } else {
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
    }
    
    /**
     Views all appointment for a specific doctor
    * @param doctor the doctor appointment to be viewed
     */
    //Utilizing method overloading to do the same, but for doctor
    public void viewAppointments(Doctor doctor) {
        List<Appointment> appointments = doctor.getAppointments();
        System.out.println("Scheduled Appointments for Doctor ID: " + doctor.getUserId());
        if (appointments.isEmpty()) {
            System.out.println("No available appointments for the patient.");
        } else {
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
    }
    
   /**
     * Accepts a specified appointment for a doctor and adding it to the doctor and patient records.
     * @param doctor the doctor accepting the appointment
     * @param appointment the appointment to be accepted
     */
    public void acceptAppointment(Doctor doctor, Appointment appointment) {
        if (appointment.getDoctorID().equals(doctor.getUserId())) {
            if (!doctor.getAppointments().contains(appointment)) {
                doctor.removeAvailability(appointment.getTimeSlot());
            }
            appointment.confirm();
            if (!allAppointments.contains(appointment)) {
                allAppointments.add(appointment); 
            }
            System.out.println("Appointment " + appointment.getAppointmentID() + " accepted.");
            
            Patient patient = patientManager.findPatientById(appointment.getPatientID());
            if (patient != null) {
                if (!patient.getAppointments().contains(appointment)) {
                    patient.addAppointment(appointment);
                    System.out.println("Appointment added to Patient " + patient.getName() + "'s record.");
                }
                
            } else {
                System.out.println("Patient with ID " + appointment.getPatientID() + " not found.");
            }
            doctor.addAssignedPatientID(patient.getUserId());
        } else {
            System.out.println("Access Denied: You are not authorized to accept this appointment.");
        }
    }

    /**
     * Declines a specified appointment for a doctor and adding it to the doctor and patient records.
     * @param doctor the doctor declining the appointment
     * @param appointment the appointment to be declined
     */
    public void declineAppointment(Doctor doctor, Appointment appointment) {
        if (appointment.getDoctorID().equals(doctor.getUserId()) && doctor.getAppointments().contains(appointment)) {
            appointment.setStatus("Declined");

            doctor.addAvailability(appointment.getTimeSlot());
            System.out.println("Appointment " + appointment.getAppointmentID() + " declined.");
        } else {
            System.out.println("Access Denied: You are not authorized to decline this appointment.");
        }
    }

    /**
     * Cancels a specified appointment and removing it to the doctor and patient records.
     * @param appointment the appointment to be canceled
     * @param caller the user who requested the cancellation (doctor or patient)
     */
    public void cancelAppointment(Appointment appointment, User caller) {
        if (caller instanceof Doctor) {
            Doctor doctor = (Doctor) caller;
            if (appointment.getDoctorID().equals(doctor.getUserId())) {
                Patient patient = patientManager.findPatientById(appointment.getPatientID());
                if (patient != null) {
                    // Cancel appointment for both doctor and patient
                    appointment.cancel();
                    patient.removeAppointment(appointment);
                    doctor.removeAppointment(appointment);
                    doctor.addAvailability(appointment.getTimeSlot());
                    System.out.println("Appointment with ID " + appointment.getAppointmentID() + " has been canceled.");
                }
            }
        } else if (caller instanceof Patient) {
            Patient patient = (Patient) caller;
            if (appointment.getPatientID().equals(patient.getUserId())) {
                Doctor doctor = doctorManager.findDoctorById(appointment.getDoctorID());
                if (doctor != null) {
                    // Cancel appointment for both patient and doctor
                    appointment.cancel();
                    patient.removeAppointment(appointment);
                    doctor.removeAppointment(appointment);
                    doctor.addAvailability(appointment.getTimeSlot());
                    System.out.println("Appointment with ID " + appointment.getAppointmentID() + " has been canceled.");
                }
            }
        }
    }

    
    /**
     * Views all appointment outcomes in the system, accessible only to pharmacists.
     * 
     * @param caller the staff member requesting access to view all outcomes
     */
    public void viewAllAppointmentOutcome(Staff caller) {
        // Ensure only pharmacists can view all outcomes
        if (caller == null || !"Pharmacist".equalsIgnoreCase(caller.getRole())) {
            System.out.println("Access denied. Only pharmacists are allowed to view all appointment outcomes.");
            return;
        }
    
        System.out.println("=== Viewing All Appointment Outcomes ===");
        for (Appointment appointment : allAppointments) {
            AppointmentOutcome outcome = appointment.getOutcome();
    
            if (outcome != null) {
                System.out.println("Appointment ID: " + appointment.getAppointmentID() + ", Outcome: ");
                System.out.println(outcome.getDetails());
            } else {
                System.out.println("Appointment ID: " + appointment.getAppointmentID() + " has no recorded outcome.");
            }
        }
    }
    
    /**
     * Retrieves a list of appointment outcomes based on their patient ID.
     * 
     * @param patientID the ID of the patient
     * @return a list of all recorded outcomes for the specified patient
     */
    public List<AppointmentOutcome> getOutcomesByPatientID(String patientID) {
        List<AppointmentOutcome> outcomes = new ArrayList<>();
        for (Appointment appointment : allAppointments) {
            if (appointment.getPatientID().equals(patientID) && appointment.getOutcome() != null) {
                outcomes.add(appointment.getOutcome());
            }
        }
        return outcomes;
    }
    
    /**
     * Views all appointment outcomes for a specific patient.
     * 
     * @param patient the patient which appointment outcomes are to be viewed
     */
    public void viewAppointmentOutcome(Patient patient) {
        List<AppointmentOutcome> outcomes = getOutcomesByPatientID(patient.getUserId());
        if (outcomes.isEmpty()) {
            System.out.println("No available appointments for the patient.");
        }
        else{
            for (AppointmentOutcome outcome : outcomes) {
            System.out.println(outcome);
            }
        }
    }
    
}
