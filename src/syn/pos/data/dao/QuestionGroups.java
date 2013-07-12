package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.ProductGroups;
import syn.pos.data.model.ProductGroups.QuestionAnswerData;
import syn.pos.data.model.ProductGroups.QuestionDetail;
import syn.pos.mobile.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

public class QuestionGroups {
	private DataBaseHelper dbHelper;
	private Context context;
	
	private final String ANSWER_QUESTION_TEMP = "answer_question_tmp";
	private final String ANSWER_QUESTION_TEMP_COL_QUESTION_ID = "question_id";
	private final String ANSWER_QUESTION_TEMP_COL_QUESTION_OPTION_ID = "question_option_id";
	private final String ANSWER_QUESTION_TEMP_COL_QUESTION_QTY = "question_qty";
	private final String ANSWER_QUESTION_TEMP_COL_QUESTION_NAME = "question_name";
	
	private final String TB_ANSWER_OPTION = "answer_option";
	private final String TB_ANSWER_OPTION_COL_ANSWER_ID = "answer_id";
	private final String TB_ANSWER_OPTION_COL_QUESTION_ID = "question_id";
	private final String TB_ANSWER_OPTION_COL_ANSWER_NAME = "answer_name";
	
	private final String ANSWWER_OPTION_ALL_COLUMN[] = {
			TB_ANSWER_OPTION_COL_ANSWER_ID,
			TB_ANSWER_OPTION_COL_QUESTION_ID,
			TB_ANSWER_OPTION_COL_ANSWER_NAME
	};
	
	private final String ANSWER_QUESTION_ALL_COLUMN[] = {
			ANSWER_QUESTION_TEMP_COL_QUESTION_ID,
			ANSWER_QUESTION_TEMP_COL_QUESTION_OPTION_ID,
			ANSWER_QUESTION_TEMP_COL_QUESTION_QTY,
			ANSWER_QUESTION_TEMP_COL_QUESTION_NAME
	};
	
	public QuestionGroups(Context c){
		dbHelper = new DataBaseHelper(c);
		context = c;
	}
	
	private void openDatabase(){
		dbHelper.openDataBase();
	}
	
	private void closeDatabase(){
		dbHelper.closeDataBase();
	}
	
	public void deleteAnswerQuestion(int questionId){
		openDatabase();
		dbHelper.myDataBase.execSQL("DELETE FROM " + ANSWER_QUESTION_TEMP +
				" WHERE " + ANSWER_QUESTION_TEMP_COL_QUESTION_ID + "=" +
				questionId);
		closeDatabase();
	}
	
	public int checkAddedOpt(int questionId){
		int addedOptId = -1;
		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery("SELECT " + ANSWER_QUESTION_TEMP_COL_QUESTION_OPTION_ID + 
				" FROM " + ANSWER_QUESTION_TEMP +
				" WHERE " + ANSWER_QUESTION_TEMP_COL_QUESTION_ID + "=" + questionId, null);
		if(cursor.moveToFirst()){
			addedOptId = cursor.getInt(cursor.getColumnIndex(ANSWER_QUESTION_TEMP_COL_QUESTION_OPTION_ID));
			if(addedOptId == 0)
				addedOptId = -1;
		}
		cursor.close();
		closeDatabase();
		return addedOptId;
	}
	
	public boolean checkAddedQuestion(int questId){
		boolean isAdded = false;
		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery("SELECT " + ANSWER_QUESTION_TEMP_COL_QUESTION_ID + 
				" FROM " + ANSWER_QUESTION_TEMP +
				" WHERE " + ANSWER_QUESTION_TEMP_COL_QUESTION_ID + "=" + questId, null);
		if(cursor.moveToFirst()){
			isAdded = true;
		}
		cursor.close();
		closeDatabase();
		return isAdded;
	}
	
	public void addAnswerQuestion(int questionId, int questionOptId, String questionName){
		openDatabase();
		String sqlDelete = "DELETE FROM " + ANSWER_QUESTION_TEMP + 
			" WHERE " + ANSWER_QUESTION_TEMP_COL_QUESTION_ID + "=" + questionId;
		
		dbHelper.myDataBase.execSQL(sqlDelete);
		
		ContentValues cv = new ContentValues();
		cv.put(ANSWER_QUESTION_TEMP_COL_QUESTION_ID, questionId);
		cv.put(ANSWER_QUESTION_TEMP_COL_QUESTION_OPTION_ID, questionOptId);
		cv.put(ANSWER_QUESTION_TEMP_COL_QUESTION_NAME, questionName);
		
		dbHelper.myDataBase.insert(ANSWER_QUESTION_TEMP, null, cv);
		
		closeDatabase();
	}
	
