package syn.pos.data.dao;

import syn.pos.data.model.ShopData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public class GlobalProperty {
	private DataBaseHelper dbHelper;

	public GlobalProperty(Context context, DataBaseHelper db) {
		if (db != null)
			dbHelper = db;
		else
			dbHelper = new DataBaseHelper(context);
	}

	public ShopData.GlobalProperty getGlobalProperty() {
		dbHelper.openDataBase();

		String strSql = " SELECT * FROM GlobalProperty ";

		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);

		ShopData.GlobalProperty gb = new ShopData.GlobalProperty();
		if (cursor.moveToFirst()) {
			do {
				gb.setCurrencySymbol(cursor.getString(cursor
						.getColumnIndex("CurrencySymbol")));
				gb.setCurrencyCode(cursor.getString(cursor
						.getColumnIndex("CurrencyCode")));
				gb.setCurrencyName(cursor.getString(cursor
						.getColumnIndex("CurrencyName")));
				gb.setCurrencyFormat(cursor.getString(cursor
						.getColumnIndex("CurrencyFormat")));
				gb.setDateFormat(cursor.getString(cursor
						.getColumnIndex("DateFormat")));
				gb.setTimeFormat(cursor.getString(cursor
						.getColumnIndex("TimeFormat")));
				gb.setQtyFormat(cursor.getString(cursor
						.getColumnIndex("QtyFormat")));
				gb.setPrefixTextTW(cursor.getString(cursor
						.getColumnIndex("PrefixTextTW")));
				gb.setPositionPrefix(cursor.getInt(cursor
						.getColumnIndex("PositionPrefix")));
			} while (cursor.moveToNext());
		}
		cursor.close();

		dbHelper.closeDataBase();
		return gb;
	}

	public void insertGlobalProperty(ShopData shopData) {
		dbHelper.openDataBase();
		try {
			dbHelper.myDataBase.execSQL("BEGIN");
			dbHelper.myDataBase.execSQL("DELETE FROM GlobalProperty");

			for (ShopData.GlobalProperty gb : shopData.GlobalProperty) {
				ContentValues cv = new ContentValues();
				cv.put("CurrencySymbol", gb.getCurrencySymbol());
				cv.put("CurrencyCode", gb.getCurrencyCode());
				cv.put("CurrencyName", gb.getCurrencyName());
				cv.put("CurrencyFormat", gb.getCurrencyFormat());
				cv.put("DateFormat", gb.getDateFormat());
				cv.put("TimeFormat", gb.getTimeFormat());
				cv.put("QtyFormat", gb.getQtyFormat());
				cv.put("PrefixTextTW", gb.getPrefixTextTW());
				cv.put("PositionPrefix", gb.getPositionPrefix());
				dbHelper.myDataBase.insert("GlobalProperty", null, cv);
			}

			dbHelper.myDataBase.execSQL("COMMIT");
		} catch (SQLException e) {
			dbHelper.myDataBase.execSQL("ROLLBACK");
			e.printStackTrace();
		}
		dbHelper.closeDataBase();
	}

}
