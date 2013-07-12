package syn.pos.data.model;

import java.util.List;

public class Order extends OrderTransaction {
	public List<OrderDetail> orderDetailList;
	
	public Order(int shopId, int computerId, int staffId){
		super.setShopID(shopId);
		super.setComputerID(computerId);
		super.setOpenStaffID(staffId);
		super.setStaffID(staffId);
	}
	
	public Order(){
		
	}
}
