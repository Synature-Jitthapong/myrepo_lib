package syn.pos.data.dao;

import syn.pos.mobile.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class Register {
	private DataBaseHelper dbHelper;
	private Context context;
	
	private final String TB_REGISTER = "register";
	private final String COL_KEY_CODE = "key_code";
	private final String COL_DEVICE_CODE = "device_code";
	private final String COL_REGISTER_CODE = "register_code";
	
	private String keyCode;
	private String deviceCode;
	private String registerCode;
	
	public Register(Context c){
		dbHelper = new DataBaseHelper(c);
		context = c;
	}
	
	public void getRegisterInfo(){
		openDb();
		Cursor cursor = dbHelper.myDataBase.rawQuery("SELECT * FROM " + 
				TB_REGISTER, null);
		if(cursor.moveToFirst()){
			this.setKeyCode(cursor.getString(cursor.getColumnIndex(COL_KEY_CODE)));
			this.setDeviceCode(cursor.getString(cursor.getColumnIndex(COL_DEVICE_CODE)));
			this.setRegisterCode(cursor.getString(cursor.getColumnIndex(COL_REGISTER_CODE)));
		}
		cursor.close();
		closeDb();
	}
	
	public void insertRegisterInfo(String keyCode, String deviceCode, String registerCode){
		openDb();
		dbHelper.myDataBase.execSQL("DELETE FROM " + TB_REGISTER);
		
		ContentValues cv = new ContentValues();
		cv.put(COL_KEY_CODE, keyCode);
		cv.put(COL_DEVICE_CODE, deviceCode);
		cv.put(COL_REGISTER_CODE, registerCode);
		
		try {
			dbHelper.myDataBase.insert(TB_REGISTER, null, cv);
		} catch (Exception e) {
			Log.appendLog(context, e.getMessage());
			e.printStackTrace();
		}
		closeDb();
	}
	
	private void openDb(){
		dbHelper.openDataBase();
	}
	
	private void closeDb(){
		dbHelper.closeDataBase();
	}
	
	public String getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getRegisterCode() {
		return registerCode;
	}
	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}
}
