import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MedicineManagementService implements IMedicineManagementService {
    private List<Medicine> medicines;

    public MedicineManagementService() {
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

    public void displayMedicineManagementMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Medicine Management ---");
        System.out.println("1. View All Medicines");
        System.out.println("2. Add New Medicine");
        System.out.println("3. Update Medicine Stock");
        System.out.println("4. Update Stock Alert Level");
        System.out.println("5. Remove Medicine");
        System.out.print("Choose an option (1-5): ");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1 -> viewMedicines();
            case 2 -> addMedicineMenu();
            case 3 -> updateMedicineStockMenu();
            case 4 -> updateStockAlertLevelMenu();
            case 5 -> removeMedicineMenu();
            default -> System.out.println("Invalid option.");
        }
    }

    private void addMedicineMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Medicine Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Initial Stock: ");
        int stock = scanner.nextInt();

        System.out.print("Enter Stock Alert Level: ");
        int alertLevel = scanner.nextInt();

        addMedicine(name, stock, alertLevel);
    }

    private void updateMedicineStockMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Medicine Name to Update Stock: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter New Stock Amount: ");
        int newStock = scanner.nextInt();

        updateMedicineStock(name, newStock);
    }

    private void updateStockAlertLevelMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Medicine Name to Update Alert Level: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter New Stock Alert Level: ");
        int newAlertLevel = scanner.nextInt();

        updateStockAlertLevel(name, newAlertLevel);
    }

    private void removeMedicineMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Medicine Name to Remove: ");
        String name = scanner.nextLine();

        removeMedicine(name);
    }

    public void viewReplenishmentRequests() {
        System.out.println("Pending Replenishment Requests:");
        for (ReplenishmentRequest request : ReplenishmentRequest.getRequests()) {
            String status = request.isApproved() ? "Approved" : "Pending";
            System.out.println("Request ID: " + request.getRequestID() 
                               + " | Medicine: " + request.getMedicine().getName() 
                               + " | Amount: " + request.getRequestedAmount()
                               + " | Status: " + status);
        }
    }

    public void approveReplenishment(String requestID) {
        for (ReplenishmentRequest request : ReplenishmentRequest.getRequests()) {
            if (request.getRequestID().equals(requestID) && !request.isApproved()) {
                request.approve();
                System.out.println("Replenishment request " + requestID + " has been approved.");
                return;
            }
        }
        System.out.println("Replenishment request not found or already approved.");
    }
}
