package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import syn.pos.data.model.Province;

public class ProvinceDataSource{
	private DataBaseHelper dbHelper;
	private static final String TB_NAME = "Provinces";
	
	public ProvinceDataSource(Context context) {
		dbHelper = new DataBaseHelper(context);
	}

	public List<Province> listProvince(int langId){
		List<Province> provinceLst = new ArrayList<Province>();
		dbHelper.openDataBase();
		String strSql = "SELECT * FROM " + TB_NAME  +
				" WHERE LangID=" + langId + " ORDER BY ProvinceName ASC ";
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			do{
				Province province = new Province();
				province.setProvinceId(cursor.getInt(1));
				province.setProvinceName(cursor.getString(2));
				provinceLst.add(province);
			}while(cursor.moveToNext());
			
		}
		cursor.close();
		dbHelper.closeDataBase();
		return provinceLst;
	}
}
