package syn.pos.data.dao;

import java.security.MessageDigest;

import syn.pos.data.model.ShopData.Staff;
import android.content.Context;
import android.database.Cursor;

public class Staffs{

	private DataBaseHelper dbHelper;
	private String strSql = "SELECT * FROM Staffs";
	private String staffPassword;
	
	public Staffs(Context context, String staffCode, String staffPassword) {
		dbHelper = new DataBaseHelper(context);
		this.staffPassword = staffPassword;
		
		String passDecrypt = decryptSHA1();
		strSql += " WHERE StaffCode='" + staffCode + "' AND StaffPassword='" + passDecrypt + "'";
	}

	public String decryptSHA1() {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(this.staffPassword.getBytes("UTF-8"));
			byte[] digestBytes = messageDigest.digest();

			String hex = null;
			for (int i = 0; i < digestBytes.length; i++) {
				hex = Integer.toHexString(0xFF & digestBytes[i]);
				if (hex.length() < 2)
					sb.append("0");
				sb.append(hex);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String pass = sb.toString();
		return pass.toUpperCase();
	}
	
	public Staff checkLogin() {
		Staff s = new Staff(); 
		dbHelper.openDataBase();

		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			s.setStaffID(cursor.getInt(0));
			s.setStaffCode(cursor.getString(1));
			s.setStaffName(cursor.getString(2));
			cursor.moveToNext();
		}

		cursor.close();
		
		dbHelper.closeDataBase();
		return s;
	}
}
