import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Patient extends User implements IUser {

    private String patientID;
    private LocalDate dateOfBirth;
    private String bloodType;
    private String contactInfo;
    private List<Appointment> appointments;
    private MedicalRecord medicalRecord;
    private PatientManager patientManager;
    private AppointmentManager appointmentManager;


    // constructor
    public Patient(String userId, String password, String name, String gender, LocalDate dateOfBirth, String bloodType, String contactInfo, PatientManager patientManager, AppointmentManager appointmentManager) {
        super(userId, password, name, gender);
        this.patientID = userId;
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
        this.appointments = new ArrayList<>();
        this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        
        this.medicalRecord = new MedicalRecord(userId, name, dateOfBirth, gender, bloodType, contactInfo);
        MedicalRecord.addRecord(this.medicalRecord);
    }

    // Getters and Setters
    public String getPatientID() {
        return patientID;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    ////////////////////
    public void viewMedicalRecord(){
        patientManager.viewMedicalRecord(this);
    }
    
    public void updateContactInfo(String newContactInfo) {
        patientManager.updateContactInfo(this, newContactInfo);
    }

    public void viewAvailableAppointmentSlots(Doctor doctor) {
        appointmentManager.viewAvailableSlots(doctor);
    }

    public void scheduleAppointment(Doctor doctor, TimeSlot timeSlot) {
        appointmentManager.scheduleAppointment(this, doctor, timeSlot);
    }

    public void rescheduleAppointment(Appointment appointment, TimeSlot newTimeSlot, Doctor doctor) {
        appointmentManager.rescheduleAppointment(this, appointment, newTimeSlot, doctor);
    }

    public void cancelAppointment(Appointment appointment, Doctor doctor) {
        appointmentManager.cancelAppointment(doctor, this, appointment);
    }

    public void viewScheduledAppointments() {
        appointmentManager.viewAppointments(this);
    }

    public void viewPastAppointmentOutcomes() {
        appointmentManager.viewAppointmentOutcome(this);
    }

    // Display patient-specific menu
    @Override
    public void displayMenu() {
        if (isLoggedIn()) {
            
            System.out.println("\n--- Patient Menu ---");
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
}
