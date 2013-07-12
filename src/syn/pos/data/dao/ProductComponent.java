package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.MenuGroups;
import syn.pos.data.model.ProductGroups;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public class ProductComponent {
	private DataBaseHelper dbHelper;
	private String strSql = "";

	public ProductComponent(Context context) {
		dbHelper = new DataBaseHelper(context);
	}

	public ProductComponent(Context context, int productId) {
		dbHelper = new DataBaseHelper(context);

		strSql = " SELECT a.ChildProductID, a.FlexibleProductPrice, a.FlexibleIncludePrice, " +
				" a.SaleMode, a.ChildProductAmount, b.MenuName_0, b.MenuShortName_0 " +
				" FROM ProductComponent a " +
				" LEFT JOIN MenuItem b " + 
				" ON a.ChildProductID=b.ProductID " +
				" WHERE a.ProductID=" + (productId + 1000000);
	}

	public List<ProductGroups.ProductComponent> listProductComponent() {
		List<ProductGroups.ProductComponent> productComponentList = new ArrayList<ProductGroups.ProductComponent>();

		dbHelper.openDataBase();

		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String shortName = "";
			shortName = cursor.getString(cursor.getColumnIndex("MenuShortName_0"));

			ProductGroups.ProductComponent pc = new ProductGroups.ProductComponent();
			pc.setProductID(cursor.getInt(cursor.getColumnIndex("ChildProductID")));
			pc.setFlexibleIncludePrice(cursor.getInt(cursor.getColumnIndex("FlexibleIncludePrice")));
			pc.setFlexibleProductPrice(cursor.getDouble(cursor.getColumnIndex("FlexibleProductPrice")));
			pc.setSaleMode(cursor.getInt(cursor.getColumnIndex("SaleMode")));
			pc.setChildProductAmount(cursor.getDouble(cursor.getColumnIndex("ChildProductAmount")));
			pc.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName_0")));
			pc.setMenuShortName(shortName);
			productComponentList.add(pc);
			cursor.moveToNext();
		}
		cursor.close();

		dbHelper.closeDataBase();
		return productComponentList;
	}

	public void insertProductComponent(ProductGroups pg) {
		dbHelper.openDataBase();
		try {
			dbHelper.myDataBase.execSQL("BEGIN");
			dbHelper.myDataBase.execSQL("DELETE FROM ProductComponent");

			for (ProductGroups.ProductComponent pc : pg.getProductComponent()) {
				ContentValues cv = new ContentValues();

				cv.put("PGroupID", pc.getPGroupID());
				cv.put("ProductID", pc.getProductID());
				cv.put("SaleMode", pc.getSaleMode());
				cv.put("ChildProductID", pc.getChildProductID());
				cv.put("ChildProductAmount", pc.getChildProductAmount());
				cv.put("FlexibleProductPrice", pc.getFlexibleProductPrice());
				cv.put("FlexibleIncludePrice", pc.getFlexibleIncludePrice());
				
				dbHelper.myDataBase.insert("ProductComponent", null, cv);
			}
			dbHelper.myDataBase.execSQL("COMMIT");
		} catch (SQLException e) {
			dbHelper.myDataBase.execSQL("ROLLBACK");
			e.printStackTrace();
		}

		dbHelper.closeDataBase();
	}
}
