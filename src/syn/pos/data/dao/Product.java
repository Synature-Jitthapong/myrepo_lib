package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import syn.pos.data.model.MenuDataItem;
import syn.pos.data.model.ProductGroups;
import syn.pos.mobile.util.Log;

public class Product{
	private DataBaseHelper dbHelper;
	private GlobalVar globalVar;
	private Context context;
	private final String tbName = "Products";
	private String strSql = "SELECT * FROM " + this.tbName;

	public Product(Context context) {
		dbHelper = new DataBaseHelper(context);
		globalVar = new GlobalVar(context, dbHelper);
		this.context = context;
	}
	
	public Product(Context context, int productGroupId, int productDeptId) {
		dbHelper = new DataBaseHelper(context);
		globalVar = new GlobalVar(context, dbHelper);
		strSql = " SELECT * FROM Products WHERE ProductGroupID="
				+ productGroupId + " AND ProductDeptID=" + productDeptId;
	}

	public Product(Context context, String productCode) {
		dbHelper = new DataBaseHelper(context);
		globalVar = new GlobalVar(context, dbHelper);
		strSql = "SELECT * FROM Products WHERE ProductCode LIKE '%"
				+ productCode + "%'";
	}

	public Product(Context context, int productId) {
		dbHelper = new DataBaseHelper(context);
		globalVar = new GlobalVar(context, dbHelper);
		strSql = "SELECT * FROM Products WHERE ProductID = " + productId;
	}

	public long countProducts() {
		long rows = 0;
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery("SELECT COUNT(*) FROM "
				+ this.tbName, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			rows = cursor.getLong(0);
			cursor.moveToNext();
		}

		cursor.close();
		dbHelper.closeDataBase();

		return rows;
	}

	public void deleteAllProduct() {
		dbHelper.openDataBase();
		dbHelper.myDataBase.execSQL("DELETE FROM Products");
		dbHelper.closeDataBase();
	}

	public void setOutOfStock(List<Integer> productIdLst){
		dbHelper.openDataBase();
		
		String sqlReset = "UPDATE Products SET IsOutOfStock=0";

		dbHelper.myDataBase.execSQL("BEGIN");
		try {
			dbHelper.myDataBase.execSQL(sqlReset);
			dbHelper.myDataBase.execSQL("COMMIT");
		} catch (SQLException e) {
			dbHelper.myDataBase.execSQL("ROLLBACK");
			Log.appendLog(context, e.getMessage());
			e.printStackTrace();
		}
		
		if(productIdLst != null){
			String sqlUpdate = " UPDATE Products SET IsOutOfStock=1"
					+ " WHERE ProductID IN (";
			if (productIdLst.size() > 0) {

				for (int i = 0; i < productIdLst.size(); i++) {
					sqlUpdate += productIdLst.get(i);
					if (i < productIdLst.size() - 1)
						sqlUpdate += ",";
				}
				sqlUpdate += " )";

				dbHelper.myDataBase.execSQL("BEGIN");
				try {
					dbHelper.myDataBase.execSQL(sqlUpdate);
					dbHelper.myDataBase.execSQL("COMMIT");
				} catch (SQLException e) {
					dbHelper.myDataBase.execSQL("ROLLBACK");
					Log.appendLog(context, e.getMessage());
					e.printStackTrace();
				}

			}
		}
		
		dbHelper.closeDataBase();
	}
	
