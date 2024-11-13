import java.util.List;

public interface IMedicineManager {

    
    List<Medicine> getInventory();

    List<ReplenishmentRequest> getPendingReplenishmentRequests();

    void displayMedicineManagementMenu();

    void approveReplenishment(String requestID);

    void addMedicine(String name, int stock, int alertLevel);

    void displayInventory();

    Medicine findMedicineByName(String medicineName);

    boolean needsReplenishment(String name);

    void viewMedicines();

}
