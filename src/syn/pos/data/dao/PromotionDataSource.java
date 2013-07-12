package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.PromotionDiscount;
import android.content.Context;
import android.database.Cursor;

public class PromotionDataSource{
	private DataBaseHelper dbHelper;
	
	public PromotionDataSource(Context context) {
		dbHelper = new DataBaseHelper(context);
	}
	
	public List<PromotionDiscount> getPromotionDiscount(int priceGroupId){
		List<PromotionDiscount> promotionList = 
				new ArrayList<PromotionDiscount>();
		
		String strSql = "SELECT ProductID FROM PromotionDiscount " +
				" WHERE PriceGroupID=" + priceGroupId;
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			do{
				PromotionDiscount promotion 
					= new PromotionDiscount();
				promotion.setProductId(cursor.getInt(0));
				
				promotionList.add(promotion);
			}while(cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		return promotionList;
	}
}
