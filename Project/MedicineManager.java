import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MedicineManager implements IMedicineManager {
    private List<Medicine> medicines;

    public MedicineManager() {
        this.medicines = new ArrayList<>();
    }

    @Override
    public void addMedicine(String name, int stock, int alertLevel) {
        Medicine existingMedicine = findMedicineByName(name);
        if (existingMedicine != null) {
            System.out.println("Medicine with name " + name + " already exists in the inventory.");
            return;
        }
        Medicine medicine = new Medicine(name, stock, alertLevel);
        medicines.add(medicine);
        
    }

    public List<Medicine> getInventory() {
        return medicines;
    }

    @Override
    public boolean needsReplenishment(String name) {
        Medicine medicine = findMedicineByName(name);
        if (medicine != null) {
            return medicine.getStock() <= medicine.getAlertLevel();
        }
        System.out.println("Medicine '" + name + "' not found in inventory.");
        return false;
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
        Medicine medicineToRemove = findMedicineByName(name);
        if (medicineToRemove != null) {
            medicines.remove(medicineToRemove);
            System.out.println("Medicine " + name + " removed.");
        } else {
            System.out.println("Medicine '" + name + "' not found in inventory.");
        }
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
        System.out.println("6. Return");
        System.out.print("Choose an option (1-6): ");

        int option = scanner.nextInt();
        scanner.nextLine();

        if (option ==(6)) {
            return; 
        }
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
        System.out.println("Medicine added: " + name + " with stock " + stock + " and alert level " + alertLevel);
    }

    private void updateMedicineStockMenu() {
        Medicine selectedMedicine = selectMedicineByIndex();
        if (selectedMedicine == null) return;
    
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new stock amount: ");
        int newStock;
        try {
            newStock = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid stock amount. Please enter a valid number.");
            return;
        }
    
        updateMedicineStock(selectedMedicine.getName(), newStock);
    }
    
    

    private void updateStockAlertLevelMenu() {
        Medicine selectedMedicine = selectMedicineByIndex();
        if (selectedMedicine == null) return; 
    
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new stock alert level: ");
        int newAlertLevel;
        try {
            newAlertLevel = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid alert level. Please enter a valid number.");
            return;
        }
    
        updateStockAlertLevel(selectedMedicine.getName(), newAlertLevel);
    }
    

    private void removeMedicineMenu() {
        try {
            Medicine selectedMedicine = selectMedicineByIndex();
            
            if (selectedMedicine == null) {
                System.out.println("No medicine selected or invalid index.");
                return;
            }
            
            removeMedicine(selectedMedicine.getName());
            System.out.println("Removed medicine: " + selectedMedicine.getName());
            
        } catch (Exception e) {
            System.out.println("Error removing medicine: " + e.getMessage());
        }
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

    public List<ReplenishmentRequest> getPendingReplenishmentRequests() {
        List<ReplenishmentRequest> pendingRequests = new ArrayList<>();
        for (ReplenishmentRequest request : ReplenishmentRequest.getRequests()) {
            if (!request.isApproved()) {
                pendingRequests.add(request);
            }
        }
        return pendingRequests;
    }

    public boolean isAvailable(String medicineName) {
        return medicines.stream()
                .anyMatch(medicine -> medicine.getName().equalsIgnoreCase(medicineName));
    }

    public void displayInventory() {
        System.out.println("\n=== Inventory ===");
        for (int i = 0; i < medicines.size(); i++) {
            Medicine med = medicines.get(i);
            System.out.println(i + ": Name: " + med.getName());
        }
    }

    public Medicine getMedicineByIndex(int index) {
        if (index >= 0 && index < medicines.size()) {
            return medicines.get(index);
        }
        System.out.println("Invalid index. Please select a valid medicine.");
        return null;
    }
    
    
    //will call this method a few time repeatedly, so might as well make a method for it
    private Medicine selectMedicineByIndex() {
        Scanner scanner = new Scanner(System.in);
        List<Medicine> inventory = getInventory();
        
        System.out.println("\n--- Medicine Inventory ---");
        for (int i = 0; i < inventory.size(); i++) {
            Medicine medicine = inventory.get(i);
            System.out.println(i + ": " + medicine.getName() + " (Stock: " + medicine.getStock() + ", Alert Level: " + medicine.getAlertLevel() + ")");
        }
    
        System.out.print("Enter the index of the medicine: ");
        int index;
        try {
            index = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return null;
        }
    
        if (index < 0 || index >= inventory.size()) {
            System.out.println("Invalid index. Please select a valid medicine.");
            return null;
        }
    
        return inventory.get(index);
    }


    
    

}

