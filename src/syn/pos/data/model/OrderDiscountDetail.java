package syn.pos.data.model;

public class OrderDiscountDetail {
	private int OrderDetailID;
	private int TransactionID;
	private int ComputerID;
	private int FoodID;
	private double MemberDiscount;
	private double StaffDiscount;
	private double CouponDiscount;
	private double OtherPercentDiscount;
	private double EachProductOtherDiscount;
	private double VoucherDiscount;
	private double PricePromotionDiscount;
	private double SalePrice;
	private double SaleVAT;
	private double SaleServiceCharge;
	private double TotalRetailPrice;
	private double TotalSalePrice;
	
	public int getOrderDetailID() {
		return OrderDetailID;
	}
	public void setOrderDetailID(int orderDetailID) {
		OrderDetailID = orderDetailID;
	}
	public int getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(int transactionID) {
		TransactionID = transactionID;
	}
	public int getComputerID() {
		return ComputerID;
	}
	public void setComputerID(int computerID) {
		ComputerID = computerID;
	}
	public int getFoodID() {
		return FoodID;
	}
	public void setFoodID(int foodID) {
		FoodID = foodID;
	}
	public double getMemberDiscount() {
		return MemberDiscount;
	}
	public void setMemberDiscount(double memberDiscount) {
		MemberDiscount = memberDiscount;
	}
	public double getStaffDiscount() {
		return StaffDiscount;
	}
	public void setStaffDiscount(double staffDiscount) {
		StaffDiscount = staffDiscount;
	}
	public double getCouponDiscount() {
		return CouponDiscount;
	}
	public void setCouponDiscount(double couponDiscount) {
		CouponDiscount = couponDiscount;
	}
	public double getOtherPercentDiscount() {
		return OtherPercentDiscount;
	}
	public void setOtherPercentDiscount(double otherPercentDiscount) {
		OtherPercentDiscount = otherPercentDiscount;
	}
	public double getEachProductOtherDiscount() {
		return EachProductOtherDiscount;
	}
	public void setEachProductOtherDiscount(double eachProductOtherDiscount) {
		EachProductOtherDiscount = eachProductOtherDiscount;
	}
	public double getVoucherDiscount() {
		return VoucherDiscount;
	}
	public void setVoucherDiscount(double voucherDiscount) {
		VoucherDiscount = voucherDiscount;
	}
	public double getPricePromotionDiscount() {
		return PricePromotionDiscount;
	}
	public void setPricePromotionDiscount(double pricePromotionDiscount) {
		PricePromotionDiscount = pricePromotionDiscount;
	}
	public double getSalePrice() {
		return SalePrice;
	}
	public void setSalePrice(double salePrice) {
		SalePrice = salePrice;
	}
	public double getSaleVAT() {
		return SaleVAT;
	}
	public void setSaleVAT(double saleVAT) {
		SaleVAT = saleVAT;
	}
	public double getSaleServiceCharge() {
		return SaleServiceCharge;
	}
	public void setSaleServiceCharge(double saleServiceCharge) {
		SaleServiceCharge = saleServiceCharge;
	}
	public double getTotalRetailPrice() {
		return TotalRetailPrice;
	}
	public void setTotalRetailPrice(double totalRetailPrice) {
		TotalRetailPrice = totalRetailPrice;
	}
	public double getTotalSalePrice() {
		return TotalSalePrice;
	}
	public void setTotalSalePrice(double totalSalePrice) {
		TotalSalePrice = totalSalePrice;
	}
}
