package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.ProductGroups;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class PComponentGroup {
	private DataBaseHelper dbHelper;

	public PComponentGroup(Context c) {
		dbHelper = new DataBaseHelper(c);
	}

	public List<ProductGroups.PComponentGroup> getPcomponentGroup(int productId) {
		dbHelper.openDataBase();

		List<ProductGroups.PComponentGroup> pcgLst = new ArrayList<ProductGroups.PComponentGroup>();

		String strSql = "SELECT a.productid, b.PGroupID, "
				+ " b.SetGroupNo, b.SetGroupName, b.RequireAmount, c.MenuImageLink "
				+ " FROM " + " Products a " + " INNER JOIN "
				+ " PComponentGroup b " + " on a.ProductID=b.ProductID "
				+ " LEFT JOIN MenuItem c " + " ON a.ProductID=c.ProductID "
				+ " WHERE a.productid=" + productId;

		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);

		if (cursor.moveToFirst()) {
			do {
				ProductGroups.PComponentGroup pcg = new ProductGroups.PComponentGroup();

				pcg.setProductID(cursor.getInt(0));
				pcg.setPGroupID(cursor.getInt(1));
				pcg.setSetGroupNo(cursor.getInt(2));
				pcg.setSetGroupName(cursor.getString(3));
				pcg.setRequireAmount(cursor.getDouble(4));
				pcg.setMenuImageLink(cursor.getString(5));

				pcgLst.add(pcg);
			} while (cursor.moveToNext());
		}
		cursor.close();

		dbHelper.closeDataBase();
		return pcgLst;
	}

	public void insertPcomponentGroup(ProductGroups pg) {
		dbHelper.openDataBase();
		try {
			dbHelper.myDataBase.execSQL("BEGIN");
			dbHelper.myDataBase.execSQL("DELETE FROM PComponentGroup");
			for (ProductGroups.PComponentGroup pcg : pg.getPComponentGroup()) {
				ContentValues cv = new ContentValues();
				cv.put("PGroupID", pcg.getPGroupID());
				cv.put("ProductID", pcg.getProductID());
				cv.put("SaleMode", pcg.getSaleMode());
				cv.put("SetGroupNo", pcg.getSetGroupNo());
				cv.put("SetGroupName", pcg.getSetGroupName());
				cv.put("RequireAmount", pcg.getRequireAmount());

				dbHelper.myDataBase.insert("PComponentGroup", null, cv);
			}
			dbHelper.myDataBase.execSQL("COMMIT");
		} catch (Exception e) {
			dbHelper.myDataBase.execSQL("ROLLBACK");
			Log.d("Insert pcomponentgroup", e.getMessage());
			e.printStackTrace();
		}

		dbHelper.closeDataBase();
	}
}
