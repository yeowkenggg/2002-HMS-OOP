import java.util.ArrayList;
import java.util.List;

public class ReplenishmentRequest {
	
	private static List<ReplenishmentRequest> requests = new ArrayList<>();  // Shared across all instances

	private String requestID;
	private Medicine medicine;
	private int requestedAmount;
	private String pharmacistID;
	private boolean isApproved;

	//constructor
	public ReplenishmentRequest(String requestID, Medicine medicine, int requestedAmount, String pharmacistID) {
        this.requestID = requestID;
        this.medicine = medicine;
        this.requestedAmount = requestedAmount;
        this.pharmacistID = pharmacistID;
        this.isApproved = false;  // default

		requests.add(this);
    }

	//approve and call replenish method to add qty into the medicine
	public void approve() {
		this.isApproved = true;
		medicine.replenish(requestedAmount);
	}


	//get methods
	public static List<ReplenishmentRequest> getRequests() {
        return requests;
    }

	public String getRequestID() {
		return requestID;
	}

	public Medicine getMedicine() {
        return medicine;
    }

    public int getRequestedAmount() {
        return requestedAmount;
    }

	//check of status
	public boolean isApproved() {
		// TODO - implement ReplenishmentRequest.isApproved
		return isApproved;
	}
 
}