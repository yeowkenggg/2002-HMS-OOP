public class ReplenishmentRequest {

	private String requestID;
	private Medicine medicine;
	private int requestedAmount;
	private String pharmacistID;
	private boolean isApproved;

	public void approve() {
		// TODO - implement ReplenishmentRequest.approve
		this.isApproved = true;
		medicine.replenish(requestedAmount);
		throw new UnsupportedOperationException();
	}

	public String getRequestID() {
		return this.requestID;
	}

	public boolean isApproved() {
		// TODO - implement ReplenishmentRequest.isApproved
		return isApproved;
	}

}