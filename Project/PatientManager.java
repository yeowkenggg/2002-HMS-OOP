import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatientManager implements IPatientManager {

    private List<Patient> patientList;
    private IAppointmentManager appointmentManager;

    public PatientManager(AppointmentManager appointmentManager) {
        this.patientList = new ArrayList<>();
        this.appointmentManager = appointmentManager;
    }

    public void setAppointmentManager(IAppointmentManager am){
        this.appointmentManager = am;
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
    
    public List<Patient> getAllPatients(Staff caller) { //
    if ("Doctor".equals(caller.getRole()) || "Administrator".equals(caller.getRole())) {
        return new ArrayList<>(patientList);
    } else {
        System.out.println("Access denied. Only doctors or administrators can access all patient records.");
        return Collections.emptyList();
    }
    }


    public List<Patient> getAllPatientsPrv() {
        return patientList;
    }

    public Patient findPatientById(String patientID) {
        for (Patient patient : getAllPatientsPrv()) {
            if (patient.getPatientID().equals(patientID)) {
                return patient;
            }
        }
        return null;
    }
    
    @Override
    public void addPatient(Patient patient) {
        if (!patientList.contains(patient)) {
            patientList.add(patient);
            //System.out.println("Patient added: " + patient.getName() + " (ID: " + patient.getUserId() + ")");
        }
    }


    
    







    
}
