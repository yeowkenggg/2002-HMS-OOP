import java.util.List;

/**
 * An interface class which manage the pharmacist related operations
 */
public interface IPharmacistManager {

    /**
     * set nthe manager responsible for handling medicine-related operations.
     * @param medicineManager the medicine manager to be set
     */
    void setMedicineManager(IMedicineManager medicineManager);

    /**
     * set the  manager responsible for managing prescription records.
     * @param prescriptionManager the prescription manager to be set
     */
    void setPrescriptionManager(IPrescriptionManager prescriptionManager);

    /**
     * Views all prescription records
     */
    void viewPrescriptionRecords();

    /**
     * Views all pending prescription records
     */
    void viewPendingPrescriptionRecords();

    /**
     * Updates the status of a prescription
     * @param prescriptionID the unique identifier of the prescription to be updated
     */
    void updatePrescriptionStatus(String prescriptionID);

    /**
     * Initiates a replenishment request for a medicine in the inventory.
     * @param medicineName the name of the medicine to be replenished
     * @param amt the amount of stock to request for replenishment
     */
    void replenishmentRequest(String medicineName, int amt);

    /**
     * Views the current inventory
     */
    void viewInventory();

    /**
     * Views all replenishment requests submitted
     */
    void viewReplenishmentRequests();
}