	public void insertCommentProduct(ProductGroups pg){
		dbHelper.openDataBase();
		dbHelper.myDataBase.beginTransaction();
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM CommentProduct");
			
			for(ProductGroups.CommentProduct comment : pg.getCommentProduct()){
				ContentValues cv = new ContentValues();
				cv.put("ProductID", comment.getProductID());
				cv.put("CommentID", comment.getCommentID());
				
				dbHelper.myDataBase.insert("CommentProduct", null, cv);
			}
			dbHelper.myDataBase.setTransactionSuccessful();
		} catch (SQLException e) {
			Log.appendLog(context, e.getMessage());
			e.printStackTrace();
		}finally{
			dbHelper.myDataBase.endTransaction();
		}
		dbHelper.closeDataBase();
	}
	
	public Boolean insertProduct(ProductGroups pg) {
		Boolean isSuccess = false;
		dbHelper.openDataBase();
		dbHelper.myDataBase.beginTransaction();
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM Products");
			for (ProductGroups.Products p : pg.getProduct()) {
				ContentValues cv = new ContentValues();

				cv.put("ProductID", p.getProductID());
				cv.put("ProductDeptID", p.getProductDeptID());
				cv.put("ProductGroupID", p.getProductGroupID());
				cv.put("ProductCode", p.getProductCode());
				cv.put("ProductBarCode", p.getProductBarCode());
				cv.put("ProductTypeID", p.getProductTypeID());
				cv.put("ProductPricePerUnit", p.getProductPricePerUnit());
				cv.put("ProductUnitName", p.getProductUnitName());
				cv.put("ProductDesc", p.getProductDesc());
				cv.put("DiscountAllow", p.getDiscountAllow());
				cv.put("VatType", p.getVatType());
				cv.put("VatRate", p.getVatRate());
				cv.put("HasServiceCharge", p.getHasServiceCharge());
				cv.put("Activate", p.getActivate());
				cv.put("IsOutOfStock", p.getIsOutOfStock());
				cv.put("SaleMode1", p.getSaleMode1());
				cv.put("ProductPricePerUnit1", p.getProductPricePerUnit1());
				cv.put("SaleMode2", p.getSaleMode2());
				cv.put("ProductPricePerUnit2", p.getProductPricePerUnit2());
				cv.put("SaleMode3", p.getSaleMode3());
				cv.put("ProductPricePerUnit3", p.getProductPricePerUnit3());
				cv.put("SaleMode4", p.getSaleMode4());
				cv.put("ProductPricePerUnit4", p.getProductPricePerUnit4());
				cv.put("SaleMode5", p.getSaleMode5());
				cv.put("ProductPricePerUnit5", p.getProductPricePerUnit5());
				cv.put("UpdateDate", p.getUpdateDate());

				dbHelper.myDataBase.insert("Products", null, cv);
				isSuccess = true;
			}
			dbHelper.myDataBase.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
			isSuccess = false;
			Log.appendLog(context, e.getMessage());
		} finally{
			dbHelper.myDataBase.endTransaction();
		}
		dbHelper.closeDataBase();
		return isSuccess;
	}

	public MenuDataItem getProductData(String productCode){
		MenuDataItem m = new MenuDataItem();
		
		dbHelper.openDataBase();
		strSql = "SELECT a.ProductID, a.ProductCode, " +
				" a.ProductPricePerUnit, a.DiscountAllow, " +
				" a.VatType, b.MenuName_0, a.ProductUnitName " +
				" FROM Products a " +
				" LEFT JOIN MenuItem b " +
				" ON a.ProductID=b.ProductID " +
				" WHERE a.ProductBarCode = '" + productCode + "'";
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			do{
				m.setProductID(cursor.getInt(0));
				m.setProductCode(cursor.getString(1));
				m.setPricePerUnit(cursor.getDouble(2));
				m.setDiscountAllow(cursor.getInt(3));
				m.setVatType(cursor.getInt(4));
				m.setMenuName(cursor.getString(5));
				m.setCurrencySymbol(globalVar.currency.getSymbol());
				m.setProductUnitName(cursor.getString(6));
			}while(cursor.moveToNext());
		}

		cursor.close();
		dbHelper.closeDataBase();
		
		return m;
	}
	
	public ProductGroups.Products getProductData() {
		ProductGroups.Products p = new ProductGroups.Products();
		dbHelper.openDataBase();

		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			p.setProductPricePerUnit(cursor.getDouble(6));
			cursor.moveToNext();
		}

		cursor.close();
		dbHelper.closeDataBase();
		return p;
	}

	public List<ProductGroups.Products> listProducts() {

		dbHelper.openDataBase();

		List<ProductGroups.Products> pl = new ArrayList<ProductGroups.Products>();

		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			ProductGroups.Products p = new ProductGroups.Products();
			p.setProductID(cursor.getInt(0));
			p.setProductDeptID(cursor.getInt(1));
			p.setProductGroupID(cursor.getInt(2));
			p.setProductCode(cursor.getString(3));
			p.setProductBarCode(cursor.getString(4));
			p.setProductTypeID(cursor.getInt(5));
			p.setProductPricePerUnit(cursor.getDouble(6));
			p.setProductUnitName(cursor.getString(7));
			p.setProductDesc(cursor.getString(8));
			p.setDiscountAllow(cursor.getInt(9));
			p.setVatType(cursor.getInt(10));
			p.setVatRate(cursor.getDouble(11));
			p.setHasServiceCharge(cursor.getInt(12));
			p.setActivate(cursor.getInt(13));
			p.setIsOutOfStock(cursor.getInt(14));
			p.setSaleMode1(cursor.getInt(15));
			p.setSaleMode2(cursor.getInt(16));
			p.setSaleMode3(cursor.getInt(17));
			p.setSaleMode4(cursor.getInt(18));
			p.setSaleMode5(cursor.getInt(19));
			p.setUpdateDate(cursor.getString(20));

			pl.add(p);
			cursor.moveToNext();
		}

		cursor.close();
		dbHelper.closeDataBase();
		return pl;
	}

}
