import java.util.List;

public interface IMedicineManagementService {
    void addMedicine(String name, int stock, int alertLevel);
    boolean needsReplenishment(Medicine medicine);
    void viewMedicines();
    void updateMedicineStock(String name, int newStock);
    void updateStockAlertLevel(String name, int newAlertLevel);
    void removeMedicine(String name);
    Medicine findMedicineByName(String name);
    List<Medicine> getAllMedicines();
    void displayMedicineManagementMenu();
}
