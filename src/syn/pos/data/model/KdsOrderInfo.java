package syn.pos.data.model;

public class KdsOrderInfo {
	private int iOrderDetailID;
	private int iProductID;
	private String szProductName;
	private int iProductType;
	private int iOrderStatusID;
	private double fOrderQty;
	private String szStaffOrder;
	private int iPrintStatus;
	private int iIsSendKDS;
	private int iProcessStatusID;
	private String szSubmitOrderDateTime;
	private String szFinishOrderDateTime;
	private int iWaitMinTime;
	
	public int getiOrderDetailID() {
		return iOrderDetailID;
	}
	public void setiOrderDetailID(int iOrderDetailID) {
		this.iOrderDetailID = iOrderDetailID;
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
	public double getfOrderQty() {
		return fOrderQty;
	}
	public void setfOrderQty(double fOrderQty) {
		this.fOrderQty = fOrderQty;
	}
	public String getSzStaffOrder() {
		return szStaffOrder;
	}
	public void setSzStaffOrder(String szStaffOrder) {
		this.szStaffOrder = szStaffOrder;
	}
	public int getiPrintStatus() {
		return iPrintStatus;
	}
	public void setiPrintStatus(int iPrintStatus) {
		this.iPrintStatus = iPrintStatus;
	}
	public int getiIsSendKDS() {
		return iIsSendKDS;
	}
	public void setiIsSendKDS(int iIsSendKDS) {
		this.iIsSendKDS = iIsSendKDS;
	}
	public int getiProcessStatusID() {
		return iProcessStatusID;
	}
	public void setiProcessStatusID(int iProcessStatusID) {
		this.iProcessStatusID = iProcessStatusID;
	}
	public String getSzSubmitOrderDateTime() {
		return szSubmitOrderDateTime;
	}
	public void setSzSubmitOrderDateTime(String szSubmitOrderDateTime) {
		this.szSubmitOrderDateTime = szSubmitOrderDateTime;
	}
	public String getSzFinishOrderDateTime() {
		return szFinishOrderDateTime;
	}
	public void setSzFinishOrderDateTime(String szFinishOrderDateTime) {
		this.szFinishOrderDateTime = szFinishOrderDateTime;
	}
	public int getiWaitMinTime() {
		return iWaitMinTime;
	}
	public void setiWaitMinTime(int iWaitMinTime) {
		this.iWaitMinTime = iWaitMinTime;
	}
}
