package syn.pos.data.model;

import java.util.Date;
import java.util.List;

public class OrderDetail extends OrderDiscountDetail {
	public List<POSData_OrderTransInfo.POSData_CommentInfo> OrderComment; 
	
	private int ShopID;
	private int ProductID;
	private int ProductType;
	private int OrderStatusID;
	private int SaleMode;
	private double Qty;
	private double PricePerUnit;
	private double RetailPrice;
	private double MinimumPrice;
	private String Comment;
	private String OtherFoodName;
	private int OtherProductGroupID;
	private int DiscountAllow;
	private int ProductTotalPriceRoundingType;
	private int LastTransactionID;
	private int LastComputerID;
	private int Deleted;
	private int PrinterID;
	private int PromotionPriceID;
	private int PromotionNPriceID;
	private int OrderStaffID;
	private int OrderComputerID;
	private int OrderTableID;
	private int VoidStaffID;
	private int VatType;
	private float VatRate;
	private double VatAmount;
	private int PrintGroup;
	private int NoPrintBill;
	private int NoRePrintOrder;
	private int StaffID;
	private int SubRoomID;
	private Date StartTime;
	private Date DurationTime;	
	private int HasServiceCharge;
	private int PrintStatus;
	private int ProcessID;
	private Date SubmitOrderDateTime;
	private double PercentDiscount;
	private int ParentOrderDiscountID;
	private int ParentOrderDetailID;
	private String MenuName;
	private String OrderRemark;
	private double PriceDiscountAmount;
	private double DiscountValue;
	
	public List<POSData_OrderTransInfo.POSData_CommentInfo> getOrderComment() {
		return OrderComment;
	}
	
	public void setOrderComment(
			List<POSData_OrderTransInfo.POSData_CommentInfo> orderComment) {
		OrderComment = orderComment;
	}
	
	public int getParentOrderDetailID() {
		return ParentOrderDetailID;
	}

	public void setParentOrderDetailID(int parentOrderDetailID) {
		ParentOrderDetailID = parentOrderDetailID;
	}

	public double getPriceDiscountAmount() {
		return PriceDiscountAmount;
	}

	public void setPriceDiscountAmount(double priceDiscountAmount) {
		PriceDiscountAmount = priceDiscountAmount;
	}

	public double getDiscountValue() {
		return DiscountValue;
	}

	public void setDiscountValue(double discountValue) {
		DiscountValue = discountValue;
	}