	public void addAnswerQuestion(int questionId, int questionOptId, double qty,
			String questionName){
		String sqlCheck = "SELECT " + ANSWER_QUESTION_TEMP_COL_QUESTION_ID + 
				" FROM " + ANSWER_QUESTION_TEMP + 
				" WHERE " + ANSWER_QUESTION_TEMP_COL_QUESTION_ID + "=" + questionId;

		String sqlUpdate = "UPDATE " + ANSWER_QUESTION_TEMP + 
				" SET " + ANSWER_QUESTION_TEMP_COL_QUESTION_QTY + "=" + qty + 
				" WHERE " + ANSWER_QUESTION_TEMP_COL_QUESTION_ID + "=" + questionId;
		
		openDatabase();
		// check
		Cursor cursor = dbHelper.myDataBase.rawQuery(sqlCheck, null);
		boolean found = false;
		if(cursor.moveToFirst()){
			found = true;
		}
		cursor.close();
		
		if(found){
			// update
			dbHelper.myDataBase.execSQL(sqlUpdate);
		}else{
			// add
			ContentValues cv = new ContentValues();
			cv.put(ANSWER_QUESTION_TEMP_COL_QUESTION_ID, questionId);
			cv.put(ANSWER_QUESTION_TEMP_COL_QUESTION_OPTION_ID, questionOptId);
			cv.put(ANSWER_QUESTION_TEMP_COL_QUESTION_QTY, qty);
			cv.put(ANSWER_QUESTION_TEMP_COL_QUESTION_NAME, questionName);
			
			dbHelper.myDataBase.insert(ANSWER_QUESTION_TEMP, null, cv);
		}
		closeDatabase();
	}
	
	public void insertCurrentAnswerQuestion(List<ProductGroups.QuestionAnswerData> questAnsLst){
		createAnswerQuestionTemp();
		
		openDatabase();
		for(ProductGroups.QuestionAnswerData qsAns : questAnsLst){
			ContentValues cv = new ContentValues();
			cv.put(ANSWER_QUESTION_TEMP_COL_QUESTION_ID, qsAns.getiQuestionID());
			cv.put(ANSWER_QUESTION_TEMP_COL_QUESTION_OPTION_ID, qsAns.getiAnsOptionID());
			cv.put(ANSWER_QUESTION_TEMP_COL_QUESTION_QTY, qsAns.getfAnsValue());
			cv.put(ANSWER_QUESTION_TEMP_COL_QUESTION_NAME, qsAns.getSzAnsText());
			
			try {
				dbHelper.myDataBase.insert(ANSWER_QUESTION_TEMP, null, cv);
			} catch (Exception e) {
				Log.appendLog(context, e.getMessage());
				e.printStackTrace();
			}
		}
		closeDatabase();
	}
	public void createAnswerQuestionTemp(){
		String sqlDrop = "DROP TABLE IF EXISTS " + ANSWER_QUESTION_TEMP;
		String sqlCreate = "CREATE TABLE " + ANSWER_QUESTION_TEMP + 
				" ( question_id INTEGER, " +
				" question_option_id INTEGER, " +
				" question_qty REAL, " +
				" question_name TEXT );";
		
		openDatabase();
		dbHelper.myDataBase.execSQL(sqlDrop);
		dbHelper.myDataBase.execSQL(sqlCreate);
		closeDatabase();
	}
	
	public int getTotalAnswerQty(){
		int totalQty = 0;
		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery("SELECT SUM(" + ANSWER_QUESTION_TEMP_COL_QUESTION_QTY + ") " +
				" FROM " + ANSWER_QUESTION_TEMP, null);
		if(cursor.moveToFirst()){
			totalQty = cursor.getInt(0);
		}
		cursor.close();
		closeDatabase();
		return totalQty;
	}
	
	public boolean checkChoiceTypeQuestion(int typeId){
		boolean isChoiceType = false;
		openDatabase();
		Cursor cursor = dbHelper.myDataBase.rawQuery("SELECT * FROM QuestionDetail " +
				" WHERE QuestionTypeID = " + typeId, null);
		if(cursor.moveToFirst()){
			isChoiceType = true;
		}
		cursor.close();
		closeDatabase();
		return isChoiceType;
	}
	
