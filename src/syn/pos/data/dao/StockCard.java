package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.StockCardData;

import android.content.Context;
import android.database.Cursor;

public class StockCard {
	private Context c;
	private DataBaseHelper dbHelper;
	public StockCard(Context context){
		c = context;
		dbHelper = new DataBaseHelper(c);
	}
	
	public List<StockCardData> getStockCardData(int shopId, String dateFrom, String dateTo){
		StockTempTable.createMaterialData(c);
		createBeginData(shopId, dateTo);
		createReceiptData(shopId, dateFrom, dateTo);
		createSaleData(shopId, dateFrom, dateTo);
		createStockCountData(shopId, dateFrom, dateTo);
		
		List<StockCardData> stockList = new ArrayList<StockCardData>();
		String strSql = " SELECT a.MaterialID, a.MaterialCode, a.MaterialName, " +
				" a.ProductUnitName, b.BeginAmount, " +
				" c.ReceiptAmount, d.SaleAmount, " +
				" e.MovementAmount AS Variance " +
				" FROM materialtmp a " +
				" LEFT JOIN begintmp b " + 
				" ON a.MaterialID=b.MaterialID " +
				" LEFT JOIN receipttmp c " +
				" ON a.MaterialID=c.MaterialID " + 
				" LEFT JOIN saletmp d " +
				" ON a.MaterialID=d.MaterialID " +
				" LEFT JOIN stockcounttmp e " +
				" ON a.MaterialID=e.MaterialID ";
		
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			do{
				StockCardData stock = new StockCardData();
				stock.setProductCode(cursor.getString(1));
				stock.setProductName(cursor.getString(2));
				stock.setProductUnitName(cursor.getString(3));
				stock.setBegin(cursor.getDouble(4));
				stock.setReceipt(cursor.getDouble(5));
				stock.setSale(cursor.getDouble(6));
				stock.setEndding(stock.getBegin() + stock.getReceipt() + 
						stock.getSale());
				stock.setVariance(cursor.getDouble(7));
				stock.setSummary(stock.getEndding() + stock.getVariance());
				stockList.add(stock);
			}while(cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		return stockList;
	}
	
	public void createBeginData(int shopId, String date){
		StockTempTable.createBeginTmpTable(c);
		String strSql = "INSERT INTO begintmp " +
				" SELECT a.DocumentID, a.ShopID, b.materialId, " +
				" sum(b.MaterialQty) " +
				" FROM document a " +
				" INNER JOIN docdetail b " +
				" ON a.DocumentID=b.DocumentID " +
				" AND a.ShopID=b.ShopID " +
				" WHERE a.DocumentDate = '" + date + "' " +
				" AND a.ShopID=" + shopId + 
				" GROUP BY b.MaterialID ";
		dbHelper.openDataBase();
		dbHelper.myDataBase.execSQL("BEGIN TRANSACTION");
		dbHelper.myDataBase.execSQL(strSql);
		dbHelper.myDataBase.execSQL("COMMIT");
		dbHelper.closeDataBase();
	}
	
	public void createReceiptData(int shopId, String dateFrom, String dateTo){
		StockTempTable.createReceiptTable(c);
		
		String strSql = "INSERT INTO receipttmp " +
				" SELECT a.DocumentID, a.ShopID, b.materialId, " +
				" sum(b.MaterialQty * c.MovementInStock) " +
				" FROM document a " +
				" INNER JOIN docdetail b " +
				" ON a.DocumentID=b.DocumentID " +
				" AND a.ShopID=b.ShopID " +
				" LEFT JOIN documenttype c " + 
				" ON a.DocumentTypeID=c.DocumentTypeID " +
				" WHERE a.DocumentTypeID=39 AND a.DocumentDate BETWEEN '" + dateFrom + "' " +
				" AND '" + dateTo + "' " +
				" AND a.ShopID=" + shopId + 
				" GROUP BY b.MaterialID ";
		dbHelper.openDataBase();
		dbHelper.myDataBase.execSQL("BEGIN TRANSACTION");
		dbHelper.myDataBase.execSQL(strSql);
		dbHelper.myDataBase.execSQL("COMMIT");
		dbHelper.closeDataBase();
	}
	
	public void createSaleData(int shopId, String dateFrom, String dateTo){
		StockTempTable.createSaleTmpTable(c);
		
		String strSql = "INSERT INTO saletmp " +
				" SELECT a.DocumentID, a.ShopID, b.materialId, " +
				" sum(b.MaterialQty * c.MovementInStock) " +
				" FROM document a " +
				" INNER JOIN docdetail b " +
				" ON a.DocumentID=b.DocumentID " +
				" LEFT JOIN documenttype c " + 
				" ON a.DocumentTypeID=c.DocumentTypeID " +
				" AND a.ShopID=b.ShopID " +
				" WHERE a.DocumentTypeID=20 AND a.DocumentDate BETWEEN '" + dateFrom + "' " +
				" AND '" + dateTo + "' " +
				" AND a.ShopID=" + shopId + 
				" GROUP BY b.MaterialID ";
		dbHelper.openDataBase();
		dbHelper.myDataBase.execSQL("BEGIN TRANSACTION");
		dbHelper.myDataBase.execSQL(strSql);
		dbHelper.myDataBase.execSQL("COMMIT");
		dbHelper.closeDataBase();
	}
	
	public void createStockCountData(int shopId, String dateFrom, String dateTo){
		StockTempTable.createStockCountTmpTable(c);
		
		String strSql = "INSERT INTO stockcounttmp " +
				" SELECT a.DocumentID, a.ShopID, b.materialId, " +
				" sum(b.MaterialQty * c.MovementInStock), " +
				" b.MaterialCountQty " +
				" FROM document a " +
				" INNER JOIN docdetail b " +
				" ON a.DocumentID=b.DocumentID " +
				" LEFT JOIN documenttype c " + 
				" ON a.DocumentTypeID=c.DocumentTypeID " +
				" AND a.ShopID=b.ShopID " +
				" WHERE a.DocumentTypeID IN (18, 19, 24) AND a.DocumentDate BETWEEN '" + dateFrom + "' " +
				" AND '" + dateTo + "' " +
				" AND a.ShopID=" + shopId + 
				" GROUP BY b.MaterialID ";
		dbHelper.openDataBase();
		dbHelper.myDataBase.execSQL("BEGIN TRANSACTION");
		dbHelper.myDataBase.execSQL(strSql);
		dbHelper.myDataBase.execSQL("COMMIT");
		dbHelper.closeDataBase();
	}
}
