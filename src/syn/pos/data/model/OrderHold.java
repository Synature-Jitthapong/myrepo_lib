package syn.pos.data.model;

public class OrderHold extends OrderTransaction {
	private double holdQty;

	public double getHoldQty() {
		return holdQty;
	}

	public void setHoldQty(double holdQty) {
		this.holdQty = holdQty;
	}
}
