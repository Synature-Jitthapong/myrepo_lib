package syn.pos.data.dao;

import syn.pos.data.model.ProductGroups;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;

public class ProductDept {

	private DataBaseHelper dbHelper;
	public ProductDept(Context context){
		dbHelper = new DataBaseHelper(context);
	}
	
	public void insertProductDept(ProductGroups pg){
		dbHelper.openDataBase();
		
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM ProductDept");
			
			for(ProductGroups.ProductDept pd : pg.getProductDept()){
				ContentValues cv = new ContentValues();
				
				cv.put("ProductDeptID", pd.getProductDeptID());
				cv.put("ProductGroupID", pd.getProductGroupID());
				cv.put("ProductDeptCode", pd.getProductDeptCode());
				cv.put("ProductDeptName", pd.getProductDeptName());
				cv.put("ProductDeptOrdering", pd.getProductDeptOrdering());
				cv.put("UpdateDate", pd.getUpdateDate());
				
				dbHelper.myDataBase.insert("ProductDept", null, cv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbHelper.closeDataBase();
	}
}
