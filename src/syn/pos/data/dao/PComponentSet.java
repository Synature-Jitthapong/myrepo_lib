package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.MenuDataItem;
import syn.pos.data.model.ProductGroups;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class PComponentSet {
	private DataBaseHelper dbHelper;

	public PComponentSet(Context c) {
		dbHelper = new DataBaseHelper(c);
	}

	public List<ProductGroups.PComponentSet> listPComponentSetOfProduct(int productId) {
		List<ProductGroups.PComponentSet> pCompSetLst = new ArrayList<ProductGroups.PComponentSet>();
		dbHelper.openDataBase();

		String strSql = "SELECT a.ChildProductID, a.ChildProductAmount, a.FlexibleProductPrice, "
				+ " a.FlexibleIncludePrice, a.PGroupID, "
				+ " b.ProductPricePerUnit, b.IsOutOfStock, c.MenuName_0, " +
				" c.MenuName_1, c.MenuName_2, c.MenuShortName_0, c.MenuShortName_1, c.MenuDesc_0, "
				+ " c.MenuImageLink FROM PComponentSet a "
				+ " INNER JOIN Products b "
				+ " ON a.ChildProductID=b.ProductID "
				+ " LEFT JOIN MenuItem c "
				+ " ON a.ChildProductID = c.ProductID "
				+ " WHERE a.ProductID="
				+ productId;
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			do {
				ProductGroups.PComponentSet pcs = new ProductGroups.PComponentSet();
				pcs.setProductID(cursor.getInt(cursor
						.getColumnIndex("ChildProductID")));
				pcs.setChildProductAmount(cursor.getDouble(cursor
						.getColumnIndex("ChildProductAmount")));
				pcs.setFlexibleProductPrice(cursor.getDouble(cursor
						.getColumnIndex("FlexibleProductPrice")));
				pcs.setFlexibleIncludePrice(cursor.getInt(cursor
						.getColumnIndex("FlexibleIncludePrice")));
				pcs.setPGroupID(cursor.getInt(cursor.getColumnIndex("PGroupID")));
				pcs.setPricePerUnit(cursor.getDouble(cursor
						.getColumnIndex("ProductPricePerUnit")));
				pcs.setMenuName(cursor.getString(cursor
						.getColumnIndex("MenuName_0")));
				pcs.setMenuName1(cursor.getString(cursor
						.getColumnIndex("MenuName_1")));
				pcs.setMenuName2(cursor.getString(cursor
						.getColumnIndex("MenuName_2")));
				pcs.setMenuShortName(cursor.getString(cursor
						.getColumnIndex("MenuShortName_0")));
				pcs.setMenuShortName1(cursor.getString(cursor
						.getColumnIndex("MenuShortName_1")));
				pcs.setMenuDesc(cursor.getString(cursor.getColumnIndex("MenuDesc_0")));
				pcs.setMenuImageLink(cursor.getString(cursor
						.getColumnIndex("MenuImageLink")));
				pcs.setIsOutOfStock(cursor.getInt(cursor
						.getColumnIndex("IsOutOfStock")));
				pCompSetLst.add(pcs);
			} while (cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		return pCompSetLst;
	}
	
	public List<ProductGroups.PComponentSet> listPComponentSize(int productId) {
		List<ProductGroups.PComponentSet> pCompSetLst = new ArrayList<ProductGroups.PComponentSet>();
		dbHelper.openDataBase();

		String strSql = "SELECT a.ChildProductID, a.ChildProductAmount, a.FlexibleProductPrice, "
				+ " a.FlexibleIncludePrice, a.PGroupID, "
				+ " b.ProductPricePerUnit, b.IsOutOfStock, c.MenuName_0, " +
				" c.MenuName_1, c.MenuName_2, c.MenuShortName_0, c.MenuShortName_1, c.MenuDesc_0, "
				+ " c.MenuImageLink FROM PComponentSet a "
				+ " INNER JOIN Products b "
				+ " ON a.ChildProductID=b.ProductID "
				+ " LEFT JOIN MenuItem c "
				+ " ON a.ChildProductID = c.ProductID "
				+ " WHERE a.ProductID="
				+ productId;
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			do {
				ProductGroups.PComponentSet pcs = new ProductGroups.PComponentSet();
				pcs.setProductID(cursor.getInt(cursor
						.getColumnIndex("ChildProductID")));
				pcs.setChildProductAmount(cursor.getDouble(cursor
						.getColumnIndex("ChildProductAmount")));
				pcs.setFlexibleProductPrice(cursor.getDouble(cursor
						.getColumnIndex("FlexibleProductPrice")));
				pcs.setFlexibleIncludePrice(cursor.getInt(cursor
						.getColumnIndex("FlexibleIncludePrice")));
				pcs.setPGroupID(cursor.getInt(cursor.getColumnIndex("PGroupID")));
				pcs.setPricePerUnit(cursor.getDouble(cursor
						.getColumnIndex("ProductPricePerUnit")));
				pcs.setMenuName(cursor.getString(cursor
						.getColumnIndex("MenuName_0")));
				pcs.setMenuName1(cursor.getString(cursor
						.getColumnIndex("MenuName_1")));
				pcs.setMenuName2(cursor.getString(cursor
						.getColumnIndex("MenuName_2")));
				pcs.setMenuShortName(cursor.getString(cursor
						.getColumnIndex("MenuShortName_0")));
				pcs.setMenuShortName1(cursor.getString(cursor
						.getColumnIndex("MenuShortName_1")));
				pcs.setMenuDesc(cursor.getString(cursor.getColumnIndex("MenuDesc_0")));
				pcs.setMenuImageLink(cursor.getString(cursor
						.getColumnIndex("MenuImageLink")));
				pcs.setIsOutOfStock(cursor.getInt(cursor
						.getColumnIndex("IsOutOfStock")));
				pCompSetLst.add(pcs);
			} while (cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		return pCompSetLst;
	}
	
	public List<ProductGroups.PComponentSet> getPComponentSet(int pCompGroupId) {
		List<ProductGroups.PComponentSet> pCompSetLst = new ArrayList<ProductGroups.PComponentSet>();
		dbHelper.openDataBase();

		String strSql = "SELECT a.ChildProductID, a.ChildProductAmount, a.FlexibleProductPrice, "
				+ " a.FlexibleIncludePrice, a.PGroupID, "
				+ " b.ProductPricePerUnit, b.IsOutOfStock, c.MenuName_0, " +
				" c.MenuName_1, c.MenuName_2, c.MenuShortName_0, c.MenuShortName_1, c.MenuDesc_0, "
				+ " c.MenuImageLink FROM PComponentSet a "
				+ " INNER JOIN Products b "
				+ " ON a.ChildProductID=b.ProductID "
				+ " LEFT JOIN MenuItem c "
				+ " ON a.ChildProductID = c.ProductID "
				+ " WHERE a.PGroupID="
				+ pCompGroupId;
		// (a.ChildProductID - 1000000)
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			do {
				ProductGroups.PComponentSet pcs = new ProductGroups.PComponentSet();
				pcs.setProductID(cursor.getInt(cursor
						.getColumnIndex("ChildProductID")));
				pcs.setChildProductAmount(cursor.getDouble(cursor
						.getColumnIndex("ChildProductAmount")));
				pcs.setFlexibleProductPrice(cursor.getDouble(cursor
						.getColumnIndex("FlexibleProductPrice")));
				pcs.setFlexibleIncludePrice(cursor.getInt(cursor
						.getColumnIndex("FlexibleIncludePrice")));
				pcs.setPGroupID(cursor.getInt(cursor.getColumnIndex("PGroupID")));
				pcs.setPricePerUnit(cursor.getDouble(cursor
						.getColumnIndex("ProductPricePerUnit")));
				pcs.setMenuName(cursor.getString(cursor
						.getColumnIndex("MenuName_0")));
				pcs.setMenuName1(cursor.getString(cursor
						.getColumnIndex("MenuName_1")));
				pcs.setMenuName2(cursor.getString(cursor
						.getColumnIndex("MenuName_2")));
				pcs.setMenuShortName(cursor.getString(cursor
						.getColumnIndex("MenuShortName_0")));
				pcs.setMenuShortName1(cursor.getString(cursor
						.getColumnIndex("MenuShortName_1")));
				pcs.setMenuDesc(cursor.getString(cursor.getColumnIndex("MenuDesc_0")));
				pcs.setMenuImageLink(cursor.getString(cursor
						.getColumnIndex("MenuImageLink")));
				pcs.setIsOutOfStock(cursor.getInt(cursor
						.getColumnIndex("IsOutOfStock")));
				pCompSetLst.add(pcs);
			} while (cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		return pCompSetLst;
	}

	public void insertPcomponentSet(ProductGroups pg) {
		dbHelper.openDataBase();
		try {
			dbHelper.myDataBase.execSQL("BEGIN");
			dbHelper.myDataBase.execSQL("DELETE FROM PComponentSet");
			for (ProductGroups.PComponentSet pcs : pg.getPComponentSet()) {
				ContentValues cv = new ContentValues();
				cv.put("PGroupID", pcs.getPGroupID());
				cv.put("ProductID", pcs.getProductID());
				cv.put("SaleMode", pcs.getSaleMode());
				cv.put("ChildProductID", pcs.getChildProductID());
				cv.put("ChildProductAmount", pcs.getChildProductAmount());
				cv.put("FlexibleProductPrice", pcs.getFlexibleProductPrice());
				cv.put("FlexibleIncludePrice", pcs.getFlexibleIncludePrice());

				dbHelper.myDataBase.insert("PComponentSet", null, cv);
			}
			dbHelper.myDataBase.execSQL("COMMIT");
		} catch (Exception e) {
			dbHelper.myDataBase.execSQL("ROLLBACK");
			Log.d("Insert pcomponentset", e.getMessage());
			e.printStackTrace();
		}
		dbHelper.closeDataBase();
	}
}
