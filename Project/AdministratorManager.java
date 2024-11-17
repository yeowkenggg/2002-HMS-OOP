import java.util.*;

/**
 * AdminstratorManager - logic implementation for Administrator class
 */
public class AdministratorManager {
    private IMedicineManager medicineManager;

    /**
     * Constructor for AdministratorManager
     * @param medicineManager manager responsible for medicine-related logic
     */
    public AdministratorManager(IMedicineManager medicineManager) {
        this.medicineManager = medicineManager;
    }

    /**
     * Logic for approving replenishment requests.
     * Asks user for an index and updates it from "Pending" to "Approved"
     */
    public void approveReplenishmentRequests() {
        List<ReplenishmentRequest> requests = medicineManager.getPendingReplenishmentRequests();
        if (requests.isEmpty()) {
            System.out.println("No pending replenishment requests.");
            return;
        }
    
        System.out.println("\nPending Replenishment Requests:");
        for (int i = 0; i < requests.size(); i++) {
            ReplenishmentRequest request = requests.get(i);
            System.out.println(i + ": " + request);
        }
    
        Scanner scanner = new Scanner(System.in);
    
        while (true) { 
            System.out.print("Enter the index of the replenishment request to approve (or 'E' to exit): ");
            String input = scanner.nextLine().trim();
    
            if (input.equalsIgnoreCase("E")) {
                System.out.println("Exiting to previous menu.");
                break; 
            }
    
            try {
                int index = Integer.parseInt(input);
    
                if (index >= 0 && index < requests.size()) {
                    ReplenishmentRequest selectedRequest = requests.get(index);
                    medicineManager.approveReplenishment(selectedRequest.getRequestID());
                    System.out.println("Replenishment request approved for Request ID: " + selectedRequest.getRequestID());
                    break; 
                } else {
                    System.out.println("Invalid index. Please select a valid request index.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number or 'E' to exit.");
            }
        }
    }
}