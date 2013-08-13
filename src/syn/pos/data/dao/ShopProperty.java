package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.ShopData;
import syn.pos.data.model.ShopData.SeatNo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class ShopProperty {
	private DataBaseHelper dbHelper;
	private Context c;

	public ShopProperty(Context context, DataBaseHelper db) {
		if (db != null)
			dbHelper = db;
		else
			dbHelper = new DataBaseHelper(context);
		c = context;
	}
	
	public ShopData.Language getLanguage(){
		ShopData.Language lang = new ShopData.Language();
		lang.setLangCode("en");
		dbHelper.openDataBase();
		String sqlQueury = "SELECT * FROM Language WHERE IsDefault=1";
		Cursor cursor = dbHelper.myDataBase.rawQuery(sqlQueury, null);
		if(cursor.moveToFirst()){
			lang.setLangID(cursor.getInt(cursor.getColumnIndex("LangID")));
			lang.setLangCode(cursor.getString(cursor.getColumnIndex("LangCode")));
			lang.setLangName(cursor.getString(cursor.getColumnIndex("LangName")));
			lang.setIsDefault(cursor.getInt(cursor.getColumnIndex("IsDefault")));
		}
		cursor.close();
		dbHelper.closeDataBase();
		return lang;
	}
	
	public void setSelectedLanguage(int langId){
		dbHelper.openDataBase();
		
		String sqlClear = "UPDATE Language SET IsDefault=0";
		String sqlUpdate = "UPDATE Language SET IsDefault=1 WHERE LangID=" + langId;
		
		dbHelper.myDataBase.execSQL(sqlClear);
		dbHelper.myDataBase.execSQL(sqlUpdate);
		
		dbHelper.closeDataBase();
	}
	
	public List<ShopData.Language> listLanguage() {
		List<ShopData.Language> langLst = new ArrayList<ShopData.Language>();
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery("SELECT * FROM Language",
				null);
		if (cursor.moveToFirst()) {
			do {
				ShopData.Language lang = new ShopData.Language();
				lang.setLangID(cursor.getInt(cursor.getColumnIndex("LangID")));
				lang.setLangCode(cursor.getString(cursor
						.getColumnIndex("LangCode")));
				lang.setLangName(cursor.getString(cursor
						.getColumnIndex("LangName")));
				lang.setIsDefault(cursor.getInt(cursor.getColumnIndex("IsDefault")));
				langLst.add(lang);
			} while (cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		return langLst;
	}

	public ShopData getShopProperty() {
		dbHelper.openDataBase();

		ShopData sd = new ShopData();
		String strSql = "SELECT * FROM ShopProperty";

		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);

		sd.ShopProperty = new ArrayList<ShopData.ShopProperty>();
		if (cursor.moveToFirst()) {

			do {
				ShopData.ShopProperty sp = new ShopData.ShopProperty();
				sp.setShopID(cursor.getInt(0));
				sp.setShopCode(cursor.getString(1));
				sp.setShopName(cursor.getString(2));
				sp.setShopType(cursor.getInt(3));
				sp.setFastFoodType(cursor.getInt(4));
				sp.setTableType(cursor.getInt(5));
				sp.setVatType(cursor.getInt(6));
				sp.setServiceCharge(cursor.getInt(7));
				sp.setServiceChargeType(cursor.getInt(8));
				sp.setOpenHour(cursor.getString(9));
				sp.setCloseHour(cursor.getString(10));
				sp.setCalculateServiceChargeWhenFreeBill(cursor.getInt(11));
				sp.setCommentType(cursor.getInt(12));
				sp.setCompanyName(cursor.getString(13));
				sp.setCompanyAddress1(cursor.getString(14));
				sp.setCompanyAddress2(cursor.getString(15));
				sp.setCompanyCity(cursor.getString(16));
				sp.setCompanyProvince(cursor.getString(17));
				sp.setCompanyZipCode(cursor.getString(18));
				sp.setCompanyTelephone(cursor.getString(19));
				sp.setCompanyFax(cursor.getString(20));
				sp.setCompanyTaxID(cursor.getString(21));
				sp.setCompanyRegisterID(cursor.getString(22));
				sp.setCompanyVat(cursor.getDouble(23));
				try {
					sp.setFeatureQueue(cursor.getInt(24));
				} catch (Exception ex) {
					Log.d("feature queue column ", ex.getMessage());
				}

				sd.ShopProperty.add(sp);
			} while (cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		return sd;
	}

	public ShopData.SeatNo getSeatNo(int seatId){
		ShopData.SeatNo seat = new ShopData.SeatNo();
		
		dbHelper.openDataBase();
		
		String strSql = "SELECT * FROM SeatNo " +
				" WHERE SeatID=" + seatId;
		
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			seat.setSeatID(cursor.getInt(cursor.getColumnIndex("SeatID")));
			seat.setSeatName(cursor.getString(cursor.getColumnIndex("SeatName")));
			cursor.moveToNext();
		}
		cursor.close();
		
		dbHelper.closeDataBase();
		
		return seat;
	}
	
	public List<ShopData.SeatNo> getSeatNo(){
		List<ShopData.SeatNo> seatLst = 
				new ArrayList<ShopData.SeatNo>();
		
		dbHelper.openDataBase();
		
		String strSql = "SELECT * FROM SeatNo";
		
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			do{
				ShopData.SeatNo seat = new ShopData.SeatNo();
				seat.setSeatID(cursor.getInt(cursor.getColumnIndex("SeatID")));
				seat.setSeatName(cursor.getString(cursor.getColumnIndex("SeatName")));
				seatLst.add(seat);
			}while(cursor.moveToNext());
		}
		cursor.close();
		
		dbHelper.closeDataBase();
		return seatLst;
	}
	
	public ShopData.ShopProperty getShopProper() {
		dbHelper.openDataBase();

		String strSql = "SELECT * FROM ShopProperty";
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);

		ShopData.ShopProperty sp = new ShopData.ShopProperty();
		if (cursor.moveToFirst()) {
			do {
				sp.setShopID(cursor.getInt(0));
				sp.setShopCode(cursor.getString(1));
				sp.setShopName(cursor.getString(2));
				sp.setShopType(cursor.getInt(3));
				sp.setFastFoodType(cursor.getInt(4));
				sp.setTableType(cursor.getInt(5));
				sp.setVatType(cursor.getInt(6));
				sp.setServiceCharge(cursor.getInt(7));
				sp.setServiceChargeType(cursor.getInt(8));
				sp.setOpenHour(cursor.getString(9));
				sp.setCloseHour(cursor.getString(10));
				sp.setCalculateServiceChargeWhenFreeBill(cursor.getInt(11));
				sp.setCommentType(cursor.getInt(12));
				sp.setCompanyName(cursor.getString(13));
				sp.setCompanyAddress1(cursor.getString(14));
				sp.setCompanyAddress2(cursor.getString(15));
				sp.setCompanyCity(cursor.getString(16));
				sp.setCompanyProvince(cursor.getString(17));
				sp.setCompanyZipCode(cursor.getString(18));
				sp.setCompanyTelephone(cursor.getString(19));
				sp.setCompanyFax(cursor.getString(20));
				sp.setCompanyTaxID(cursor.getString(21));
				sp.setCompanyRegisterID(cursor.getString(22));
				sp.setCompanyVat(cursor.getDouble(23));
			} while (cursor.moveToNext());
		}
		cursor.close();

		dbHelper.closeDataBase();
		return sp;
	}

	public void insertShopProperty(ShopData shopData) {
		dbHelper.openDataBase();

		try {
			dbHelper.myDataBase.execSQL("DELETE FROM ShopProperty");

			for (ShopData.ShopProperty s : shopData.getShopProperty()) {
				ContentValues cv = new ContentValues();
				cv.put("ShopID", s.getShopID());
				cv.put("ShopCode", s.getShopCode());
				cv.put("ShopName", s.getShopName());
				cv.put("ShopType", s.getShopType());
				cv.put("FastFoodType", s.getFastFoodType());
				cv.put("TableType", s.getTableType());
				cv.put("VatType", s.getVatType());
				cv.put("ServiceCharge", s.getServiceCharge());
				cv.put("ServiceChargeType", s.getServiceChargeType());
				cv.put("OpenHour", s.getOpenHour());
				cv.put("CloseHour", s.getCloseHour());
				cv.put("CalculateServiceChargeWhenFreeBill",
						s.getCalculateServiceChargeWhenFreeBill());
				cv.put("CommentType", s.getCommentType());
				cv.put("CompanyName", s.getCompanyName());
				cv.put("CompanyAddress1", s.getCompanyAddress1());
				cv.put("CompanyAddress2", s.getCompanyAddress2());
				cv.put("CompanyCity", s.getCompanyCity());
				cv.put("CompanyProvince", s.getCompanyProvince());
				cv.put("CompanyZipCode", s.getCompanyZipCode());
				cv.put("CompanyTelephone", s.getCompanyTelephone());
				cv.put("CompanyFax", s.getCompanyFax());
				cv.put("CompanyTaxID", s.getCompanyTaxID());
				cv.put("CompanyRegisterID", s.getCompanyRegisterID());
				cv.put("CompanyVat", s.getCompanyVat());

				try {
					dbHelper.myDataBase.insert("ShopProperty", null, cv);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbHelper.closeDataBase();
	}

	public void insertStaffData(ShopData shopData) {
		dbHelper.openDataBase();

		try {
			dbHelper.myDataBase.execSQL("DELETE FROM Staffs");

			for (ShopData.Staff s : shopData.getStaffs()) {
				ContentValues cv = new ContentValues();
				cv.put("StaffID", s.getStaffID());
				cv.put("StaffCode", s.getStaffCode());
				cv.put("StaffName", s.getStaffName());
				cv.put("StaffPassword", s.getStaffPassword());

				dbHelper.myDataBase.insert("Staffs", null, cv);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbHelper.closeDataBase();
	}

	public boolean chkAccessPocketPermission(){
		boolean isFound = false;
		dbHelper.openDataBase();
		
		String strSql = "SELECT * FROM StaffPermission " +
				" WHERE PermissionItemID = 155";
		
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		
		if(cursor.moveToFirst()){
			isFound = true;
		}
		cursor.close();
		
		dbHelper.closeDataBase();
		
		return isFound;
	}
	
	public List<ShopData.StaffPermission> getPermission(){
		List<ShopData.StaffPermission> permissionLst = 
				new ArrayList<ShopData.StaffPermission>();
		
		dbHelper.openDataBase();
		
		String strSql = "SELECT * FROM StaffPermission";
		
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			do{
				ShopData.StaffPermission permission = 
						new ShopData.StaffPermission();
				permission.setPermissionItemID(cursor.getInt(cursor.getColumnIndex("PermissionItemID")));
				permissionLst.add(permission);
			}while(cursor.moveToNext());
		}
		cursor.close();
		
		dbHelper.closeDataBase();
		
		return permissionLst;
	}
	
	public void insertStaffPermissionData(List<ShopData.StaffPermission> permissionLst) {
		dbHelper.openDataBase();
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM StaffPermission");

			for (ShopData.StaffPermission s : permissionLst) {
				ContentValues cv = new ContentValues();
				cv.put("StaffRoleID", s.getStaffRoleID());
				cv.put("PermissionItemID", s.getPermissionItemID());

				dbHelper.myDataBase.insert("StaffPermission", null, cv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbHelper.closeDataBase();
	}
	
	public void insertStaffPermissionData(ShopData shopData) {
		dbHelper.openDataBase();
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM StaffPermission");

			for (ShopData.StaffPermission s : shopData.getStaffPermission()) {
				ContentValues cv = new ContentValues();
				cv.put("StaffRoleID", s.getStaffRoleID());
				cv.put("PermissionItemID", s.getPermissionItemID());

				dbHelper.myDataBase.insert("StaffPermission", null, cv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbHelper.closeDataBase();
	}

	public void insertLanguage(ShopData shopData) {
		dbHelper.openDataBase();

		try {
			dbHelper.myDataBase.execSQL("DELETE FROM Language");

			for (ShopData.Language lang : shopData.Language) {
				ContentValues cv = new ContentValues();
				cv.put("LangID", lang.getLangID());
				cv.put("LangName", lang.getLangName());
				cv.put("LangCode", lang.getLangCode());

				dbHelper.myDataBase.insert("Language", null, cv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		dbHelper.closeDataBase();
	}

	public void insertSeatNo(ShopData shopData){
		dbHelper.openDataBase();
		
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM SeatNo");
			
			for (ShopData.SeatNo seat : shopData.SeatNo){
				ContentValues cv = new ContentValues();
				cv.put("SeatID", seat.getSeatID());
				cv.put("SeatName", seat.getSeatName());
				
				dbHelper.myDataBase.insert("SeatNo", null, cv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbHelper.closeDataBase();
	}
}
