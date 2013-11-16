package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.ProductGroups;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public class TransactionComment {
	private DataBaseHelper mSqlite;
	
	private static final String TB_COMM_TRANS_DEPT = "CommentTransDept";
	private static final String TB_COMM_TRANS_ITEM = "CommentTransItem";
	private static final String TB_TRANS_COMM = "TransactionComment";
	private static final String COL_COMM_DEPT_ID = "CommentDeptID";
	private static final String COL_COMM_DEPT_CODE = "CommentDeptCode";
	private static final String COL_COMM_DEPT_NAME = "CommentDeptName";
	private static final String COL_COMM_ITEM_ID = "CommentItemID";
	private static final String COL_COMM_ITEM_CODE = "CommentItemCode";
	private static final String COL_COMM_ITEM_NAME = "CommentItemName";
	private static final String COL_TABLE_ID = "TableID";
	
	public TransactionComment(Context c){
		mSqlite = new DataBaseHelper(c);
	}
	
	public List<ProductGroups.CommentTransItem> listCommentTransItem(int deptId){
		List<ProductGroups.CommentTransItem> commLst = 
				new ArrayList<ProductGroups.CommentTransItem>();
		
		String strSql = "SELECT * FROM " +
				TB_COMM_TRANS_ITEM;
		
		if(deptId != 0)
				strSql += " WHERE " + COL_COMM_DEPT_ID + "=" + deptId;
		
		mSqlite.openDataBase();
		Cursor cursor = mSqlite.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			do{
				ProductGroups.CommentTransItem ti = 
						new ProductGroups.CommentTransItem();
				ti.setCommentItemID(cursor.getInt(cursor.getColumnIndex(COL_COMM_ITEM_ID)));
				ti.setCommentDeptID(cursor.getInt(cursor.getColumnIndex(COL_COMM_DEPT_ID)));
				ti.setCommentItemCode(cursor.getString(cursor.getColumnIndex(COL_COMM_ITEM_CODE)));
				ti.setCommentItemName(cursor.getString(cursor.getColumnIndex(COL_COMM_ITEM_NAME)));
				
				commLst.add(ti);
			}while(cursor.moveToNext());
		}
		cursor.close();
		mSqlite.closeDataBase();
		return commLst;
	}
	
	public List<ProductGroups.CommentTransItem> listAllCommentTransItem(){
		List<ProductGroups.CommentTransItem> commLst = 
				new ArrayList<ProductGroups.CommentTransItem>();
		
		mSqlite.openDataBase();
		Cursor cursor = mSqlite.myDataBase.rawQuery("SELECT * FROM " +
				TB_COMM_TRANS_ITEM, null);
		if(cursor.moveToFirst()){
			do{
				ProductGroups.CommentTransItem ti = 
						new ProductGroups.CommentTransItem();
				ti.setCommentItemID(cursor.getInt(cursor.getColumnIndex(COL_COMM_ITEM_ID)));
				ti.setCommentDeptID(cursor.getInt(cursor.getColumnIndex(COL_COMM_DEPT_ID)));
				ti.setCommentItemCode(cursor.getString(cursor.getColumnIndex(COL_COMM_ITEM_CODE)));
				ti.setCommentItemName(cursor.getString(cursor.getColumnIndex(COL_COMM_ITEM_NAME)));
				
				commLst.add(ti);
			}while(cursor.moveToNext());
		}
		cursor.close();
		mSqlite.closeDataBase();
		return commLst;
	}
	
	public List<ProductGroups.CommentTransDept> listAllCommentTransDept(){
		List<ProductGroups.CommentTransDept> commLst = 
				new ArrayList<ProductGroups.CommentTransDept>();
		
		mSqlite.openDataBase();
		Cursor cursor = mSqlite.myDataBase.rawQuery("SELECT * FROM " +
				TB_COMM_TRANS_DEPT, null);
		if(cursor.moveToFirst()){
			do{
				ProductGroups.CommentTransDept td = 
						new ProductGroups.CommentTransDept();
				td.setCommentDeptID(cursor.getInt(cursor.getColumnIndex(COL_COMM_DEPT_ID)));
				td.setCommentDeptCode(cursor.getString(cursor.getColumnIndex(COL_COMM_DEPT_CODE)));
				td.setCommentDeptName(cursor.getString(cursor.getColumnIndex(COL_COMM_DEPT_NAME)));
				commLst.add(td);
			}while(cursor.moveToNext());
		}
		cursor.close();
		mSqlite.closeDataBase();
		return commLst;
	}
	
	public boolean chkTransComment(int tableId, int commentItemId){
		boolean isChecked = false;
		mSqlite.openDataBase();
		Cursor cursor = mSqlite.myDataBase.rawQuery("SELECT " + COL_COMM_ITEM_ID + 
				" FROM " + TB_TRANS_COMM + 
				" WHERE " + COL_TABLE_ID + "=" + tableId + 
				" AND " + COL_COMM_ITEM_ID + "=" + commentItemId, null);
		if(cursor.moveToFirst()){
			if(cursor.getInt(0) != 0)
				isChecked = true;
		}
		cursor.close();
		mSqlite.closeDataBase();
		return isChecked;
	}
	
	public List<ProductGroups.CommentTransItem> listTransComment(int tableId){
		List<ProductGroups.CommentTransItem> transCommLst =
				new ArrayList<ProductGroups.CommentTransItem>();
		
		mSqlite.openDataBase();
		Cursor cursor = mSqlite.myDataBase.rawQuery("SELECT a.*, b.* " +
				" FROM " + TB_TRANS_COMM + " a " +
				" LEFT JOIN " + TB_COMM_TRANS_ITEM + " b " +
				" ON a." + COL_COMM_ITEM_ID + "=b." + COL_COMM_ITEM_ID +
				" WHERE a." + COL_TABLE_ID + "=" + tableId, null);
		
		if(cursor.moveToFirst()){
			do{
				ProductGroups.CommentTransItem ti = 
						new ProductGroups.CommentTransItem();
				ti.setCommentItemID(cursor.getInt(cursor.getColumnIndex(COL_COMM_ITEM_ID)));
				ti.setCommentDeptID(cursor.getInt(cursor.getColumnIndex(COL_COMM_DEPT_ID)));
				ti.setCommentItemCode(cursor.getString(cursor.getColumnIndex(COL_COMM_ITEM_CODE)));
				ti.setCommentItemName(cursor.getString(cursor.getColumnIndex(COL_COMM_ITEM_NAME)));
				transCommLst.add(ti);
			}while(cursor.moveToNext());
		}
		cursor.close();
		mSqlite.closeDataBase();
		return transCommLst;
	}
	
	public void deleteAllTransComment(int tableId){
		mSqlite.openDataBase();
		mSqlite.myDataBase.execSQL("DELETE FROM " + TB_TRANS_COMM +
				" WHERE " + COL_TABLE_ID + "=" + tableId);
		mSqlite.closeDataBase();
	}
	
	public void deleteTransComment(int tableId, int commentItemId){
		mSqlite.openDataBase();
		mSqlite.myDataBase.execSQL("DELETE FROM " + TB_TRANS_COMM + 
				" WHERE " + COL_TABLE_ID + "=" + tableId +
				" AND " + COL_COMM_ITEM_ID + "=" + commentItemId);
		mSqlite.closeDataBase();
	}
	
	public void insertTransComment(int tableId, int commentItemId) throws SQLException{
		
		deleteTransComment(tableId, commentItemId);
		
		mSqlite.openDataBase();
		ContentValues cv = new ContentValues();
		cv.put(COL_TABLE_ID, tableId);
		cv.put(COL_COMM_ITEM_ID, commentItemId);
		mSqlite.myDataBase.insertOrThrow(TB_TRANS_COMM, null, cv);
		mSqlite.closeDataBase();
	}
	
	public void insertCommentTransItem(ProductGroups pg) throws SQLException{
		mSqlite.openDataBase();
		
		mSqlite.myDataBase.execSQL("DELETE FROM " + TB_COMM_TRANS_ITEM);
		for(ProductGroups.CommentTransItem ci : pg.getCommentTransItem()){
			ContentValues cv = new ContentValues();
			cv.put(COL_COMM_ITEM_ID, ci.getCommentItemID());
			cv.put(COL_COMM_DEPT_ID, ci.getCommentDeptID());
			cv.put(COL_COMM_ITEM_CODE, ci.getCommentItemCode());
			cv.put(COL_COMM_ITEM_NAME, ci.getCommentItemName());
			
			mSqlite.myDataBase.insertOrThrow(TB_COMM_TRANS_ITEM, null, cv);
		}
		
		mSqlite.closeDataBase();
	}
	
	public void insertCommentTransDept(ProductGroups pg) throws SQLException{
		mSqlite.openDataBase();
		
		mSqlite.myDataBase.execSQL("DELETE FROM " + TB_COMM_TRANS_DEPT);
		
		for(ProductGroups.CommentTransDept cd : pg.getCommentTransDept()){
			ContentValues cv = new ContentValues();
			cv.put(COL_COMM_DEPT_ID, cd.getCommentDeptID());
			cv.put(COL_COMM_DEPT_CODE, cd.getCommentDeptCode());
			cv.put(COL_COMM_DEPT_NAME, cd.getCommentDeptName());
			
			mSqlite.myDataBase.insertOrThrow(TB_COMM_TRANS_DEPT, null, cv);
		}
		
		mSqlite.closeDataBase();
	}
}
