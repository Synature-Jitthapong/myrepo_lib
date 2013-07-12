package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.ShopData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ProgramFeature {
	private DataBaseHelper dbHelper;
	private String TABLE = "ProgramFeature";

	public ProgramFeature(Context c) {
		dbHelper = new DataBaseHelper(c);
	}

	public ShopData.ProgramFeature checkOpenFeature(int featureId){
		ShopData.ProgramFeature feature = 
				new ShopData.ProgramFeature();
		
		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery("SELECT * FROM " + TABLE + 
				" WHERE FeatureID=" + featureId, null);
		if(cursor.moveToFirst()){
			feature.setFeatureName(cursor.getString(cursor.getColumnIndex("FeatureName")));
			feature.setFeatureDesc(cursor.getString(cursor.getColumnIndex("FeatureDesc")));
			feature.setFeatureValue(cursor.getInt(cursor.getColumnIndex("FeatureValue")));
			feature.setFeatureText(cursor.getString(cursor.getColumnIndex("FeatureText")));
		}
		cursor.close();
		closeDatabase();
		return feature;
	}
	
	public List<ShopData.ProgramFeature> listProgramFeature() {
		List<ShopData.ProgramFeature> featureList = new ArrayList<ShopData.ProgramFeature>();
		String strSql = "SELECT * FROM " + TABLE;
		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if (cursor.moveToFirst()) {
			do {
				ShopData.ProgramFeature feature = new ShopData.ProgramFeature();
				feature.setFeatureID(cursor.getInt(cursor
						.getColumnIndex("FeatureID")));
				feature.setFeatureName(cursor.getString(cursor
						.getColumnIndex("FeatureName")));
				feature.setFeatureValue(cursor.getInt(cursor
						.getColumnIndex("FeatureValue")));
				feature.setFeatureText(cursor.getString(cursor
						.getColumnIndex("FeatureText")));
				feature.setFeatureDesc(cursor.getString(cursor
						.getColumnIndex("FeatureDesc")));
				featureList.add(feature);
			} while (cursor.moveToNext());
		}
		cursor.close();
		closeDatabase();
		return featureList;
	}

	public void insertProgramFeature(ShopData sp) {
		openDatabase();
		try {
			dbHelper.myDataBase.execSQL("BEGIN");
			dbHelper.myDataBase.execSQL("DELETE FROM " + TABLE);
			for (ShopData.ProgramFeature feature : sp.ProgramFeature) {
				ContentValues cv = new ContentValues();
				cv.put("FeatureID", feature.getFeatureID());
				cv.put("FeatureName", feature.getFeatureName());
				cv.put("FeatureValue", feature.getFeatureValue());
				cv.put("FeatureText", feature.getFeatureText());
				cv.put("FeatureDesc", feature.getFeatureDesc());

				dbHelper.myDataBase.insert(TABLE, null, cv);
			}
			dbHelper.myDataBase.execSQL("COMMIT");
		} catch (Exception e) {
			dbHelper.myDataBase.execSQL("ROLLBACK");
			e.printStackTrace();
		}
		closeDatabase();
	}

	private void openDatabase() {
		dbHelper.openDataBase();
	}

	private void closeDatabase() {
		dbHelper.closeDataBase();
	}
}
