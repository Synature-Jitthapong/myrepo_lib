package syn.pos.data.dao;

import syn.pos.data.model.AppConfigModel;
import syn.pos.data.model.WebServiceResult;
import syn.pos.mobile.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class AppConfig {

	private DataBaseHelper dbHelper;
	private Context context;
	private final String APP_CONFIG_TABLE = "AppConfig";
	private final String COL_SERVER_IP = "ServerIP";
	private final String COL_WEBSERVICE_URL = "WebserviceUrl";
	private final String COL_DIS_MENU_IMG = "DisplayMenuImage";
	
	public AppConfig(Context context) {
		dbHelper = new DataBaseHelper(context);
		this.context = context;
	}
	
	public void addAppConfig(String serverIp, String serviceUrl, 
			int displayMenuImg){
		dbHelper.openDataBase();
		ContentValues cv = new ContentValues();
		cv.put(COL_SERVER_IP, serverIp);
		cv.put(COL_WEBSERVICE_URL, serviceUrl);
		cv.put(COL_DIS_MENU_IMG, displayMenuImg);
		
		dbHelper.myDataBase.execSQL("DELETE FROM " + APP_CONFIG_TABLE);
		try {
			dbHelper.myDataBase.insert(APP_CONFIG_TABLE, null, cv);
		} catch (Exception e) {
			Log.appendLog(context, e.getMessage());
			e.printStackTrace();
		}
		dbHelper.closeDataBase();
	}
	
	public void addAppConfig(AppConfigModel appModel){
		dbHelper.openDataBase();
		ContentValues cv = new ContentValues();
		cv.put("ServerIP", appModel.getServerIP());
		cv.put("WebserviceUrl", appModel.getWebServiceUrl());
		dbHelper.myDataBase.execSQL("DELETE FROM " + APP_CONFIG_TABLE);
		dbHelper.myDataBase.insert(APP_CONFIG_TABLE, null, cv);
		dbHelper.closeDataBase();
	}
	
	public AppConfigModel getConfigs(){
		dbHelper.openDataBase();
		
		AppConfigModel config = new AppConfigModel();
		Cursor cursor = dbHelper.myDataBase.rawQuery(
				"SELECT * FROM " + APP_CONFIG_TABLE, null);
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			config.setServerIP(cursor.getString(
					cursor.getColumnIndex(COL_SERVER_IP)));
			config.setWebServiceUrl(cursor.getString(
					cursor.getColumnIndex(COL_WEBSERVICE_URL)));
			config.setDisplayImageMenu(cursor.getInt(
					cursor.getColumnIndex(COL_DIS_MENU_IMG)));
			cursor.moveToNext();
		}
		cursor.close();
		dbHelper.closeDataBase();
		return config;
	}
	
	public AppConfigModel getConfig(){
		dbHelper.openDataBase();
		
		AppConfigModel config = new AppConfigModel();
		Cursor cursor = dbHelper.myDataBase.rawQuery("SELECT * FROM " + APP_CONFIG_TABLE, null);
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			config.setServerIP(cursor.getString(cursor.getColumnIndex(COL_SERVER_IP)));
			config.setWebServiceUrl(cursor.getString(cursor.getColumnIndex(COL_WEBSERVICE_URL)));
			
			cursor.moveToNext();
		}
		cursor.close();
		dbHelper.closeDataBase();
		return config;
	}
}