	public ProductGroups.QuestionDetail getQuestionDetail(int questionId){
		ProductGroups.QuestionDetail questDetail = 
				new ProductGroups.QuestionDetail();
		
		openDatabase();
		Cursor cursor = dbHelper.myDataBase.query(ANSWER_QUESTION_TEMP, ANSWER_QUESTION_ALL_COLUMN, 
				ANSWER_QUESTION_TEMP_COL_QUESTION_ID + "=" + questionId, 
				null, null, null, ANSWER_QUESTION_TEMP_COL_QUESTION_ID);
		if(cursor.moveToFirst()){
			questDetail.setQuestionID(cursor.getInt(cursor.getColumnIndex(ANSWER_QUESTION_TEMP_COL_QUESTION_ID)));
			questDetail.setQuestionName(cursor.getString(cursor.getColumnIndex(ANSWER_QUESTION_TEMP_COL_QUESTION_NAME)));
			questDetail.setQuestionOptId(cursor.getInt(cursor.getColumnIndex(ANSWER_QUESTION_TEMP_COL_QUESTION_OPTION_ID)));
			
			if(cursor.getString(cursor.getColumnIndex(ANSWER_QUESTION_TEMP_COL_QUESTION_ID)) != null){
				questDetail.setQuestionValue(cursor.getDouble(cursor.getColumnIndex(ANSWER_QUESTION_TEMP_COL_QUESTION_QTY)));
			}
		}
		cursor.close();
		closeDatabase();
		
		return questDetail;
	}
	
	public List<ProductGroups.QuestionAnswerData> listAnswerQuestion(){
		List<ProductGroups.QuestionAnswerData> answerQuestLst = 
				new ArrayList<ProductGroups.QuestionAnswerData>();
		
		openDatabase();
		Cursor cursor = dbHelper.myDataBase.query(ANSWER_QUESTION_TEMP, ANSWER_QUESTION_ALL_COLUMN, 
				null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				ProductGroups.QuestionAnswerData qsAns = 
						new ProductGroups.QuestionAnswerData();
				qsAns.setiQuestionID(cursor.getInt(cursor.getColumnIndex(ANSWER_QUESTION_TEMP_COL_QUESTION_ID)));
				qsAns.setiAnsOptionID(cursor.getInt(cursor.getColumnIndex(ANSWER_QUESTION_TEMP_COL_QUESTION_OPTION_ID)));
				qsAns.setfAnsValue(cursor.getDouble(cursor.getColumnIndex(ANSWER_QUESTION_TEMP_COL_QUESTION_QTY)));
				qsAns.setSzAnsText(cursor.getString(cursor.getColumnIndex(ANSWER_QUESTION_TEMP_COL_QUESTION_NAME)));
				
				answerQuestLst.add(qsAns);
			}while(cursor.moveToNext());
		}
		cursor.close();
		closeDatabase();
		