	public String getOrderRemark() {
		return OrderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		OrderRemark = orderRemark;
	}
	public String getMenuName() {
		return MenuName;
	}
	public void setMenuName(String menuName) {
		MenuName = menuName;
	}
	public int getShopID() {
		return ShopID;
	}
	public void setShopID(int shopID) {
		ShopID = shopID;
	}
	public int getParentOrderDiscountID() {
		return ParentOrderDiscountID;
	}
	public void setParentOrderDiscountID(int parentOrderDiscountID) {
		ParentOrderDiscountID = parentOrderDiscountID;
	}
	public double getPercentDiscount() {
		return PercentDiscount;
	}
	public void setPercentDiscount(double percentDiscount) {
		PercentDiscount = percentDiscount;
	}
	public double getVatAmount() {
		return VatAmount;
	}
	public void setVatAmount(double vatAmount) {
		VatAmount = vatAmount;
	}
	public int getVatType() {
		return VatType;
	}
	public void setVatType(int vatType) {
		VatType = vatType;
	}
	public float getVatRate() {
		return VatRate;
	}
	public void setVatRate(float vatRate) {
		VatRate = vatRate;
	}
	public int getProductID() {
		return ProductID;
	}
	public void setProductID(int productID) {
		ProductID = productID;
	}
	public int getProductType() {
		return ProductType;
	}
	public void setProductType(int productSetType) {
		ProductType = productSetType;
	}
	public int getOrderStatusID() {
		return OrderStatusID;
	}
	public void setOrderStatusID(int orderStatusID) {
		OrderStatusID = orderStatusID;
	}
	public int getSaleMode() {
		return SaleMode;
	}
	public void setSaleMode(int saleMode) {
		SaleMode = saleMode;
	}
	public double getQty() {
		return Qty;
	}
	public void setQty(double qty) {
		Qty = qty;
	}
	public double getPricePerUnit() {
		return PricePerUnit;
	}
	public void setPricePerUnit(double pricePerUnit) {
		PricePerUnit = pricePerUnit;
	}
	public double getRetailPrice() {
		return RetailPrice;
	}
	public void setRetailPrice(double retailPrice) {
		RetailPrice = retailPrice;
	}
	public double getMinimumPrice() {
		return MinimumPrice;
	}
	public void setMinimumPrice(double minimumPrice) {
		MinimumPrice = minimumPrice;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	public String getOtherFoodName() {
		return OtherFoodName;
	}
	public void setOtherFoodName(String otherFoodName) {
		OtherFoodName = otherFoodName;
	}
	public int getOtherProductGroupID() {
		return OtherProductGroupID;
	}
	public void setOtherProductGroupID(int otherProductGroupID) {
		OtherProductGroupID = otherProductGroupID;
	}
	public int getDiscountAllow() {
		return DiscountAllow;
	}
	public void setDiscountAllow(int discountAllow) {
		DiscountAllow = discountAllow;
	}
	public int getProductTotalPriceRoundingType() {
		return ProductTotalPriceRoundingType;
	}
	public void setProductTotalPriceRoundingType(int productTotalPriceRoundingType) {
		ProductTotalPriceRoundingType = productTotalPriceRoundingType;
	}
	public int getLastTransactionID() {
		return LastTransactionID;
	}
	public void setLastTransactionID(int lastTransactionID) {
		LastTransactionID = lastTransactionID;
	}
	public int getLastComputerID() {
		return LastComputerID;
	}
	public void setLastComputerID(int lastComputerID) {
		LastComputerID = lastComputerID;
	}
	public int getDeleted() {
		return Deleted;
	}
	public void setDeleted(int deleted) {
		Deleted = deleted;
	}
	public int getPrinterID() {
		return PrinterID;
	}
	public void setPrinterID(int printerID) {
		PrinterID = printerID;
	}
	public int getPromotionPriceID() {
		return PromotionPriceID;
	}
	public void setPromotionPriceID(int promotionPriceID) {
		PromotionPriceID = promotionPriceID;
	}
	public int getPromotionNPriceID() {
		return PromotionNPriceID;
	}
	public void setPromotionNPriceID(int promotionNPriceID) {
		PromotionNPriceID = promotionNPriceID;
	}
	public int getOrderStaffID() {
		return OrderStaffID;
	}
	public void setOrderStaffID(int orderStaffID) {
		OrderStaffID = orderStaffID;
	}
	public int getOrderComputerID() {
		return OrderComputerID;
	}
	public void setOrderComputerID(int orderComputerID) {
		OrderComputerID = orderComputerID;
	}
	public int getOrderTableID() {
		return OrderTableID;
	}
	public void setOrderTableID(int orderTableID) {
		OrderTableID = orderTableID;
	}
	public int getVoidStaffID() {
		return VoidStaffID;
	}
	public void setVoidStaffID(int voidStaffID) {
		VoidStaffID = voidStaffID;
	}
	public int getPrintGroup() {
		return PrintGroup;
	}
	public void setPrintGroup(int printGroup) {
		PrintGroup = printGroup;
	}
	public int getNoPrintBill() {
		return NoPrintBill;
	}
	public void setNoPrintBill(int noPrintBill) {
		NoPrintBill = noPrintBill;
	}
	public int getNoRePrintOrder() {
		return NoRePrintOrder;
	}
	public void setNoRePrintOrder(int noRePrintOrder) {
		NoRePrintOrder = noRePrintOrder;
	}
	public int getStaffID() {
		return StaffID;
	}
	public void setStaffID(int staffID) {
		StaffID = staffID;
	}
	public int getSubRoomID() {
		return SubRoomID;
	}
	public void setSubRoomID(int subRoomID) {
		SubRoomID = subRoomID;
	}
	public Date getStartTime() {
		return StartTime;
	}
	public void setStartTime(Date startTime) {
		StartTime = startTime;
	}
	public Date getDurationTime() {
		return DurationTime;
	}
	public void setDurationTime(Date durationTime) {
		DurationTime = durationTime;
	}
	public int getHasServiceCharge() {
		return HasServiceCharge;
	}
	public void setHasServiceCharge(int hasServiceCharge) {
		HasServiceCharge = hasServiceCharge;
	}
	public int getPrintStatus() {
		return PrintStatus;
	}
	public void setPrintStatus(int printStatus) {
		PrintStatus = printStatus;
	}
	public int getProcessID() {
		return ProcessID;
	}
	public void setProcessID(int processID) {
		ProcessID = processID;
	}
	public Date getSubmitOrderDateTime() {
		return SubmitOrderDateTime;
	}
	public void setSubmitOrderDateTime(Date submitOrderDateTime) {
		SubmitOrderDateTime = submitOrderDateTime;
	}
	
	@Override
	public String toString() {
		return MenuName + " " + PricePerUnit;
	}
}
