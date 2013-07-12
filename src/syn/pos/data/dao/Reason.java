package syn.pos.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.ReasonGroups;
import syn.pos.data.model.ReasonGroups.ReasonDetail;

public class Reason {
	private final String tbReasonGroup = "ReasonGroup";
	private final String tbReasonDetail = "ReasonDetail";
	private final String tbSelectedReasonTmp = "SelectedReasonTmp"; 
	
	private DataBaseHelper dbHelper;
	public Reason(Context c){
		dbHelper = new DataBaseHelper(c);
	}
	
	public List<ReasonDetail> listSelectedReasonDetail(int reasonGroupId){
		List<ReasonDetail> reasonDetailLst = 
				new ArrayList<ReasonDetail>();
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(
				"SELECT ReasonID, ReasonGroupID, ReasonText " +
				" FROM " + tbSelectedReasonTmp + 
				" WHERE ReasonGroupID=" + reasonGroupId, null);
		if(cursor.moveToFirst()){
			do{
				ReasonDetail reasonDetail = 
						new ReasonDetail();
				reasonDetail.setReasonID(cursor.getInt(0));
				reasonDetail.setReasonGroupID(cursor.getInt(1));
				reasonDetail.setReasonText(cursor.getString(2));
				reasonDetailLst.add(reasonDetail);
			}while(cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		return reasonDetailLst;
	}
	
	public void addSelectedReason(int reasonId, int reasonGroupId, String reasonText){
		dbHelper.openDataBase();
		boolean isAdded = false;
		
		String strSql = "SELECT COUNT(ReasonID) FROM " + tbSelectedReasonTmp + 
				" WHERE ReasonID=" + reasonId + 
				" AND ReasonGroupID=" + reasonGroupId;
		
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			if(cursor.getInt(0) != 0){
				isAdded = true;
			}
		}
		cursor.close();
		
		if(isAdded){
			strSql = "DELETE FROM " + tbSelectedReasonTmp + 
					" WHERE ReasonID=" + reasonId + 
					" AND ReasonGroupID=" + reasonGroupId;
			dbHelper.myDataBase.execSQL(strSql);
		}else{
			ContentValues cv = new ContentValues();
			cv.put("ReasonID", reasonId);
			cv.put("ReasonGroupID", reasonGroupId);
			cv.put("ReasonText", reasonText);
			
			dbHelper.myDataBase.insert(tbSelectedReasonTmp, null, cv);
		}
		dbHelper.closeDataBase();
	}
	
	private boolean chkSelectedReasonTmp(){
		boolean tableExists = false;
		String strSql = "SELECT COUNT(*) FROM sqlite_master " +
				" WHERE type='table' AND name='" + tbSelectedReasonTmp + "'";
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			if(cursor.getInt(0) != 0){
				tableExists = true;
			}
		}
		cursor.close();
		dbHelper.closeDataBase();
		return tableExists;
	}
	
	public void createSelectedReasonTmp(){
		//if(chkSelectedReasonTmp()){
		String strSql = "CREATE TABLE " + tbSelectedReasonTmp + 
				" ( ReasonID INTEGER, ReasonGroupID INTEGER, ReasonText TEXT );";
		dbHelper.openDataBase();
		dbHelper.myDataBase.execSQL("DROP TABLE IF EXISTS " + tbSelectedReasonTmp);
		dbHelper.myDataBase.execSQL(strSql);
		dbHelper.closeDataBase();
		//}
	}
	
	public int checkExistsReasonDetail(){
		int exists = 0;
		String strSql = "SELECT COUNT(*) FROM " + tbReasonDetail;
		
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			exists = cursor.getInt(0);
		}
		cursor.close();
		dbHelper.closeDataBase();
		return exists;
	}
	
	public int checkExistsReasonGroup(){
		int exists = 0;
		String strSql = "SELECT COUNT(*) FROM " + tbReasonGroup;
		
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			exists = cursor.getInt(0);
		}
		cursor.close();
		dbHelper.closeDataBase();
		return exists;
	}
	
	public List<ReasonGroups.ReasonDetail> listAllReasonDetail(int reasonGroupId){
		List<ReasonGroups.ReasonDetail> reasonDetailLst = 
				new ArrayList<ReasonGroups.ReasonDetail>();
		dbHelper.openDataBase();
		String strSql = "SELECT ReasonID, ReasonGroupID, ReasonText " +
				" FROM " + tbReasonDetail + 
				" WHERE ReasonGroupID=" + reasonGroupId + 
				" ORDER BY Ordering ";
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			do{
				ReasonGroups.ReasonDetail detail = new ReasonGroups.ReasonDetail();
				detail.setReasonID(cursor.getInt(cursor.getColumnIndex("ReasonID")));
				detail.setReasonGroupID(cursor.getInt(cursor.getColumnIndex("ReasonGroupID")));
				detail.setReasonText(cursor.getString(cursor.getColumnIndex("ReasonText")));
				
				reasonDetailLst.add(detail);
			}while(cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		
		return reasonDetailLst;
	}
	
	public void addReasonDetail(ReasonGroups reasonGroup){
		dbHelper.openDataBase();
		dbHelper.myDataBase.execSQL("BEGIN");	
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM " + tbReasonDetail);
			for(ReasonGroups.ReasonDetail detail : reasonGroup.ReasonDetail){
				ContentValues cv = new ContentValues();
				cv.put("ReasonID", detail.getReasonID());
				cv.put("ReasonGroupID", detail.getReasonGroupID());
				cv.put("ReasonText", detail.getReasonText());
				cv.put("Ordering", detail.getOrdering());
				dbHelper.myDataBase.insert(tbReasonDetail, null, cv);
			}
			dbHelper.myDataBase.execSQL("COMMIT");
		} catch (SQLException e) {
			dbHelper.myDataBase.execSQL("ROLLBACK");
			e.printStackTrace();
		}
		
		dbHelper.closeDataBase();
	}
	
	public void addReasonGroup(ReasonGroups reasonGroup){
		dbHelper.openDataBase();
		dbHelper.myDataBase.execSQL("BEGIN");	
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM " + tbReasonGroup);
			for(ReasonGroups.ReasonGroup group : reasonGroup.ReasonGroup){
				ContentValues cv = new ContentValues();
				cv.put("ReasonGroupID", group.getReasonGroupID());
				cv.put("ReasonGroupName", group.getReasonGroupName());
				dbHelper.myDataBase.insert(tbReasonGroup, null, cv);
			}
			dbHelper.myDataBase.execSQL("COMMIT");
		} catch (SQLException e) {
			dbHelper.myDataBase.execSQL("ROLLBACK");
			e.printStackTrace();
		}
		
		dbHelper.closeDataBase();
	}
}