		return answerQuestLst;
	}
	
	public List<ProductGroups.AnswerOption> listAnswerOption(int questId){
		List<ProductGroups.AnswerOption> optLst = 
				new ArrayList<ProductGroups.AnswerOption>();
		
		openDatabase();
		Cursor cursor = dbHelper.myDataBase.query(TB_ANSWER_OPTION, ANSWWER_OPTION_ALL_COLUMN, 
				TB_ANSWER_OPTION_COL_QUESTION_ID + "=" + questId, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				ProductGroups.AnswerOption opt = 
						new ProductGroups.AnswerOption(cursor.getInt(cursor.getColumnIndex(TB_ANSWER_OPTION_COL_ANSWER_ID)), 
								cursor.getInt(cursor.getColumnIndex(TB_ANSWER_OPTION_COL_QUESTION_ID)), 
								cursor.getString(cursor.getColumnIndex(TB_ANSWER_OPTION_COL_ANSWER_NAME)));
				optLst.add(opt);
			}while(cursor.moveToNext());
		}
		cursor.close();
		closeDatabase();
		return optLst;
	}
	
	public List<ProductGroups.QuestionDetail> listQuestionDetail(){
		List<ProductGroups.QuestionDetail> questDetailLst = 
				new ArrayList<ProductGroups.QuestionDetail>();
		
		openDatabase();
		
		Cursor cursor = dbHelper.myDataBase.rawQuery("SELECT * FROM QuestionDetail " +
				" ORDER BY Ordering ", null);
		if(cursor.moveToFirst()){
			do{
				ProductGroups.QuestionDetail questDetail = 
						new ProductGroups.QuestionDetail();
				questDetail.setQuestionID(cursor.getInt(cursor.getColumnIndex("QuestionID")));
				questDetail.setQuestionName(cursor.getString(cursor.getColumnIndex("QuestionName")));
				questDetail.setQuestionGroupID(cursor.getInt(cursor.getColumnIndex("QuestionGroupID")));
				questDetail.setQuestionTypeID(cursor.getInt(cursor.getColumnIndex("QuestionTypeID")));
				questDetail.setIsRequired(cursor.getInt(cursor.getColumnIndex("IsRequired")));
				questDetail.setOrdering(cursor.getInt(cursor.getColumnIndex("Ordering")));
				questDetailLst.add(questDetail);
			}while(cursor.moveToNext());
		}
		cursor.close();
		
		closeDatabase();
		return questDetailLst;
	}
	
	public List<ProductGroups.QuestionDetail> listCurrentQuestionDetail(int questionTypeId){
		List<ProductGroups.QuestionDetail> questDetailLst = 
				new ArrayList<ProductGroups.QuestionDetail>();
		
		openDatabase();
		
		Cursor cursor = dbHelper.myDataBase.rawQuery("SELECT a.*, b.* FROM QuestionDetail a " +
				" LEFT JOIN " + ANSWER_QUESTION_TEMP + " b " +
				" ON a.QuestionID=b." + ANSWER_QUESTION_TEMP_COL_QUESTION_ID +
				" WHERE a.QuestionTypeID=" + questionTypeId +
				" ORDER BY a.Ordering ", null);
		if(cursor.moveToFirst()){
			do{
				ProductGroups.QuestionDetail questDetail = 
						new ProductGroups.QuestionDetail();
				questDetail.setQuestionID(cursor.getInt(cursor.getColumnIndex("QuestionID")));
				questDetail.setQuestionName(cursor.getString(cursor.getColumnIndex("QuestionName")));
				questDetail.setQuestionGroupID(cursor.getInt(cursor.getColumnIndex("QuestionGroupID")));
				questDetail.setQuestionTypeID(cursor.getInt(cursor.getColumnIndex("QuestionTypeID")));
				questDetail.setIsRequired(cursor.getInt(cursor.getColumnIndex("IsRequired")));
				questDetail.setOrdering(cursor.getInt(cursor.getColumnIndex("Ordering")));
				
				if(cursor.getString(cursor.getColumnIndex(ANSWER_QUESTION_TEMP_COL_QUESTION_ID)) != null){
					questDetail.setQuestionValue(cursor.getDouble(cursor.getColumnIndex(ANSWER_QUESTION_TEMP_COL_QUESTION_QTY)));
				}
				questDetailLst.add(questDetail);
			}while(cursor.moveToNext());
		}
		cursor.close();
		
		closeDatabase();
		return questDetailLst;
	}
	
	public List<ProductGroups.QuestionDetail> listCurrentQuestionDetail(){
		List<ProductGroups.QuestionDetail> questDetailLst = 
				new ArrayList<ProductGroups.QuestionDetail>();
		
		openDatabase();
		
		Cursor cursor = dbHelper.myDataBase.rawQuery("SELECT a.*, b.* FROM QuestionDetail a " +
				" LEFT JOIN " + ANSWER_QUESTION_TEMP + " b " +
				" ON a.QuestionID=b." + ANSWER_QUESTION_TEMP_COL_QUESTION_ID +
				" ORDER BY a.Ordering ", null);
		if(cursor.moveToFirst()){
			do{
				ProductGroups.QuestionDetail questDetail = 
						new ProductGroups.QuestionDetail();
				questDetail.setQuestionID(cursor.getInt(cursor.getColumnIndex("QuestionID")));
				questDetail.setQuestionName(cursor.getString(cursor.getColumnIndex("QuestionName")));
				questDetail.setQuestionGroupID(cursor.getInt(cursor.getColumnIndex("QuestionGroupID")));
				questDetail.setQuestionTypeID(cursor.getInt(cursor.getColumnIndex("QuestionTypeID")));
				questDetail.setIsRequired(cursor.getInt(cursor.getColumnIndex("IsRequired")));
				questDetail.setOrdering(cursor.getInt(cursor.getColumnIndex("Ordering")));
				
				if(cursor.getString(cursor.getColumnIndex(ANSWER_QUESTION_TEMP_COL_QUESTION_ID)) != null){
					questDetail.setQuestionValue(cursor.getDouble(cursor.getColumnIndex(ANSWER_QUESTION_TEMP_COL_QUESTION_QTY)));
				}
				questDetailLst.add(questDetail);
			}while(cursor.moveToNext());
		}
		cursor.close();
		
		closeDatabase();
		return questDetailLst;
	}
	
	public List<ProductGroups.QuestionGroup> listQuestionGroup(){
		List<ProductGroups.QuestionGroup> questGroupLst = 
				new ArrayList<ProductGroups.QuestionGroup>();
		
		openDatabase();
		
		Cursor cursor = dbHelper.myDataBase.rawQuery("SELECT * FROM QuestionGroup", null);
		if(cursor.moveToFirst()){
			do{
				ProductGroups.QuestionGroup questGroup = 
						new ProductGroups.QuestionGroup();
				questGroup.setQuestionGroupID(cursor.getInt(cursor.getColumnIndex("QuestionGroupID")));
				questGroup.setQuestionGroupName(cursor.getString(cursor.getColumnIndex("QuestionGroupName")));
				questGroup.setOrdering(cursor.getInt(cursor.getColumnIndex("Ordering")));
				
				questGroupLst.add(questGroup);
			}while(cursor.moveToNext());
		}
		cursor.close();
		
		closeDatabase();
		return questGroupLst;
	}
	
	public void insertQuestionDetail(ProductGroups pGroup){
		openDatabase();
		
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM QuestionDetail");
			for(ProductGroups.QuestionDetail questDetail : pGroup.getQuestionDetail()){
				ContentValues cv = new ContentValues();
				cv.put("QuestionID", questDetail.getQuestionID());
				cv.put("QuestionName", questDetail.getQuestionName());
				cv.put("QuestionGroupID", questDetail.getQuestionGroupID());
				cv.put("QuestionTypeID", questDetail.getQuestionTypeID());
				cv.put("IsRequired", questDetail.getIsRequired());
				cv.put("Ordering", questDetail.getOrdering());
				
				dbHelper.myDataBase.insert("QuestionDetail", null, cv);
			}
		} catch (SQLException e) {
			Log.appendLog(context, e.getMessage());
		}
		
		closeDatabase();
	}
	
	public void insertAnswerOption(ProductGroups pGroup){
		String sqlCreate = "CREATE TABLE " + TB_ANSWER_OPTION +
				" ( " + TB_ANSWER_OPTION_COL_ANSWER_ID + " INTEGER, " +
				TB_ANSWER_OPTION_COL_QUESTION_ID + " INTEGER, " + 
				TB_ANSWER_OPTION_COL_ANSWER_NAME + " TEXT );";
		
		String sqlDrop = "DROP TABLE IF EXISTS " + TB_ANSWER_OPTION;
		openDatabase();
		dbHelper.myDataBase.execSQL(sqlDrop);
		dbHelper.myDataBase.execSQL(sqlCreate);
		
		try {
			for(ProductGroups.AnswerOption opt : pGroup.getAnswerOption()){
				ContentValues cv = new ContentValues();
				cv.put(TB_ANSWER_OPTION_COL_ANSWER_ID, opt.getAnswerID());
				cv.put(TB_ANSWER_OPTION_COL_QUESTION_ID, opt.getQuestionID());
				cv.put(TB_ANSWER_OPTION_COL_ANSWER_NAME, opt.getAnswerName());
				
				dbHelper.myDataBase.insert(TB_ANSWER_OPTION, null, cv);
			}
		} catch (Exception e) {
			Log.appendLog(context, e.getMessage());
			e.printStackTrace();
		}
		
		closeDatabase();
	}
	
	public void insertQuestionGroups(ProductGroups pGroup){
		openDatabase();
		
		try {
			dbHelper.myDataBase.execSQL("DELETE FROM QuestionGroup");
			
			for(ProductGroups.QuestionGroup questGroup : pGroup.getQuestionGroup()){
				ContentValues cv = new ContentValues();
				cv.put("QuestionGroupID", questGroup.getQuestionGroupID());
				cv.put("QuestionGroupName", questGroup.getQuestionGroupName());
				cv.put("Ordering", questGroup.getOrdering());
				
				dbHelper.myDataBase.insert("QuestionGroup", null, cv);
			}
		} catch (SQLException e) {
			Log.appendLog(context, e.getMessage());
		}
		
		closeDatabase();
	}
}
