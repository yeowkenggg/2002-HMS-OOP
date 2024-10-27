import java.util.ArrayList;
import java.util.List;

public class MedicineInventoryManager implements IInventoryManager {

    private List<Medicine> medicines;

    public MedicineInventoryManager() {
        this.medicines = new ArrayList<>();
    }

    @Override
    public void addMedicine(String name, int stock, int alertLevel) {
        Medicine medicine = new Medicine(name, stock, alertLevel);
        medicines.add(medicine);
        System.out.println("Medicine added: " + name + " with stock " + stock + " and alert level " + alertLevel);
    }

    @Override
    public boolean needsReplenishment(Medicine medicine) {
        return medicine.getStock() <= medicine.getAlertLevel();
    }

    @Override
    public void viewMedicines() {
        System.out.println("\n=== All Medicines ===");
        for (Medicine med : medicines) {
            System.out.println("Name: " + med.getName() + ", Stock: " + med.getStock() + ", Alert Level: " + med.getAlertLevel());
        }
    }

    @Override
    public void updateMedicineStock(String name, int newStock) {
        Medicine medicine = findMedicineByName(name);
        if (medicine != null) {
            medicine.setStock(newStock);
            System.out.println("Updated stock for " + name + " to " + newStock + ".");
        } else {
            System.out.println("Medicine '" + name + "' not found in inventory.");
        }
    }

    @Override
    public void updateStockAlertLevel(String name, int newAlertLevel) {
        Medicine medicine = findMedicineByName(name);
        if (medicine != null) {
            medicine.setAlertLevel(newAlertLevel);
            System.out.println("Updated alert level for " + name + " to " + newAlertLevel + ".");
        } else {
            System.out.println("Medicine '" + name + "' not found in inventory.");
        }
    }

    @Override
    public void removeMedicine(String name) {
        medicines.removeIf(med -> med.getName().equalsIgnoreCase(name));
        System.out.println("Medicine " + name + " has been removed from the inventory.");
    }

    @Override
    public Medicine findMedicineByName(String name) {
        for (Medicine med : medicines) {
            if (med.getName().equalsIgnoreCase(name)) {
                return med;
            }
        }
        return null;
    }

    @Override
    public List<Medicine> getAllMedicines() {
        return new ArrayList<>(medicines);
    }
}
