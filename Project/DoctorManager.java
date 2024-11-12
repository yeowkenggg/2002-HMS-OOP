import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DoctorManager implements IDoctorManager {

    private IStaffManager staffManager;
    private IPrescriptionManager prescriptionManager; 

    public DoctorManager(IStaffManager staffManager, IPrescriptionManager prescriptionManager) {
        this.staffManager = staffManager;
        this.prescriptionManager = prescriptionManager;
    }

    public void setStaffManager(IStaffManager sm){
        this.staffManager = sm;
    }

    public void setPrescriptionManager(IPrescriptionManager pm) {
        this.prescriptionManager = pm;
    }

    public void recordAppointmentOutcome(Doctor doctor, String patientID, String appointmentID, 
        String services, String notes, Prescription prescription) {
        MedicalRecord record = MedicalRecord.getRecordByPatientID(patientID);

        if (record != null) {
        record.addPrescription(prescription);
        System.out.println("Prescription added to medical record for Patient ID: " + patientID);

        if (prescriptionManager != null) {
        prescriptionManager.addPrescription(prescription);
        System.out.println("Prescription added to PrescriptionManager for pharmacist access.");
        } else {
        System.out.println("Error: PrescriptionManager is not initialized.");
        }

        System.out.println("Appointment outcome recorded successfully.");
        } else {
        System.out.println("Patient record not found.");
        }
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctorList = new ArrayList<>();
        for (Staff staff : staffManager.getAllStaff()) {
            if (staff instanceof Doctor) {
                doctorList.add((Doctor) staff);
            }
        }
        return doctorList;  // copy to prevent direct modification
    }
    
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

    public Doctor findDoctorById(String doctorId) {
        for (Doctor doctor : getAllDoctors()) {  
            if (doctor.getUserId().equalsIgnoreCase(doctorId)) {
                return doctor;
            }
        }
        System.out.println("Doctor with ID " + doctorId + " not found.");
        return null;
    }
    
    public boolean isAvailable(Doctor doctor, TimeSlot timeSlot) {
        return doctor.isAvailable(timeSlot);
    }

    public List<TimeSlot> getAvailability(Doctor doctor) {
        return doctor.getAvailability(); 
    }
    

    public void assignPatient(Doctor doctor, String patientID) {
        if (!doctor.getAssignedPatientIDs().contains(patientID)) {
            doctor.getAssignedPatientIDs().add(patientID);
            System.out.println("Patient ID " + patientID + " assigned to Dr. " + doctor.getName());
        } else {
            System.out.println("Patient ID " + patientID + " is already assigned to Dr. " + doctor.getName());
        }
    }

    public void addAppointment(Doctor doctor, Appointment appointment) {
        if (!doctor.getAppointments().contains(appointment)) {
            doctor.getAppointments().add(appointment);
            System.out.println("Appointment added to Dr. " + doctor.getName() + "'s schedule.");
        } else {
            System.out.println("This appointment already exists in the schedule.");
        }
    }

    public void removeAppointment(Doctor doctor, Appointment appointment) {
        if (doctor.getAppointments().remove(appointment)) {
            
        } else {
            System.out.println("Appointment not found in Dr. " + doctor.getName() + "'s schedule.");
        }
    }

    public List<String> getAssignedPatientIDs(Doctor doctor) {
        return new ArrayList<>(doctor.getAssignedPatientIDs()); 
    }

    public void addDiagnosis(String patientID, String diagnosisID, String details) {
        MedicalRecord record = MedicalRecord.getRecordByPatientID(patientID);
        if (record != null) {
            Diagnosis diagnosis = new Diagnosis(diagnosisID, details, LocalDate.now());
            record.addDiagnosis(diagnosis);
        } else {
            System.out.println("Patient record not found.");
        }
    }
    
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