package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.ProductGroups;
import syn.pos.mobile.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public class SaleMode {
	private DataBaseHelper dbHelper;
	private Context context;
	
	public SaleMode(Context c){
		context = c;
		dbHelper = new DataBaseHelper(c);
	}
	
	private void openDatabase(){
		dbHelper.openDataBase();
	}
	
	private void closeDatabase(){
		dbHelper.closeDataBase();
	}
	
	public ProductGroups.SaleMode getSaleMode(int saleModeId){
		ProductGroups.SaleMode saleMode = 
				new ProductGroups.SaleMode();
		
		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery("SELECT * FROM SaleMode WHERE SaleModeID=" + saleModeId, null);
		if(cursor.moveToFirst()){
				saleMode.setSaleModeID(cursor.getInt(cursor.getColumnIndex("SaleModeID")));
				saleMode.setSaleModeName(cursor.getString(cursor.getColumnIndex("SaleModeName")));
				saleMode.setPositionPrefix(cursor.getInt(cursor.getColumnIndex("PositionPrefix")));
				saleMode.setPrefixText(cursor.getString(cursor.getColumnIndex("PrefixText")));
				saleMode.setPrefixQueue(cursor.getString(cursor.getColumnIndex("PrefixQueue")));
		}
		cursor.close();
		closeDatabase();
		return saleMode;
	}
	
	public List<ProductGroups.SaleMode> listSaleMode(){
		List<ProductGroups.SaleMode> saleModeList = 
				new ArrayList<ProductGroups.SaleMode>();
		
		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery("SELECT * FROM SaleMode", null);
		if(cursor.moveToFirst()){
			do{
				ProductGroups.SaleMode saleMode = new ProductGroups.SaleMode();
				saleMode.setSaleModeID(cursor.getInt(cursor.getColumnIndex("SaleModeID")));
				saleMode.setSaleModeName(cursor.getString(cursor.getColumnIndex("SaleModeName")));
				saleMode.setPositionPrefix(cursor.getInt(cursor.getColumnIndex("PositionPrefix")));
				saleMode.setPrefixText(cursor.getString(cursor.getColumnIndex("PrefixText")));
				saleMode.setPrefixQueue(cursor.getString(cursor.getColumnIndex("PrefixQueue")));
				saleModeList.add(saleMode);
			}while(cursor.moveToNext());
		}
		cursor.close();
		closeDatabase();
		return saleModeList;
	}
	
	public void insertSaleMode(ProductGroups pGroup){
		openDatabase();
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM SaleMode");
			for(ProductGroups.SaleMode saleMode : pGroup.getSaleMode()){
				ContentValues cv = new ContentValues();
				cv.put("SaleModeID", saleMode.getSaleModeID());
				cv.put("SaleModeName", saleMode.getSaleModeName());
				cv.put("PositionPrefix", saleMode.getPositionPrefix());
				cv.put("PrefixText", saleMode.getPrefixText());
				cv.put("PrefixQueue", saleMode.getPrefixQueue());
				
				dbHelper.myDataBase.insert("SaleMode", null, cv);
			}
		} catch (SQLException e) {
			Log.appendLog(context, e.getMessage());
		}
		closeDatabase();
	}
	
}
