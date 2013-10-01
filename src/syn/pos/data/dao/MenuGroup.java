package syn.pos.data.dao;

import java.util.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import syn.pos.data.model.*;

public class MenuGroup{
	private DataBaseHelper dbHelper;
	private String strSql = "SELECT * FROM MenuGroup WHERE MenuGroupType=0 " +
			" AND Activate=1 ORDER BY MenuGroupOrdering";

	public MenuGroup(Context context) {
		dbHelper = new DataBaseHelper(context);
	}

	public Boolean insertMenuGroup(MenuGroups menuGroup) {
		Boolean isSuccess = false;
		dbHelper.openDataBase();

		try {
			dbHelper.myDataBase.execSQL("DELETE FROM MenuGroup");
			for (MenuGroups.MenuGroup mg : menuGroup.getMenuGroup()) {
				ContentValues cv = new ContentValues();
				cv.put("MenuGroupID", mg.getMenuGroupID());
				cv.put("MenuGroupName_0", mg.getMenuGroupName_0());
				cv.put("MenuGroupName_1", mg.getMenuGroupName_1());
				cv.put("MenuGroupName_2", mg.getMenuGroupName_2());
				cv.put("MenuGroupName_3", mg.getMenuGroupName_3());
				cv.put("MenuGroupType", mg.getMenuGroupType());
				cv.put("MenuGroupOrdering", mg.getMenuGroupOrdering());
				cv.put("UpdateDate", mg.getUpdateDate());
				cv.put("Activate", mg.getActivate());

				dbHelper.myDataBase.insert("MenuGroup", null, cv);
				isSuccess = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isSuccess = false;
		}

		dbHelper.closeDataBase();
		return isSuccess;
	}

	public List<MenuGroups.MenuGroup> listAllMenuGroup() {
		List<MenuGroups.MenuGroup> mgl = new ArrayList<MenuGroups.MenuGroup>();

		dbHelper.openDataBase();

		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			MenuGroups.MenuGroup mg = new MenuGroups.MenuGroup();

			mg.setMenuGroupID(cursor.getInt(0));
			mg.setMenuGroupName_0(cursor.getString(1));
			mg.setMenuGroupName_1(cursor.getString(2));
			mg.setMenuGroupName_2(cursor.getString(3));
			mg.setMenuGroupName_3(cursor.getString(4));
			// .setMenuGroupName_4(cursor.getString(5));
			// mg.setMenuGroupName_5(cursor.getString(6));
			mg.setMenuGroupOrdering(cursor.getInt(5));
			mg.setMenuGroupType(cursor.getInt(6));
			mg.setUpdateDate(cursor.getString(7));

			mgl.add(mg);

			cursor.moveToNext();
		}

		cursor.close();
		dbHelper.closeDataBase();

		return mgl;
	}
}
