package syn.pos.data.model;

public class TableInfo {
	private int iTableID;
	private int iTableZoneID;
	private int TableStatus;
	private String szTableName;
	private int iTableCapacity;
	private String szCustomerName;
	private int iNoOfCustomer;
	private int iNoOfCustomerWhenOpen;
	private int iMemberID;
	private boolean bHasOrder;
	private int iNumberPrintBill;
	private String dTableTime;
	private boolean bIsCombineTable;
	private String szCombineTableName;
	private int iTransactionID;
	private int iComputerID;
	private String dBeginTime;
	private String dEndTime;
	private String dPrintWarningTime;
	private int iPrintBeginTime;
	private int iCallForCheckBillStatus;
	private int iCurrentAccessComputer;
	private String szCurrentAccessComputerName;
	private boolean bIsDummy;
	private boolean bIsSplitTransaction;
	private int iNoUnSubmitOrder;
	private boolean isChecked;

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public int getiTableID() {
		return iTableID;
	}

	public void setiTableID(int iTableID) {
		this.iTableID = iTableID;
	}

	public int getiTableZoneID() {
		return iTableZoneID;
	}

	public void setiTableZoneID(int iTableZoneID) {
		this.iTableZoneID = iTableZoneID;
	}

	public int getTableStatus() {
		return TableStatus;
	}

	public void setTableStatus(int tableStatus) {
		TableStatus = tableStatus;
	}

	public String getSzTableName() {
		return szTableName;
	}

	public void setSzTableName(String szTableName) {
		this.szTableName = szTableName;
	}

	public int getiTableCapacity() {
		return iTableCapacity;
	}

	public void setiTableCapacity(int iTableCapacity) {
		this.iTableCapacity = iTableCapacity;
	}

	public String getSzCustomerName() {
		return szCustomerName;
	}

	public void setSzCustomerName(String szCustomerName) {
		this.szCustomerName = szCustomerName;
	}

	public int getiNoOfCustomer() {
		return iNoOfCustomer;
	}

	public void setiNoOfCustomer(int iNoOfCustomer) {
		this.iNoOfCustomer = iNoOfCustomer;
	}

	public int getiNoOfCustomerWhenOpen() {
		return iNoOfCustomerWhenOpen;
	}

	public void setiNoOfCustomerWhenOpen(int iNoOfCustomerWhenOpen) {
		this.iNoOfCustomerWhenOpen = iNoOfCustomerWhenOpen;
	}

	public int getiMemberID() {
		return iMemberID;
	}

	public void setiMemberID(int iMemberID) {
		this.iMemberID = iMemberID;
	}

	public boolean isbHasOrder() {
		return bHasOrder;
	}

	public void setbHasOrder(boolean bHasOrder) {
		this.bHasOrder = bHasOrder;
	}

	public int getiNumberPrintBill() {
		return iNumberPrintBill;
	}

	public void setiNumberPrintBill(int iNumberPrintBill) {
		this.iNumberPrintBill = iNumberPrintBill;
	}

	public String getdTableTime() {
		return dTableTime;
	}

	public void setdTableTime(String dTableTime) {
		this.dTableTime = dTableTime;
	}

	public boolean isbIsCombineTable() {
		return bIsCombineTable;
	}

	public void setbIsCombineTable(boolean bIsCombineTable) {
		this.bIsCombineTable = bIsCombineTable;
	}

	public String getSzCombineTableName() {
		return szCombineTableName;
	}

	public void setSzCombineTableName(String szCombineTableName) {
		this.szCombineTableName = szCombineTableName;
	}

	public int getiTransactionID() {
		return iTransactionID;
	}

	public void setiTransactionID(int iTransactionID) {
		this.iTransactionID = iTransactionID;
	}

	public int getiComputerID() {
		return iComputerID;
	}

	public void setiComputerID(int iComputerID) {
		this.iComputerID = iComputerID;
	}

	public String getdBeginTime() {
		return dBeginTime;
	}

	public void setdBeginTime(String dBeginTime) {
		this.dBeginTime = dBeginTime;
	}

	public String getdEndTime() {
		return dEndTime;
	}

	public void setdEndTime(String dEndTime) {
		this.dEndTime = dEndTime;
	}

	public String getdPrintWarningTime() {
		return dPrintWarningTime;
	}

	public void setdPrintWarningTime(String dPrintWarningTime) {
		this.dPrintWarningTime = dPrintWarningTime;
	}

	public int getiPrintBeginTime() {
		return iPrintBeginTime;
	}

	public void setiPrintBeginTime(int iPrintBeginTime) {
		this.iPrintBeginTime = iPrintBeginTime;
	}

	public int getiCallForCheckBillStatus() {
		return iCallForCheckBillStatus;
	}

	public void setiCallForCheckBillStatus(int iCallForCheckBillStatus) {
		this.iCallForCheckBillStatus = iCallForCheckBillStatus;
	}

	public int getiCurrentAccessComputer() {
		return iCurrentAccessComputer;
	}

	public void setiCurrentAccessComputer(int iCurrentAccessComputer) {
		this.iCurrentAccessComputer = iCurrentAccessComputer;
	}

	public String getSzCurrentAccessComputerName() {
		return szCurrentAccessComputerName;
	}

	public void setSzCurrentAccessComputerName(
			String szCurrentAccessComputerName) {
		this.szCurrentAccessComputerName = szCurrentAccessComputerName;
	}

	public boolean isbIsDummy() {
		return bIsDummy;
	}

	public void setbIsDummy(boolean bIsDummy) {
		this.bIsDummy = bIsDummy;
	}

	public boolean isbIsSplitTransaction() {
		return bIsSplitTransaction;
	}

	public void setbIsSplitTransaction(boolean bIsSplitTransaction) {
		this.bIsSplitTransaction = bIsSplitTransaction;
	}

	public int getiNoUnSubmitOrder() {
		return iNoUnSubmitOrder;
	}

	public void setiNoUnSubmitOrder(int iNoUnSubmitOrder) {
		this.iNoUnSubmitOrder = iNoUnSubmitOrder;
	}
}
