package syn.pos.data.model;

public class OrderTransaction extends OrderDetail {
	private String OpenTime;
	private int OpenStaffID;
	private String PaidTime;
	private int PaidStaffID;
	private String CloseTime;
	private int TransactionStatusID;
	private String QueueName;
	private int NoCustomer;
	private int DocType;
	private int ReceiptNo;
	private int ReceiptID;
	private String SaleDate;
	private Double TransactionVAT;
	private Double ServiceCharge;
	private Double ServiceChargeVAT;
	private Double VatPercent;
	private Double ServiceChargePercent;
	private int IsCalculateServiceCharge;
	private int SessionID;
	private String VoidReason;
	private String VoidTime;
	private int MemberID;
	private String TransactionNote;
	private int AlreadySendToHQ;
	private String SendToHQDateTime;
	private String UpdateDate;
	private int TableID;
	private int QueueID;
	private String TableName;
	
	public String getTableName() {
		return TableName;
	}
	public void setTableName(String tableName) {
		TableName = tableName;
	}
	public int getTableID() {
		return TableID;
	}
	public void setTableID(int tableID) {
		TableID = tableID;
	}
	public int getQueueID() {
		return QueueID;
	}
	public void setQueueID(int queueID) {
		QueueID = queueID;
	}
	public String getUpdateDate() {
		return UpdateDate;
	}
	public void setUpdateDate(String updateDate) {
		UpdateDate = updateDate;
	}
	public String getOpenTime() {
		return OpenTime;
	}
	public void setOpenTime(String openTime) {
		OpenTime = openTime;
	}
	public int getOpenStaffID() {
		return OpenStaffID;
	}
	public void setOpenStaffID(int openStaffID) {
		OpenStaffID = openStaffID;
	}
	public String getPaidTime() {
		return PaidTime;
	}
	public void setPaidTime(String paidTime) {
		PaidTime = paidTime;
	}
	public int getPaidStaffID() {
		return PaidStaffID;
	}
	public void setPaidStaffID(int paidStaffID) {
		PaidStaffID = paidStaffID;
	}
	public String getCloseTime() {
		return CloseTime;
	}
	public void setCloseTime(String closeTime) {
		CloseTime = closeTime;
	}
	public int getTransactionStatusID() {
		return TransactionStatusID;
	}
	public void setTransactionStatusID(int transactionStatusID) {
		TransactionStatusID = transactionStatusID;
	}
	public String getQueueName() {
		return QueueName;
	}
	public void setQueueName(String queueName) {
		QueueName = queueName;
	}
	public int getNoCustomer() {
		return NoCustomer;
	}
	public void setNoCustomer(int noCustomer) {
		NoCustomer = noCustomer;
	}
	public int getDocType() {
		return DocType;
	}
	public void setDocType(int docType) {
		DocType = docType;
	}
	public int getReceiptNo() {
		return ReceiptNo;
	}
	public void setReceiptNo(int receiptNo) {
		ReceiptNo = receiptNo;
	}
	public int getReceiptID() {
		return ReceiptID;
	}
	public void setReceiptID(int receiptID) {
		ReceiptID = receiptID;
	}
	public String getSaleDate() {
		return SaleDate;
	}
	public void setSaleDate(String saleDate) {
		SaleDate = saleDate;
	}
	public Double getTransactionVAT() {
		return TransactionVAT;
	}
	public void setTransactionVAT(Double transactionVAT) {
		TransactionVAT = transactionVAT;
	}
	public Double getServiceCharge() {
		return ServiceCharge;
	}
	public void setServiceCharge(Double serviceCharge) {
		ServiceCharge = serviceCharge;
	}
	public Double getServiceChargeVAT() {
		return ServiceChargeVAT;
	}
	public void setServiceChargeVAT(Double serviceChargeVAT) {
		ServiceChargeVAT = serviceChargeVAT;
	}
	public Double getVatPercent() {
		return VatPercent;
	}
	public void setVatPercent(Double vatPercent) {
		VatPercent = vatPercent;
	}
	public Double getServiceChargePercent() {
		return ServiceChargePercent;
	}
	public void setServiceChargePercent(Double serviceChargePercent) {
		ServiceChargePercent = serviceChargePercent;
	}
	public int getIsCalculateServiceCharge() {
		return IsCalculateServiceCharge;
	}
	public void setIsCalculateServiceCharge(int isCalculateServiceCharge) {
		IsCalculateServiceCharge = isCalculateServiceCharge;
	}
	public int getSessionID() {
		return SessionID;
	}
	public void setSessionID(int sessionID) {
		SessionID = sessionID;
	}
	public String getVoidReason() {
		return VoidReason;
	}
	public void setVoidReason(String voidReason) {
		VoidReason = voidReason;
	}
	public String getVoidTime() {
		return VoidTime;
	}
	public void setVoidTime(String voidTime) {
		VoidTime = voidTime;
	}
	public int getMemberID() {
		return MemberID;
	}
	public void setMemberID(int memberID) {
		MemberID = memberID;
	}
	public String getTransactionNote() {
		return TransactionNote;
	}
	public void setTransactionNote(String transactionNote) {
		TransactionNote = transactionNote;
	}
	public int getAlreadySendToHQ() {
		return AlreadySendToHQ;
	}
	public void setAlreadySendToHQ(int alreadySendToHQ) {
		AlreadySendToHQ = alreadySendToHQ;
	}
	public String getSendToHQDateTime() {
		return SendToHQDateTime;
	}
	public void setSendToHQDateTime(String sendToHQDateTime) {
		SendToHQDateTime = sendToHQDateTime;
	}
}
