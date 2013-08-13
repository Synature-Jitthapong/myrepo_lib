package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.OrderSendData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class MenuUtil {
	private DataBaseHelper dbHelper;

	String sourceTableName = "sourcemenu_tmp";
	String destTableName = "destmenu_tmp";
	String menuTableName = "menu_tmp";
	
	public MenuUtil(Context c){
		dbHelper = new DataBaseHelper(c);
	}
	
	// tmp table
	public void createMenuTemp(){
		dbHelper.openDataBase();
		
		dbHelper.myDataBase.execSQL("DROP TABLE IF EXISTS " + menuTableName);
		
		String strSql = "CREATE TABLE " + menuTableName + " ( " +
				" OrderDetailID INTEGER, " +
				" OrderStatusID INTEGER, " +
				" ProductID INTEGER, " +
				" ProductName TEXT, " +
				" ProductQty REAL, " +
				" SeatID INTEGER " +
				" )";
		dbHelper.myDataBase.execSQL(strSql);
		dbHelper.closeDataBase();
	}
	
	public void createMoveMenuTemp(){
		dbHelper.openDataBase();
		
		dbHelper.myDataBase.execSQL("DROP TABLE IF EXISTS " + sourceTableName);
		dbHelper.myDataBase.execSQL("DROP TABLE IF EXISTS " + destTableName);
		
		String strSql = "CREATE TABLE " + sourceTableName + " ( " +
				" OrderDetailID INTEGER, " +
				" OrderStatusID INTEGER, " +
				" ProductID INTEGER, " +
				" ProductName TEXT, " +
				" ProductQty REAL, " +
				" SeatID INTEGER " +
				" )";
		dbHelper.myDataBase.execSQL(strSql);
		

		strSql = "CREATE TABLE " + destTableName + " ( " + 
				" OrderDetailID INTEGER, " +
				" OrderStatusID INTEGER, " +
				" ProductID INTEGER, " +
				" ProductName TEXT, " +
				" ProductQty REAL, " +
				" SeatID INTEGER " +
				" )";
		dbHelper.myDataBase.execSQL(strSql);
		
		dbHelper.closeDataBase();
	}
	
	// delete menu when tap list
	protected void deleteReMovedMenu(int orderId, int productId, double productQty){
		String strSql = "";
		if(productQty > 0){
			strSql = "UPDATE " + destTableName +
					" SET ProductQty=" + productQty +
					" WHERE OrderDetailID=" + orderId + 
					" AND ProductID=" + productId;	
		}else{
			strSql = "DELETE FROM " + destTableName +
					" WHERE OrderDetailID=" + orderId + 
					" AND ProductID=" + productId;
		}
		
		dbHelper.openDataBase();
		dbHelper.myDataBase.execSQL(strSql);
		dbHelper.closeDataBase();
	}
		
	// delete menu when tap list
	protected void deleteMovedMenu(int orderId, int productId, double productQty){
		String strSql = "";
		if(productQty > 0){
			strSql = "UPDATE " + sourceTableName +
					" SET ProductQty=" + productQty +
					" WHERE OrderDetailID=" + orderId + 
					" AND ProductID=" + productId;	
		}else{
			strSql = "DELETE FROM " + sourceTableName +
					" WHERE OrderDetailID=" + orderId + 
					" AND ProductID=" + productId;
		}
		
		dbHelper.openDataBase();
		dbHelper.myDataBase.execSQL(strSql);
		dbHelper.closeDataBase();
	}
	
	// list moved menu
	public OrderSendData.OrderDetail listMovedMenu(int orderDetailId){
		OrderSendData.OrderDetail detail = new OrderSendData.OrderDetail();
		
		String strSql = " SELECT OrderDetailID, OrderStatusID, ProductID, ProductName, " +
				" SUM(ProductQty) AS TotalQty, SeatID " +
				" FROM " + destTableName +
				" WHERE OrderDetailID=" + orderDetailId +
				" GROUP BY OrderDetailID";
		
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		
		if(cursor.moveToFirst()){
			do{
				detail.setiOrderID(cursor.getInt(cursor.getColumnIndex("OrderDetailID")));
				detail.setiOrderStatusID(cursor.getInt(cursor.getColumnIndex("OrderStatusID")));
				detail.setiProductID(cursor.getInt(cursor.getColumnIndex("ProductID")));
				detail.setSzProductName(cursor.getString(cursor.getColumnIndex("ProductName")));
				detail.setfItemQty(cursor.getDouble(cursor.getColumnIndex("TotalQty")));
				detail.setiSeatID(cursor.getInt(cursor.getColumnIndex("SeatID")));
			}while(cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		
		return detail;
	}
	
	public List<OrderSendData.OrderDetail> listMovedMenu(){
		List<OrderSendData.OrderDetail> detailLst = 
				new ArrayList<OrderSendData.OrderDetail>();
		
		String strSql = " SELECT OrderDetailID, OrderStatusID, ProductID, ProductName, " +
				" SUM(ProductQty) AS TotalQty, SeatID " +
				" FROM " + destTableName + 
				" GROUP BY OrderDetailID";
		
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		
		if(cursor.moveToFirst()){
			do{
				OrderSendData.OrderDetail detail = new OrderSendData.OrderDetail();
				detail.setiOrderID(cursor.getInt(cursor.getColumnIndex("OrderDetailID")));
				detail.setiOrderStatusID(cursor.getInt(cursor.getColumnIndex("OrderStatusID")));
				detail.setiProductID(cursor.getInt(cursor.getColumnIndex("ProductID")));
				detail.setSzProductName(cursor.getString(cursor.getColumnIndex("ProductName")));
				detail.setfItemQty(cursor.getDouble(cursor.getColumnIndex("TotalQty")));
				detail.setiSeatID(cursor.getInt(cursor.getColumnIndex("SeatID")));
				detailLst.add(detail);
			}while(cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		
		return detailLst;
	}
	
	// List menu from temp
	public OrderSendData.OrderDetail listMenuToMove(int orderDetailId){
		OrderSendData.OrderDetail detail = new OrderSendData.OrderDetail();
		
		String strSql = " SELECT OrderDetailID, OrderStatusID, ProductID, ProductName, " +
				" SUM(ProductQty) AS TotalQty, SeatID " +
				" FROM " + sourceTableName + 
				" WHERE OrderDetailID=" + orderDetailId +
				" GROUP BY OrderDetailID";
		
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		
		if(cursor.moveToFirst()){
			do{
				detail.setiOrderID(cursor.getInt(cursor.getColumnIndex("OrderDetailID")));
				detail.setiOrderStatusID(cursor.getInt(cursor.getColumnIndex("OrderStatusID")));
				detail.setiProductID(cursor.getInt(cursor.getColumnIndex("ProductID")));
				detail.setSzProductName(cursor.getString(cursor.getColumnIndex("ProductName")));
				detail.setfItemQty(cursor.getDouble(cursor.getColumnIndex("TotalQty")));
				detail.setiSeatID(cursor.getInt(cursor.getColumnIndex("SeatID")));
			}while(cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		
		return detail;
	}
	
	public List<OrderSendData.OrderDetail> listMenuToMove(){
		List<OrderSendData.OrderDetail> detailLst = 
				new ArrayList<OrderSendData.OrderDetail>();
		
		String strSql = " SELECT OrderDetailID, OrderStatusID, ProductID, ProductName," +
				" SUM(ProductQty) AS TotalQty, SeatID " +
				" FROM " + sourceTableName + 
				" GROUP BY OrderDetailID";
		
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		
		if(cursor.moveToFirst()){
			do{
				OrderSendData.OrderDetail detail = new OrderSendData.OrderDetail();
				detail.setiOrderID(cursor.getInt(cursor.getColumnIndex("OrderDetailID")));
				detail.setiOrderStatusID(cursor.getInt(cursor.getColumnIndex("OrderStatusID")));
				detail.setiProductID(cursor.getInt(cursor.getColumnIndex("ProductID")));
				detail.setSzProductName(cursor.getString(cursor.getColumnIndex("ProductName")));
				detail.setfItemQty(cursor.getDouble(cursor.getColumnIndex("TotalQty")));
				detail.setiSeatID(cursor.getInt(cursor.getColumnIndex("SeatID")));
				detailLst.add(detail);
			}while(cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		
		return detailLst;
	}
	
	public int checkSelectedMenu(int orderId, int productId){
		int foundProId = 0;
		String strSql = "SELECT ProductID " +
				" FROM " + menuTableName +
				" WHERE OrderDetailID=" + orderId + 
				" AND ProductID=" + productId;
		
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			foundProId = cursor.getInt(0);
		}
		cursor.close();
		dbHelper.closeDataBase();
		return foundProId;
	}
	
	public List<OrderSendData.OrderDetail> listMenuToCancel(){
		List<OrderSendData.OrderDetail> detailLst = 
				new ArrayList<OrderSendData.OrderDetail>();
		
		String strSql = " SELECT OrderDetailID, OrderStatusID, ProductID, ProductName, ProductQty, SeatID " +
				" FROM " + menuTableName;
		
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		
		if(cursor.moveToFirst()){
			do{
				OrderSendData.OrderDetail detail = new OrderSendData.OrderDetail();
				detail.setiOrderID(cursor.getInt(cursor.getColumnIndex("OrderDetailID")));
				detail.setiOrderStatusID(cursor.getInt(cursor.getColumnIndex("OrderStatusID")));
				detail.setiProductID(cursor.getInt(cursor.getColumnIndex("ProductID")));
				detail.setSzProductName(cursor.getString(cursor.getColumnIndex("ProductName")));
				detail.setfItemQty(cursor.getDouble(cursor.getColumnIndex("ProductQty")));
				detail.setiSeatID(cursor.getInt(cursor.getColumnIndex("SeatID")));
				
				detailLst.add(detail);
			}while(cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		
		return detailLst;
	}
	
	// re move menu
	public void reMoveMenu(int orderId, int orderStatusId, int productId, String productName, 
			double productQty, int seatId){
		int movedQty = getReMovedMenu(orderId, productId);
		
		dbHelper.openDataBase();
		if(movedQty > 0){
			dbHelper.myDataBase.execSQL(
					"UPDATE " + sourceTableName + 
					" SET ProductQty=" + (movedQty + 1) +
					" WHERE OrderDetailID=" + orderId + 
					" AND ProductID=" + productId);
		}else{
			ContentValues cv = new ContentValues();
			cv.put("OrderDetailID", orderId);
			cv.put("OrderStatusID", orderStatusId);
			cv.put("ProductID", productId);
			cv.put("ProductName", productName);
			cv.put("ProductQty", 1);
			cv.put("SeatID", seatId);
			dbHelper.myDataBase.insert(sourceTableName, null, cv);
		}
		dbHelper.closeDataBase();
		
		deleteReMovedMenu(orderId, productId, --productQty);
	}
		
	public int getReMovedMenu(int orderId, int productId){
		int movedQty = 0;
		dbHelper.openDataBase();
		String strSql = "SELECT ProductQty " +
				" FROM " + sourceTableName + 
				" WHERE OrderDetailID=" + orderId +
				" AND ProductID=" + productId;
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			movedQty = cursor.getInt(0);
		}
		cursor.close();
		dbHelper.closeDataBase();
		return movedQty;
	}
	
	public int getMovedMenu(int orderId, int productId){
		int movedQty = 0;
		dbHelper.openDataBase();
		String strSql = "SELECT ProductQty " +
				" FROM " + destTableName + 
				" WHERE OrderDetailID=" + orderId +
				" AND ProductID=" + productId;
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			movedQty = cursor.getInt(0);
		}
		cursor.close();
		dbHelper.closeDataBase();
		return movedQty;
	}
	
	// move menu
	public void moveMenu(int orderId, int orderStatusId, int productId, String productName, 
			double productQty, int seatId){
		int movedQty = getMovedMenu(orderId, productId);
		
		dbHelper.openDataBase();
		if(movedQty > 0){
			dbHelper.myDataBase.execSQL(
					"UPDATE " + destTableName + 
					" SET ProductQty=" + (movedQty + 1) +
					" WHERE OrderDetailID=" + orderId + 
					" AND ProductID=" + productId);
		}else{
			ContentValues cv = new ContentValues();
			cv.put("OrderDetailID", orderId);
			cv.put("OrderStatusID", orderStatusId);
			cv.put("ProductID", productId);
			cv.put("ProductName", productName);
			cv.put("ProductQty", 1);
			cv.put("SeatID", seatId);
			
			dbHelper.myDataBase.insert(destTableName, null, cv);
		}
		dbHelper.closeDataBase();
		
		deleteMovedMenu(orderId, productId, --productQty);
	}
		
	// prepare data for cancel
	public void prepareMenuForCacnel(OrderSendData.OrderDetail detail){
		if(detail != null){
			dbHelper.openDataBase();
			boolean found = false;
			Cursor cursor = dbHelper.myDataBase.rawQuery(
					"SELECT OrderDetailID, ProductID " +
					" FROM " + menuTableName +
					" WHERE OrderDetailID=" + detail.getiOrderID() +
					" AND ProductID=" + detail.getiProductID(), null);
			if(cursor.moveToFirst()){
				if(cursor.getInt(1) != 0){
					found = true;
				}
			}
			cursor.close();
			
			if(found){
				// delete
				dbHelper.myDataBase.execSQL(
						"DELETE FROM " + menuTableName +
						" WHERE OrderDetailID=" + detail.getiOrderID() + 
						" AND ProductID=" + detail.getiProductID());
			}else{
				ContentValues cv = new ContentValues();
				cv.put("OrderDetailID", detail.getiOrderID());
				cv.put("OrderStatusID", detail.getiOrderStatusID());
				cv.put("ProductID", detail.getiProductID());
				cv.put("ProductName", detail.getSzProductName());
				cv.put("ProductQty", detail.getfItemQty());
				cv.put("SeatID", detail.getiSeatID());
				dbHelper.myDataBase.insert(menuTableName, null, cv);
			}
			dbHelper.closeDataBase();
		}
	}
	
	// prepare data for move
	public void prepareMenuForMove(OrderSendData data){
		if(data != null){
			dbHelper.openDataBase();
			if(data.xListOrderDetail != null && data.xListOrderDetail.size() > 0){
				for(OrderSendData.OrderDetail detail : data.xListOrderDetail){
					if(detail.getiProductType() != 14 && detail.getiProductType() != 15){
						ContentValues cv = new ContentValues();
						cv.put("OrderDetailID", detail.getiOrderID());
						cv.put("OrderStatusID", detail.getiOrderStatusID());
						cv.put("ProductID", detail.getiProductID());
						cv.put("ProductName", detail.getSzProductName());
						cv.put("ProductQty", detail.getfItemQty());
						cv.put("SeatID", detail.getiSeatID());
						dbHelper.myDataBase.insert(sourceTableName, null, cv);
					}
				}
			}
			dbHelper.closeDataBase();
		}
	}
	
	
}
