import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Patient extends User implements IUser, IAppointmentManager {
    
    private String patientID;
    private LocalDate dateOfBirth;
    private String bloodType;
    private String contactInfo;
    private MedicalRecord medicalRecord;
    private List<Appointment> appointments; 

    // Constructor
    public Patient(String userId, String password, String name, String gender, LocalDate dateOfBirth, String bloodType, String contactInfo) {
        super(userId, password, name, gender);
        this.patientID = userId;
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
        this.appointments = new ArrayList<>();
        this.medicalRecord = new MedicalRecord(userId, name, dateOfBirth, gender, bloodType, contactInfo);
        MedicalRecord.addRecord(medicalRecord);
    }

    // Schedule an appointment with a doctor
    public void scheduleAppointment(Doctor doctor, TimeSlot timeSlot) {
        if (doctor.isAvailable(timeSlot)) {
            String requestID = "R" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMHHmmss"));
            Appointment appointment = new Appointment(requestID, this.patientID, doctor.getUserId(), timeSlot, "Pending");
            appointments.add(appointment);
            doctor.addAppointment(appointment);
            System.out.println("Appointment scheduled with Dr. " + doctor.getName() + " on " + timeSlot);
        } else {
            System.out.println("The doctor is not available for the selected time slot.");
        }
    }

    // View all scheduled appointments
    public void viewAppointments() {
        System.out.println("\nScheduled Appointments:");
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    // Cancel an appointment
    public void cancelAppointment(Appointment appointment, Doctor doctor) {
        if (appointments.contains(appointment)) {
            appointment.cancel();
            appointments.remove(appointment);
            System.out.println("Appointment with ID " + appointment.getAppointmentID() + " has been canceled.");

            if (doctor != null) {
                doctor.cancelAppointment(appointment);
            }
        } else {
            System.out.println("No such appointment found.");
        }
    }

    // View outcomes of past appointments
    public void viewAppointmentOutcome() {
        List<AppointmentOutcome> outcomes = AppointmentOutcome.getOutcomesByPatientID(this.getUserId());
        if (outcomes.isEmpty()) {
            System.out.println("No appointment outcomes available.");
        } else {
            for (AppointmentOutcome outcome : outcomes) {
                System.out.println(outcome);
            }
        }
    }

    // Update contact information
    public void updateContactInfo(String newContactInfo) {
        if (!newContactInfo.isEmpty()) {
            this.contactInfo = newContactInfo;
            medicalRecord.setContactInfo(newContactInfo);
            System.out.println("Contact information updated successfully to: " + contactInfo);
        } else {
            System.out.println("Invalid input. Contact information not updated.");
        }
    }

    // Update name
    public void updateName(String newName) {
        if (!newName.isEmpty()) {
            this.setName(newName);
            medicalRecord.setName(newName);
            System.out.println("Name updated to: " + newName);
        } else {
            System.out.println("Name cannot be empty.");
        }
    }

    // Update gender
    public void updateGender(String newGender) {
        if (newGender.equalsIgnoreCase("Male") || newGender.equalsIgnoreCase("Female")) {
            this.setGender(newGender);
            medicalRecord.setGender(newGender);
            System.out.println("Gender updated to: " + newGender);
        } else {
            System.out.println("Invalid gender input.");
        }
    }

    // Update date of birth
    public void updateDateOfBirth(LocalDate newDateOfBirth) {
        this.dateOfBirth = newDateOfBirth;
        medicalRecord.setDateOfBirth(newDateOfBirth);
        System.out.println("Date of birth updated to: " + newDateOfBirth);
    }

    // View medical record
    public void viewMedicalRecord() {
        medicalRecord.viewMedicalRecord();
    }

    // View available slots for a doctor
    public void viewAvailableSlots(Doctor doctor) {
        System.out.println("Available Appointment Slots for Dr. " + doctor.getName() + ":");
        for (TimeSlot slot : doctor.getAvailability()) {
            if (doctor.isAvailable(slot)) {
                System.out.println(slot);
            }
        }
    }

    // Reschedule an appointment
    public void rescheduleAppointment(Appointment appointment, TimeSlot newTimeSlot, Doctor doctor) {
        if (appointments.contains(appointment)) {
            if (doctor.isAvailable(newTimeSlot)) {
                appointment.setStatus("Rescheduled");
                appointments.remove(appointment);

                Appointment newAppointment = new Appointment(
                    appointment.getAppointmentID(),
                    appointment.getPatientID(),
                    appointment.getDoctorID(),
                    newTimeSlot,
                    "Confirmed"
                );
                appointments.add(newAppointment);
                doctor.addAppointment(newAppointment);

                System.out.println("Appointment rescheduled successfully to " + newTimeSlot);
            } else {
                System.out.println("The selected time slot is not available for rescheduling.");
            }
        } else {
            System.out.println("No such appointment found to reschedule.");
        }
    }
    
    // Override display menu method for patient-specific menu
    @Override
    public void displayMenu() {
        if (isLoggedIn()) {
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Logout");
        } else {
            System.out.println("ERROR. PLEASE LOG IN! (Patient)");
        }
    }

    
    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }
}
