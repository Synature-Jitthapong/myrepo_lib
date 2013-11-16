package syn.pos.data.model;

import java.util.List;

public class MenuDataItem {
	private int transactionId;
	private int computerId;
	private int orderDetailId;
	private int ProductID;
	private int MenuDeptID;
	private String MenuName;
	private String MenuName1;
	private String MenuName2;
	private String MenuShortName;
	private String MenuShortName1;
	private String MenuDesc;
	private String MenuSize;
	private double PricePerUnit;
	private double PricePerUnitExcludeVat;
	private double PricePerUnitIncludeVat;
	private double TotalPriceExcludeVat;
	private double TotalPriceIncludeVat;
	private String CurrencySymbol;
	private String ImgUrl;
	private int VatType;
	private double VatRate;
	private int DiscountAllow;
	private int IsOutOfStock;
	private double VatAmount;
	private String ProductUnitName;
	private String ProductCode;
	private double MaterialQty;
	private double MaterialCountQty;
	private String MaterialCountDiff;
	private String OrderComment;
	private int ProductTypeID;
	private double ProductQty;
	private int saleMode;
	private int saleMode2;
	private int memberId;
	private String productBarcode;
	private int seatId;
	private String seatName;
	private int courseId;
	private String courseName;

	private String holdDate;
	private String holdBy;
	private double holdQty;
	
	public List<MenuGroups.MenuComment> menuCommentList;
	public List<ProductGroups.PComponentSet> pCompSetLst;
		
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getSeatName() {
		return seatName;
	}
	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}
	public int getSeatId() {
		return seatId;
	}
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
	public String getMenuShortName() {
		return MenuShortName;
	}
	public void setMenuShortName(String menuShortName) {
		MenuShortName = menuShortName;
	}
	public String getMenuShortName1() {
		return MenuShortName1;
	}
	public void setMenuShortName1(String menuShortName1) {
		MenuShortName1 = menuShortName1;
	}
	public String getMenuName1() {
		return MenuName1;
	}
	public void setMenuName1(String menuName1) {
		MenuName1 = menuName1;
	}
	public String getMenuName2() {
		return MenuName2;
	}
	public void setMenuName2(String menuName2) {
		MenuName2 = menuName2;
	}
	public String getMenuDesc() {
		return MenuDesc;
	}
	public void setMenuDesc(String menuDesc) {
		MenuDesc = menuDesc;
	}
	public String getProductBarcode() {
		return productBarcode;
	}
	public void setProductBarcode(String productBarcode) {
		this.productBarcode = productBarcode;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getSaleMode2() {
		return saleMode2;
	}
	public void setSaleMode2(int saleMode2) {
		this.saleMode2 = saleMode2;
	}
	public int getSaleMode() {
		return saleMode;
	}
	public void setSaleMode(int saleMode) {
		this.saleMode = saleMode;
	}
	public double getProductQty() {
		return ProductQty;
	}
	public void setProductQty(double productQty) {
		ProductQty = productQty;
	}
	public int getProductTypeID() {
		return ProductTypeID;
	}
	public void setProductTypeID(int productTypeID) {
		ProductTypeID = productTypeID;
	}
	public String getHoldDate() {
		return holdDate;
	}
	public void setHoldDate(String holdDate) {
		this.holdDate = holdDate;
	}
	public String getHoldBy() {
		return holdBy;
	}
	public void setHoldBy(String holdBy) {
		this.holdBy = holdBy;
	}
	public double getHoldQty() {
		return holdQty;
	}
	public void setHoldQty(double holdQty) {
		this.holdQty = holdQty;
	}
	public String getOrderComment() {
		return OrderComment;
	}
	public void setOrderComment(String orderComment) {
		OrderComment = orderComment;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getComputerId() {
		return computerId;
	}
	public void setComputerId(int computerId) {
		this.computerId = computerId;
	}
	public int getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(int orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	public double getMaterialCountQty() {
		return MaterialCountQty;
	}
	public void setMaterialCountQty(double materialCountQty) {
		MaterialCountQty = materialCountQty;
	}
	
	public String getMaterialCountDiff() {
		return MaterialCountDiff;
	}
	public void setMaterialCountDiff(String materialCountDiff) {
		MaterialCountDiff = materialCountDiff;
	}
	public double getMaterialQty() {
		return MaterialQty;
	}
	public void setMaterialQty(double materialQty) {
		MaterialQty = materialQty;
	}
	public String getProductCode() {
		return ProductCode;
	}
	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}
	public String getProductUnitName() {
		return ProductUnitName;
	}
	public void setProductUnitName(String productUnitName) {
		ProductUnitName = productUnitName;
	}
	public int getMenuDeptID() {
		return MenuDeptID;
	}
	public void setMenuDeptID(int menuDeptID) {
		MenuDeptID = menuDeptID;
	}
	public double getTotalPriceIncludeVat() {
		return TotalPriceIncludeVat;
	}
	public void setTotalPriceIncludeVat(double totalPriceIncludeVat) {
		TotalPriceIncludeVat = totalPriceIncludeVat;
	}
	public double getPricePerUnitIncludeVat() {
		return PricePerUnitIncludeVat;
	}
	public void setPricePerUnitIncludeVat(double pricePerUnitIncludeVat) {
		PricePerUnitIncludeVat = pricePerUnitIncludeVat;
	}
	public double getTotalPriceExcludeVat() {
		return TotalPriceExcludeVat;
	}
	public void setTotalPriceExcludeVat(double totalPriceExcludeVat) {
		TotalPriceExcludeVat = totalPriceExcludeVat;
	}
	public double getPricePerUnitExcludeVat() {
		return PricePerUnitExcludeVat;
	}
	public void setPricePerUnitExcludeVat(double pricePerUnitExcludeVat) {
		PricePerUnitExcludeVat = pricePerUnitExcludeVat;
	}
	public double getVatAmount() {
		return VatAmount;
	}
	public void setVatAmount(double vatAmount) {
		VatAmount = vatAmount;
	}
	public int getDiscountAllow() {
		return DiscountAllow;
	}
	public void setDiscountAllow(int discountAllow) {
		DiscountAllow = discountAllow;
	}
	public int getIsOutOfStock() {
		return IsOutOfStock;
	}
	public void setIsOutOfStock(int isOutOfStock) {
		IsOutOfStock = isOutOfStock;
	}
	public int getVatType() {
		return VatType;
	}
	public void setVatType(int vatType) {
		VatType = vatType;
	}
	public double getVatRate() {
		return VatRate;
	}
	public void setVatRate(double vatRate) {
		VatRate = vatRate;
	}
	public String getImgUrl() {
		return ImgUrl;
	}
	public void setImgUrl(String imgUrl) {
		ImgUrl = imgUrl;
	}
	public int getProductID() {
		return ProductID;
	}
	public void setProductID(int productID) {
		ProductID = productID;
	}
	public String getMenuName() {
		return MenuName;
	}
	public void setMenuName(String menuName) {
		MenuName = menuName;
	}
	public String getMenuSize() {
		return MenuSize;
	}
	public void setMenuSize(String menuSize) {
		MenuSize = menuSize;
	}
	public double getPricePerUnit() {
		return PricePerUnit;
	}
	public void setPricePerUnit(double pricePerUnit) {
		PricePerUnit = pricePerUnit;
	}
	public String getCurrencySymbol() {
		return CurrencySymbol;
	}
	public void setCurrencySymbol(String currencySymbol) {
		CurrencySymbol = currencySymbol;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return MenuName.toString();
	}
}
