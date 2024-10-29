import java.time.LocalDate;

public interface IPatientManager {

    void viewMedicalRecord(Patient patient);

    void updateContactInfo(Patient patient, String newContactInfo);

    void updateName(Patient patient, String newName);

    void updateGender(Patient patient, String newGender);

    void updateDateOfBirth(Patient patient, LocalDate newDateOfBirth);

    void addPatient(Patient patient);
}
