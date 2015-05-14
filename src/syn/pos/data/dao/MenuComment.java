package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.MenuGroups;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public class MenuComment{
	private DataBaseHelper dbHelper;
	private GlobalVar globalVar;
	private final String TB_MENU_COMMENT = "MenuComment";
	private final String TB_MENU_FIX_COMMENT = "MenuFixComment";
	private final String TB_MENU_COMMENT_GROUP = "MenuCommentGroup";
	private final String TB_PRODUCT = "Products";
	
	private final String COL_MENU_COMMENT_GROUP_ID = "MenuCommentGroupID";
	private final String COL_MENU_COMMENT_GROUP_NAME_0 = "MenuCommentGroupName_0";
	private final String COL_MENU_COMMENT_GROUP_NAME_1 = "MenuCommentGroupName_1";
	
	private final String COL_MENU_ITEM_ID = "MenuItemID";
	private final String COL_MENU_COMMENT_ID = "MenuCommentID";
	private final String COL_MENU_COMMENT_NAME_0 = "MenuCommentName_0";
	private final String COL_MENU_COMMENT_ORDERING = "MenuCommentOrdering";
	
	private final String COL_PRODUCT_ID = "ProductID";
	private final String COL_PRODUCT_PRICE = "ProductPricePerUnit";
	private final String COL_PRODUCT_TYPE_ID = "ProductTypeID";
	
	private String strSql = " SELECT a.MenuCommentID, a.MenuCommentName_0, "
			+ " b.ProductID, b.ProductPricePerUnit " + " FROM MenuComment a "
			+ " LEFT JOIN Products b " + " ON a.MenuCommentID=b.ProductID "
			+ " ORDER BY a.MenuCommentOrdering ";
	
	
	public MenuComment(Context context) {
		dbHelper = new DataBaseHelper(context);
		globalVar = new GlobalVar(context, dbHelper);
	}
	
	public MenuComment(Context context, int productId){
		dbHelper = new DataBaseHelper(context);
		globalVar = new GlobalVar(context, dbHelper);
		
		listCommentConfig(productId);
	}

	public List<MenuGroups.MenuComment> listMenuComments(int menuCommentGroupId){
		List<MenuGroups.MenuComment> mcLst = 
				new ArrayList<MenuGroups.MenuComment>();
		dbHelper.openDataBase();
		String strSql = "SELECT " + COL_MENU_COMMENT_ID + ", " + COL_MENU_COMMENT_GROUP_ID + ", " +
				COL_MENU_COMMENT_NAME_0 + ", " + COL_PRODUCT_ID + ", " + 
				COL_PRODUCT_PRICE + ", " + COL_PRODUCT_TYPE_ID +
				" FROM " + TB_MENU_COMMENT + " a " +
				" LEFT JOIN " + TB_PRODUCT + " b " +
				" ON a.MenuCommentID=b.ProductID ";
				strSql += menuCommentGroupId != 0 ? " WHERE " + COL_MENU_COMMENT_GROUP_ID + "=" + menuCommentGroupId : "";
				strSql += " ORDER BY " + COL_MENU_COMMENT_ORDERING;
		
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			do{
				MenuGroups.MenuComment mc = 
						new MenuGroups.MenuComment();
				
				mc.setMenuCommentID(cursor.getInt(cursor.getColumnIndex(COL_MENU_COMMENT_ID)));
				mc.setMenuCommentGroupID(cursor.getInt(cursor.getColumnIndex(COL_MENU_COMMENT_GROUP_ID)));
				mc.setMenuCommentName_0(cursor.getString(cursor.getColumnIndex(COL_MENU_COMMENT_NAME_0)));
				mc.setProductID(cursor.getInt(cursor.getColumnIndex(COL_PRODUCT_ID)));
				mc.setProductPricePerUnit(cursor.getDouble(cursor.getColumnIndex(COL_PRODUCT_PRICE)));
				mc.setCommentWithPrice(
						cursor.getInt(cursor.getColumnIndex(COL_PRODUCT_TYPE_ID)) == 15 ? true : false);
				
				mcLst.add(mc);
			}while(cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		return mcLst;
	}
	
	public List<MenuGroups.MenuCommentGroup> listMenuCommentGroups(){
		dbHelper.openDataBase();
		String strSql = "SELECT * FROM " + TB_MENU_COMMENT_GROUP;
		List<MenuGroups.MenuCommentGroup> mcgLst = 
				new ArrayList<MenuGroups.MenuCommentGroup>();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);

		MenuGroups.MenuCommentGroup mg = 
				new MenuGroups.MenuCommentGroup();
		mg.setMenuCommentGroupID(0);
		mg.setMenuCommentGroupName_0("--All--");
		mcgLst.add(mg);
		
		if(cursor.moveToFirst()){
			do{
				MenuGroups.MenuCommentGroup mcg = 
						new MenuGroups.MenuCommentGroup();
				mcg.setMenuCommentGroupID(cursor.getInt(cursor.getColumnIndex(COL_MENU_COMMENT_GROUP_ID)));
				mcg.setMenuCommentGroupName_0(cursor.getString(cursor.getColumnIndex(COL_MENU_COMMENT_GROUP_NAME_0)));
				mcg.setMenuCommentGroupName_1(cursor.getString(cursor.getColumnIndex(COL_MENU_COMMENT_GROUP_NAME_1)));
				mcgLst.add(mcg);
			}while(cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		return mcgLst;
	}
	
	public List<MenuGroups.MenuComment> listGlobalMenuComment(){
		List<MenuGroups.MenuComment> menuCommentList = 
				new ArrayList<MenuGroups.MenuComment>();
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			do{
				MenuGroups.MenuComment mc = new MenuGroups.MenuComment();

				mc.setMenuCommentID(cursor.getInt(cursor.getColumnIndex("MenuCommentID")));
				mc.setMenuCommentGroupID(cursor.getInt(cursor.getColumnIndex("MenuCommentGroupID")));
				mc.setMenuCommentName_0(cursor.getString(cursor.getColumnIndex("MenuCommentName_0")));
				mc.setProductID(cursor.getInt(cursor.getColumnIndex("ProductID")));
				mc.setProductPricePerUnit(cursor.getDouble(cursor.getColumnIndex("ProductPricePerUnit")));

				menuCommentList.add(mc);
			}while(cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		return menuCommentList;
	}
	
	public List<MenuGroups.MenuComment> listFixMenuComment(int menuItemId){
		dbHelper.openDataBase();
		List<MenuGroups.MenuComment> mcLst = 
				new ArrayList<MenuGroups.MenuComment>();
		
		String sqlMenuFix = "SELECT " + COL_MENU_COMMENT_ID +
				" FROM " + TB_MENU_FIX_COMMENT +
				" WHERE " + COL_MENU_ITEM_ID + "=" + menuItemId;
		
		StringBuilder sqlMenuCommentId = new StringBuilder();
		Cursor cursor = dbHelper.myDataBase.rawQuery(sqlMenuFix, null);
		if(cursor.moveToFirst()){
			for(int i = 1; i <= cursor.getCount(); i++){
				sqlMenuCommentId.append(cursor.getInt(cursor.getColumnIndex(COL_MENU_COMMENT_ID)));
				if(i < cursor.getCount()){
					sqlMenuCommentId.append(",");
				}
				cursor.moveToNext();
			}
		}
		cursor.close();
		
		String strSql = "SELECT " + COL_MENU_COMMENT_ID + ", " + COL_MENU_COMMENT_GROUP_ID + ", " +
				COL_MENU_COMMENT_NAME_0 + ", " + COL_PRODUCT_ID + ", " + 
				COL_PRODUCT_PRICE +
				" FROM " + TB_MENU_COMMENT + " a " +
				" LEFT JOIN " + TB_PRODUCT + " b " +
				" ON a.MenuCommentID=b.ProductID " +
				" WHERE " + COL_MENU_COMMENT_ID + 
				" IN (" + sqlMenuCommentId + ") " +
				" ORDER BY " + COL_MENU_COMMENT_ORDERING;
		
		cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			do{
				MenuGroups.MenuComment mc = 
						new MenuGroups.MenuComment();
				
				mc.setMenuCommentID(cursor.getInt(cursor.getColumnIndex(COL_MENU_COMMENT_ID)));
				mc.setMenuCommentGroupID(cursor.getInt(cursor.getColumnIndex(COL_MENU_COMMENT_GROUP_ID)));
				mc.setMenuCommentName_0(cursor.getString(cursor.getColumnIndex(COL_MENU_COMMENT_NAME_0)));
				mc.setProductID(cursor.getInt(cursor.getColumnIndex(COL_PRODUCT_ID)));
				mc.setProductPricePerUnit(cursor.getDouble(cursor.getColumnIndex(COL_PRODUCT_PRICE)));
				
				mcLst.add(mc);
			}while(cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		return mcLst;
	}
	
	public void listCommentConfig(int productId){
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery("SELECT * FROM " +
				" CommentProduct WHERE " +
				" ProductID=" + productId, null);
		
		if(cursor.moveToFirst()){
			String strComment = "";
			for(int i = 0; i < cursor.getCount(); i++){
				strComment += cursor.getString(cursor.getColumnIndex("CommentID"));
				if(i < cursor.getCount() - 1)
					strComment += ",";
				cursor.moveToNext();
			}

			strSql = " SELECT a.MenuCommentID, a.MenuCommentName_0, "
					+ " b.ProductID, b.ProductPricePerUnit " 
					+ " FROM MenuComment a "
					+ " LEFT JOIN Products b " 
					+ " ON a.MenuCommentID=b.ProductID "
					+ " WHERE MenuCommentID IN (" + strComment + ")"
					+ " ORDER BY a.MenuCommentOrdering";
		}
		cursor.close();
		dbHelper.closeDataBase();
	}
	
	public List<MenuGroups.MenuComment> menuCommentList() {
		dbHelper.openDataBase();

		List<MenuGroups.MenuComment> menuCommentList = new ArrayList<MenuGroups.MenuComment>();

		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			MenuGroups.MenuComment mc = new MenuGroups.MenuComment();

			mc.setMenuCommentID(cursor.getInt(0));
			mc.setMenuCommentName_0(cursor.getString(1));
			mc.setProductID(cursor.getInt(2));
			mc.setProductPricePerUnit(cursor.getDouble(3));

			menuCommentList.add(mc);
			
			cursor.moveToNext();
		}
		cursor.close();
		
		dbHelper.closeDataBase();

		return menuCommentList;
	}
	
	public MenuGroups.MenuComment[] menuCommentArr() {
		dbHelper.openDataBase();

		MenuGroups.MenuComment[] menuCommentArr;

		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		
		menuCommentArr = new MenuGroups.MenuComment[cursor.getCount()];
		
		cursor.moveToFirst();
		int i = 0;
		while (!cursor.isAfterLast()) {
			MenuGroups.MenuComment mc = new MenuGroups.MenuComment();

			mc.setMenuCommentID(cursor.getInt(0));
			mc.setMenuCommentName_0(cursor.getString(1));
			mc.setProductID(cursor.getInt(2));
			mc.setProductPricePerUnit(cursor.getDouble(3));
			
			menuCommentArr[i] = mc;
			
			cursor.moveToNext();
			
			i++;
		}

		cursor.close();
		dbHelper.closeDataBase();

		return menuCommentArr;
	}
	
	public List<MenuGroups.MenuComment> listMenuComment() {
		dbHelper.openDataBase();

		List<MenuGroups.MenuComment> listMenuComment = new ArrayList<MenuGroups.MenuComment>();

		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			MenuGroups.MenuComment mc = new MenuGroups.MenuComment();

			mc.setMenuCommentID(cursor.getInt(0));
			mc.setMenuCommentName_0(cursor.getString(1));
			mc.setProductID(cursor.getInt(2));
			mc.setProductPricePerUnit(cursor.getDouble(3));

			String strHtml = "<div style=\"float:left;\"><span class=\"menuComment\">" + cursor.getString(1) + "</span></div>" + 
						"<div style=\"float:right\"><span style=\"display:none;\">" + cursor.getDouble(3) + "</span>" + 
						"<span style=\"display:none;\">" + cursor.getInt(0) + "</span>" + 
						"</div><div style=\"clear:both;height:1px\"></div>";
			if (cursor.getDouble(3) == 0){
				mc.setItemHtml(strHtml);
			} else {
				strHtml = "<div style=\"float:left;\"><span class=\"menuComment\">" + cursor.getString(1) + "</span>&nbsp;" +
						"<span>" + globalVar.currency.getSymbol() + globalVar.decFormat.format(cursor.getDouble(3)) + "</span></div>" + 
						"<div style=\"float:right\"><button type=\"button\" class = \"deletenumber\"  style=\"width:60px; height:40px;\">-</button> " +
						"<span class=\"comm_txtnumber\" id=\"" + cursor.getInt(0) + "_comm-qty\" style=\"padding:5px; font-size:20px; font-weight:bold;\">0</span> " + 
						"<button type=\"button\" class = \"addnumber\" style=\"width:60px; height:40px;\">+</button></p> " +
						"<span style=\"display:none;\">" + cursor.getInt(0) + "</span></div><div style=\"clear:both;height:1px\"></div>";
				mc.setItemHtml(strHtml);
			}
			listMenuComment.add(mc);
			cursor.moveToNext();
		}
		cursor.close();
		
		dbHelper.closeDataBase();

		return listMenuComment;
	}

//	public List<MenuGroups.MenuComment> listMenuCommentNoPrice() {
//		dbHelper.openDataBase();
//
//		List<MenuGroups.MenuComment> listMenuCommentNoPrice = new ArrayList<MenuGroups.MenuComment>();
//
//		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
//
//		cursor.moveToFirst();
//		while (!cursor.isAfterLast()) {
//			if (cursor.getDouble(3) != 0) {
//				MenuGroups.MenuComment mc = new MenuGroups.MenuComment();
//				mc.setMenuCommentID(cursor.getInt(0));
//				mc.setMenuCommentName_0(cursor.getString(1));
//				mc.setProductID(cursor.getInt(2));
//				mc.setProductPricePerUnit(cursor.getDouble(3));
//				listMenuCommentNoPrice.add(mc);
//			}
//			cursor.moveToNext();
//		}
//		dbHelper.closeDataBase();
//
//		return listMenuCommentNoPrice;
//	}

	public void insertMenuCommentGroup(MenuGroups mg){
		dbHelper.openDataBase();
		dbHelper.myDataBase.execSQL("DELETE FROM MenuCommentGroup");
		
		for(MenuGroups.MenuCommentGroup mcg : mg.getMenuCommentGroup()){
			ContentValues cv = new ContentValues();
			cv.put("MenuCommentGroupID", mcg.getMenuCommentGroupID());
			cv.put("MenuCommentGroupName_0", mcg.getMenuCommentGroupName_0());
			cv.put("MenuCommentGroupName_1", mcg.getMenuCommentGroupName_1());
			cv.put("UpdateDate", mcg.getUpdateDate());
			
			try {
				dbHelper.myDataBase.insert("MenuCommentGroup", null, cv);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		dbHelper.closeDataBase();
	}
	
	public void insertMenuFixComment(MenuGroups mg) {
		dbHelper.openDataBase();
		dbHelper.myDataBase.execSQL("DELETE FROM MenuFixComment");

		for (MenuGroups.MenuFixComment mfc : mg.getMenuFixComment()) {
			ContentValues cv = new ContentValues();
			cv.put("MenuItemID", mfc.getMenuItemID());
			cv.put("MenuCommentID", mfc.getMenuCommentID());

			dbHelper.myDataBase.insert("MenuFixComment", null, cv);
		}
		dbHelper.closeDataBase();
	}
	
	public void insertMenuComment2(MenuGroups mg) {
		dbHelper.openDataBase();
		dbHelper.myDataBase.execSQL("DELETE FROM MenuComment");

		for (MenuGroups.MenuComment mc : mg.getMenuComment()) {
			ContentValues cv = new ContentValues();
			cv.put("MenuCommentID", mc.getMenuCommentID());
			cv.put("MenuCommentGroupID", mc.getMenuCommentGroupID());
			cv.put("MenuCommentName_0", mc.getMenuCommentName_0());
			cv.put("MenuCommentName_1", mc.getMenuCommentName_1());
			cv.put("MenuCommentName_2", mc.getMenuCommentName_2());
			cv.put("MenuCommentName_3", mc.getMenuCommentName_3());
			cv.put("MenuCommentOrdering", mc.getMenuCommentOrdering());
			cv.put("UpdateDate", mc.getUpdateDate());

			dbHelper.myDataBase.insert("MenuComment", null, cv);
		}
		dbHelper.closeDataBase();
	}
	
	public void insertMenuComment(MenuGroups mg) {
		dbHelper.openDataBase();		
		dbHelper.myDataBase.execSQL("DELETE FROM MenuComment");

		for (MenuGroups.MenuComment mc : mg.getMenuComment()) {
			ContentValues cv = new ContentValues();
			cv.put("MenuCommentID", mc.getMenuCommentID());
			cv.put("MenuCommentName_0", mc.getMenuCommentName_0());
			cv.put("MenuCommentName_1", mc.getMenuCommentName_1());
			cv.put("MenuCommentName_2", mc.getMenuCommentName_2());
			cv.put("MenuCommentName_3", mc.getMenuCommentName_3());
			cv.put("MenuCommentOrdering", mc.getMenuCommentOrdering());
			cv.put("UpdateDate", mc.getUpdateDate());

			dbHelper.myDataBase.insert("MenuComment", null, cv);
		}
		dbHelper.closeDataBase();
	}
}
