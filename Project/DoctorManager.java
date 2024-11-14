import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * DoctorManager class, the logic implementation for Doctor class
 */
public class DoctorManager implements IDoctorManager {

    private IStaffManager staffManager;
    private IPrescriptionManager prescriptionManager; 

    /**
     * DoctorManager Constructor
     * @param staffManager  manager responsible for staff-related logic
     * @param prescriptionManager manager responsible for prescription-related logic
     */
    public DoctorManager(IStaffManager staffManager, IPrescriptionManager prescriptionManager) {
        this.staffManager = staffManager;
        this.prescriptionManager = prescriptionManager;
    }

    /**
     * setting StaffManager (to prevent cyclic in Main)
     * @param sm set staffManager
     */
    public void setStaffManager(IStaffManager sm){
        this.staffManager = sm;
    }

    /**
     * setting PrescriptionManager (to prevent cyclic in Main)
     * @param pm set prescritpionManager
     */
    public void setPrescriptionManager(IPrescriptionManager pm) {
        this.prescriptionManager = pm;
    }

    
    /**
     * get all the doctors from the list
     * @return a seperate doctorList so operations wont tamper the original information accidentally
     */
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctorList = new ArrayList<>();
        for (Staff staff : staffManager.getAllStaff()) {
            if (staff instanceof Doctor) {
                doctorList.add((Doctor) staff);
            }
        }
        return doctorList;  // copy to prevent direct modification
    }
    
    /**
     * Retrieves patient record through patient ID
     * @param doctor to ensure that the doctor has the authorization to view the record
     * @param patientID ID to retrieve the patient medical records
     */
    public void viewPatientRecord(Doctor doctor, String patientID) {
        if (!doctor.getAssignedPatientIDs().contains(patientID)) {
            System.out.println("Access denied. Patient is not under your care.");
            return;
        }
        MedicalRecord record = MedicalRecord.getRecordByPatientID(patientID);
        if (record != null) {
            System.out.println("Medical Record for Patient ID: " + patientID);
            record.viewMedicalRecord();
        } else {
            System.out.println("No medical record found for Patient ID: " + patientID);
        }
    }

    /**
     * Method to retrieve doctor by doctorID
     * @param doctorId ID to retrieve doctor by
     */
    public Doctor findDoctorById(String doctorId) {
        for (Doctor doctor : getAllDoctors()) {  
            if (doctor.getUserId().equalsIgnoreCase(doctorId)) {
                return doctor;
            }
        }
        System.out.println("Doctor with ID " + doctorId + " not found.");
        return null;
    }
    
    /**
     * Method to check whether if a timeslot is available for a specific doctor
     * @param doctor the doctor that has the available timeslot
     * @param timeSlot the chosen timeslot to check for availability 
     * @return whether if the timeslot is available or not
     */
    public boolean isAvailable(Doctor doctor, TimeSlot timeSlot) {
        return doctor.isAvailable(timeSlot);
    }

    /**
     * Method to get a list of available timeslot based on a specified doctor
     * @param doctor the specified doctor for get availability from
     * @return a list of avaiable timeslot
     */
    public List<TimeSlot> getAvailability(Doctor doctor) {
        return doctor.getAvailability(); 
    }
    

    /**
     * Assign a patient to doctor
     * @param doctor the doctor that patient is going to be assigned to
     * @param patientID the patient to be assigned
     */
    public void assignPatient(Doctor doctor, String patientID) {
        if (!doctor.getAssignedPatientIDs().contains(patientID)) {
            doctor.getAssignedPatientIDs().add(patientID);
            System.out.println("Patient ID " + patientID + " assigned to Dr. " + doctor.getName());
        } else {
            System.out.println("Patient ID " + patientID + " is already assigned to Dr. " + doctor.getName());
        }
    }
    
    /**
     * Adds an appointment to the doctor's schedule
     * @param doctor the doctor to which the appointment is being added
     * @param appointment the appointment that is going to be added
     */
    public void addAppointment(Doctor doctor, Appointment appointment) {
        if (!doctor.getAppointments().contains(appointment)) {
            doctor.getAppointments().add(appointment);
            System.out.println("Appointment added to Dr. " + doctor.getName() + "'s schedule.");
        } else {
            System.out.println("This appointment already exists in the schedule.");
        }
    }

    /**
     * Removes an appointment from the doctor's schedule
     * @param doctor the doctor which the appointment is being removed
     * @param appointment the appointment that is going to be removed
     */
    public void removeAppointment(Doctor doctor, Appointment appointment) {
        if (doctor.getAppointments().remove(appointment)) {
            
        } else {
            System.out.println("Appointment not found in Dr. " + doctor.getName() + "'s schedule.");
        }
    }

    /**
     * Retrieve the list of patient that is assigned to the doctor
     * @param doctor the doctor which the patients are assigned to
     * @return a list of patient that the doctor is assigned to
     */
    public List<String> getAssignedPatientIDs(Doctor doctor) {
        return new ArrayList<>(doctor.getAssignedPatientIDs()); 
    }

    /**
     * Adds a diagnosis to a patient's medical record
     * @param patientID the ID of the patient that the diagnosis is being added to
     * @param diagnosisID the ID of diagnosis
     * @param details the information of the diagnosis
     */
    public void addDiagnosis(String patientID, String diagnosisID, String details) {
        MedicalRecord record = MedicalRecord.getRecordByPatientID(patientID);
        if (record != null) {
            Diagnosis diagnosis = new Diagnosis(diagnosisID, details, LocalDate.now());
            record.addDiagnosis(diagnosis);
        } else {
            System.out.println("Patient record not found.");
        }
    }
    
    /**
     * Adds a treatment to a patient's medical record
     * @param patientID the ID of the patient that the treatment is being added to
     * @param treatmentID the ID of treatment
     * @param details the information of treatment
     */
    public void addTreatment(String patientID, String treatmentID, String details) {
        MedicalRecord record = MedicalRecord.getRecordByPatientID(patientID);
        if (record != null) {
            Treatment treatment = new Treatment(treatmentID, details, LocalDate.now());
            record.addTreatment(treatment);
            System.out.println("Treatment added successfully.");
        } else {
            System.out.println("Patient record not found.");
        }
    }
    
    /**
     * Adds a prescription to a patient's medical record
     * @param patientID the ID of patient that the treatment is being added to
     * @param prescriptionID the ID of prescription
     * @param medicineManager manager responsible for medicine-related logic
     */
    public void addPrescription(String patientID, String prescriptionID, IMedicineManager medicineManager) {
        Scanner scanner = new Scanner(System.in);
        List<Medicine> selectedMedicines = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
    
        medicineManager.displayInventory();  
    
        while (true) {
            System.out.print("Enter the index of the medicine to add (or -1 to finish): ");
            int index;
            try {
                index = scanner.nextInt();
                scanner.nextLine();  
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();  
                continue;
            }
    
            if (index == -1) {
                break;  
            }
            
            List<Medicine> inventory = medicineManager.getInventory();
            if (index >= 0 && index < inventory.size()) {
                Medicine selectedMedicine = inventory.get(index);
                System.out.print("Enter quantity for " + selectedMedicine.getName() + ": ");
                int quantity;
                try {
                    quantity = scanner.nextInt();
                    scanner.nextLine();
                    if (quantity <= 0) {
                        System.out.println("Quantity must be a positive number.");
                        continue;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid quantity.");
                    scanner.nextLine();
                    continue;
                }
    
                selectedMedicines.add(selectedMedicine);
                quantities.add(quantity);
                System.out.println("Added: " + selectedMedicine.getName() + " (Quantity: " + quantity + ")");
            } else {
                System.out.println("Invalid index. Please select a valid medicine from the inventory.");
            }
        }
    
        if (selectedMedicines.isEmpty()) {
            System.out.println("No medicines selected. Prescription was not created.");
            return;
        }
    
        Prescription prescription = new Prescription(prescriptionID, selectedMedicines, quantities, "Pending");
        MedicalRecord record = MedicalRecord.getRecordByPatientID(patientID);
        if (record != null) {
            record.addPrescription(prescription);
            System.out.println("Prescription added successfully to patient record.");
    
            if (prescriptionManager != null) {
                prescriptionManager.addPrescription(prescription);
                System.out.println("Prescription added to PrescriptionManager for pharmacist access.");
            } else {
                System.out.println("Error: PrescriptionManager is not initialized.");
            }
        } else {
            System.out.println("Patient record not found.");
        }
    }





    


    
}