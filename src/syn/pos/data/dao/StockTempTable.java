package syn.pos.data.dao;

import android.content.Context;

public class StockTempTable {
	static DataBaseHelper dbHelper;
	public static void createMaterialData(Context context){
		createMaterialTmpTable(context);
		
		String strSql = " INSERT INTO materialtmp " +
				" SELECT a.ProductID, b.ProductCode, a.MenuName_0," +
				" b.ProductUnitName " +
				" FROM MenuItem a " +
				" LEFT JOIN Products b" +
				" ON a.ProductID=b.ProductID ";
		
		dbHelper = new DataBaseHelper(context);
		dbHelper.openDataBase();
		dbHelper.myDataBase.execSQL("BEGIN TRANSACTION");
		dbHelper.myDataBase.execSQL(strSql);
		dbHelper.myDataBase.execSQL("COMMIT");
		dbHelper.closeDataBase();
	}
	
	public static void createMaterialTmpTable(Context context){
		dbHelper = new DataBaseHelper(context);
		dbHelper.openDataBase();
		String strSql = "DROP TABLE IF EXISTS materialtmp";
		dbHelper.myDataBase.execSQL(strSql);
		
		strSql = " CREATE TABLE materialtmp ( " +
				" MaterialID int(11), " +
				" MaterialCode varchar(255), " +
				" MaterialName varchar(255), " +
				" ProductUnitName varchar(255) " +
				" )";
		dbHelper.myDataBase.execSQL(strSql);
		dbHelper.closeDataBase();
	}
	
	public static void createStockCountTmpTable(Context context){
		dbHelper = new DataBaseHelper(context);
		dbHelper.openDataBase();
		String strSql = "DROP TABLE IF EXISTS stockcounttmp; ";
		dbHelper.myDataBase.execSQL(strSql);
		
		strSql = "CREATE TABLE stockcounttmp (" +
				" DocumentID int(11), " +
				" ShopID int (11), " +
				" MaterialID int(11), " +
				" MovementAmount decimal(18,4) default 0.0000, " +
				" CountAmount decimal(18,4) default 0.0000" +
				" ); ";
		dbHelper.myDataBase.execSQL(strSql);
		dbHelper.closeDataBase();
	}
	
	
	public static void createBeginTmpTable(Context context){
		dbHelper = new DataBaseHelper(context);
		dbHelper.openDataBase();
		String strSql = "DROP TABLE IF EXISTS begintmp; ";
		dbHelper.myDataBase.execSQL(strSql);
		
		strSql = "CREATE TABLE begintmp (" +
				" DocumentID int(11), " +
				" ShopID int (11), " +
				" MaterialID int(11), " +
				" BeginAmount decimal(18,4)  default 0.0000" +
				" ); ";
		dbHelper.myDataBase.execSQL(strSql);
		dbHelper.closeDataBase();
	}
	
	public static void createSaleTmpTable(Context context){
		dbHelper = new DataBaseHelper(context);
		dbHelper.openDataBase();
		String strSql = "DROP TABLE IF EXISTS saletmp; ";
		dbHelper.myDataBase.execSQL(strSql);
		
		strSql = "CREATE TABLE saletmp (" +
				" DocumentID int(11), " +
				" ShopID int (11), " +
				" MaterialID int(11), " +
				" SaleAmount decimal(18,4) default 0.0000 " +
				" ); ";
		dbHelper.myDataBase.execSQL(strSql);
		dbHelper.closeDataBase();
	}
	
	public static void createReceiptTable(Context context){
		dbHelper = new DataBaseHelper(context);
		dbHelper.openDataBase();
		String strSql = "DROP TABLE IF EXISTS receipttmp; ";
		dbHelper.myDataBase.execSQL(strSql);
		
		strSql = "CREATE TABLE receipttmp (" +
				" DocumentID int(11), " +
				" ShopID int (11), " +
				" MaterialID int(11), " +
				" ReceiptAmount decimal(18,4) default 0.0000 " +
				" ); ";
		dbHelper.myDataBase.execSQL(strSql);
		dbHelper.closeDataBase();
	}
	
	public static void createVarianceTable(Context context){
		dbHelper = new DataBaseHelper(context);
		dbHelper.openDataBase();
		String strSql = "DROP TABLE IF EXISTS variancetmp; ";
		dbHelper.myDataBase.execSQL(strSql);
		
		strSql = "CREATE TABLE variancetmp (" +
				" DocumentID int(11), " +
				" ShopID int (11), " +
				" MaterialID int(11), " +
				" VarianceAmount decimal(18,4) default 0.0000" +
				" ); ";
		dbHelper.myDataBase.execSQL(strSql);
		dbHelper.closeDataBase();
	}
}
