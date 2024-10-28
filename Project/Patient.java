import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Patient extends User implements IUser {

    private String patientID;
    private LocalDate dateOfBirth;
    private String bloodType;
    private String contactInfo;
    private List<Appointment> appointments;

    // constructor
    public Patient(String userId, String password, String name, String gender, LocalDate dateOfBirth, String bloodType, String contactInfo) {
        super(userId, password, name, gender);
        this.patientID = userId;
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
        this.appointments = new ArrayList<>();
        
        MedicalRecord medicalRecord = new MedicalRecord(userId, name, dateOfBirth, gender, bloodType, contactInfo);
        MedicalRecord.addRecord(medicalRecord);
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

    // Display patient-specific menu
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
}
