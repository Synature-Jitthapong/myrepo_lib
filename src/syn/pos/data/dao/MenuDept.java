package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.MenuGroups;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public class MenuDept {
	private DataBaseHelper dbHelper;
	private String strSql = "SELECT * FROM MenuDept";

	public MenuDept(Context context) {
		dbHelper = new DataBaseHelper(context);
		strSql += " ORDER BY MenuDeptOrdering, MenuDeptName_0";
	}

	public MenuDept(Context context, int menuGroupId) {
		dbHelper = new DataBaseHelper(context);
		if (menuGroupId != 0)
			strSql += " WHERE MenuGroupID=" + menuGroupId;
		strSql += " ORDER BY MenuDeptOrdering, MenuDeptName_0";
	}

	public Boolean insertMenuDept(MenuGroups mg) {
		Boolean isSuccess = false;
		dbHelper.openDataBase();
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM MenuDept");
			for (MenuGroups.MenuDept md : mg.getMenuDept()) {
				ContentValues cv = new ContentValues();

				cv.put("MenuDeptID", md.getMenuDeptID());
				cv.put("MenuGroupID", md.getMenuGroupID());
				cv.put("MenuDeptName_0", md.getMenuDeptName_0());
				cv.put("MenuDeptName_1", md.getMenuDeptName_1());
				cv.put("MenuDeptName_2", md.getMenuDeptName_2());
				cv.put("MenuDeptName_3", md.getMenuDeptName_3());
				cv.put("MenuDeptOrdering", md.getMenuDeptOrdering());
				cv.put("UpdateDate", md.getUpdateDate());

				dbHelper.myDataBase.insert("MenuDept", null, cv);
				isSuccess = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			isSuccess = false;
		}

		dbHelper.closeDataBase();
		return isSuccess;
	}

	public Cursor getMenuDept() {
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		dbHelper.closeDataBase();
		return cursor;
	}
	
	public List<MenuGroups.MenuDept> listMenuDept() {
		List<MenuGroups.MenuDept> mdl = new ArrayList<MenuGroups.MenuDept>();

		dbHelper.openDataBase();

		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);

		cursor.moveToFirst();
		MenuGroups.MenuDept md;
		while (!cursor.isAfterLast()) {
			md = new MenuGroups.MenuDept();

			md.setMenuDeptID(cursor.getInt(0));
			md.setMenuGroupID(cursor.getInt(1));
			md.setMenuDeptName_0(cursor.getString(2) != "" ? cursor.getString(2) : "");
//			md.setMenuDeptName_1(cursor.getString(3));
//			md.setMenuDeptName_2(cursor.getString(4));
//			md.setMenuDeptName_3(cursor.getString(5));
			// md.setMenuDeptName_4(cursor.getString(6));
			// md.setMenuDeptName_5(cursor.getString(7));
			md.setMenuDeptOrdering(cursor.getInt(6));
			md.setUpdateDate(cursor.getString(7));

			mdl.add(md);

			cursor.moveToNext();
		}
		cursor.close();
		
		dbHelper.closeDataBase();

		return mdl;
	}
}
