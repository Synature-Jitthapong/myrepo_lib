package syn.pos.data.model;

import java.util.List;

public class POSTable_MoveMenuData {
	public List<POSData_MoveMenu> xListMoveMenuData;
	public List<Integer> iListReasonID;
	public String szReasonText;
    
	public static class POSData_MoveMenu
    {
        private double fAddAmount;
        private int iOrderID;
        
		public double getfAddAmount() {
			return fAddAmount;
		}
		public void setfAddAmount(double fAddAmount) {
			this.fAddAmount = fAddAmount;
		}
		public int getiOrderId() {
			return iOrderID;
		}
		public void setiOrderId(int iOrderId) {
			this.iOrderID = iOrderId;
		}
    }

    public static class POSData_OutOfProduct
    {
    	private int iItemQty;
    	private int iProductID;
    	
		public int getiItemQty() {
			return iItemQty;
		}
		public void setiItemQty(int iItemQty) {
			this.iItemQty = iItemQty;
		}
		public int getiProductID() {
			return iProductID;
		}
		public void setiProductID(int iProductID) {
			this.iProductID = iProductID;
		}
    }
}
