package syn.pos.data.dao;

import syn.pos.data.model.SyncDataLogModel;
import syn.pos.data.model.WebServiceResult;
import syn.pos.mobile.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public class SyncDataLog{

	private DataBaseHelper dbHelper;
	private GlobalVar globalVar; 
	private Context context;
	
	public SyncDataLog(Context context) {
		dbHelper = new DataBaseHelper(context);
		globalVar = new GlobalVar(context, dbHelper);
		this.context = context;
	}

	
	public SyncDataLogModel getSyncTimeStamp(){
		SyncDataLogModel syncData = new SyncDataLogModel();
		
		String strSql = " SELECT * FROM SyncDataLog";
		
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			if(!cursor.isAfterLast()){
				syncData.setSyncTime(cursor.getString(cursor.getColumnIndex("SyncTime")));
				syncData.setStatus(cursor.getInt(cursor.getColumnIndex("Status")));
				syncData.setNote(cursor.getString(cursor.getColumnIndex("Note")));
				syncData.setSyncDate(cursor.getString(cursor.getColumnIndex("SyncDate")));
				syncData.setWebServiceVersion(cursor.getString(cursor.getColumnIndex("WebServiceVersion")));
				syncData.setWebServiceBuildVersion(cursor.getString(cursor.getColumnIndex("WebServiceBuildVersion")));
				syncData.setWebServiceBuildDateTime(cursor.getString(cursor.getColumnIndex("WebServiceBuildDateTime")));
			}
		}
		
		cursor.close();
		
		dbHelper.closeDataBase();
		return syncData;
	}
	
	public void updateWebServiceVersion(WebServiceResult.WebServiceVersion version){
			dbHelper.openDataBase();
			try {
				dbHelper.myDataBase.execSQL("UPDATE SyncDataLog SET WebServiceVersion='" + version.getWS_Version() + "', " + 
						" WebServiceBuildVersion='" + version.getSzBuildVersion() + "', " +
						" WebServiceBuildDateTime='" + version.getDtBuildVersion() + "'");
			} catch (SQLException e) {
				Log.appendLog(context, e.getMessage());
				e.printStackTrace();
			}
			dbHelper.closeDataBase();
	}
	
	public void setCompareDateFail(int status){
		dbHelper.openDataBase();
		dbHelper.myDataBase.execSQL("UPDATE SyncDataLog SET Status=" + status);
		dbHelper.closeDataBase();
	}
	
	public void stampTime(int status, String note, String saleDate){
		dbHelper.openDataBase();
		dbHelper.myDataBase.execSQL("DELETE FROM SyncDataLog");
		
		ContentValues cv = new ContentValues();
		cv.put("SyncTime", globalVar.dateTimeFormat.format(globalVar.date));
		cv.put("SyncDate", saleDate);
		cv.put("Status", status);
		cv.put("Note", note);
		
		try {
			dbHelper.myDataBase.insert("SyncDataLog", null, cv);
		} catch (Exception e) {
			Log.appendLog(context, e.getMessage());
			e.printStackTrace();
		}
		
		dbHelper.closeDataBase();
	}
}
