package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

public class ShowMenuColumnName {
	private DataBaseHelper dbHelper;
	
	public ShowMenuColumnName(Context c){
		dbHelper = new DataBaseHelper(c);
	}
	
	public void saveUseColumn(int columnId){
		String strSqlClear = "UPDATE ShowMenuColumnName SET " +
				" IsUseThis=0";
		String strSql = "UPDATE ShowMenuColumnName SET " +
				" IsUseThis=1 " +
				" WHERE ShowMenuColId=" + columnId;
		openDatabase();
		dbHelper.myDataBase.execSQL(strSqlClear);
		dbHelper.myDataBase.execSQL(strSql);
		closeDatabase();
	}
	
	public MenuColumn getShowMenuColumnObj(){
		MenuColumn mc = new MenuColumn();
		openDatabase();
		String strSql = "SELECT * FROM ShowMenuColumnName WHERE IsUseThis=1";
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			mc.setShowMenuColId(cursor.getInt(cursor.getColumnIndex("ShowMenuColId")));
			mc.setShowMenuColName(cursor.getString(cursor.getColumnIndex("ShowMenuColName")));
			mc.setUseThis(cursor.getInt(cursor.getColumnIndex("IsUseThis")));
		}
		cursor.close();
		closeDatabase();
		return mc;
	}
	
	public List<MenuColumn> listMenuColumn(){
		List<MenuColumn> menuColumnLst = 
				new ArrayList<MenuColumn>();
		
		String strSql = "SELECT * FROM " +
				" ShowMenuColumnName ";
		
		openDatabase();
		
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		
		if(cursor.moveToFirst()){
			do{
				MenuColumn mc = new MenuColumn(
						cursor.getInt(cursor.getColumnIndex("ShowMenuColId")),
						cursor.getString(cursor.getColumnIndex("ShowMenuColName")),
						cursor.getInt(cursor.getColumnIndex("IsUseThis")));
				
				menuColumnLst.add(mc);
			}while(cursor.moveToNext());
		}
		cursor.close();
		
		closeDatabase();
		return menuColumnLst;
	}
	
	public int getShowMenuColumn(){
		int menuColId = 1; // default
		String strSql = "SELECT * FROM ShowMenuColumnName " +
				" WHERE IsUseThis=1";
		
		openDatabase();
		
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			menuColId = cursor.getInt(cursor.getColumnIndex("ShowMenuColId"));
		}
		cursor.close();
		closeDatabase();
		
		return menuColId;
	}
	
	private void closeDatabase(){
		dbHelper.closeDataBase();
	}
	
	private void openDatabase(){
		dbHelper.openDataBase();
	}
	
	public static class MenuColumn{
		private int showMenuColId;
		private String showMenuColName;
		private int isUseThis;
		
		public MenuColumn(){
			
		}
		
		public MenuColumn(int showMenuColId, String showMenuColName, int isUseThis){
			this.showMenuColId = showMenuColId;
			this.showMenuColName = showMenuColName;
			this.isUseThis = isUseThis;
		}
		
		public int getShowMenuColId() {
			return showMenuColId;
		}

		public void setShowMenuColId(int showMenuColId) {
			this.showMenuColId = showMenuColId;
		}


		public String getShowMenuColName() {
			return showMenuColName;
		}


		public void setShowMenuColName(String showMenuColName) {
			this.showMenuColName = showMenuColName;
		}


		public int isUseThis() {
			return isUseThis;
		}


		public void setUseThis(int isUseThis) {
			this.isUseThis = isUseThis;
		}

		@Override
		public String toString() {
			return showMenuColName;
		}
		
		
	}
}
