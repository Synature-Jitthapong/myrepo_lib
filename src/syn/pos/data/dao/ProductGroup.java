package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import syn.pos.data.model.*;
import syn.pos.mobile.util.Log;

public class ProductGroup{

	private DataBaseHelper dbHelper;
	private Context context;
	public ProductGroup(Context context) {
		dbHelper = new DataBaseHelper(context);
		this.context = context;
	}

	public ProductGroups listAllProductGroup() {
		ProductGroups pg = new ProductGroups();

		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery("ProductGroup", null);

		cursor.moveToFirst();
		List<ProductGroups.ProductGroup> pgList = new ArrayList<ProductGroups.ProductGroup>();
		while (!cursor.isAfterLast()) {
			ProductGroups.ProductGroup p = new ProductGroups.ProductGroup();
			p.setProductGroupId(cursor.getInt(0));
			p.setProductGroupCode(cursor.getString(1));
			p.setProductGroupName(cursor.getString(2));

			pgList.add(p);
			pg.setProductGroup(pgList);
			cursor.moveToNext();
		}

		cursor.close();
		dbHelper.closeDataBase();
		
		return pg;
	}

	public void insertProductGroup(ProductGroups pGroup) {

		dbHelper.openDataBase();
		dbHelper.myDataBase.beginTransaction();
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM ProductGroup");
			
			for (ProductGroups.ProductGroup productGroup : pGroup.getProductGroup()) {
			ContentValues cv = new ContentValues();
				cv.put("ProductGroupID", productGroup.getProductGroupId());
				cv.put("ProductGroupCode", productGroup.getProductGroupCode());
				cv.put("ProductGroupName", productGroup.getProductGroupName());
				cv.put("ProductGroupOrdering",
						productGroup.getProductGroupOrdering());
				cv.put("IsComment", productGroup.getIsComment());
				cv.put("ProductGroupType", productGroup.getProductGroupType());
				cv.put("UpdateDate", productGroup.getUpdateDate());
				
				dbHelper.myDataBase.insert("ProductGroup", null, cv);
			}
			dbHelper.myDataBase.setTransactionSuccessful();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			dbHelper.myDataBase.endTransaction();
		}

		dbHelper.closeDataBase();
	}
}
