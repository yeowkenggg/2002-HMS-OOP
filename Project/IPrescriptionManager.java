import java.util.List;

/**
 * An interface class which manage the prescription related operations
 */
public interface IPrescriptionManager {

    /**
    * set the manager responsible for managing medicine-related operations.
    * @param medicineManager the medicine manager to be set
    */
    void setMedicineManager(IMedicineManager medicineManager);

    /**
    * Adds a new prescription
    * @param prescription the prescription to be added
    */
    void addPrescription(Prescription prescription);

    /**
    * Retrieves a list of all prescriptions
    * @return a list of all prescriptions
    */
    List<Prescription> getAllPrescriptions();

    /**
    * Retrieves a list of pending prescriptions
    * @return a list of pending prescriptions
    */
    List<Prescription> getPendingPrescriptions();

    /**
    * Updates the status of a specific prescription
    * @param prescriptionID the unique identifier of the prescription to update
    * @return a boolean indicating whether if it was succesful or not
    */
    boolean updatePrescriptionStatus(String prescriptionID);
}