import java.util.List;

public interface IMedicineManager {

    void addMedicine(String name, int stock, int alertLevel);

    boolean needsReplenishment(String name);

    void viewMedicines();

    void updateMedicineStock(String name, int newStock);

    void updateStockAlertLevel(String name, int newAlertLevel);

    void removeMedicine(String name);

    Medicine findMedicineByName(String name);

    List<Medicine> getAllMedicines();
}
