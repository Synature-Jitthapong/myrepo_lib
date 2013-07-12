package syn.pos.data.dao;

import java.util.ArrayList;

import syn.pos.data.model.ShopData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ComputerProperty{
	private DataBaseHelper dbHelper;
	public ComputerProperty(Context context) {
		dbHelper = new DataBaseHelper(context);
	}

	public ShopData.ComputerProperty getComputerData(){
		dbHelper.openDataBase();
		
		String strSql = "SELECT * FROM ComputerProperty ";
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);

		ShopData.ComputerProperty cp = new ShopData.ComputerProperty();
		
		if (cursor.moveToFirst()) {
			cp.setComputerID(cursor.getInt(0));
			cp.setComputerName(cursor.getString(1));
			cp.setDeviceCode(cursor.getString(2));
			cp.setRegistrationNumber(cursor.getString(3));
			cp.setIsMainComputer(cursor.getInt(4));
			cp.setIsQueueOrder(cursor.getInt(5));
			
			cursor.moveToNext();
		}
		cursor.close();
		
		dbHelper.closeDataBase();
		return cp;
	}
	
	public ShopData getComputerProperty() {
		ShopData sd = new ShopData();
		String strSql = "SELECT * FROM ComputerProperty ";
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		cursor.moveToFirst();
		sd.ComputerProperty = new ArrayList<ShopData.ComputerProperty>();
		while (!cursor.isAfterLast()) {
			ShopData.ComputerProperty cp = new ShopData.ComputerProperty();
			cp.setComputerID(cursor.getInt(0));
			cp.setComputerName(cursor.getString(1));
			cp.setDeviceCode(cursor.getString(2));
			cp.setRegistrationNumber(cursor.getString(3));
			cp.setIsMainComputer(cursor.getInt(4));
			//cp.setIsQueueOrder(cursor.getInt(5));
			sd.ComputerProperty.add(cp);
			cursor.moveToNext();
		}
		cursor.close();
		dbHelper.closeDataBase();
		return sd;
	}

	public void insertComputerProperty(ShopData shopData) {
		dbHelper.openDataBase();
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM ComputerProperty");
			for (ShopData.ComputerProperty c : shopData.getComputerProperty()) {
				ContentValues cv = new ContentValues();
				cv.put("ComputerID", c.getComputerID());
				cv.put("ComputerName", c.getComputerName());
				cv.put("DeviceCode", c.getDeviceCode());
				cv.put("RegistrationNumber", c.getRegistrationNumber());
				cv.put("IsMainComputer", c.getIsMainComputer());
				cv.put("IsQueueOrder", c.getIsQueueOrder());

				dbHelper.myDataBase.insert("ComputerProperty", null, cv);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		dbHelper.closeDataBase();
	}

}
