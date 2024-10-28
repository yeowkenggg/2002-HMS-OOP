import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PatientService implements IPatientService {

    private List<Patient> patientList;

    // Constructor to initialize the patient list
    public PatientService() {
        this.patientList = new ArrayList<>();
    }

    @Override
    public void viewMedicalRecord(Patient patient) {
        MedicalRecord record = MedicalRecord.getRecordByPatientID(patient.getUserId());
        if (record != null) {
            record.viewMedicalRecord();
        } else {
            System.out.println("No medical record found for Patient ID: " + patient.getUserId());
        }
    }

    @Override
    public void updateContactInfo(Patient patient, String newContactInfo) {
        if (newContactInfo != null && !newContactInfo.trim().isEmpty()) {
            patient.setContactInfo(newContactInfo);
            MedicalRecord record = MedicalRecord.getRecordByPatientID(patient.getUserId());
            if (record != null) {
                record.setContactInfo(newContactInfo);
                System.out.println("Updated contact info to: " + newContactInfo);
            }
        } else {
            System.out.println("Invalid contact information.");
        }
    }

    @Override
    public void updateName(Patient patient, String newName) {
        if (newName != null && !newName.trim().isEmpty()) {
            patient.setName(newName);
            MedicalRecord record = MedicalRecord.getRecordByPatientID(patient.getUserId());
            if (record != null) {
                record.setName(newName);
                System.out.println("Name updated to: " + newName);
            }
        } else {
            System.out.println("Name cannot be empty.");
        }
    }

    @Override
    public void updateGender(Patient patient, String newGender) {
        if ("Male".equalsIgnoreCase(newGender) || "Female".equalsIgnoreCase(newGender)) {
            patient.setGender(newGender);
            MedicalRecord record = MedicalRecord.getRecordByPatientID(patient.getUserId());
            if (record != null) {
                record.setGender(newGender);
                System.out.println("Gender updated to: " + newGender);
            }
        } else {
            System.out.println("Invalid gender input.");
        }
    }

    @Override
    public void updateDateOfBirth(Patient patient, LocalDate newDateOfBirth) {
        if (newDateOfBirth != null) {
            patient.setDateOfBirth(newDateOfBirth);
            MedicalRecord record = MedicalRecord.getRecordByPatientID(patient.getUserId());
            if (record != null) {
                record.setDateOfBirth(newDateOfBirth);
                System.out.println("Date of birth updated to: " + newDateOfBirth);
            }
        } else {
            System.out.println("Invalid date of birth.");
        }
    }
    
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientList); // Returning a copy to avoid modification of the original list
    }

    public Patient findPatientById(String patientID) {
        for (Patient patient : getAllPatients()) {
            if (patient.getPatientID().equals(patientID)) {
                return patient;
            }
        }
        return null;
    }

    @Override
    public void viewAvailableSlots(DoctorService doctorService, Doctor doctor) {
        System.out.println("Available Slots for Dr. " + doctor.getName() + ":");
        List<TimeSlot> availableSlots = doctorService.getAvailability(doctor);
        for (TimeSlot slot : availableSlots) {
            System.out.println(slot);
        }
    }

    @Override
    public void scheduleAppointment(Patient patient, DoctorService doctorService, Doctor doctor, TimeSlot timeSlot) {
        if (doctorService.isAvailable(doctor, timeSlot)) {
            String appointmentID = "APT" + System.currentTimeMillis();
            Appointment appointment = new Appointment(appointmentID, patient.getUserId(), doctor.getUserId(), timeSlot, "Pending");
            patient.addAppointment(appointment);
            doctorService.addAppointment(doctor, appointment);
            System.out.println("Scheduled appointment for " + patient.getName() + " with Dr. " + doctor.getName() + " at " + timeSlot);
        } else {
            System.out.println("Doctor is unavailable at the selected time slot.");
        }
    }

    @Override
    public void rescheduleAppointment(Patient patient, Appointment appointment, DoctorService doctorService, TimeSlot newTimeSlot, Doctor doctor) {
        if (doctorService.isAvailable(doctor, newTimeSlot)) {
            appointment.setTimeSlot(newTimeSlot);
            appointment.setStatus("Rescheduled");
            System.out.println("Rescheduled appointment to " + newTimeSlot);
        } else {
            System.out.println("The selected slot is not available.");
        }
    }

    @Override
    public void cancelAppointment(Patient patient, Appointment appointment, DoctorService doctorService, Doctor doctor) {
        if (patient.getAppointments().contains(appointment)) {
            patient.removeAppointment(appointment);
            doctorService.cancelAppointment(doctor, appointment);
            System.out.println("Appointment with ID " + appointment.getAppointmentID() + " has been canceled.");
        } else {
            System.out.println("Appointment not found for the patient.");
        }
    }
    
    @Override
    public void addPatient(Patient patient) {
        if (!patientList.contains(patient)) {
            patientList.add(patient);
            System.out.println("Patient added: " + patient.getName() + " (ID: " + patient.getUserId() + ")");
        }
    }

    @Override
    public void viewAppointments(Patient patient) {
        for (Appointment appointment : patient.getAppointments()) {
            System.out.println(appointment);
        }
    }


    @Override
    public List<Appointment> getUpcomingAppointments(Patient patient) {
        List<Appointment> pastAppointments = new ArrayList<>();
        for (Appointment appointment : patient.getAppointments()) {
            if (appointment.isUpcoming()) { 
                pastAppointments.add(appointment);
            }
        }
        return pastAppointments;
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

@Override
public Appointment getAppointmentDetails(Patient patient, String appointmentID) {
    for (Appointment appointment : patient.getAppointments()) {
        if (appointment.getAppointmentID().equals(appointmentID)) {
            return appointment;
        }
    }
    return null; 
}

    @Override
    public void viewAppointmentOutcome(Patient patient) {
        List<AppointmentOutcome> outcomes = AppointmentOutcome.getOutcomesByPatientID(patient.getUserId());
        for (AppointmentOutcome outcome : outcomes) {
            System.out.println(outcome);
        }
    }
}
