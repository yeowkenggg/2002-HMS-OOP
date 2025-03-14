import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A manager class for managing the medicines
 */
public class MedicineManager implements IMedicineManager {
    private List<Medicine> medicines;

    /**
     * Constructing a new MedicineManager with a empty list 
    */
    public MedicineManager() {
        this.medicines = new ArrayList<>();
    }

    /**
     * Adds a new medicine to the inventory
     * @param name the name of the medicine
     * @param stock the stock of the medicine
     * @param alertLevel the alert level of the medicine
     */
    public void addMedicine(String name, int stock, int alertLevel) {
        Medicine existingMedicine = findMedicineByName(name);
        if (existingMedicine != null) {
            System.out.println("Medicine with name " + name + " already exists in the inventory.");
            return;
        }
        Medicine medicine = new Medicine(name, stock, alertLevel);
        medicines.add(medicine);
        
    }

    /**
     * get method to retrieve the inventory of medicine
     * @return the list of medicine
     */
    public List<Medicine> getInventory() {
        return medicines;
    }

    /**
     * check if a medicine requires replenishment
     * @param name the name of the medicine
     * @return boolean to check if the medicine requires replenishment
     */
    public boolean needsReplenishment(String name) {
        Medicine medicine = findMedicineByName(name);
        if (medicine != null) {
            return medicine.getStock() <= medicine.getAlertLevel();
        }
        System.out.println("Medicine '" + name + "' not found in inventory.");
        return false;
    }

    /**
     * display information of all the medicine
     */
    public void viewMedicines() {
        System.out.println("\n=== All Medicines ===");
        for (Medicine med : medicines) {
            System.out.println("Name: " + med.getName() + ", Stock: " + med.getStock() + ", Alert Level: " + med.getAlertLevel());
        }
    }

    /**
     * update the stock of a medicine
     * @param name the name of the medicine
     * @param newStock the new stock of the medicine
     */
    public void updateMedicineStock(String name, int newStock) {
        Medicine medicine = findMedicineByName(name);
        if (medicine != null) {
            medicine.setStock(newStock);
            System.out.println("Updated stock for " + name + " to " + newStock + ".");
        } else {
            System.out.println("Medicine '" + name + "' not found in inventory.");
        }
    }

    /**
     * update the stock alert level of a medicine
     * @param name the name of the medicine
     * @param newAlertLevel the new alert level of the medicine
     */
    public void updateStockAlertLevel(String name, int newAlertLevel) {
        Medicine medicine = findMedicineByName(name);
        if (medicine != null) {
            medicine.setAlertLevel(newAlertLevel);
            System.out.println("Updated alert level for " + name + " to " + newAlertLevel + ".");
        } else {
            System.out.println("Medicine '" + name + "' not found in inventory.");
        }
    }
    
    /**
     * remove a medicine from the inventory
     * @param name the name of the medicine to be removed
     */
    public void removeMedicine(String name) {
        Medicine medicineToRemove = findMedicineByName(name);
        if (medicineToRemove != null) {
            medicines.remove(medicineToRemove);
            System.out.println("Medicine " + name + " removed.");
        } else {
            System.out.println("Medicine '" + name + "' not found in inventory.");
        }
    }

    /**
     * retrieves the medicine based on its name
     * @param name the name of the medicine
     * @return the medicine
     */
    public Medicine findMedicineByName(String name) {
        for (Medicine med : medicines) {
            if (med.getName().equalsIgnoreCase(name)) {
                return med;
            }
        }
        return null;
    }

    /**
     * get method to get all the medicine
     * @return a list of medicines
     */
    public List<Medicine> getAllMedicines() {
        return new ArrayList<>(medicines);
    }

    /**
     * a display menu for medicine managmenet
     */
    public void displayMedicineManagementMenu() {
        Scanner scanner = new Scanner(System.in);
    
        while (true) { 
            try {
                System.out.println("\n--- Medicine Management ---");
                System.out.println("1. View All Medicines");
                System.out.println("2. Add New Medicine");
                System.out.println("3. Update Medicine Stock");
                System.out.println("4. Update Stock Alert Level");
                System.out.println("5. Remove Medicine");
                System.out.println("6. Return");
                System.out.print("Choose an option (1-6): ");
    
                String input = scanner.nextLine().trim(); 
    
                int option;
                try {
                    option = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 6.");
                    continue; 
                }
    
                if (option == 6) {
                    return; 
                }
    
                // Handle the actions
                switch (option) {
                    case 1 -> viewMedicines();
                    case 2 -> addMedicineMenu();
                    case 3 -> updateMedicineStockMenu();
                    case 4 -> updateStockAlertLevelMenu();
                    case 5 -> removeMedicineMenu();
                    default -> System.out.println("Invalid option. Please enter a number between 1 and 6.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
    

    //internal CRUD of medicine
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
    

    /**
     * view all pending replenishment request
     */
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

    /**
     * approve a replenishment based on its ID
     * @param requestID the ID of the replennishmnet request
     */
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

    /**
     * retrieve a list of replenishment request that is pending status
     * @return a list of pending replenishment request
     */
    public List<ReplenishmentRequest> getPendingReplenishmentRequests() {
        List<ReplenishmentRequest> pendingRequests = new ArrayList<>();
        for (ReplenishmentRequest request : ReplenishmentRequest.getRequests()) {
            if (!request.isApproved()) {
                pendingRequests.add(request);
            }
        }
        return pendingRequests;
    }

    /**
     * Check if a medicien is available
     * @param medicineName the medicine name to be checked
     * @return a indication if the medicine is available
     */
    public boolean isAvailable(String medicineName) {
        return medicines.stream()
                .anyMatch(medicine -> medicine.getName().equalsIgnoreCase(medicineName));
    }

    /**
     * Display inventory
     */
    public void displayInventory() {
        System.out.println("\n=== Inventory ===");
        for (int i = 0; i < medicines.size(); i++) {
            Medicine med = medicines.get(i);
            System.out.println(i + ": Name: " + med.getName());
        }
    }

    /**
     * get the index of medicine (for input)
     * @param index the index of medicine
     * @return the medicine
     */
    public Medicine getMedicineByIndex(int index) {
        if (index >= 0 && index < medicines.size()) {
            return medicines.get(index);
        }
        System.out.println("Invalid index. Please select a valid medicine.");
        return null;
    }
    
    
    /**
     * Select a medicine by indicating the index (user input)
     * @return the medicine
     */
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

