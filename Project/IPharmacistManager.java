import java.util.List;

public interface IPharmacistManager {

    void viewPrescriptionRecords();

    void viewPendingPrescriptionRecords();

    void updatePrescriptionStatus(String prescriptionID);

    void replenishmentRequest(String medicineName, int amt);

    void viewInventory();

    void viewReplenishmentRequests();
}
