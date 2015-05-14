package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.*;
import syn.pos.mobile.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public class MenuItem {
	private DataBaseHelper dbHelper;
	private GlobalVar globalVar;
	private Context context;
	private String SALE_MODE_COL = "saleMode1";
	private String SALE_MODE_PRICE_COL = "ProductPricePerUnit";
	private String strSql = "SELECT * FROM MenuItem";

	public MenuItem(Context context) {
		dbHelper = new DataBaseHelper(context);
		globalVar = new GlobalVar(context, dbHelper);
		this.context = context;
		
		strSql = " SELECT a.MenuImageLink, a.MenuName_0, a.MenuName_1, a.MenuName_2, " +
				" a.MenuDesc_0, a.MenuShortName_0, a.MenuShortName_1, " +
				" b.ProductID, b.ProductCode, b.ProductBarCode, b." + SALE_MODE_PRICE_COL + ", " +
				" b.DiscountAllow, b.IsOutOfStock, " +
				" b.VatType, a.MenuDeptID, b.ProductUnitName, " +
				" b.ProductTypeID, b.SaleMode1, b.SaleMode2 " +
				" FROM MenuItem a " +
				" LEFT JOIN Products b " +
				" ON a.ProductID=b.ProductID " +
				" WHERE a.MenuActivate=1 " +
				" ORDER BY a.MenuItemOrdering";
	}
	
	public MenuItem(Context context, int menuDeptId) {
		dbHelper = new DataBaseHelper(context);
		globalVar = new GlobalVar(context, dbHelper);
		this.context = context;
		
		strSql = " SELECT a.MenuImageLink, a.MenuName_0, a.MenuName_1, a.MenuName_2, " +
				" a.MenuDesc_0,  a.MenuShortName_0, a.MenuShortName_1, " +
				" b.ProductID,  b.ProductCode, b.ProductBarCode, b." + SALE_MODE_PRICE_COL + ", " +
				" b.DiscountAllow, b.IsOutOfStock, " +
				" b.VatType, a.MenuDeptID, b.ProductUnitName, " + 
				" b.ProductTypeID, b.SaleMode1, b.SaleMode2 " +
				" FROM MenuItem a " +
				" LEFT JOIN Products b " +
				" ON a.ProductID=b.ProductID " +
				" WHERE a.MenuDeptID=" + menuDeptId +
				" AND a.MenuActivate=1 " +
				" ORDER BY a.MenuItemOrdering";
	}
	
	public MenuItem(Context context, int menuGroupId, int menuDeptId, int saleMode) {
		dbHelper = new DataBaseHelper(context);
		globalVar = new GlobalVar(context, dbHelper);
		this.context = context;
		switch(saleMode){
			case 1:
				SALE_MODE_COL = "saleMode1 = 1";
				SALE_MODE_PRICE_COL = "ProductPricePerUnit";
				break;
			case 2:
				SALE_MODE_COL = "saleMode2 = 1";
				SALE_MODE_PRICE_COL = "ProductPricePerUnit2";
				break;
			case 3:
				SALE_MODE_COL = "saleMode3 = 1";
				SALE_MODE_PRICE_COL = "ProductPricePerUnit3";
				break;
		}
		strSql = " SELECT a.MenuImageLink, a.MenuName_0, a.MenuName_1, a.MenuName_2, " +
				" a.MenuDesc_0, a.MenuShortName_0, a.MenuShortName_1, " +
				" b.ProductID, b.ProductCode, b.ProductBarCode,  b." + SALE_MODE_PRICE_COL + ", " +
				" b.DiscountAllow, b.IsOutOfStock, " +
				" b.VatType, a.MenuDeptID, b.ProductUnitName, " + 
				" b.ProductTypeID, b.SaleMode1, b.SaleMode2 " +
				" FROM MenuItem a " +
				" LEFT JOIN Products b " +
				" ON a.ProductID=b.ProductID " +
				" WHERE a.MenuDeptID=" + menuDeptId +
				" AND b." + SALE_MODE_COL + 
				" AND a.MenuActivate=1 " +
				" ORDER BY a.MenuItemOrdering";
	}

	public MenuDataItem getMenuItem(int productId){
		MenuDataItem mi = new MenuDataItem();
		dbHelper.openDataBase();
		String strSql = "SELECT * FROM MenuItem " +
				" WHERE ProductID=" + productId;
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			mi.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName_0")));
		}
		cursor.close();
		dbHelper.closeDataBase();
		return mi;
	}
	
	public List<MenuDataItem> pluSearchListItem(String pluCode, String column1, String column2, int saleMode){
		switch(saleMode){
			case 1:
				SALE_MODE_COL = "saleMode1 = 1";
				SALE_MODE_PRICE_COL = "ProductPricePerUnit";
				break;
			case 2:
				SALE_MODE_COL = "saleMode2 = 1";
				SALE_MODE_PRICE_COL = "ProductPricePerUnit2";
				break;
			case 3:
				SALE_MODE_COL = "saleMode3 = 1";
				SALE_MODE_PRICE_COL = "ProductPricePerUnit3";
				break;
		}
		
		List<MenuDataItem> mdl = new ArrayList<MenuDataItem>();
		strSql = " SELECT a.MenuImageLink, a.MenuName_0, a.MenuDesc_0, a.MenuName_1, a.MenuName_2, " +
				" a.MenuShortName_0, a.MenuShortName_1, b.ProductID, b." + SALE_MODE_PRICE_COL + ", " +
				" b.DiscountAllow, b.IsOutOfStock, " +
				" b.VatType, a.MenuDeptID, b.ProductUnitName, b.ProductCode, b.ProductBarCode, " +
				" b.ProductTypeID, b.SaleMode1, b.SaleMode2 " +
				" FROM MenuItem a " +
				" LEFT JOIN Products b " +
				" ON a.ProductID=b.ProductID " +
				" WHERE b." + column1 + " LIKE '%" + pluCode + "%' OR b." + column2 + " LIKE '%"  + pluCode + "%' " +
				" AND b." + SALE_MODE_COL + 
				" AND a.MenuActivate=1 " +
				" ORDER BY a.MenuItemOrdering";
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			do{
				MenuDataItem md = new MenuDataItem();
				md.setProductID(cursor.getInt(cursor.getColumnIndex("ProductID")));
				md.setImgUrl(cursor.getString(cursor.getColumnIndex("MenuImageLink")));
				md.setPricePerUnit(cursor.getDouble(cursor.getColumnIndex(SALE_MODE_PRICE_COL)));
				md.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName_0")));
				md.setMenuName1(cursor.getString(cursor.getColumnIndex("MenuName_1")));
				md.setMenuName2(cursor.getString(cursor.getColumnIndex("MenuName_2")));
				md.setMenuShortName(cursor.getString(cursor.getColumnIndex("MenuShortName_0")));
				md.setMenuShortName1(cursor.getString(cursor.getColumnIndex("MenuShortName_1")));
				md.setMenuDesc(cursor.getString(cursor.getColumnIndex("MenuDesc_0")));
				md.setVatType(cursor.getInt(cursor.getColumnIndex("VatType")));
				md.setDiscountAllow(cursor.getInt(cursor.getColumnIndex("DiscountAllow")));
				md.setIsOutOfStock(cursor.getInt(cursor.getColumnIndex("IsOutOfStock")));
				md.setMenuDeptID(cursor.getInt(cursor.getColumnIndex("MenuDeptID")));
				md.setProductUnitName(cursor.getString(cursor.getColumnIndex("ProductUnitName")));
				md.setProductCode(cursor.getString(cursor.getColumnIndex("ProductCode")));
				md.setProductBarcode(cursor.getString(cursor.getColumnIndex("ProductBarCode")));
				md.setProductTypeID(cursor.getInt(cursor.getColumnIndex("ProductTypeID")));
				md.setSaleMode(cursor.getInt(cursor.getColumnIndex("SaleMode1")));
				md.setSaleMode2(cursor.getInt(cursor.getColumnIndex("SaleMode2")));
				mdl.add(md);
			}while(cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		return mdl;
	}
	
	public List<MenuDataItem> pluSearchListItem(String pluCode, String column, int saleMode){
		List<MenuDataItem> mdl = new ArrayList<MenuDataItem>();
		
		switch(saleMode){
			case 1:
				SALE_MODE_COL = "saleMode1 = 1";
				SALE_MODE_PRICE_COL = "ProductPricePerUnit";
				break;
			case 2:
				SALE_MODE_COL = "saleMode2 = 1";
				SALE_MODE_PRICE_COL = "ProductPricePerUnit2";
				break;
			case 3:
				SALE_MODE_COL = "saleMode3 = 1";
				SALE_MODE_PRICE_COL = "ProductPricePerUnit3";
				break;
		}
		
		strSql = " SELECT a.MenuImageLink, a.MenuName_0, a.MenuDesc_0, a.MenuName_1, a.MenuName_2, " +
				" a.MenuShortName_0, a.MenuShortName_1, b.ProductID, b." + SALE_MODE_PRICE_COL + ", " +
				" b.DiscountAllow, b.IsOutOfStock, " +
				" b.VatType, a.MenuDeptID, b.ProductUnitName, b.ProductCode, b.ProductBarCode, " +
				" b.ProductTypeID, b.SaleMode1, b.SaleMode2 " +
				" FROM MenuItem a " +
				" LEFT JOIN Products b " +
				" ON a.ProductID=b.ProductID " +
				" WHERE b." + column + " LIKE '%" + pluCode + "%' "  +
				" AND b." + SALE_MODE_COL +
				" AND a.MenuActivate=1 " +
				" ORDER BY a.MenuItemOrdering, b." + column;
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			do{
				MenuDataItem md = new MenuDataItem();
				md.setProductID(cursor.getInt(cursor.getColumnIndex("ProductID")));
				md.setImgUrl(cursor.getString(cursor.getColumnIndex("MenuImageLink")));
				md.setPricePerUnit(cursor.getDouble(cursor.getColumnIndex(SALE_MODE_PRICE_COL)));
				md.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName_0")));
				md.setMenuName1(cursor.getString(cursor.getColumnIndex("MenuName_1")));
				md.setMenuName2(cursor.getString(cursor.getColumnIndex("MenuName_2")));
				md.setMenuShortName(cursor.getString(cursor.getColumnIndex("MenuShortName_0")));
				md.setMenuShortName1(cursor.getString(cursor.getColumnIndex("MenuShortName_1")));
				md.setMenuDesc(cursor.getString(cursor.getColumnIndex("MenuDesc_0")));
				md.setVatType(cursor.getInt(cursor.getColumnIndex("VatType")));
				md.setDiscountAllow(cursor.getInt(cursor.getColumnIndex("DiscountAllow")));
				md.setIsOutOfStock(cursor.getInt(cursor.getColumnIndex("IsOutOfStock")));
				md.setMenuDeptID(cursor.getInt(cursor.getColumnIndex("MenuDeptID")));
				md.setProductUnitName(cursor.getString(cursor.getColumnIndex("ProductUnitName")));
				md.setProductCode(cursor.getString(cursor.getColumnIndex("ProductCode")));
				md.setProductBarcode(cursor.getString(cursor.getColumnIndex("ProductBarCode")));
				md.setProductTypeID(cursor.getInt(cursor.getColumnIndex("ProductTypeID")));
				md.setSaleMode(cursor.getInt(cursor.getColumnIndex("SaleMode1")));
				md.setSaleMode2(cursor.getInt(cursor.getColumnIndex("SaleMode2")));
				mdl.add(md);
			}while(cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		return mdl;
	}
	
	public Boolean insertMenuItem(MenuGroups mg) {
		Boolean isSuccess = false;
		dbHelper.openDataBase();
		dbHelper.myDataBase.beginTransaction();
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM MenuItem");
			ContentValues cv = new ContentValues();
			for (MenuGroups.MenuItem mi : mg.getMenuItem()) {
				cv.put("MenuItemID", mi.getMenuItemID());
				cv.put("ProductID", mi.getProductID());
				cv.put("MenuDeptID", mi.getMenuDeptID());
				cv.put("MenuGroupID", mi.getMenuGroupID());
				cv.put("MenuName_0", mi.getMenuName_0());
				cv.put("MenuName_1", mi.getMenuName_1());
				cv.put("MenuName_2", mi.getMenuName_2());
				cv.put("MenuName_3", mi.getMenuName_3());
				cv.put("MenuDesc_0", mi.getMenuDesc_0());
				cv.put("MenuDesc_1", mi.getMenuDesc_1());
				cv.put("MenuDesc_2", mi.getMenuDesc_2());
				cv.put("MenuDesc_3", mi.getMenuDesc_3());
				cv.put("MenuShortName_0", mi.getMenuShortName_0());
				cv.put("MenuShortName_1", mi.getMenuShortName_1());
				cv.put("MenuShortName_2", mi.getMenuShortName_2());
				cv.put("MenuShortName_3", mi.getMenuShortName_3());
				cv.put("MenuImageLink", mi.getMenuImageLink());
				cv.put("MenuItemOrdering", mi.getMenuItemOrdering());
				cv.put("UpdateDate", mi.getUpdateDate());
				cv.put("MenuActivate", mi.getMenuActivate());

				dbHelper.myDataBase.insert("MenuItem", null, cv);
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

	
	public String getImageUrl(int productId){
		String imageUrl = "";
		strSql = " SELECT a.MenuImageLink, a.MenuName_0," +
				" b.ProductID, b.ProductPricePerUnit, " +
				" b.DiscountAllow, b.IsOutOfStock, " +
				" b.VatType, a.MenuDeptID, b.ProductUnitName, b.ProductBarCode, " + 
				" b.ProductTypeID " +
				" FROM MenuItem a " +
				" INNER JOIN Products b " +
				" ON a.ProductID=b.ProductID " +
				" WHERE a.ProductID=" + productId;
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);

		if(cursor.moveToFirst()){
			imageUrl = cursor.getString(cursor.getColumnIndex("MenuImageLink"));
		}

		cursor.close();
		dbHelper.closeDataBase();
		return imageUrl;
	}
	
	public List<MenuDataItem> getMenuItem() {
		List<MenuDataItem> mdl = new ArrayList<MenuDataItem>();
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);

		if(cursor.moveToFirst()){
			do{
				MenuDataItem md = new MenuDataItem();
				md.setProductID(cursor.getInt(cursor.getColumnIndex("ProductID")));
				md.setImgUrl(cursor.getString(cursor.getColumnIndex("MenuImageLink")));
				md.setPricePerUnit(cursor.getDouble(cursor.getColumnIndex(SALE_MODE_PRICE_COL)));
				md.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName_0")));
				md.setMenuName1(cursor.getString(cursor.getColumnIndex("MenuName_1")));
				md.setMenuName2(cursor.getString(cursor.getColumnIndex("MenuName_2")));
				md.setMenuShortName(cursor.getString(cursor.getColumnIndex("MenuShortName_0")));
				md.setMenuShortName1(cursor.getString(cursor.getColumnIndex("MenuShortName_1")));
				md.setMenuDesc(cursor.getString(cursor.getColumnIndex("MenuDesc_0")));
				md.setCurrencySymbol(globalVar.currency.getSymbol());
				md.setVatType(cursor.getInt(cursor.getColumnIndex("VatType")));
				md.setVatRate(globalVar.shopProper.getCompanyVat());
				md.setDiscountAllow(cursor.getInt(cursor.getColumnIndex("DiscountAllow")));
				md.setIsOutOfStock(cursor.getInt(cursor.getColumnIndex("IsOutOfStock")));
				md.setProductCode(cursor.getString(cursor.getColumnIndex("ProductCode")));
				md.setProductBarcode(cursor.getString(cursor.getColumnIndex("ProductBarCode")));
				md.setProductTypeID(cursor.getInt(cursor.getColumnIndex("ProductTypeID")));
				md.setSaleMode(cursor.getInt(cursor.getColumnIndex("SaleMode1")));
				md.setSaleMode2(cursor.getInt(cursor.getColumnIndex("SaleMode2")));
				
				mdl.add(md);
			}while(cursor.moveToNext());
		}

		cursor.close();
		dbHelper.closeDataBase();
		return mdl;
	}
}
