package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.MenuGroups;
import syn.pos.data.model.MenuGroups.MenuComment;
import syn.pos.data.model.OrderDetail;
import syn.pos.data.model.OrderTransaction;
import syn.pos.data.model.POSData_OrderTransInfo;
import syn.pos.data.model.POSTable_MoveMenuData;
import syn.pos.data.model.POSTable_MoveMenuData.POSData_MoveMenu;
import syn.pos.data.model.Order;
import syn.pos.data.model.ProductGroups;
import syn.pos.data.model.TableInfo;
import syn.pos.mobile.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public abstract class POSOrderBase {
	private DataBaseHelper dbHelper;
	private Context context;
	private GlobalVar globalVar;

	private final String ORDER_TRANSACTION_TABLE = "OrderTransaction";
	private final String ORDER_DETAIL_TABLE = "OrderDetail";
	private final String ORDER_SET_OF_PRODUCT = "OrderSetOfProduct";
	private final String ORDER_COMMENT_TABLE = "OrderComment";
	private final String ORDER_COMMENT_TABLE_TMP = "OrderCommentTmp";
	private final String ORDER_SET_TABLE = "OrderSet";
	private final String ORDER_SET_TABLE_TMP = "OrderSetTmp";
	private final String ORDER_SET_COMMENT_TABLE = "OrderSetComment";
	private final String ORDER_SET_COMMENT_TABLE_TMP = "OrderSetCommentTmp";

	public POSOrderBase(Context context) {
		this.context = context;
		dbHelper = new DataBaseHelper(context);
		globalVar = new GlobalVar(context, dbHelper);
	}

	private void openDatabase() {
		dbHelper.openDataBase();
	}

	private void closeDatabase() {
		dbHelper.closeDataBase();
	}

	public double summaryTotalPrice(int transactionId, int orderDetailId) {
		double totalPrice = 0.0d;
		openDatabase();

		closeDatabase();
		return totalPrice;
	}

	public OrderTransaction getQueuNameFromTransaction(int transactionId,
			int computerId) {
		OrderTransaction tran = new OrderTransaction();
		String strSql = "SELECT QueueID, QueueName, NoCustomer " + " FROM "
				+ ORDER_TRANSACTION_TABLE + " WHERE TransactionID="
				+ transactionId + " AND ComputerID=" + computerId;
		openDatabase();

		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			tran.setQueueID(cursor.getInt(cursor.getColumnIndex("QueueID")));
			tran.setQueueName(cursor.getString(cursor
					.getColumnIndex("QueueName")));
			tran.setNoCustomer(cursor.getInt(cursor
					.getColumnIndex("NoCustomer")));
		}
		cursor.close();

		closeDatabase();
		return tran;
	}

	public OrderTransaction getTableNameFromTransaction(int transactionId,
			int computerId) {
		OrderTransaction tran = new OrderTransaction();
		String strSql = "SELECT TableID, TableName, NoCustomer " + " FROM "
				+ ORDER_TRANSACTION_TABLE + " WHERE TransactionID="
				+ transactionId + " AND ComputerID=" + computerId;
		openDatabase();

		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			tran.setTableID(cursor.getInt(cursor.getColumnIndex("TableID")));
			tran.setTableName(cursor.getString(cursor
					.getColumnIndex("TableName")));
			tran.setNoCustomer(cursor.getInt(cursor
					.getColumnIndex("NoCustomer")));
		}
		cursor.close();

		closeDatabase();
		return tran;
	}

	public void setQueueIdToTransaction(int transactionId, int computerId,
			int queueId, String queueName, int custNo) {
		String strSql = "UPDATE " + ORDER_TRANSACTION_TABLE + " SET QueueID="
				+ queueId + ", " + " QueueName='" + queueName + "', "
				+ " NoCustomer=" + custNo + ", " + " TableID=0, "
				+ " TableName='', " + " UpdateDate='"
				+ globalVar.dateTimeFormat.format(globalVar.date) + "' "
				+ " WHERE TransactionID=" + transactionId + " AND ComputerID="
				+ computerId;
		openDatabase();
		try {
			dbHelper.myDataBase.execSQL(strSql);
		} catch (SQLException e) {
			Log.appendLog(context,
					"setQueueIdToTransaction : " + e.getMessage());
		}
		closeDatabase();
	}

	public void setMemberIdToTransaction(int transactionId, int computerId,
			int memberId, String memberName) {
		String strSql = "UPDATE " + ORDER_TRANSACTION_TABLE + " SET MemberID="
				+ memberId + ", " + " MemberName='" + memberName + "', "
				+ " UpdateDate='"
				+ globalVar.dateTimeFormat.format(globalVar.date) + "' "
				+ " WHERE TransactionID=" + transactionId + " AND ComputerID="
				+ computerId;
		openDatabase();
		try {
			dbHelper.myDataBase.execSQL(strSql);
		} catch (SQLException e) {
			Log.appendLog(context,
					"setTableIdToTransaction : " + e.getMessage());
		}
		closeDatabase();
	}

	public void setTableIdToTransaction(int transactionId, int computerId,
			int tableId, String tableName, int custNo) {
		String strSql = "UPDATE " + ORDER_TRANSACTION_TABLE + " SET TableID="
				+ tableId + ", " + " TableName='" + tableName + "', "
				+ " NoCustomer=" + custNo + ", " + " QueueID=0, "
				+ " QueueName='', " + " UpdateDate='"
				+ globalVar.dateTimeFormat.format(globalVar.date) + "' "
				+ " WHERE TransactionID=" + transactionId + " AND ComputerID="
				+ computerId;
		openDatabase();
		try {
			dbHelper.myDataBase.execSQL(strSql);
		} catch (SQLException e) {
			Log.appendLog(context,
					"setTableIdToTransaction : " + e.getMessage());
		}
		closeDatabase();
	}

	// count hold order
	public int countHoldOrder(int computerId) {
		int totalHold = 0;

		String strSql = " SELECT COUNT(TransactionID) " + " FROM "
				+ ORDER_TRANSACTION_TABLE + " WHERE TransactionStatusID=9 "
				+ " AND ComputerID=" + computerId + " AND SaleDate='"
				+ globalVar.dateFormat.format(globalVar.date) + "'";

		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			totalHold = cursor.getInt(0);
		}
		cursor.close();
		closeDatabase();
		return totalHold;
	}

	// list hold order
	public List<syn.pos.data.model.OrderHold> listHoldOrder() {
		List<syn.pos.data.model.OrderHold> holdLst = new ArrayList<syn.pos.data.model.OrderHold>();

		String strSql = " SELECT a.TransactionID, a.ComputerID, "
				+ " a.UpdateDate, a.UpdateStaffID, a.TransactionNote, "
				+ " a.TableID, a.QueueID, a.QueueName, a.TableName, a.NoCustomer, "
				+ " SUM(b.Qty) AS TotalQty, c.StaffCode, c.StaffName "
				+ " FROM " + ORDER_TRANSACTION_TABLE + " a " + " INNER JOIN "
				+ ORDER_DETAIL_TABLE + " b "
				+ " ON a.TransactionID=b.TransactionID "
				+ " AND a.ComputerID=b.ComputerID " + " LEFT JOIN Staffs c "
				+ " ON a.UpdateStaffID=c.StaffID "
				+ " WHERE a.TransactionStatusID=9 " + " AND SaleDate='"
				+ globalVar.dateFormat.format(globalVar.date) + "' "
				+ " GROUP BY a.TransactionID ";
		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			do {
				syn.pos.data.model.OrderHold order = new syn.pos.data.model.OrderHold();
				order.setTransactionID(cursor.getInt(cursor
						.getColumnIndex("TransactionID")));
				order.setComputerID(cursor.getInt(cursor
						.getColumnIndex("ComputerID")));
				order.setUpdateDate(cursor.getString(cursor
						.getColumnIndex("UpdateDate")));
				order.setTransactionNote(cursor.getString(cursor
						.getColumnIndex("TransactionNote")));
				order.setHoldQty(cursor.getDouble(cursor
						.getColumnIndex("TotalQty")));
				order.setQueueName(cursor.getString(cursor
						.getColumnIndex("QueueName")));
				order.setTableName(cursor.getString(cursor
						.getColumnIndex("TableName")));
				order.setQueueID(cursor.getInt(cursor.getColumnIndex("QueueID")));
				holdLst.add(order);
			} while (cursor.moveToNext());
		}
		cursor.close();
		closeDatabase();
		return holdLst;
	}

	// list order grouper
	public List<syn.pos.data.model.MenuDataItem> listAllOrder(
			int transactionId, int computerId) {
		List<syn.pos.data.model.MenuDataItem> ml = new ArrayList<syn.pos.data.model.MenuDataItem>();

		String strSql = "SELECT a.TransactionID, a.ComputerID, b.OrderDetailID, "
				+ " b.ProductID,  b.Qty, b.PricePerUnit, b.TotalRetailPrice, b.SaleMode, "
				+ " b.OrderComment, c.ProductTypeID, d.MenuName_0, d.MenuName_1, d.MenuName_2, "
				+ " d.MenuShortName_0, d.MenuShortName_1, d.MenuImageLink "
				+ " FROM "
				+ ORDER_TRANSACTION_TABLE
				+ " a "
				+ " INNER JOIN "
				+ ORDER_DETAIL_TABLE
				+ " b "
				+ " ON a.TransactionID=b.TransactionID AND a.ComputerID=b.ComputerID "
				+ " INNER JOIN Products c "
				+ " ON b.ProductID=c.ProductID "
				+ " INNER JOIN MenuItem d "
				+ " ON c.ProductID=d.ProductID "
				+ " WHERE a.TransactionID="
				+ transactionId
				+ " AND a.ComputerID="
				+ computerId
				+ " AND a.TransactionStatusID=1 ORDER BY b.OrderDetailID ";

		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);

		if (cursor.moveToFirst()) {
			do {
				syn.pos.data.model.MenuDataItem mi = new syn.pos.data.model.MenuDataItem();
				mi.setTransactionId(cursor.getInt(cursor
						.getColumnIndex("TransactionID")));
				mi.setComputerId(cursor.getInt(cursor
						.getColumnIndex("ComputerID")));
				mi.setOrderDetailId(cursor.getInt(cursor
						.getColumnIndex("OrderDetailID")));
				mi.setProductID(cursor.getInt(cursor
						.getColumnIndex("ProductID")));
				mi.setMenuName(cursor.getString(cursor
						.getColumnIndex("MenuName_0")));
				mi.setMenuName1(cursor.getString(cursor
						.getColumnIndex("MenuName_1")));
				mi.setMenuName2(cursor.getString(cursor
						.getColumnIndex("MenuName_2")));
				mi.setMenuShortName(cursor.getString(cursor.getColumnIndex("MenuShortName_0")));
				mi.setMenuShortName1(cursor.getString(cursor.getColumnIndex("MenuShortName_1")));
				mi.setProductQty(cursor.getDouble(cursor.getColumnIndex("Qty")));
				mi.setPricePerUnit(cursor.getDouble(cursor
						.getColumnIndex("PricePerUnit")));
				mi.setOrderComment(cursor.getString(cursor
						.getColumnIndex("OrderComment")));
				mi.setProductTypeID(cursor.getInt(cursor
						.getColumnIndex("ProductTypeID")));
				mi.setImgUrl(cursor.getString(cursor
						.getColumnIndex("MenuImageLink")));
				mi.setSaleMode(cursor.getInt(cursor.getColumnIndex("SaleMode")));

				// set of product
				String strSqlSetOfProduct = "SELECT a.ProductID, a.ProductPrice, a.ProductQty, " +
						" b.MenuName_0, b.MenuName_1, b.MenuName_2, b.MenuShortName_0, " +
						" b.MenuShortName_1, b.MenuImageLink " +
						" FROM " + ORDER_SET_OF_PRODUCT + " a " +
						" INNER JOIN MenuItem b " +
						" ON a.ProductID=b.ProductID " + 
						" WHERE a.TransactionID=" + transactionId + 
						" AND a.OrderDetailId=" + mi.getOrderDetailId();
				
				Cursor cursorSetOfProduct = dbHelper.myDataBase.rawQuery(strSqlSetOfProduct, null);
				
				if(cursorSetOfProduct.moveToFirst()){
					mi.pCompSetLst = new ArrayList<ProductGroups.PComponentSet>();
					do{
						ProductGroups.PComponentSet pgs = 
								new ProductGroups.PComponentSet();
						
						pgs.setProductID(cursorSetOfProduct.getInt(cursorSetOfProduct
								.getColumnIndex("ProductID")));
						pgs.setPricePerUnit(cursorSetOfProduct.getDouble(cursorSetOfProduct
								.getColumnIndex("ProductPrice")));
						pgs.setProductQty(cursorSetOfProduct.getDouble(cursorSetOfProduct
								.getColumnIndex("ProductQty")));
						pgs.setMenuName(cursorSetOfProduct.getString(cursorSetOfProduct
								.getColumnIndex("MenuName_0")));
						pgs.setMenuName1(cursorSetOfProduct.getString(cursorSetOfProduct
								.getColumnIndex("MenuName_1")));
						pgs.setMenuName2(cursorSetOfProduct.getString(cursorSetOfProduct
								.getColumnIndex("MenuName_2")));
						pgs.setMenuShortName(cursorSetOfProduct.getString(cursorSetOfProduct
								.getColumnIndex("MenuShortName_0")));
						pgs.setMenuShortName1(cursorSetOfProduct.getString(cursorSetOfProduct
								.getColumnIndex("MenuShortName_1")));
						pgs.setMenuImageLink(cursorSetOfProduct.getString(cursorSetOfProduct
								.getColumnIndex("MenuImageLink")));
						pgs.setOrderComment("");
						mi.pCompSetLst.add(pgs);						
					}while(cursorSetOfProduct.moveToNext());
				}
				cursorSetOfProduct.close();
				
				// menu set
				String strSqlSet = "SELECT a.OrderSetID, a.OrderDetailID, a.PGroupID, a.ProductID, "
						+ " a.ProductPrice, a.ProductQty, a.Comment, b.MenuName_0, b.MenuName_1, " +
						" b.MenuName_2, b.MenuShortName_0, b.MenuShortName_1, "
						+ " b.MenuImageLink "
						+ " FROM "
						+ ORDER_SET_TABLE
						+ " a "
						+ " INNER JOIN MenuItem b "
						+ " ON a.ProductID=b.ProductID "
						+ " WHERE a.TransactionID="
						+ transactionId
						+ " AND a.OrderDetailID=" + mi.getOrderDetailId();

				Cursor cursorSet = dbHelper.myDataBase
						.rawQuery(strSqlSet, null);
				if (cursorSet.moveToFirst()) {
					mi.pCompSetLst = new ArrayList<ProductGroups.PComponentSet>();
					do {
						ProductGroups.PComponentSet pgs = new ProductGroups.PComponentSet();
						pgs.setOrderSetID(cursorSet.getInt(cursorSet
								.getColumnIndex("OrderSetID")));
						pgs.setOrderDetailId(cursorSet.getInt(cursorSet
								.getColumnIndex("OrderDetailID")));
						pgs.setPGroupID(cursorSet.getInt(cursorSet
								.getColumnIndex("PGroupID")));
						pgs.setProductID(cursorSet.getInt(cursorSet
								.getColumnIndex("ProductID")));
						pgs.setPricePerUnit(cursorSet.getDouble(cursorSet
								.getColumnIndex("ProductPrice")));
						pgs.setProductQty(cursorSet.getDouble(cursorSet
								.getColumnIndex("ProductQty")));
						pgs.setOrderComment(cursorSet.getString(cursorSet
								.getColumnIndex("Comment")));
						pgs.setMenuName(cursorSet.getString(cursorSet
								.getColumnIndex("MenuName_0")));
						pgs.setMenuName1(cursorSet.getString(cursorSet
								.getColumnIndex("MenuName_1")));
						pgs.setMenuName2(cursorSet.getString(cursorSet
								.getColumnIndex("MenuName_2")));
						pgs.setMenuShortName(cursorSet.getString(cursorSet
								.getColumnIndex("MenuShortName_0")));
						pgs.setMenuShortName1(cursorSet.getString(cursorSet
								.getColumnIndex("MenuShortName_1")));
						pgs.setMenuImageLink(cursorSet.getString(cursorSet
								.getColumnIndex("MenuImageLink")));

						// order comment
						strSql = "SELECT a.PGroupID, a.ProductID, a.MenuCommentID, "
								+ " a.Qty, a.PricePerUnit, b.MenuCommentName_0 "
								+ " FROM "
								+ ORDER_SET_COMMENT_TABLE
								+ " a "
								+ " LEFT JOIN MenuComment b "
								+ " ON a.MenuCommentID=b.MenuCommentID "
								+ " WHERE a.TransactionID="
								+ transactionId
								+ " AND a.OrderDetailID="
								+ mi.getOrderDetailId()
								+ " AND a.OrderSetID="
								+ pgs.getOrderSetID();

						Cursor cursor3 = dbHelper.myDataBase.rawQuery(strSql,
								null);
						if (cursor3.moveToFirst()) {
							pgs.menuCommentList = new ArrayList<MenuGroups.MenuComment>();

							do {
								MenuGroups.MenuComment mc = new MenuGroups.MenuComment();
								mc.setPGroupID(cursor3.getInt(cursor3
										.getColumnIndex("PGroupID")));
								mc.setProductID(cursor3.getInt(cursor3
										.getColumnIndex("ProductID")));
								mc.setMenuCommentID(cursor3.getInt(cursor3
										.getColumnIndex("MenuCommentID")));
								mc.setCommentQty(cursor3.getDouble(cursor3
										.getColumnIndex("Qty")));
								mc.setProductPricePerUnit(cursor3.getDouble(cursor3
										.getColumnIndex("PricePerUnit")));
								mc.setMenuCommentName_0(cursor3.getString(cursor3
										.getColumnIndex("MenuCommentName_0")));

								pgs.menuCommentList.add(mc);
							} while (cursor3.moveToNext());
						}
						cursor3.close();

						mi.pCompSetLst.add(pgs);

					} while (cursorSet.moveToNext());
				}
				cursorSet.close();

				// add order comment
				String strSql2 = "SELECT a.MenuCommentID, "
						+ " a.Qty, a.PricePerUnit, b.MenuCommentName_0 "
						+ " FROM " + ORDER_COMMENT_TABLE + " a "
						+ " LEFT JOIN MenuComment b "
						+ " ON a.MenuCommentID=b.MenuCommentID "
						+ " WHERE a.TransactionID=" + transactionId
						+ " AND a.OrderDetailID=" + mi.getOrderDetailId();

				Cursor cursor2 = dbHelper.myDataBase.rawQuery(strSql2, null);

				if (cursor2.moveToFirst()) {
					mi.menuCommentList = new ArrayList<MenuGroups.MenuComment>();
					do {
						MenuGroups.MenuComment mc = new MenuGroups.MenuComment();
						mc.setMenuCommentID(cursor2.getInt(cursor2
								.getColumnIndex("MenuCommentID")));
						mc.setCommentQty(cursor2.getDouble(cursor2
								.getColumnIndex("Qty")));
						mc.setProductPricePerUnit(cursor2.getDouble(cursor2
								.getColumnIndex("PricePerUnit")));
						mc.setMenuCommentName_0(cursor2.getString(cursor2
								.getColumnIndex("MenuCommentName_0")));

						mi.menuCommentList.add(mc);
					} while (cursor2.moveToNext());
				}

				cursor2.close();

				ml.add(mi);
			} while (cursor.moveToNext());
		}

		cursor.close();
		closeDatabase();
		return ml;
	}

	public syn.pos.data.model.MenuDataItem listOrder(int transactionId,
			int computerId, int orderDetailId) {
		syn.pos.data.model.MenuDataItem mi = new syn.pos.data.model.MenuDataItem();

		String strSql = "SELECT a.TransactionID, a.ComputerID, b.OrderDetailID, "
				+ " b.ProductID,  b.Qty, b.PricePerUnit, b.TotalRetailPrice, b.SaleMode, "
				+ " b.OrderComment, c.ProductTypeID, d.MenuName_0, d.MenuName_1, d.MenuName_2, "
				+ " d.MenuShortName_0, d.MenuShortName_1, d.MenuImageLink "
				+ " FROM "
				+ ORDER_TRANSACTION_TABLE
				+ " a "
				+ " INNER JOIN "
				+ ORDER_DETAIL_TABLE
				+ " b "
				+ " ON a.TransactionID=b.TransactionID AND a.ComputerID=b.ComputerID "
				+ " INNER JOIN Products c "
				+ " ON b.ProductID=c.ProductID "
				+ " INNER JOIN MenuItem d "
				+ " ON c.ProductID=d.ProductID "
				+ " WHERE a.TransactionID="
				+ transactionId
				+ " AND a.ComputerID="
				+ computerId
				+ " AND b.OrderDetailID="
				+ orderDetailId
				+ " AND a.TransactionStatusID=1 ORDER BY b.OrderDetailID ";

		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);

		if (cursor.moveToFirst()) {
			do {
				mi.setTransactionId(cursor.getInt(cursor
						.getColumnIndex("TransactionID")));
				mi.setComputerId(cursor.getInt(cursor
						.getColumnIndex("ComputerID")));
				mi.setOrderDetailId(cursor.getInt(cursor
						.getColumnIndex("OrderDetailID")));
				mi.setProductID(cursor.getInt(cursor
						.getColumnIndex("ProductID")));
				mi.setMenuName(cursor.getString(cursor
						.getColumnIndex("MenuName_0")));
				mi.setMenuName1(cursor.getString(cursor
						.getColumnIndex("MenuName_1")));
				mi.setMenuName2(cursor.getString(cursor
						.getColumnIndex("MenuName_2")));
				mi.setMenuShortName(cursor.getString(cursor.getColumnIndex("MenuShortName_0")));
				mi.setMenuShortName1(cursor.getString(cursor.getColumnIndex("MenuShortName_1")));
				mi.setProductQty(cursor.getDouble(cursor.getColumnIndex("Qty")));
				mi.setPricePerUnit(cursor.getDouble(cursor
						.getColumnIndex("PricePerUnit")));
				mi.setCurrencySymbol(globalVar.currency.getSymbol());
				mi.setOrderComment(cursor.getString(cursor
						.getColumnIndex("OrderComment")));
				mi.setProductTypeID(cursor.getInt(cursor
						.getColumnIndex("ProductTypeID")));
				mi.setImgUrl(cursor.getString(cursor
						.getColumnIndex("MenuImageLink")));
				mi.setSaleMode(cursor.getInt(cursor.getColumnIndex("SaleMode")));

				// set of product
				String strSqlSetOfProduct = "SELECT a.ProductID, a.ProductPrice, a.ProductQty, " +
						" b.MenuName_0, b.MenuName_1, b.MenuName_2, b.MenuShortName_0, b.MenuShortName_1," +
						" b.MenuImageLink " +
						" FROM " + ORDER_SET_OF_PRODUCT + " a " +
						" INNER JOIN MenuItem b " +
						" ON a.ProductID=b.ProductID " + 
						" WHERE a.TransactionID=" + transactionId + 
						" AND a.OrderDetailId=" + mi.getOrderDetailId();
				
				Cursor cursorSetOfProduct = dbHelper.myDataBase.rawQuery(strSqlSetOfProduct, null);
				
				if(cursorSetOfProduct.moveToFirst()){
					mi.pCompSetLst = new ArrayList<ProductGroups.PComponentSet>();
					do{
						ProductGroups.PComponentSet pgs = 
								new ProductGroups.PComponentSet();
						
						pgs.setProductID(cursorSetOfProduct.getInt(cursorSetOfProduct
								.getColumnIndex("ProductID")));
						pgs.setPricePerUnit(cursorSetOfProduct.getDouble(cursorSetOfProduct
								.getColumnIndex("ProductPrice")));
						pgs.setProductQty(cursorSetOfProduct.getDouble(cursorSetOfProduct
								.getColumnIndex("ProductQty")));
						pgs.setMenuName(cursorSetOfProduct.getString(cursorSetOfProduct
								.getColumnIndex("MenuName_0")));
						pgs.setMenuName1(cursorSetOfProduct.getString(cursorSetOfProduct
								.getColumnIndex("MenuName_1")));
						pgs.setMenuName2(cursorSetOfProduct.getString(cursorSetOfProduct
								.getColumnIndex("MenuName_2")));
						pgs.setMenuShortName(cursorSetOfProduct.getString(cursorSetOfProduct
								.getColumnIndex("MenuShortName_0")));
						pgs.setMenuShortName1(cursorSetOfProduct.getString(cursorSetOfProduct
								.getColumnIndex("MenuShortName_1")));
						pgs.setMenuImageLink(cursorSetOfProduct.getString(cursorSetOfProduct
								.getColumnIndex("MenuImageLink")));
						pgs.setOrderComment("");
						mi.pCompSetLst.add(pgs);						
					}while(cursorSetOfProduct.moveToNext());
				}
				cursorSetOfProduct.close();
				
				// menu set
				String strSqlSet = "SELECT a.OrderSetID, a.OrderDetailID, a.PGroupID, a.ProductID, "
						+ " a.ProductPrice, a.ProductQty, a.Comment, b.MenuName_0, b.MenuName_1, b.MenuName_2, "
						+ " b.MenuShortName_0, b.MenuShortName_1, "
						+ " b.MenuImageLink "
						+ " FROM "
						+ ORDER_SET_TABLE
						+ " a "
						+ " INNER JOIN MenuItem b "
						+ " ON a.ProductID=b.ProductID "
						+ " WHERE a.TransactionID="
						+ transactionId
						+ " AND a.OrderDetailID=" + mi.getOrderDetailId();

				Cursor cursorSet = dbHelper.myDataBase
						.rawQuery(strSqlSet, null);
				if (cursorSet.moveToFirst()) {
					mi.pCompSetLst = new ArrayList<ProductGroups.PComponentSet>();
					do {
						ProductGroups.PComponentSet pgs = new ProductGroups.PComponentSet();
						pgs.setOrderSetID(cursorSet.getInt(cursorSet
								.getColumnIndex("OrderSetID")));
						pgs.setOrderDetailId(cursorSet.getInt(cursorSet
								.getColumnIndex("OrderDetailID")));
						pgs.setPGroupID(cursorSet.getInt(cursorSet
								.getColumnIndex("PGroupID")));
						pgs.setProductID(cursorSet.getInt(cursorSet
								.getColumnIndex("ProductID")));
						pgs.setPricePerUnit(cursorSet.getDouble(cursorSet
								.getColumnIndex("ProductPrice")));
						pgs.setProductQty(cursorSet.getDouble(cursorSet
								.getColumnIndex("ProductQty")));
						pgs.setOrderComment(cursorSet.getString(cursorSet
								.getColumnIndex("Comment")));
						pgs.setMenuName(cursorSet.getString(cursorSet
								.getColumnIndex("MenuName_0")));
						pgs.setMenuName1(cursorSet.getString(cursorSet
								.getColumnIndex("MenuName_1")));
						pgs.setMenuName2(cursorSet.getString(cursorSet
								.getColumnIndex("MenuName_2")));
						pgs.setMenuShortName(cursorSet.getString(cursorSet
								.getColumnIndex("MenuShortName_0")));
						pgs.setMenuShortName1(cursorSet.getString(cursorSet
								.getColumnIndex("MenuShortName_1")));
						pgs.setMenuImageLink(cursorSet.getString(cursorSet
								.getColumnIndex("MenuImageLink")));

						// order comment
						strSql = "SELECT a.PGroupID, a.ProductID, a.MenuCommentID, "
								+ " a.Qty, a.PricePerUnit, b.MenuCommentName_0 "
								+ " FROM "
								+ ORDER_SET_COMMENT_TABLE
								+ " a "
								+ " LEFT JOIN MenuComment b "
								+ " ON a.MenuCommentID=b.MenuCommentID "
								+ " WHERE a.TransactionID="
								+ transactionId
								+ " AND a.OrderDetailID="
								+ orderDetailId
								+ " AND a.OrderSetID=" + pgs.getOrderSetID();

						Cursor cursor3 = dbHelper.myDataBase.rawQuery(strSql,
								null);
						if (cursor3.moveToFirst()) {
							pgs.menuCommentList = new ArrayList<MenuGroups.MenuComment>();

							do {
								MenuGroups.MenuComment mc = new MenuGroups.MenuComment();
								mc.setPGroupID(cursor3.getInt(cursor3
										.getColumnIndex("PGroupID")));
								mc.setProductID(cursor3.getInt(cursor3
										.getColumnIndex("ProductID")));
								mc.setMenuCommentID(cursor3.getInt(cursor3
										.getColumnIndex("MenuCommentID")));
								mc.setCommentQty(cursor3.getDouble(cursor3
										.getColumnIndex("Qty")));
								mc.setProductPricePerUnit(cursor3.getDouble(cursor3
										.getColumnIndex("PricePerUnit")));
								mc.setMenuCommentName_0(cursor3.getString(cursor3
										.getColumnIndex("MenuCommentName_0")));

								pgs.menuCommentList.add(mc);
							} while (cursor3.moveToNext());
						}
						cursor3.close();

						mi.pCompSetLst.add(pgs);

					} while (cursorSet.moveToNext());
				}
				cursorSet.close();

				// add order comment
				String strSql2 = "SELECT a.MenuCommentID, "
						+ " a.Qty, a.PricePerUnit, b.MenuCommentName_0 "
						+ " FROM " + ORDER_COMMENT_TABLE + " a "
						+ " LEFT JOIN MenuComment b "
						+ " ON a.MenuCommentID=b.MenuCommentID "
						+ " WHERE a.TransactionID=" + transactionId
						+ " AND a.OrderDetailID=" + mi.getOrderDetailId();

				Cursor cursor2 = dbHelper.myDataBase.rawQuery(strSql2, null);

				if (cursor2.moveToFirst()) {
					mi.menuCommentList = new ArrayList<MenuGroups.MenuComment>();
					do {
						MenuGroups.MenuComment mc = new MenuGroups.MenuComment();
						mc.setMenuCommentID(cursor2.getInt(cursor2
								.getColumnIndex("MenuCommentID")));
						mc.setCommentQty(cursor2.getDouble(cursor2
								.getColumnIndex("Qty")));
						mc.setProductPricePerUnit(cursor2.getDouble(cursor2
								.getColumnIndex("PricePerUnit")));
						mc.setMenuCommentName_0(cursor2.getString(cursor2
								.getColumnIndex("MenuCommentName_0")));

						mi.menuCommentList.add(mc);
					} while (cursor2.moveToNext());
				}

				cursor2.close();
			} while (cursor.moveToNext());
		}

		cursor.close();
		closeDatabase();
		return mi;
	}

	// list order
	public List<syn.pos.data.model.MenuDataItem> listOrder(int transactionId,
			int computerId) {
		List<syn.pos.data.model.MenuDataItem> ml = new ArrayList<syn.pos.data.model.MenuDataItem>();

		String strSql = "SELECT a.TransactionID, a.ComputerID, a.MemberID, b.OrderDetailID, "
				+ " b.ProductID, d.MenuName_0, d.MenuName_1, d.MenuName_2, " 
				+ " d.MenuShortName_0, d.MenuShortName_1, b.Qty, b.PricePerUnit, b.TotalRetailPrice, "
				+ " b.OrderComment, b.SaleMode, c.ProductTypeID "
				+ " FROM "
				+ ORDER_TRANSACTION_TABLE
				+ " a "
				+ " INNER JOIN "
				+ ORDER_DETAIL_TABLE
				+ " b "
				+ " ON a.TransactionID=b.TransactionID AND a.ComputerID=b.ComputerID "
				+ " INNER JOIN Products c "
				+ " ON b.ProductID=c.ProductID "
				+ " INNER JOIN MenuItem d "
				+ " ON c.ProductID=d.ProductID "
				+ " WHERE a.TransactionID="
				+ transactionId
				+ " AND a.ComputerID="
				+ computerId
				+ " AND a.TransactionStatusID=1 ORDER BY b.OrderDetailID ";

		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);

		if (cursor.moveToFirst()) {
			do {
				syn.pos.data.model.MenuDataItem mi = new syn.pos.data.model.MenuDataItem();
				mi.setTransactionId(cursor.getInt(cursor
						.getColumnIndex("TransactionID")));
				mi.setComputerId(cursor.getInt(cursor
						.getColumnIndex("ComputerID")));
				mi.setMemberId(cursor.getInt(cursor.getColumnIndex("MemberID")));
				mi.setOrderDetailId(cursor.getInt(cursor
						.getColumnIndex("OrderDetailID")));
				mi.setProductID(cursor.getInt(cursor
						.getColumnIndex("ProductID")));
				mi.setMenuName(cursor.getString(cursor
						.getColumnIndex("MenuName_0")));
				mi.setMenuName1(cursor.getString(cursor
						.getColumnIndex("MenuName_1")));
				mi.setMenuName2(cursor.getString(cursor
						.getColumnIndex("MenuName_2")));
				mi.setMenuShortName(cursor.getString(cursor.getColumnIndex("MenuShortName_0")));
				mi.setMenuShortName1(cursor.getString(cursor.getColumnIndex("MenuShortName_1")));
				mi.setProductQty(cursor.getDouble(cursor.getColumnIndex("Qty")));
				mi.setPricePerUnit(cursor.getDouble(cursor
						.getColumnIndex("PricePerUnit")));
				mi.setCurrencySymbol(globalVar.currency.getSymbol());
				mi.setOrderComment(cursor.getString(cursor
						.getColumnIndex("OrderComment")));
				mi.setProductTypeID(cursor.getInt(cursor
						.getColumnIndex("ProductTypeID")));
				mi.setSaleMode(cursor.getInt(cursor.getColumnIndex("SaleMode")));

				// type 7
				strSql = " SELECT OrderSetID, PGroupID, ProductID, "
						+ " ProductPrice, ProductQty, SetGroupNo, Comment "
						+ " FROM " + ORDER_SET_TABLE + " WHERE TransactionID="
						+ transactionId + " AND OrderDetailID="
						+ mi.getOrderDetailId();

				Cursor cursorType7 = dbHelper.myDataBase.rawQuery(strSql, null);
				mi.pCompSetLst = new ArrayList<ProductGroups.PComponentSet>();

				if (cursorType7.moveToFirst()) {
					do {
						ProductGroups.PComponentSet pcs = new ProductGroups.PComponentSet();
						pcs.setOrderSetID(cursorType7.getInt(cursorType7
								.getColumnIndex("OrderSetID")));
						pcs.setPGroupID(cursorType7.getInt(cursorType7
								.getColumnIndex("PGroupID")));
						pcs.setProductID(cursorType7.getInt(cursorType7
								.getColumnIndex("ProductID")));
						pcs.setPricePerUnit(cursorType7.getDouble(cursorType7
								.getColumnIndex("ProductPrice")));
						pcs.setProductQty(cursorType7.getDouble(cursorType7
								.getColumnIndex("ProductQty")));
						pcs.setSetGroupNo(cursorType7.getInt(cursorType7
								.getColumnIndex("SetGroupNo")));
						pcs.setOrderComment(cursorType7.getString(cursorType7
								.getColumnIndex("Comment")));

						// comment of type 7
						strSql = " SELECT PGroupID, ProductID, MenuCommentID, Qty, PricePerUnit "
								+ " FROM "
								+ ORDER_SET_COMMENT_TABLE
								+ " WHERE TransactionID="
								+ transactionId
								+ " AND OrderDetailID="
								+ mi.getOrderDetailId()
								+ " AND OrderSetID=" + pcs.getOrderSetID();

						Cursor curCommentType7 = dbHelper.myDataBase.rawQuery(
								strSql, null);
						pcs.menuCommentList = new ArrayList<MenuGroups.MenuComment>();
						if (curCommentType7.moveToFirst()) {
							do {
								MenuGroups.MenuComment mc = new MenuGroups.MenuComment();
								mc.setPGroupID(curCommentType7
										.getInt(curCommentType7
												.getColumnIndex("PGroupID")));
								mc.setProductID(curCommentType7
										.getInt(curCommentType7
												.getColumnIndex("ProductID")));
								mc.setMenuCommentID(curCommentType7.getInt(curCommentType7
										.getColumnIndex("MenuCommentID")));
								mc.setCommentQty(curCommentType7
										.getDouble(curCommentType7
												.getColumnIndex("Qty")));
								mc.setProductPricePerUnit(curCommentType7.getDouble(curCommentType7
										.getColumnIndex("PricePerUnit")));

								pcs.menuCommentList.add(mc);
							} while (curCommentType7.moveToNext());
						}
						curCommentType7.close();

						mi.pCompSetLst.add(pcs);
					} while (cursorType7.moveToNext());
				}
				cursorType7.close();
				// type 7

				// add order comment
				String strSql2 = "SELECT a.MenuCommentID, "
						+ " a.Qty, a.PricePerUnit, b.MenuCommentName_0 "
						+ " FROM " + ORDER_COMMENT_TABLE + " a "
						+ " LEFT JOIN MenuComment b "
						+ " ON a.MenuCommentID=b.MenuCommentID "
						+ " WHERE a.TransactionID=" + transactionId
						+ " AND a.OrderDetailID="
						+ cursor.getInt(cursor.getColumnIndex("OrderDetailID"));

				Cursor cursor2 = dbHelper.myDataBase.rawQuery(strSql2, null);

				if (cursor2.moveToFirst()) {
					mi.menuCommentList = new ArrayList<MenuGroups.MenuComment>();
					do {
						MenuGroups.MenuComment mc = new MenuGroups.MenuComment();
						mc.setMenuCommentID(cursor2.getInt(cursor2
								.getColumnIndex("MenuCommentID")));
						mc.setCommentQty(cursor2.getDouble(cursor2
								.getColumnIndex("Qty")));
						mc.setProductPricePerUnit(cursor2.getDouble(cursor2
								.getColumnIndex("PricePerUnit")));
						mc.setMenuCommentName_0(cursor2.getString(cursor2
								.getColumnIndex("MenuCommentName_0")));

						mi.menuCommentList.add(mc);
					} while (cursor2.moveToNext());
				}
				cursor2.close();

				ml.add(mi);
			} while (cursor.moveToNext());
		}

		cursor.close();
		closeDatabase();

		return ml;
	}

	/*************** ordercomment ***************/
	public double getOrderCommentQty(int transactionId, int orderId, int commentId) {

		double qty = 0.0d;

		String strSql = "SELECT MenuCommentID, Qty " + " FROM OrderCommentTmp "
				+ " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderId + " AND MenuCommentID="
				+ commentId;

		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			if (cursor.getDouble(cursor.getColumnIndex("MenuCommentID")) != 0) {
				qty = cursor.getDouble(cursor.getColumnIndex("Qty"));
			}
		}
		cursor.close();
		closeDatabase();
		return qty;
	}
	
	public boolean chkOrderComment(int transactionId, int orderId, int commentId) {

		boolean isFound = false;

		String strSql = "SELECT MenuCommentID " + " FROM OrderCommentTmp "
				+ " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderId + " AND MenuCommentID="
				+ commentId;

		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			if (cursor.getDouble(cursor.getColumnIndex("MenuCommentID")) != 0) {
				isFound = true;
			}
		}
		cursor.close();
		closeDatabase();
		return isFound;
	}

	public List<syn.pos.data.model.MenuGroups.MenuComment> listOrderCommentTemp(
			int transactionId, int orderId) {
		List<MenuGroups.MenuComment> mcLst = new ArrayList<MenuGroups.MenuComment>();

		String strSql = "SELECT a.MenuCommentID, "
				+ " a.Qty, a.PricePerUnit, b.MenuCommentName_0 " + " FROM "
				+ ORDER_COMMENT_TABLE_TMP + " a " + " INNER JOIN MenuComment b "
				+ " ON a.MenuCommentID=b.MenuCommentID "
				+ " WHERE a.TransactionID=" + transactionId
				+ " AND a.OrderDetailID=" + orderId;

		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			do {
				MenuGroups.MenuComment mc = new MenuGroups.MenuComment();
				mc.setMenuCommentID(cursor.getInt(cursor
						.getColumnIndex("MenuCommentID")));
				mc.setCommentQty(cursor.getDouble(cursor.getColumnIndex("Qty")));
				mc.setProductPricePerUnit(cursor.getDouble(cursor
						.getColumnIndex("PricePerUnit")));
				mc.setMenuCommentName_0(cursor.getString(cursor
						.getColumnIndex("MenuCommentName_0")));
				mcLst.add(mc);
			} while (cursor.moveToNext());
		}

		cursor.close();
		closeDatabase();
		return mcLst;
	}
	
	public List<syn.pos.data.model.MenuGroups.MenuComment> listOrderComment(
			int transactionId, int orderId) {
		List<MenuGroups.MenuComment> mcLst = new ArrayList<MenuGroups.MenuComment>();

		String strSql = "SELECT a.MenuCommentID, "
				+ " a.Qty, a.PricePerUnit, b.MenuCommentName_0 " + " FROM "
				+ ORDER_COMMENT_TABLE + " a " + " INNER JOIN MenuComment b "
				+ " ON a.MenuCommentID=b.MenuCommentID "
				+ " WHERE a.TransactionID=" + transactionId
				+ " AND a.OrderDetailID=" + orderId;

		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			do {
				MenuGroups.MenuComment mc = new MenuGroups.MenuComment();
				mc.setMenuCommentID(cursor.getInt(cursor
						.getColumnIndex("MenuCommentID")));
				mc.setCommentQty(cursor.getDouble(cursor.getColumnIndex("Qty")));
				mc.setProductPricePerUnit(cursor.getDouble(cursor
						.getColumnIndex("PricePerUnit")));
				mc.setMenuCommentName_0(cursor.getString(cursor
						.getColumnIndex("MenuCommentName_0")));
				mcLst.add(mc);
			} while (cursor.moveToNext());
		}

		cursor.close();
		closeDatabase();
		return mcLst;
	}

	/***** order type 7 *********/

	public double countOrderSet(int transactionId, int orderId, int pGroupId) {
		double count = 0;

		String strSql = "SELECT SUM(ProductQty) " + " FROM "
				+ ORDER_SET_TABLE_TMP + " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderId + " AND PGroupID=" + pGroupId
				+ " GROUP BY PGroupID";

		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			count = cursor.getDouble(0);
		}
		cursor.close();
		closeDatabase();

		return count;
	}

	public int checkAddedSetGroup0(int transactionId, int orderId, int pGroupId) {
		int found = 0;
		String strSql = "SELECT OrderDetailID " + " FROM "
				+ ORDER_SET_TABLE_TMP
				+ " WHERE SetGroupNo=0 AND TransactionID = " + transactionId
				+ " AND OrderDetailID=" + orderId + " AND PGroupID=" + pGroupId;

		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			found = cursor.getInt(0);
		}
		cursor.close();
		closeDatabase();

		return found;
	}

	// public List<ProductGroups.PComponentGroup> listGrouperOrderSet(int
	// orderId,
	// int pgGroupId){
	// List<ProductGroups.PComponentGroup> pCompGroupLst =
	// new ArrayList<ProductGroups.PComponentGroup>();
	//
	// String strSql = " SELECT a.PGroupID, a.SetGroupName, a.RequireAmount " +
	// " FROM PComponentGroup a " +
	// " INNER JOIN " + ORDER_SET_TABLE_TMP + " b " +
	// " ON a.PGroupID=b.PGroupID " +
	// " WHERE a.PGroupID=" + pgGroupId +
	// " AND b.OrderDetailID=" + orderId +
	// " GROUP BY a.PGroupID ORDER BY b.OrderSetID";
	//
	// openDatabase();
	// Cursor cursor1 = dbHelper.myDataBase.rawQuery(strSql, null);
	// if(cursor1.moveToFirst()){
	// do{
	// ProductGroups.PComponentGroup pcg =
	// new ProductGroups.PComponentGroup();
	// pcg.setPGroupID(cursor1.getInt(0));
	// pcg.setSetGroupName(cursor1.getString(1));
	// pcg.setRequireAmount(cursor1.getDouble(2));
	//
	// pcg.pCompSetLst = new ArrayList<ProductGroups.PComponentSet>();
	//
	// strSql =
	// "SELECT a.OrderSetID, a.OrderDetailID, a.PGroupID, a.ProductID, " +
	// " a.ProductPrice, a.ProductQty, a.SetGroupNo, a.Comment, b.MenuName_0, "
	// +
	// " b.MenuImageLink " +
	// " FROM " + ORDER_SET_TABLE_TMP + " a " +
	// " INNER JOIN MenuItem b " +
	// " ON a.ProductID=b.ProductID " +
	// " WHERE a.OrderDetailID=" + orderId + " AND a.PGroupID=" +
	// cursor1.getInt(0) +
	// " ORDER BY a.OrderSetID";
	//
	// Cursor cursor2 = dbHelper.myDataBase.rawQuery(strSql, null);
	//
	// int totalPCompSet = 0;
	// if(cursor2.moveToFirst()){
	// do{
	// ProductGroups.PComponentSet pgs =
	// new ProductGroups.PComponentSet();
	// pgs.setOrderSetID(cursor2.getInt(0));
	// pgs.setOrderDetailId(cursor2.getInt(1));
	// pgs.setPGroupID(cursor2.getInt(2));
	// pgs.setProductID(cursor2.getInt(3));
	// pgs.setPricePerUnit(cursor2.getDouble(4));
	// pgs.setProductQty(cursor2.getDouble(5));
	// pgs.setSetGroupNo(cursor2.getInt(6));
	// pgs.setOrderComment(cursor2.getString(7));
	// pgs.setMenuName(cursor2.getString(8));
	// pgs.setMenuImageLink(cursor2.getString(9));
	//
	// try {
	// totalPCompSet += (int)cursor2.getDouble(5);
	// } catch (NumberFormatException e) {
	// android.util.Log.d("catch totalPCompSet", e.getMessage());
	// e.printStackTrace();
	// }
	//
	// // order comment
	// strSql = "SELECT a.PGroupID, a.ProductID, a.MenuCommentID, " +
	// " a.Qty, a.PricePerUnit, b.MenuCommentName_0 " +
	// " FROM " + ORDER_SET_COMMENT_TABLE_TMP + " a " +
	// " LEFT JOIN MenuComment b " +
	// " ON a.MenuCommentID=b.MenuCommentID " +
	// " WHERE a.OrderSetID=" + pgs.getOrderSetID();
	//
	// Cursor cursor3 = dbHelper.myDataBase.rawQuery(strSql, null);
	// if(cursor3.moveToFirst()){
	// pgs.menuCommentList = new ArrayList<MenuGroups.MenuComment>();
	//
	// do{
	// MenuGroups.MenuComment mc = new MenuGroups.MenuComment();
	// mc.setPGroupID(cursor3.getInt(0));
	// mc.setProductID(cursor3.getInt(1));
	// mc.setMenuCommentID(cursor3.getInt(2));
	// mc.setCommentQty(cursor3.getDouble(3));
	// mc.setProductPricePerUnit(cursor3.getDouble(4));
	// mc.setMenuCommentName_0(cursor3.getString(5));
	//
	// pgs.menuCommentList.add(mc);
	// }while(cursor3.moveToNext());
	// }
	// cursor3.close();
	//
	// pcg.pCompSetLst.add(pgs);
	//
	// }while(cursor2.moveToNext());
	// pcg.setTotalPCompSet(totalPCompSet);
	// }
	// cursor2.close();
	//
	// pCompGroupLst.add(pcg);
	// }while(cursor1.moveToNext());
	// }
	// cursor1.close();
	// closeDatabase();
	//
	// return pCompGroupLst;
	// }

	public ProductGroups.PComponentGroup getOrderSet(int transactionId,
			int orderId, int pGroupId) {
		ProductGroups.PComponentGroup pcg = new ProductGroups.PComponentGroup();

		String strSql = " SELECT a.PGroupID, a.SetGroupName, a.RequireAmount, "
				+ " a.SetGroupNo " + " FROM PComponentGroup a "
				+ " INNER JOIN " + ORDER_SET_TABLE_TMP + " b "
				+ " ON a.PGroupID=b.PGroupID " + " WHERE a.PGroupID="
				+ pGroupId + " AND b.TransactionID=" + transactionId
				+ " AND b.OrderDetailID=" + orderId + " ORDER BY b.OrderSetID";

		openDatabase();
		Cursor cursor1 = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor1.moveToFirst()) {
			do {
				pcg.setPGroupID(cursor1.getInt(cursor1
						.getColumnIndex("PGroupID")));
				pcg.setSetGroupName(cursor1.getString(cursor1
						.getColumnIndex("SetGroupName")));
				pcg.setRequireAmount(cursor1.getDouble(cursor1
						.getColumnIndex("RequireAmount")));
				pcg.setSetGroupNo(cursor1.getInt(cursor1
						.getColumnIndex("SetGroupNo")));

				pcg.pCompSetLst = new ArrayList<ProductGroups.PComponentSet>();

				strSql = "SELECT a.OrderSetID, a.OrderDetailID, a.PGroupID, a.ProductID, "
						+ " a.ProductPrice, a.ProductQty, a.SetGroupNo, a.Comment, b.MenuName_0, "
						+ " b.MenuImageLink "
						+ " FROM "
						+ ORDER_SET_TABLE_TMP
						+ " a "
						+ " INNER JOIN MenuItem b "
						+ " ON a.ProductID=b.ProductID "
						+ " WHERE a.TransactionID="
						+ transactionId
						+ " AND a.OrderDetailID="
						+ orderId
						+ " AND a.PGroupID="
						+ cursor1.getInt(0)
						+ " ORDER BY a.OrderSetID";

				Cursor cursor2 = dbHelper.myDataBase.rawQuery(strSql, null);

				int totalPCompSet = 0;
				if (cursor2.moveToFirst()) {
					do {
						ProductGroups.PComponentSet pgs = new ProductGroups.PComponentSet();
						pgs.setOrderSetID(cursor2.getInt(cursor2
								.getColumnIndex("OrderSetID")));
						pgs.setOrderDetailId(cursor2.getInt(cursor2
								.getColumnIndex("OrderDetailID")));
						pgs.setPGroupID(cursor2.getInt(cursor2
								.getColumnIndex("PGroupID")));
						pgs.setProductID(cursor2.getInt(cursor2
								.getColumnIndex("ProductID")));
						pgs.setPricePerUnit(cursor2.getDouble(cursor2
								.getColumnIndex("ProductPrice")));
						pgs.setProductQty(cursor2.getDouble(cursor2
								.getColumnIndex("ProductQty")));
						pgs.setSetGroupNo(cursor2.getInt(cursor2
								.getColumnIndex("SetGroupNo")));
						pgs.setOrderComment(cursor2.getString(cursor2
								.getColumnIndex("Comment")));
						pgs.setMenuName(cursor2.getString(cursor2
								.getColumnIndex("MenuName_0")));
						pgs.setMenuImageLink(cursor2.getString(cursor2
								.getColumnIndex("MenuImageLink")));

						try {
							totalPCompSet += (int) cursor2.getDouble(cursor2
									.getColumnIndex("ProductQty"));
						} catch (NumberFormatException e) {
							android.util.Log.d("catch totalPCompSet",
									e.getMessage());
							e.printStackTrace();
						}

						// order comment
						strSql = "SELECT a.PGroupID, a.ProductID, a.MenuCommentID, "
								+ " a.Qty, a.PricePerUnit, b.MenuCommentName_0 "
								+ " FROM "
								+ ORDER_SET_COMMENT_TABLE_TMP
								+ " a "
								+ " LEFT JOIN MenuComment b "
								+ " ON a.MenuCommentID=b.MenuCommentID "
								+ " WHERE a.TransactionID="
								+ transactionId
								+ " AND a.OrderDetailID="
								+ orderId
								+ " AND a.OrderSetID=" + pgs.getOrderSetID();

						Cursor cursor3 = dbHelper.myDataBase.rawQuery(strSql,
								null);
						if (cursor3.moveToFirst()) {
							pgs.menuCommentList = new ArrayList<MenuGroups.MenuComment>();

							do {
								MenuGroups.MenuComment mc = new MenuGroups.MenuComment();
								mc.setPGroupID(cursor3.getInt(cursor3
										.getColumnIndex("PGroupID")));
								mc.setProductID(cursor3.getInt(cursor3
										.getColumnIndex("ProductID")));
								mc.setMenuCommentID(cursor3.getInt(cursor3
										.getColumnIndex("MenuCommentID")));
								mc.setCommentQty(cursor3.getDouble(cursor3
										.getColumnIndex("Qty")));
								mc.setProductPricePerUnit(cursor3.getDouble(cursor3
										.getColumnIndex("PricePerUnit")));
								mc.setMenuCommentName_0(cursor3.getString(cursor3
										.getColumnIndex("MenuCommentName_0")));

								pgs.menuCommentList.add(mc);
							} while (cursor3.moveToNext());
						}
						cursor3.close();

						pcg.pCompSetLst.add(pgs);

					} while (cursor2.moveToNext());
					pcg.setTotalPCompSet(totalPCompSet);
				}
				cursor2.close();
			} while (cursor1.moveToNext());
		}
		cursor1.close();
		closeDatabase();

		return pcg;
	}

	public List<ProductGroups.PComponentGroup> listAllGrouperOrderSet(
			int transactionId, int orderId) {
		List<ProductGroups.PComponentGroup> pCompGroupLst = new ArrayList<ProductGroups.PComponentGroup>();

		String strSql = " SELECT a.PGroupID, a.SetGroupName, a.RequireAmount, "
				+ " a.SetGroupNo " + " FROM PComponentGroup a "
				+ " INNER JOIN " + ORDER_SET_TABLE_TMP + " b "
				+ " ON a.PGroupID=b.PGroupID " + " WHERE b.TransactionID="
				+ transactionId + " AND b.OrderDetailID=" + orderId
				+ " GROUP BY a.PGroupID ORDER BY b.OrderSetID";

		openDatabase();
		Cursor cursor1 = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor1.moveToFirst()) {
			do {
				ProductGroups.PComponentGroup pcg = new ProductGroups.PComponentGroup();
				pcg.setPGroupID(cursor1.getInt(cursor1
						.getColumnIndex("PGroupID")));
				pcg.setSetGroupName(cursor1.getString(cursor1
						.getColumnIndex("SetGroupName")));
				pcg.setRequireAmount(cursor1.getDouble(cursor1
						.getColumnIndex("RequireAmount")));
				pcg.setSetGroupNo(cursor1.getInt(cursor1
						.getColumnIndex("SetGroupNo")));

				pcg.pCompSetLst = new ArrayList<ProductGroups.PComponentSet>();

				strSql = "SELECT a.OrderSetID, a.OrderDetailID, a.PGroupID, a.ProductID, "
						+ " a.ProductPrice, a.ProductQty, a.SetGroupNo, a.Comment, b.MenuName_0," +
						" b.MenuName_1, b.MenuName_2, b.MenuShortName_0, b.MenuShortName_1, "
						+ " b.MenuImageLink "
						+ " FROM "
						+ ORDER_SET_TABLE_TMP
						+ " a "
						+ " INNER JOIN MenuItem b "
						+ " ON a.ProductID=b.ProductID "
						+ " WHERE a.TransactionID="
						+ transactionId
						+ " AND a.OrderDetailID="
						+ orderId
						+ " AND a.PGroupID="
						+ cursor1.getInt(0)
						+ " ORDER BY a.OrderSetID";

				Cursor cursor2 = dbHelper.myDataBase.rawQuery(strSql, null);

				int totalPCompSet = 0;
				if (cursor2.moveToFirst()) {
					do {
						ProductGroups.PComponentSet pgs = new ProductGroups.PComponentSet();
						pgs.setOrderSetID(cursor2.getInt(cursor2
								.getColumnIndex("OrderSetID")));
						pgs.setOrderDetailId(cursor2.getInt(cursor2
								.getColumnIndex("OrderDetailID")));
						pgs.setPGroupID(cursor2.getInt(cursor2
								.getColumnIndex("PGroupID")));
						pgs.setProductID(cursor2.getInt(cursor2
								.getColumnIndex("ProductID")));
						pgs.setPricePerUnit(cursor2.getDouble(cursor2
								.getColumnIndex("ProductPrice")));
						pgs.setProductQty(cursor2.getDouble(cursor2
								.getColumnIndex("ProductQty")));
						pgs.setSetGroupNo(cursor2.getInt(cursor2
								.getColumnIndex("SetGroupNo")));
						pgs.setOrderComment(cursor2.getString(cursor2
								.getColumnIndex("Comment")));
						pgs.setMenuName(cursor2.getString(cursor2
								.getColumnIndex("MenuName_0")));
						pgs.setMenuName1(cursor2.getString(cursor2
								.getColumnIndex("MenuName_1")));
						pgs.setMenuName2(cursor2.getString(cursor2
								.getColumnIndex("MenuName_2")));
						pgs.setMenuShortName(cursor2.getString(cursor2
								.getColumnIndex("MenuShortName_0")));
						pgs.setMenuShortName1(cursor2.getString(cursor2
								.getColumnIndex("MenuShortName_1")));
						pgs.setMenuImageLink(cursor2.getString(cursor2
								.getColumnIndex("MenuImageLink")));

						try {
							totalPCompSet += (int) cursor2.getDouble(cursor2
									.getColumnIndex("ProductQty"));
						} catch (NumberFormatException e) {
							android.util.Log.d("catch totalPCompSet",
									e.getMessage());
							e.printStackTrace();
						}

						// order comment
						strSql = "SELECT a.PGroupID, a.ProductID, a.MenuCommentID, "
								+ " a.Qty, a.PricePerUnit, b.MenuCommentName_0 "
								+ " FROM "
								+ ORDER_SET_COMMENT_TABLE_TMP
								+ " a "
								+ " LEFT JOIN MenuComment b "
								+ " ON a.MenuCommentID=b.MenuCommentID "
								+ " WHERE a.TransactionID="
								+ transactionId
								+ " AND a.OrderDetailID="
								+ orderId
								+ " AND a.OrderSetID=" + pgs.getOrderSetID();

						Cursor cursor3 = dbHelper.myDataBase.rawQuery(strSql,
								null);
						if (cursor3.moveToFirst()) {
							pgs.menuCommentList = new ArrayList<MenuGroups.MenuComment>();

							do {
								MenuGroups.MenuComment mc = new MenuGroups.MenuComment();
								mc.setPGroupID(cursor3.getInt(cursor3
										.getColumnIndex("PGroupID")));
								mc.setProductID(cursor3.getInt(cursor3
										.getColumnIndex("ProductID")));
								mc.setMenuCommentID(cursor3.getInt(cursor3
										.getColumnIndex("MenuCommentID")));
								mc.setCommentQty(cursor3.getDouble(cursor3
										.getColumnIndex("Qty")));
								mc.setProductPricePerUnit(cursor3.getDouble(cursor3
										.getColumnIndex("PricePerUnit")));
								mc.setMenuCommentName_0(cursor3.getString(cursor3
										.getColumnIndex("MenuCommentName_0")));

								pgs.menuCommentList.add(mc);
							} while (cursor3.moveToNext());
						}
						cursor3.close();

						pcg.pCompSetLst.add(pgs);

					} while (cursor2.moveToNext());
					pcg.setTotalPCompSet(totalPCompSet);
				}
				cursor2.close();

				pCompGroupLst.add(pcg);
			} while (cursor1.moveToNext());
		}
		cursor1.close();
		closeDatabase();

		return pCompGroupLst;
	}

	public List<ProductGroups.PComponentSet> getOrderSet(int transactionId,
			int orderId) {
		List<ProductGroups.PComponentSet> pCompSetLst = new ArrayList<ProductGroups.PComponentSet>();

		String strSql = "SELECT a.OrderDetailID, a.ProductID, "
				+ " a.ProductPrice, a.ProductQty, a.SetGroupNo, a.Comment, b.MenuName_0 "
				+ " FROM " + ORDER_SET_TABLE_TMP + " a "
				+ " INNER JOIN MenuItem b " + " ON a.ProductID=b.ProductID "
				+ " WHERE a.TransactionID=" + transactionId
				+ " AND a.OrderDetailID=" + orderId;

		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			do {
				ProductGroups.PComponentSet pgs = new ProductGroups.PComponentSet();
				pgs.setOrderDetailId(cursor.getInt(cursor
						.getColumnIndex("OrderDetailID")));
				pgs.setProductID(cursor.getInt(cursor
						.getColumnIndex("ProductID")));
				pgs.setPricePerUnit(cursor.getDouble(cursor
						.getColumnIndex("ProductPrice")));
				pgs.setProductQty(cursor.getDouble(cursor
						.getColumnIndex("ProductQty")));
				pgs.setSetGroupNo(cursor.getInt(cursor
						.getColumnIndex("SetGroupNo")));
				pgs.setOrderComment(cursor.getString(cursor
						.getColumnIndex("Comment")));
				pgs.setMenuName(cursor.getString(cursor
						.getColumnIndex("MenuName_0")));

				pCompSetLst.add(pgs);
			} while (cursor.moveToNext());
		}
		cursor.close();
		closeDatabase();

		return pCompSetLst;
	}

	public void deleteOrderGroupSet(int transactionId, int orderDetailId,
			int pGroupId) {
		String strSql = "DELETE FROM " + ORDER_SET_TABLE_TMP
				+ " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderDetailId + " AND PGroupID="
				+ pGroupId;
		openDatabase();
		try {
			dbHelper.myDataBase.execSQL(strSql);
		} catch (SQLException e) {
			Log.appendLog(context, "deleteOrderGroupSet : " + e.getMessage());
		}
		closeDatabase();
	}

	public void deleteOrderSet(int transactionId, int orderDetailId,
			int OrderSetID) {
		String strSql = "DELETE FROM " + ORDER_SET_TABLE_TMP
				+ " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderDetailId + " AND OrderSetID="
				+ OrderSetID;
		openDatabase();
		try {
			dbHelper.myDataBase.execSQL(strSql);
		} catch (SQLException e) {
			Log.appendLog(context, "deleteOrderSet : " + e.getMessage());
		}
		closeDatabase();
	}

	public void updateOrderSet(int transactionId, int orderDetailId,
			int OrderSetID, String comment) {
		String strSql = "UPDATE " + ORDER_SET_TABLE_TMP + " SET Comment='"
				+ comment + "', " + " UpdateTime='"
				+ globalVar.dateTimeFormat.format(globalVar.date) + "' "
				+ " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderDetailId + " AND OrderSetID="
				+ OrderSetID;
		openDatabase();
		try {
			dbHelper.myDataBase.execSQL(strSql);
		} catch (SQLException e) {
			Log.appendLog(context, "updateOrderSet : " + e.getMessage());
		}
		closeDatabase();
	}

	public void updateOrderSet(int transactionId, int orderDetailId,
			int OrderSetID, double qty) {
		String strSql = "UPDATE " + ORDER_SET_TABLE_TMP + " SET ProductQty="
				+ qty + ", " + " UpdateTime='"
				+ globalVar.dateTimeFormat.format(globalVar.date) + "' "
				+ " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderDetailId + " AND OrderSetID="
				+ OrderSetID;
		openDatabase();
		try {
			dbHelper.myDataBase.execSQL(strSql);
		} catch (SQLException e) {
			Log.appendLog(context, "updateOrderSet : " + e.getMessage());
		}
		closeDatabase();
	}

	public void createOrderSetTmp(int transactionId, int orderDetailId) {
		String strSql = "";

		strSql = " CREATE TABLE "
				+ ORDER_SET_TABLE_TMP
				+ "( "
				+ " TransactionID  INTEGER NOT NULL, "
				+ " OrderDetailID  INTEGER NOT NULL DEFAULT 0, "
				+ " OrderSetID  INTEGER NOT NULL, "
				+ " ProductID  INTEGER NOT NULL DEFAULT 0, "
				+ " PGroupID  INTEGER NOT NULL DEFAULT 0, "
				+ " SetGroupNo  INTEGER NOT NULL DEFAULT 0, "
				+ " ProductPrice  REAL NOT NULL DEFAULT 0, "
				+ " ProductQty  REAL NOT NULL DEFAULT 0, "
				+ " Comment  TEXT NOT NULL, "
				+ " UpdateTime  TEXT, "
				+ " PRIMARY KEY (OrderSetID, TransactionID ASC, OrderDetailID ASC) "
				+ " ); ";

		openDatabase();
		try {
			dbHelper.myDataBase.execSQL("DROP TABLE IF EXISTS "
					+ ORDER_SET_TABLE_TMP);
			dbHelper.myDataBase.execSQL(strSql);

			strSql = " INSERT INTO " + ORDER_SET_TABLE_TMP + " SELECT * FROM "
					+ ORDER_SET_TABLE + " WHERE TransactionID=" + transactionId
					+ " AND OrderDetailID=" + orderDetailId;
			dbHelper.myDataBase.execSQL(strSql);
		} catch (SQLException e) {
			Log.appendLog(context, "createOrderSetTmp : " + e.getMessage());
		}

		closeDatabase();

		createOrderSetComment(transactionId, orderDetailId);
	}

	public void cancelOrderSet() {
		openDatabase();
		dbHelper.myDataBase.execSQL("DROP TABLE IF EXISTS "
				+ ORDER_SET_TABLE_TMP);
		dbHelper.myDataBase.execSQL("DROP TABLE IF EXISTS "
				+ ORDER_SET_COMMENT_TABLE_TMP);
		closeDatabase();
	}

	public void confirmOrderSet(int transactionId, int orderDetailId) {
		String strSql = "DELETE FROM " + ORDER_SET_TABLE
				+ " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderDetailId;

		openDatabase();
		try {
			dbHelper.myDataBase.execSQL("BEGIN");
			dbHelper.myDataBase.execSQL(strSql);

			strSql = " INSERT INTO " + ORDER_SET_TABLE + " SELECT * FROM "
					+ ORDER_SET_TABLE_TMP + " WHERE TransactionID="
					+ transactionId + " AND OrderDetailID=" + orderDetailId;

			dbHelper.myDataBase.execSQL(strSql);
			dbHelper.myDataBase.execSQL("COMMIT");
		} catch (SQLException e) {
			dbHelper.myDataBase.execSQL("ROLLBACK");

			Log.appendLog(context, "confirmOrderSet : " + e.getMessage());
		}
		closeDatabase();

		confirmOrderSetComment(transactionId, orderDetailId);
	}

	private int getCurrMaxOrderSetId(int transactionId, int orderDetailId) {
		int maxOrderSetId = 0;

		String strSql = "SELECT MAX(OrderSetID) " + " FROM " + ORDER_SET_TABLE
				+ " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderDetailId;

		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			maxOrderSetId = cursor.getInt(0);
		}
		cursor.close();

		return maxOrderSetId + 1;
	}

	private int getMaxOrderSetId(int transactionId, int orderDetailId) {
		int maxOrderSetId = 0;

		String strSql = "SELECT MAX(OrderSetID) " + " FROM "
				+ ORDER_SET_TABLE_TMP + " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderDetailId;

		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			maxOrderSetId = cursor.getInt(0);
		}
		cursor.close();

		return maxOrderSetId + 1;
	}

	public int addOrderSet(int transactionId, int orderId, int pGroupId,
			int setGroupNo, int productId, double productPrice,
			double productQty, String comment) {

		openDatabase();
		int maxOrderSetId = getMaxOrderSetId(transactionId, orderId);

		ContentValues cv = new ContentValues();
		cv.put("TransactionID", transactionId);
		cv.put("OrderDetailID", orderId);
		cv.put("OrderSetID", maxOrderSetId);
		cv.put("ProductID", productId);
		cv.put("PGroupID", pGroupId);
		cv.put("SetGroupNo", setGroupNo);
		cv.put("ProductPrice", productPrice);
		cv.put("ProductQty", productQty);
		cv.put("Comment", comment);

		try {
			dbHelper.myDataBase.insert(ORDER_SET_TABLE_TMP, null, cv);
		} catch (Exception e) {
			Log.appendLog(context, "addOrderSet : " + e.getMessage());
		}
		closeDatabase();
		return maxOrderSetId;
	}

	public double getOrderSetCommentQty(int transactionId, int orderDetailId,
			int orderSetID, int commentId) {

		double qty = 0.0d;
		String strSql = "SELECT MenuCommentID, Qty " + " FROM "
				+ ORDER_SET_COMMENT_TABLE_TMP + " WHERE TransactionID="
				+ transactionId + " AND OrderDetailID=" + orderDetailId
				+ " AND OrderSetID=" + orderSetID + " AND MenuCommentID="
				+ commentId;

		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			if (cursor.getDouble(cursor.getColumnIndex("MenuCommentID")) != 0) {
				qty = cursor.getDouble(cursor.getColumnIndex("Qty"));
			}
		}
		cursor.close();
		closeDatabase();
		return qty;
	}

	public List<MenuGroups.MenuComment> listOrderSetComment(int transactionId, int orderId, int orderSetId){
		dbHelper.openDataBase();
		List<MenuGroups.MenuComment> menuCommentList = 
				new ArrayList<MenuGroups.MenuComment>();
		
		// order comment
		String strSql = "SELECT a.PGroupID, a.ProductID, a.MenuCommentID, "
				+ " a.Qty, a.PricePerUnit, b.MenuCommentName_0 "
				+ " FROM "
				+ ORDER_SET_COMMENT_TABLE_TMP
				+ " a "
				+ " LEFT JOIN MenuComment b "
				+ " ON a.MenuCommentID=b.MenuCommentID "
				+ " WHERE a.TransactionID="
				+ transactionId
				+ " AND a.OrderDetailID="
				+ orderId
				+ " AND a.OrderSetID=" + orderSetId;

		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql,
				null);
		if (cursor.moveToFirst()) {
			

			do {
				MenuGroups.MenuComment mc = new MenuGroups.MenuComment();
				mc.setPGroupID(cursor.getInt(cursor
						.getColumnIndex("PGroupID")));
				mc.setProductID(cursor.getInt(cursor
						.getColumnIndex("ProductID")));
				mc.setMenuCommentID(cursor.getInt(cursor
						.getColumnIndex("MenuCommentID")));
				mc.setCommentQty(cursor.getDouble(cursor
						.getColumnIndex("Qty")));
				mc.setProductPricePerUnit(cursor.getDouble(cursor
						.getColumnIndex("PricePerUnit")));
				mc.setMenuCommentName_0(cursor.getString(cursor
						.getColumnIndex("MenuCommentName_0")));

				menuCommentList.add(mc);
			} while (cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		return menuCommentList;
	}
	
	// check orderset item commented ?
	public boolean chkOrderSetComment(int transactionId, int orderDetailId,
			int orderSetID, int commentId) {

		boolean isFound = false;

		String strSql = "SELECT MenuCommentID " + " FROM "
				+ ORDER_SET_COMMENT_TABLE_TMP + " WHERE TransactionID="
				+ transactionId + " AND OrderDetailID=" + orderDetailId
				+ " AND OrderSetID=" + orderSetID + " AND MenuCommentID="
				+ commentId;

		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			if (cursor.getDouble(cursor.getColumnIndex("MenuCommentID")) != 0) {
				isFound = true;
			}
		}
		cursor.close();
		closeDatabase();
		return isFound;
	}

	public void deleteOrderSetComment(int transactionId, int orderDetailId,
			int OrderSetID, int menuCommentId) {
		String strSql = "DELETE FROM " + ORDER_SET_COMMENT_TABLE_TMP
				+ " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderDetailId + " AND OrderSetID="
				+ OrderSetID + " AND MenuCommentID=" + menuCommentId;

		openDatabase();
		dbHelper.myDataBase.execSQL(strSql);
		closeDatabase();
	}

	// public void confirmOrderSetComment(int transactionId, int orderDetailId,
	// int OrderSetID) {
	// String strSql = "DELETE FROM " + ORDER_SET_COMMENT_TABLE +
	// " WHERE TransactionID=" + transactionId +
	// " AND OrderDetailID=" + orderDetailId +
	// " AND OrderSetID=" + OrderSetID;
	//
	// openDatabase();
	// dbHelper.myDataBase.execSQL("BEGIN");
	//
	// try {
	// dbHelper.myDataBase.execSQL(strSql);
	//
	// //retrive current comment to temp
	// strSql = "INSERT INTO " + ORDER_SET_COMMENT_TABLE +
	// " SELECT * FROM " + ORDER_SET_COMMENT_TABLE_TMP +
	// " WHERE TransactionID=" + transactionId +
	// " AND OrderDetailID=" + orderDetailId +
	// " AND OrderSetID=" + OrderSetID;
	//
	// dbHelper.myDataBase.execSQL(strSql);
	// dbHelper.myDataBase.execSQL("COMMIT");
	// } catch (SQLException e) {
	// dbHelper.myDataBase.execSQL("ROLLBACK");
	// e.printStackTrace();
	// }
	// closeDatabase();
	// }

	public void confirmOrderSetComment(int transactionId, int orderDetailId) {
		String strSql = "DELETE FROM " + ORDER_SET_COMMENT_TABLE
				+ " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderDetailId;

		openDatabase();
		dbHelper.myDataBase.execSQL("BEGIN");

		try {
			dbHelper.myDataBase.execSQL(strSql);

			// retrive current comment to temp
			strSql = "INSERT INTO " + ORDER_SET_COMMENT_TABLE
					+ " SELECT * FROM " + ORDER_SET_COMMENT_TABLE_TMP
					+ " WHERE TransactionID=" + transactionId
					+ " AND OrderDetailID=" + orderDetailId;

			dbHelper.myDataBase.execSQL(strSql);
			dbHelper.myDataBase.execSQL("COMMIT");
		} catch (SQLException e) {
			dbHelper.myDataBase.execSQL("ROLLBACK");
			Log.appendLog(context, "confirmOrderSetComment : " + e.getMessage());
		}
		closeDatabase();
	}

	public void updateOrderSetComment(int transactionId, int orderDetailId,
			int OrderSetID, int menuCommentId, double qty) {

		String strSql = " UPDATE " + ORDER_SET_COMMENT_TABLE_TMP + " SET Qty="
				+ qty + " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderDetailId + " AND OrderSetID="
				+ OrderSetID + " AND MenuCommentID=" + menuCommentId;
		openDatabase();
		dbHelper.myDataBase.execSQL(strSql);
		closeDatabase();
	}

	public void addOrderSetComment(int transactionId, int orderDetailId,
			int OrderSetID, int pgroupId, int productId, int menuCommentId,
			double qty, double price) {

		ContentValues cv = new ContentValues();
		cv.put("TransactionID", transactionId);
		cv.put("OrderDetailID", orderDetailId);
		cv.put("OrderSetID", OrderSetID);
		cv.put("MenuCommentID", menuCommentId);
		cv.put("PGroupID", pgroupId);
		cv.put("ProductID", productId);
		cv.put("Qty", qty);
		cv.put("PricePerUnit", price);

		openDatabase();
		try {
			dbHelper.myDataBase.insert(ORDER_SET_COMMENT_TABLE_TMP, null, cv);
		} catch (Exception e) {
			Log.appendLog(context, "addOrderSetComment : " + e.getMessage());
		}
		closeDatabase();
	}

	// public void createOrderSetComment(int transactionId, int orderDetailId,
	// int OrderSetID) {
	// // create temp
	// String strSql = " CREATE TABLE " + ORDER_SET_COMMENT_TABLE_TMP + " ( " +
	// " TransactionID  INTEGER NOT NULL, " +
	// " OrderDetailID  INTEGER NOT NULL, " +
	// " OrderSetID  INTEGER, " +
	// " MenuCommentID  INTEGER DEFAULT 0, " +
	// " PGroupID  INTEGER NOT NULL DEFAULT 0, " +
	// " ProductID  INTEGER NOT NULL DEFAULT 0, " +
	// " Qty  REAL NOT NULL DEFAULT 0, " +
	// " PricePerUnit  REAL NOT NULL DEFAULT 0 " +
	// " ); ";
	//
	//
	// openDatabase();
	// dbHelper.myDataBase.execSQL("DROP TABLE IF EXISTS " +
	// ORDER_SET_COMMENT_TABLE_TMP);
	// dbHelper.myDataBase.execSQL(strSql);
	//
	// //retrive current comment to temp
	// strSql = "INSERT INTO " + ORDER_SET_COMMENT_TABLE_TMP +
	// " SELECT * FROM " + ORDER_SET_COMMENT_TABLE +
	// " WHERE TransactionID=" + transactionId +
	// " AND OrderDetailID=" + orderDetailId +
	// " AND OrderSetID=" + OrderSetID;
	// dbHelper.myDataBase.execSQL(strSql);
	// closeDatabase();
	// }

	public void createOrderSetComment(int transactionId, int orderDetailId) {
		// create temp
		String strSql = " CREATE TABLE " + ORDER_SET_COMMENT_TABLE_TMP + " ( "
				+ " TransactionID  INTEGER NOT NULL, "
				+ " OrderDetailID  INTEGER NOT NULL, "
				+ " OrderSetID  INTEGER, "
				+ " MenuCommentID  INTEGER DEFAULT 0, "
				+ " PGroupID  INTEGER NOT NULL DEFAULT 0, "
				+ " ProductID  INTEGER NOT NULL DEFAULT 0, "
				+ " Qty  REAL NOT NULL DEFAULT 0, "
				+ " PricePerUnit  REAL NOT NULL DEFAULT 0 " + " ); ";

		openDatabase();
		try {
			dbHelper.myDataBase.execSQL("DROP TABLE IF EXISTS "
					+ ORDER_SET_COMMENT_TABLE_TMP);
			dbHelper.myDataBase.execSQL(strSql);

			// retrive current comment to temp
			strSql = "INSERT INTO " + ORDER_SET_COMMENT_TABLE_TMP
					+ " SELECT * FROM " + ORDER_SET_COMMENT_TABLE
					+ " WHERE TransactionID=" + transactionId
					+ " AND OrderDetailID=" + orderDetailId;
			dbHelper.myDataBase.execSQL(strSql);
		} catch (SQLException e) {
			Log.appendLog(context, "createOrderSetComment : " + e.getMessage());
		}
		closeDatabase();
	}

	public void confirmOrderComment(int transactionId, int orderDetailId) {
		String strSql = "DELETE FROM " + ORDER_COMMENT_TABLE
				+ " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderDetailId;

		openDatabase();
		dbHelper.myDataBase.execSQL("BEGIN");

		try {
			dbHelper.myDataBase.execSQL(strSql);

			strSql = "INSERT INTO " + ORDER_COMMENT_TABLE + " SELECT * FROM "
					+ ORDER_COMMENT_TABLE_TMP + " WHERE TransactionID="
					+ transactionId + " AND OrderDetailID=" + orderDetailId;

			dbHelper.myDataBase.execSQL(strSql);
			dbHelper.myDataBase.execSQL("COMMIT");
		} catch (SQLException e) {
			dbHelper.myDataBase.execSQL("ROLLBACK");
			Log.appendLog(context, "confirmOrderSetComment : " + e.getMessage());
		}
		closeDatabase();
	}

	public void createOrderCommentTmp(int transactionId, int orderDetailId) {
		// create temp
		String strSql = "CREATE TABLE " + ORDER_COMMENT_TABLE_TMP
				+ " ( TransactionID INTEGER, " + " OrderDetailID INTEGER, "
				+ " MenuCommentID INTEGER, " + " Qty REAL (18,4), "
				+ " PricePerUnit REAL (18,4) )";

		openDatabase();
		try {
			dbHelper.myDataBase.execSQL("DROP TABLE IF EXISTS "
					+ ORDER_COMMENT_TABLE_TMP);
			dbHelper.myDataBase.execSQL(strSql);

			// retrive current comment to temp
			strSql = "INSERT INTO " + ORDER_COMMENT_TABLE_TMP
					+ " SELECT * FROM " + ORDER_COMMENT_TABLE
					+ " WHERE TransactionID=" + transactionId
					+ " AND OrderDetailID=" + orderDetailId;
			dbHelper.myDataBase.execSQL(strSql);
		} catch (SQLException e) {
			Log.appendLog(context, "createOrderCommentTmp : " + e.getMessage());
		}
		closeDatabase();
	}

	public void addOrderComment(int transactionId, int orderDetailId,
			int menuCommentId, double menuCommentQty,
			double menuCommentPricePerUnit) {

		ContentValues cv = new ContentValues();
		cv.put("TransactionID", transactionId);
		cv.put("OrderDetailID", orderDetailId);
		cv.put("MenuCommentID", menuCommentId);
		cv.put("Qty", menuCommentQty);
		cv.put("PricePerUnit", menuCommentPricePerUnit);

		openDatabase();
		dbHelper.myDataBase.insert(ORDER_COMMENT_TABLE_TMP, null, cv);
		closeDatabase();
	}

	public void updateOrderComment(int transactionId, int orderDetailId,
			int menuCommentId, double menuCommentQty,
			double menuCommentPricePerUnit) {

		String strSql = "UPDATE " + ORDER_COMMENT_TABLE_TMP + " SET Qty="
				+ menuCommentQty + ", " + " PricePerUnit="
				+ menuCommentPricePerUnit + " WHERE TransactionID="
				+ transactionId + " AND OrderDetailID=" + orderDetailId
				+ " AND MenuCommentID=" + menuCommentId;

		openDatabase();
		dbHelper.myDataBase.execSQL(strSql);
		closeDatabase();
	}

	public void deleteAllOrderComment(int transactionId, int orderDetailId) {

		String strSql = "DELETE FROM " + ORDER_COMMENT_TABLE_TMP
				+ " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderDetailId;
		openDatabase();
		dbHelper.myDataBase.execSQL(strSql);
		closeDatabase();
	}

	public void deleteOrderComment(int transactionId, int orderDetailId,
			int menuCommentId) {
		String strSql = "DELETE FROM " + ORDER_COMMENT_TABLE_TMP
				+ " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID = " + orderDetailId
				+ " AND MenuCommentID=" + menuCommentId;

		openDatabase();
		dbHelper.myDataBase.execSQL(strSql);
		closeDatabase();
	}

	public void clearOrderDetail() {
		openDatabase();
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_DETAIL_TABLE);
			dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_COMMENT_TABLE);
			dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_SET_TABLE);
			dbHelper.myDataBase.execSQL("DELETE FROM "
					+ ORDER_SET_COMMENT_TABLE);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeDatabase();
	}

	public void clearOrder() {
		openDatabase();
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM "
					+ ORDER_TRANSACTION_TABLE);
			dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_SET_OF_PRODUCT);
			dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_DETAIL_TABLE);
			dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_COMMENT_TABLE);
			dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_SET_TABLE);
			dbHelper.myDataBase.execSQL("DELETE FROM "
					+ ORDER_SET_COMMENT_TABLE);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeDatabase();
	}

	// delete transaction
	public Boolean deleteTransaction(int transactionId, int computerId) {
		Boolean isSuccess = false;
		String strSql = " DELETE FROM " + ORDER_TRANSACTION_TABLE
				+ " WHERE TransactionID=" + transactionId + " AND ComputerID="
				+ computerId;
		openDatabase();
		dbHelper.myDataBase.execSQL(strSql);
		closeDatabase();

		deleteOrderDetail(transactionId);
		return isSuccess;
	}

	public Boolean deleteOrderDetail(int transactionId) {
		Boolean isSuccess = false;
		String strSql = " DELETE FROM " + ORDER_DETAIL_TABLE
				+ " WHERE TransactionID=" + transactionId;

		openDatabase();
		dbHelper.myDataBase.execSQL("BEGIN");
		try {
			dbHelper.myDataBase.execSQL(strSql);
			dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_SET_OF_PRODUCT + 
					" WHERE TransactionID=" + transactionId);
			dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_COMMENT_TABLE
					+ " WHERE TransactionID=" + transactionId);
			dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_SET_TABLE
					+ " WHERE TransactionID=" + transactionId);
			dbHelper.myDataBase.execSQL("DELETE FROM "
					+ ORDER_SET_COMMENT_TABLE + " WHERE TransactionID="
					+ transactionId);
			dbHelper.myDataBase.execSQL("COMMIT");
			isSuccess = true;
		} catch (SQLException e) {
			dbHelper.myDataBase.execSQL("ROLLBACK");
			Log.appendLog(this.context,
					"delete orderdetail fail ==> " + e.getMessage());
		}
		closeDatabase();

		return isSuccess;
	}

	// delete orderDetail
	public Boolean deleteOrderDetail(int transactionId, int orderDetailId) {
		Boolean isSuccess = false;
		String strSql = "DELETE FROM " + ORDER_DETAIL_TABLE
				+ " WHERE TransactionID = " + transactionId
				+ " AND OrderDetailID=" + orderDetailId;

		openDatabase();
		dbHelper.myDataBase.execSQL("BEGIN");
		try {
			dbHelper.myDataBase.execSQL(strSql);
			dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_SET_OF_PRODUCT + 
					" WHERE TransactionID=" + transactionId + 
					" AND OrderDetailID=" + orderDetailId);
			dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_COMMENT_TABLE
					+ " WHERE TransactionID=" + transactionId
					+ " AND OrderDetailID=" + orderDetailId);
			dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_SET_TABLE
					+ " WHERE TransactionID=" + transactionId
					+ " AND OrderDetailID=" + orderDetailId);
			dbHelper.myDataBase.execSQL("DELETE FROM "
					+ ORDER_SET_COMMENT_TABLE + " WHERE TransactionID="
					+ transactionId + " AND OrderDetailID=" + orderDetailId);
			dbHelper.myDataBase.execSQL("COMMIT");
			isSuccess = true;
		} catch (SQLException e) {
			dbHelper.myDataBase.execSQL("ROLLBACK");
			Log.appendLog(this.context,
					"delete orderdetail fail ==> " + e.getMessage());
		}
		closeDatabase();
		return isSuccess;
	}

	// update orderDetail
	public Boolean updateOrderDetail(int transactionId, int orderDetailId,
			int saleMode, String orderComment) {
		Boolean isSuccess = false;
		String strSql = "UPDATE " + ORDER_DETAIL_TABLE + " SET OrderComment='"
				+ orderComment + "', " + " SaleMode=" + saleMode + ", "
				+ " UpdateTime='"
				+ globalVar.dateTimeFormat.format(globalVar.date) + "' "
				+ " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderDetailId;

		openDatabase();
		try {
			dbHelper.myDataBase.execSQL(strSql);
		} catch (SQLException e) {
			Log.appendLog(context, "updateOrderDetail : " + e.getMessage());
		}
		closeDatabase();
		return isSuccess;
	}

	public Boolean updateOrderDetail(int transactionId, int orderDetailId,
			double qty) {
		Boolean isSuccess = false;
		String strSql = "UPDATE " + ORDER_DETAIL_TABLE + " SET Qty=" + qty
				+ ", " + " UpdateTime='"
				+ globalVar.dateTimeFormat.format(globalVar.date) + "' "
				+ " WHERE TransactionID=" + transactionId
				+ " AND OrderDetailID=" + orderDetailId;

		openDatabase();
		try {
			dbHelper.myDataBase.execSQL(strSql);
		} catch (SQLException e) {
			Log.appendLog(context, "updateOrderDetail : " + e.getMessage());
		}
		closeDatabase();

		return isSuccess;
	}

	// add set of product (child of product type 1)
	public void addOrderSetOfProduct(int transactionId, int orderDetailId, int productId, 
			double productPrice, double productQty){
		openDatabase();
		ContentValues cv = new ContentValues();
		cv.put("TransactionID", transactionId);
		cv.put("OrderDetailID", orderDetailId);
		cv.put("ProductID", productId);
		cv.put("ProductPrice", productPrice);
		cv.put("ProductQty", productQty);
		
		try {
			dbHelper.myDataBase.insert(ORDER_SET_OF_PRODUCT, null, cv);
		} catch (Exception e) {
			Log.appendLog(context, e.getMessage());
		}
		closeDatabase();
	}
	
	// add preorder
	public int addOrderDetail(int transactionId, int computerId, int shopId,
			List<POSData_OrderTransInfo.POSData_OrderItemInfo> orderItemLst) {

		// clear order detail
		clearOrderDetail();

		openDatabase();

		int lastOrderDetailId = getMaxOrderDetailId(transactionId);
		if (orderItemLst.size() > 0) {
			try {
				dbHelper.myDataBase.execSQL("BEGIN");
				for (POSData_OrderTransInfo.POSData_OrderItemInfo order : orderItemLst) {

					ContentValues cv = new ContentValues();
					cv.put("OrderDetailID", lastOrderDetailId);
					cv.put("TransactionID", transactionId);
					cv.put("ComputerID", computerId);
					cv.put("ShopID", shopId);
					cv.put("ProductID", order.getiProductID());
					cv.put("ProductName", "");
					cv.put("Qty", order.getfProductQty());
					cv.put("PricePerUnit", order.getfProductPrice());
					cv.put("OrderComment", order.getSzOrderComment());
					cv.put("UpdateTime",
							globalVar.dateTimeFormat.format(globalVar.date));

					dbHelper.myDataBase.insert(ORDER_DETAIL_TABLE, null, cv);

					// order comment
					if (order.xListCommentInfo != null) {
						if (order.xListCommentInfo.size() > 0) {

							for (POSData_OrderTransInfo.POSData_CommentInfo comment : order.xListCommentInfo) {

								cv = new ContentValues();
								cv.put("TransactionID", transactionId);
								cv.put("OrderDetailID", lastOrderDetailId);
								cv.put("MenuCommentID", comment.getiCommentID());
								cv.put("Qty", comment.getfCommentQty());
								cv.put("PricePerUnit",
										comment.getfCommentPrice());

								dbHelper.myDataBase.insert(ORDER_COMMENT_TABLE,
										null, cv);
							}
						}
					}

					// type 7
					if (order.xListChildOrderSetLinkType7 != null) {
						if (order.xListChildOrderSetLinkType7.size() > 0) {

							int maxOrderSetId = getCurrMaxOrderSetId(
									transactionId, lastOrderDetailId);

							for (POSData_OrderTransInfo.POSData_ChildOrderSetLinkType7Info set : order.xListChildOrderSetLinkType7) {

								cv = new ContentValues();
								cv.put("TransactionID", transactionId);
								cv.put("OrderDetailID", lastOrderDetailId);
								cv.put("OrderSetID", maxOrderSetId);
								cv.put("ProductID", set.getiProductID());
								cv.put("PGroupID", set.getiPGroupID());
								cv.put("SetGroupNo", set.getiSetGroupNo());
								cv.put("ProductPrice", set.getfProductPrice());
								cv.put("ProductQty", set.getfProductQty());
								cv.put("Comment", set.getSzOrderComment());

								dbHelper.myDataBase.insert(ORDER_SET_TABLE,
										null, cv);

								// comment of set
								if (set.xListCommentInfo != null) {
									if (set.xListCommentInfo.size() > 0) {

										for (POSData_OrderTransInfo.POSData_CommentInfo setComment : set.xListCommentInfo) {

											cv = new ContentValues();
											cv.put("TransactionID",
													transactionId);
											cv.put("OrderDetailID",
													lastOrderDetailId);
											cv.put("OrderSetID", maxOrderSetId);
											cv.put("MenuCommentID",
													setComment.getiCommentID());
											cv.put("PGroupID",
													set.getiPGroupID());
											cv.put("ProductID",
													set.getiProductID());
											cv.put("Qty",
													setComment.getfCommentQty());
											cv.put("PricePerUnit", setComment
													.getfCommentPrice());

											dbHelper.myDataBase.insert(
													ORDER_SET_COMMENT_TABLE,
													null, cv);
										}
									}
								}
								maxOrderSetId++;
							}
						}
					}
					lastOrderDetailId++;
				}
				dbHelper.myDataBase.execSQL("COMMIT");
			} catch (Exception e) {
				Log.appendLog(context, e.getMessage());
				lastOrderDetailId = 0;

				dbHelper.myDataBase.execSQL("ROLLBACK");
			}
		}
		closeDatabase();
		return lastOrderDetailId;
	}

	// add orderdetail
	public int addOrderDetail(int transactionId, int computerId, int shopId,
			int productId, String productName, int productTypeId, int saleMode,
			double qty, double productPrice, double vatAmount,
			double memberDiscountAmount, double priceDiscountAmount,
			int parentOrderDetailId, double discountValue) {

		openDatabase();
		int maxOrderDetailId = getMaxOrderDetailId(transactionId);

		ContentValues cv = new ContentValues();
		cv.put("OrderDetailID", maxOrderDetailId);
		cv.put("TransactionID", transactionId);
		cv.put("ComputerID", computerId);
		cv.put("ShopID", shopId);
		cv.put("ProductID", productId);
		cv.put("ProductName", productName);
		cv.put("ProductTypeID", productTypeId);
		cv.put("SaleMode", saleMode);
		cv.put("Qty", qty);
		cv.put("PricePerUnit", productPrice);
		cv.put("OrderComment", "");
		cv.put("UpdateTime", globalVar.dateTimeFormat.format(globalVar.date));

		try {
			dbHelper.myDataBase.insert(ORDER_DETAIL_TABLE, null, cv);
		} catch (Exception e) {
			Log.appendLog(this.context, "Create orderdetail success fail ==> "
					+ e.getMessage());
		}
		closeDatabase();
		return maxOrderDetailId;
	}

	public void prepareTransaction(int transactionId, int computerId,
			int staffId, String transactionNote) {

		String strSql = " UPDATE " + ORDER_TRANSACTION_TABLE
				+ " SET TransactionStatusID=1, " + " TransactionNote='"
				+ transactionNote + "', " + " UpdateDate='"
				+ globalVar.dateTimeFormat.format(globalVar.date) + "', "
				+ " UpdateStaffID=" + staffId + " WHERE TransactionID="
				+ transactionId + " AND ComputerID=" + computerId;

		openDatabase();
		dbHelper.myDataBase.execSQL(strSql);
		closeDatabase();
	}

	public void holdTransaction(int transactionId, int computerId, int staffId,
			int tableId, String tableName, int queueId, String custName,
			int custNo, String remark) {

		String strSql = " UPDATE " + ORDER_TRANSACTION_TABLE
				+ " SET TransactionStatusID=9, " + " TableID=" + tableId + ", "
				+ " TableName='" + tableName + "', " + " QueueID=" + queueId
				+ ", " + " NoCustomer=" + custNo + ", " + " QueueName='"
				+ custName + "', " + " TransactionNote='" + remark + "', "
				+ " UpdateDate='"
				+ globalVar.dateTimeFormat.format(globalVar.date) + "', "
				+ " UpdateStaffID=" + staffId + " WHERE TransactionID="
				+ transactionId + " AND ComputerID=" + computerId;
		openDatabase();
		try {
			dbHelper.myDataBase.execSQL(strSql);
		} catch (SQLException e) {
			Log.appendLog(context, "holdTransaction : " + e.getMessage());
		}
		closeDatabase();
	}

	public void successTransaction(int transactionId, int computerId,
			int staffId) {
		// clearOrder();
		String strSql = " UPDATE " + ORDER_TRANSACTION_TABLE
				+ " SET TransactionStatusID=2, " + " UpdateDate='"
				+ globalVar.dateTimeFormat.format(globalVar.date) + "', "
				+ " UpdateStaffID=" + staffId + " WHERE TransactionID="
				+ transactionId + " AND ComputerID=" + computerId;

		openDatabase();
		dbHelper.myDataBase.execSQL(strSql);
		closeDatabase();
	}

	public int getCurrentTransaction(int computerId) {
		int transactionId = 0;
		String strSql = " SELECT TransactionID " + " FROM "
				+ ORDER_TRANSACTION_TABLE + " WHERE TransactionStatusID=1 "
				+ " AND ComputerID=" + computerId + " AND SaleDate='"
				+ globalVar.dateFormat.format(globalVar.date) + "'"
				+ " ORDER BY TransactionID DESC LIMIT 1";

		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);

		if (cursor.moveToFirst()) {
			if (cursor.getInt(0) != 0)
				transactionId = cursor.getInt(0);
		}

		cursor.close();

		dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_TRANSACTION_TABLE
				+ " WHERE TransactionID != " + transactionId
				+ " AND TransactionStatusID=1 " + " AND ComputerID="
				+ computerId);
		closeDatabase();

		return transactionId;
	}

	// create transaction
	public int openTransaction(int computerId, int shopId, int staffId,
			int saleMode, int noCustomer, int sessionId) {

		int maxTransactionNo = getMaxTransaction(computerId);

		ContentValues cv = new ContentValues();
		cv.put("TransactionId", maxTransactionNo);
		cv.put("ComputerID", computerId);
		cv.put("ShopID", shopId);
		cv.put("OpenTime", globalVar.dateTimeFormat.format(globalVar.date));
		cv.put("OpenStaffID", staffId);
		cv.put("TransactionStatusID", 1);
		cv.put("SaleMode", saleMode);
		cv.put("TableName", "");
		cv.put("QueueName", "");
		cv.put("NoCustomer", noCustomer);
		cv.put("DocType", 8);
		cv.put("SaleDate", globalVar.dateFormat.format(globalVar.date));
		cv.put("SessionID", sessionId);
		cv.put("UpdateStaffID", staffId);
		cv.put("UpdateDate", globalVar.dateTimeFormat.format(globalVar.date));

		openDatabase();
		try {
			dbHelper.myDataBase.insert(ORDER_TRANSACTION_TABLE, null, cv);
		} catch (Exception e) {
			e.printStackTrace();
			Log.appendLog(this.context,
					"Create transaction fail ==> " + e.getMessage() + "\n");
		}
		// }
		closeDatabase();
		return maxTransactionNo;
	}

	// get maxtransactionId
	public int getMaxTransaction(int computerId) {
		int maxTransactionId = 0;

		String strSql = "SELECT MAX(TransactionID) " + " FROM "
				+ ORDER_TRANSACTION_TABLE + " WHERE ComputerID=" + computerId;

		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql.toString(), null);
		cursor.moveToFirst();

		if (!cursor.isAfterLast())
			maxTransactionId = cursor.getInt(0);

		cursor.close();
		closeDatabase();

		return maxTransactionId + 1;
	}

	// get maxorderdetailid
	public int getMaxOrderDetailId(int transactionId) {
		int orderId = 0;
		Cursor cursor = dbHelper.myDataBase.rawQuery(
				" SELECT MAX(OrderDetailID) " + " FROM " + ORDER_DETAIL_TABLE
						+ " WHERE TransactionID=" + transactionId, null);
		cursor.moveToFirst();

		if (!cursor.isAfterLast())
			orderId = cursor.getInt(0);

		cursor.close();
		return orderId + 1;
	}

	public void clearTransaction() {
		openDatabase();
		dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_SET_OF_PRODUCT);
		dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_SET_TABLE);
		dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_SET_COMMENT_TABLE);
		dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_DETAIL_TABLE);
		dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_COMMENT_TABLE);
		dbHelper.myDataBase.execSQL("DELETE FROM " + ORDER_TRANSACTION_TABLE);
		closeDatabase();
	}
}
