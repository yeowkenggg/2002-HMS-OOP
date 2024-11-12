import java.util.ArrayList;
import java.util.List;

public class AppointmentManager implements IAppointmentManager {

    private IDoctorManager doctorManager;
    private IPatientManager patientManager;
    private List<Appointment> allAppointments;


    public AppointmentManager(IDoctorManager doctorManager, IPatientManager patientManager,List<Appointment> allAppointments) {
        this.doctorManager = doctorManager;
        this.patientManager = patientManager;    
        this.allAppointments = allAppointments;
    }
    public void setDoctorManager(IDoctorManager dm){
        this.doctorManager = dm;
    }

    public void setPatientManager(IPatientManager pm){
        this.patientManager = pm;
    }

    public void setAppList(List<Appointment> app){
        this.allAppointments = app;
    }

    public void viewAvailableSlots(Doctor doctor) {
        System.out.println("Available Slots for Dr. " + doctor.getName() + ":");
        List<TimeSlot> availableSlots = doctorManager.getAvailability(doctor);
        for (TimeSlot slot : availableSlots) {
            System.out.println(slot);
        }
    }

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

    
    public List<Appointment> getUpcomingAppointments(Patient patient) {
        List<Appointment> upcomingAppointments  = new ArrayList<>();
        for (Appointment appointment : patient.getAppointments()) {
            if (appointment.isUpcoming()) { 
                upcomingAppointments .add(appointment);
            }
        }
        return upcomingAppointments ;
    }

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

    public Appointment getAppointmentDetails(Patient patient, String appointmentID) {
        for (Appointment appointment : patient.getAppointments()) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                return appointment;
            }
        }

        
        return null; 
    }

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
    
    
    
    

    public AppointmentManager(List<Appointment> allAppointments) {
        this.allAppointments = allAppointments;
    }

    public Appointment findAppointmentById(String appointmentID) {
        for (Appointment appointment : allAppointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                return appointment;
            }
        }
        System.out.println("Error: Appointment with ID " + appointmentID + " not found.");
        return null;
    }
    
    public void modifyAppointmentOutcome(Doctor doctor, String appointmentID, String newServices, String newNotes, Prescription newPrescription) {
        Appointment appointment = findAppointmentById(appointmentID);

        if (appointment == null) {
            System.out.println("Error: Appointment ID " + appointmentID + " not found.");
            return;
        }

        if (!appointment.getDoctorID().equals(doctor.getUserId())) {
            System.out.println("Error: Unauthorized action. Doctor is not assigned to this appointment.");
            return;
        }

        AppointmentOutcome outcome = appointment.getOutcome();
        if (outcome == null) {
            System.out.println("Error: No existing outcome for Appointment ID " + appointmentID);
            return;
        }

        outcome.setServices(newServices);
        outcome.setNotes(newNotes);
        outcome.setPrescription(newPrescription);

        System.out.println("Appointment outcome modified for Appointment ID: " + appointmentID);
    }

    public void deleteAppointmentOutcome(Doctor doctor, String appointmentID) {
        Appointment appointment = findAppointmentById(appointmentID);
    
        if (appointment == null) {
            System.out.println("Error: Appointment ID " + appointmentID + " not found.");
            return;
        }
    
        if (!appointment.getDoctorID().equals(doctor.getUserId())) {
            System.out.println("Error: Unauthorized action. Doctor is not assigned to this appointment.");
            return;
        }
    
        AppointmentOutcome outcome = appointment.getOutcome();
        if (outcome == null) {
            System.out.println("Error: No existing outcome to delete for Appointment ID " + appointmentID);
            return;
        }
    
        appointment.recordOutcome(null, null, null, null);
    
        MedicalRecord record = MedicalRecord.getRecordByPatientID(appointment.getPatientID());
        if (record != null) {
            record.removeAppointmentOutcome(outcome);
            System.out.println("Appointment outcome deleted from patient record for Appointment ID: " + appointmentID);
        }
    
        System.out.println("Appointment outcome deleted for Appointment ID: " + appointmentID);
    }

    
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

    public void declineAppointment(Doctor doctor, Appointment appointment) {
        if (appointment.getDoctorID().equals(doctor.getUserId()) && doctor.getAppointments().contains(appointment)) {
            appointment.setStatus("Declined");

            doctor.addAvailability(appointment.getTimeSlot());
            System.out.println("Appointment " + appointment.getAppointmentID() + " declined.");
        } else {
            System.out.println("Access Denied: You are not authorized to decline this appointment.");
        }
    }

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
    

    private List<Appointment> getAllAppointments() {
        return new ArrayList<>(allAppointments); 
    }

    public List<AppointmentOutcome> getOutcomesByPatientID(String patientID) {
        List<AppointmentOutcome> outcomes = new ArrayList<>();
        for (Appointment appointment : allAppointments) {
            if (appointment.getPatientID().equals(patientID) && appointment.getOutcome() != null) {
                outcomes.add(appointment.getOutcome());
            }
        }
        return outcomes;
    }
    
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
