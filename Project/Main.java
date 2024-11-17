import java.time.*;
import java.util.*;

/**
 * Using Main to initialize and load data from CSV and starting of the application.
 */
public class Main {
    /**
     * Default constructor for Main
     */
    public Main() {}

    
    /**
     * Entry of application
     * @param args cli
     */
    public static void main(String[] args) {

        /*
         * CHANGE THIS TO YOUR OWN CORRECT FILEPATH
         * LOOK AT README.MD IF YOU DONT KNOW HOW TO CHANGE
         */
        //CSV file paths
        String staffFilePath = "Staff_List.csv";  
        String patientFilePath = "Patient_List.csv";  
        String medicineFilePath = "Medicine_List.csv";  

        Scanner scanner = new Scanner(System.in);
        List<Appointment> allAppointments = new ArrayList<>();
        List<User> sharedUserList = new ArrayList<>();
        List<Staff> initialStaffList = new ArrayList<>();
        
        // Initialize managers 
        IMedicineManager medicineManager = new MedicineManager();
        IPrescriptionManager prescriptionManager = new PrescriptionManager(null);
        IPatientManager patientManager = new PatientManager(null);
        IDoctorManager doctorManager = new DoctorManager(null, null);
        IPharmacistManager pharmacistManager = new PharmacistManager(null, null);
        IAppointmentManager appointmentManager = new AppointmentManager(null, null, null);
        UserManager userManager = new UserManager(sharedUserList, doctorManager, appointmentManager, medicineManager, prescriptionManager);
        IStaffManager staffManager = new StaffManager(initialStaffList, sharedUserList, userManager, medicineManager, prescriptionManager, doctorManager);

        //Pre-loaded/Sample timeslot for demo
        TimeSlot slot1 = new TimeSlot(LocalDate.now().plusDays(1), LocalTime.of(10, 0));
        TimeSlot slot2 = new TimeSlot(LocalDate.now().plusDays(2), LocalTime.of(10, 0));
        
        //manager dependencies
        prescriptionManager.setMedicineManager(medicineManager);
        patientManager.setAppointmentManager(appointmentManager);
        pharmacistManager.setMedicineManager(medicineManager);
        pharmacistManager.setPrescriptionManager(prescriptionManager);
        appointmentManager.setAppList(allAppointments);
        appointmentManager.setDoctorManager(doctorManager);
        appointmentManager.setPatientManager(patientManager);
        doctorManager.setStaffManager(staffManager);
        doctorManager.setPrescriptionManager(prescriptionManager);


        //loading of CSV files
        CSVImportManager.importStaffData(staffFilePath, staffManager, medicineManager, pharmacistManager, doctorManager, prescriptionManager);
        CSVImportManager.importPatientData(patientFilePath, patientManager, appointmentManager);
        CSVImportManager.importMedicineData(medicineFilePath, medicineManager);

        //add all users to shared list
        sharedUserList.addAll(staffManager.getAllStaff());
        sharedUserList.addAll(patientManager.getAllPatientsInternal());

        //set doctor availability based on previous demo timeslots
        Doctor doctor1 = doctorManager.findDoctorById("D001");
        doctor1.addAvailability(slot1);
        doctor1.addAvailability(slot2);

        //start
        userManager.loginUser();  
    }
}
        