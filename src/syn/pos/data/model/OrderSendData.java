package syn.pos.data.model;

import java.util.List;

public class OrderSendData {
	public List<OrderDetail> xListOrderDetail;
	
	public static class OrderTransaction{
		private int iTableID;
		private String szTransName;
	    private double fTotalItemPrice;
	    private double fTotalDiscount;
	    private double fGrandTotalPrice;
		
	    public int getiTableID() {
			return iTableID;
		}
		public void setiTableID(int iTableID) {
			this.iTableID = iTableID;
		}
		public String getSzTransName() {
			return szTransName;
		}
		public void setSzTransName(String szTransName) {
			this.szTransName = szTransName;
		}
		public double getfTotalItemPrice() {
			return fTotalItemPrice;
		}
		public void setfTotalItemPrice(double fTotalItemPrice) {
			this.fTotalItemPrice = fTotalItemPrice;
		}
		public double getfTotalDiscount() {
			return fTotalDiscount;
		}
		public void setfTotalDiscount(double fTotalDiscount) {
			this.fTotalDiscount = fTotalDiscount;
		}
		public double getfGrandTotalPrice() {
			return fGrandTotalPrice;
		}
		public void setfGrandTotalPrice(double fGrandTotalPrice) {
			this.fGrandTotalPrice = fGrandTotalPrice;
		}
	}
	
	public static class OrderDetail {

		private int iOrderID;
		private int iProductID;
		private String szProductName;
		private int iProductType;
		private int iOrderStatusID;
		private double fItemQty;
		private double fItemPrice;
		private double fItemDiscount;
		private double fTotalPrice;
		private String szOrderStaffBy;
		private String szOrderTime;
		private String szFinishTime;
		private int iOrderWaitMinTime;
		private int iSeatID;
		private int iCourseID;

		public int getiCourseID() {
			return iCourseID;
		}

		public void setiCourseID(int iCourseID) {
			this.iCourseID = iCourseID;
		}

		public int getiSeatID() {
			return iSeatID;
		}

		public void setiSeatID(int iSeatID) {
			this.iSeatID = iSeatID;
		}

		public int getiOrderWaitMinTime() {
			return iOrderWaitMinTime;
		}

		public void setiOrderWaitMinTime(int iOrderWaitMinTime) {
			this.iOrderWaitMinTime = iOrderWaitMinTime;
		}

		public String getSzOrderTime() {
			return szOrderTime;
		}

		public void setSzOrderTime(String szOrderTime) {
			this.szOrderTime = szOrderTime;
		}

		public String getSzFinishTime() {
			return szFinishTime;
		}

		public void setSzFinishTime(String szFinishTime) {
			this.szFinishTime = szFinishTime;
		}

		public String getSzOrderStaffBy() {
			return szOrderStaffBy;
		}

		public void setSzOrderStaffBy(String szOrderStaffBy) {
			this.szOrderStaffBy = szOrderStaffBy;
		}

		public int getiOrderID() {
			return iOrderID;
		}

		public void setiOrderID(int iOrderID) {
			this.iOrderID = iOrderID;
		}

		public int getiProductID() {
			return iProductID;
		}

		public void setiProductID(int iProductID) {
			this.iProductID = iProductID;
		}

		public String getSzProductName() {
			return szProductName;
		}

		public void setSzProductName(String szProductName) {
			this.szProductName = szProductName;
		}

		public int getiProductType() {
			return iProductType;
		}

		public void setiProductType(int iProductType) {
			this.iProductType = iProductType;
		}

		public int getiOrderStatusID() {
			return iOrderStatusID;
		}

		public void setiOrderStatusID(int iOrderStatusID) {
			this.iOrderStatusID = iOrderStatusID;
		}

		public double getfItemQty() {
			return fItemQty;
		}

		public void setfItemQty(double fItemQty) {
			this.fItemQty = fItemQty;
		}

		public double getfItemPrice() {
			return fItemPrice;
		}

		public void setfItemPrice(double fItemPrice) {
			this.fItemPrice = fItemPrice;
		}

		public double getfItemDiscount() {
			return fItemDiscount;
		}

		public void setfItemDiscount(double fItemDiscount) {
			this.fItemDiscount = fItemDiscount;
		}

		public double getfTotalPrice() {
			return fTotalPrice;
		}

		public void setfTotalPrice(double fTotalPrice) {
			this.fTotalPrice = fTotalPrice;
		}

		@Override
		public String toString() {
			return szProductName;
		}
	}
}
