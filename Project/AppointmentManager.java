import java.util.ArrayList;
import java.util.List;

public class AppointmentManager implements IAppointmentManager {

    private DoctorManager doctorManager;
    private PatientManager patientManager;
    private List<Appointment> allAppointments;


    public AppointmentManager(DoctorManager doctorManager, PatientManager patientManager,List<Appointment> allAppointments) {
        this.doctorManager = doctorManager;
        this.patientManager = patientManager;    
        this.allAppointments = allAppointments;
    }
    public void setDoctorManager(DoctorManager dm){
        this.doctorManager = dm;
    }

    public void setPatientManager(PatientManager pm){
        this.patientManager = pm;
    }

    public void setAppList(List<Appointment> app){
        this.allAppointments = app;
    }

    @Override
    public void viewAvailableSlots(Doctor doctor) {
        System.out.println("Available Slots for Dr. " + doctor.getName() + ":");
        List<TimeSlot> availableSlots = doctorManager.getAvailability(doctor);
        for (TimeSlot slot : availableSlots) {
            System.out.println(slot);
        }
    }

    @Override
    public void scheduleAppointment(Patient patient, Doctor doctor, TimeSlot timeSlot) {
    if (doctorManager.isAvailable(doctor, timeSlot)) {
        String appointmentID = "APT" + System.currentTimeMillis();
        Appointment appointment = new Appointment(appointmentID, patient.getUserId(), doctor.getUserId(), timeSlot, "Pending");

        patient.addAppointment(appointment);
        doctor.addAppointment(appointment);
        doctorManager.removeAvailability(doctor, timeSlot);  
        allAppointments.add(appointment);
        System.out.println("Scheduled appointment for " + patient.getName() + " with Dr. " + doctor.getName() + " at " + timeSlot);
    } else {
        System.out.println("Doctor is unavailable at the selected time slot.");
    }
}


    @Override
    public void rescheduleAppointment(Patient patient, Appointment appointment, TimeSlot newTimeSlot, Doctor doctor) {
        TimeSlot oldTimeSlot = appointment.getTimeSlot();
        if (doctorManager.isAvailable(doctor, newTimeSlot)) {
            appointment.setTimeSlot(newTimeSlot);
            appointment.setStatus("Rescheduled");
            doctorManager.setAvailability(doctor, oldTimeSlot);
            doctorManager.removeAvailability(doctor, newTimeSlot);
            System.out.println("Rescheduled appointment to " + newTimeSlot);
        } else {
            System.out.println("The selected slot is not available.");
        }
    }

    

    @Override
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


    @Override
    public List<Appointment> getPastAppointments(Patient patient) {
        List<Appointment> pastAppointments = new ArrayList<>();
        for (Appointment appointment : patient.getAppointments()) {
            if (appointment.isPast()) { 
                pastAppointments.add(appointment);
            }
        }
        return pastAppointments;
    }

    //method overloading
    public List<Appointment> getPastAppointments(Doctor doctor) {
        List<Appointment> pastAppointments = new ArrayList<>();
        for (Appointment appointment : doctor.getAppointments()) {
            if (appointment.isPast()) { 
                pastAppointments.add(appointment);
            }
        }
        return pastAppointments;
    }

    @Override
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
        if (appointment == null) {
            System.out.println("Error: Appointment ID " + appointmentID + " not found.");
            return;
        }
    
        if (!appointment.getDoctorID().equals(doctor.getUserId())) {
            System.out.println("Error: Unauthorized action. Doctor is not assigned to this appointment.");
            return;
        }
    
        if (appointment.getOutcome() != null) {
            System.out.println("Error: Appointment outcome already recorded for Appointment ID: " + appointmentID);
            return;
        }
    
        AppointmentOutcome outcome = new AppointmentOutcome(appointment, services, notes, prescription, appointment.getTimeSlot().getDate());
        appointment.setOutcome(outcome);
    
        MedicalRecord record = MedicalRecord.getRecordByPatientID(patientID);
        if (record != null) {
            record.addAppointmentOutcome(outcome);
            System.out.println("Patient record updated for Patient ID: " + patientID);
        } else {
            System.out.println("No medical record found for Patient ID: " + patientID + ", but appointment outcome was still recorded.");
        }
        System.out.println("Appointment outcome recorded for Appointment ID: " + appointmentID);
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
    
        appointment.setOutcome(null);
    
        MedicalRecord record = MedicalRecord.getRecordByPatientID(appointment.getPatientID());
        if (record != null) {
            record.removeAppointmentOutcome(outcome);
            System.out.println("Appointment outcome deleted from patient record for Appointment ID: " + appointmentID);
        }
    
        System.out.println("Appointment outcome deleted for Appointment ID: " + appointmentID);
    }

    
    @Override 
    public void viewAppointments(Patient patient) {
        for (Appointment appointment : patient.getAppointments()) {
            System.out.println(appointment);
        }
    }
    
    //Utilizing method overloading to do the same, but for doctor
    public void viewAppointments(Doctor doctor) {
        System.out.println("Scheduled Appointments for Doctor ID: " + doctor.getUserId());
        for (Appointment appointment : doctor.getAppointments()) {
            System.out.println(appointment);
        }
    }
    
    public void acceptAppointment(Doctor doctor, Appointment appointment) {
        if (appointment.getDoctorID().equals(doctor.getUserId())) {
            if (!doctor.getAppointments().contains(appointment)) {
                doctor.addAppointment(appointment);
                doctorManager.removeAvailability(doctor, appointment.getTimeSlot());
            }
            appointment.confirm();
            if (!allAppointments.contains(appointment)) {
                allAppointments.add(appointment); // Add to centralized list if not already present
            }
            System.out.println("Appointment " + appointment.getAppointmentID() + " accepted.");
            
            Patient patient = patientManager.findPatientById(appointment.getPatientID());
            if (patient != null) {
                if (!patient.getAppointments().contains(appointment)) {
                    patient.addAppointment(appointment);
                    System.out.println("Appointment added to Patient " + patient.getName() + "'s record.");
                } else {
                    System.out.println("Appointment already exists in the patient's record.");
                }
            } else {
                System.out.println("Patient with ID " + appointment.getPatientID() + " not found.");
            }
        } else {
            System.out.println("Access Denied: You are not authorized to accept this appointment.");
        }
    }

    public void declineAppointment(Doctor doctor, Appointment appointment) {
        if (appointment.getDoctorID().equals(doctor.getUserId()) && doctor.getAppointments().contains(appointment)) {
            appointment.setStatus("Declined");

            doctorManager.setAvailability(doctor, appointment.getTimeSlot());
            System.out.println("Appointment " + appointment.getAppointmentID() + " declined.");
        } else {
            System.out.println("Access Denied: You are not authorized to decline this appointment.");
        }
    }

    @Override
    // we need both patient and doctor as parameters to check if they are the correct person asking for the cancellation.
    // in implementation, we will get doctorID when its patient side, and vice versa.
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
                    doctorManager.setAvailability(doctor, appointment.getTimeSlot());
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
                    doctorManager.setAvailability(doctor, appointment.getTimeSlot());
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
    @Override
    public void viewAppointmentOutcome(Patient patient) {
        List<AppointmentOutcome> outcomes = getOutcomesByPatientID(patient.getUserId());
        for (AppointmentOutcome outcome : outcomes) {
            System.out.println(outcome);
        }
    }
}
