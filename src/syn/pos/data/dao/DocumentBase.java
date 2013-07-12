package syn.pos.data.dao;

import java.util.Calendar;
import java.util.List;

import syn.pos.data.model.DocDetail;
import syn.pos.data.model.DocDetailData;
import syn.pos.data.model.Document;
import syn.pos.data.model.DocumentParam;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public abstract class DocumentBase{
	private DataBaseHelper dbHelper;
	protected GlobalVar globalVar;
	
	public DocumentBase(Context context) {
		dbHelper = new DataBaseHelper(context);
		globalVar = new GlobalVar(context, dbHelper);
	}

	public DocumentParam chkWorkingDocument(int documentTypeId, int shopId){
		DocumentParam docParam = new DocumentParam();
		
		String strSql = " SELECT DocumentID, UpdateBy " +
				" From Document " +
				" WHERE " +
				" DocumentTypeID=" + documentTypeId +
				" AND ShopID=" + shopId +
				" AND DocumentDate='" + globalVar.dateFormat.format(globalVar.date) + "' " +
				" AND DocumentStatus=1";
		
		dbHelper.openDataBase();
		
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		
		if(cursor.moveToFirst()){
			docParam.setDocumentId(cursor.getInt(0));
			docParam.setStaffId(cursor.getInt(1));
		}
		cursor.close();
		
		dbHelper.closeDataBase();
		
		return docParam;
	}
	
	public void addMaxDocumentId(int shopId, int documentId){
		dbHelper.myDataBase.execSQL(" DELETE FROM maxdocumentid WHERE " +
				" ShopID=" + shopId);
		dbHelper.myDataBase.execSQL(" INSERT INTO maxdocumentid VALUES " +
				" (" + shopId + ", 0, " + documentId + ")");
	}
	
	public int getMaxDocumentId(int shopId, int docType){
		int documentId = 0;
		String strSql = " SELECT MaxDocumentID FROM " +
				" maxdocumentid WHERE ShopID=" + shopId;
		
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			documentId = cursor.getInt(0);
		}
		cursor.close();
		return documentId + 1;
	}
	
	public int getMaxDocumentDetailId(int documentId, int shopId){
		int documentDetailId = 0;
		String strSql = " SELECT MAX(DocDetailID) FROM " +
				" DocDetail WHERE DocumentID=" + documentId + 
				" AND ShopID=" + shopId;
		
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			documentDetailId = cursor.getInt(0);
			
		}
		cursor.close();
		return documentDetailId + 1;
	}
	
	public void addMaxDocumentNumber(int shopId, int documentTypeId, int year, int month, int documentNumber){
		dbHelper.myDataBase.execSQL("DELETE FROM maxdocumentnumber WHERE " +
				" ShopID=" + shopId + " AND DocType=" + documentTypeId +
				" AND DocumentYear=" + year + " AND DocumentMonth=" + month);
		
		dbHelper.myDataBase.execSQL("INSERT INTO maxdocumentnumber VALUES " +
				" (" + shopId + ", " + documentTypeId + ", 0, " + year + ", " + month + ", " + documentNumber + ")");
	}
	
	public int getMaxDocumentNumber(int shopId, int documentTypeId, int year, int month, int day){
		// sale document use day
		if(documentTypeId == 20)
			return day;
		
		int documentNumber = 0;
		String strSql = " SELECT MaxDocumentNumber FROM " +
				" maxdocumentnumber WHERE ShopID=" + shopId +
				" AND DocType=" + documentTypeId + 
				" AND DocumentYear=" + year + 
				" AND DocumentMonth=" + month;
		
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			documentNumber = cursor.getInt(0);
			
		}
		cursor.close();
		return documentNumber + 1;
	}
	
	// create stock document 
	public DocumentParam createDocument(Document document){
		String documentDate	= globalVar.dateFormat.format(globalVar.date);
		String updateDate	= globalVar.dateTimeFormat.format(globalVar.date);
			
		document.setDocumentDate(documentDate);
		document.setUpdateDate(updateDate);
		
		if(document.getDocumentId() != 0){
			updateDocument(document);
		}
		else{
			
			dbHelper.openDataBase();
			document.setDocumentId(getMaxDocumentId(document.getShopId(), document.getDocumentTypeId()));
			
			ContentValues values = new ContentValues();
			values.put("DocumentID", document.getDocumentId());
			values.put("ShopID", document.getShopId());
			values.put("DocumentTypeID", document.getDocumentTypeId());
			values.put("DocumentDate", documentDate);
			values.put("UpdateBy", document.getUpdateBy());
			values.put("UpdateDate", updateDate);
			values.put("DocumentStatus", document.getDocumentStatus());
			values.put("Remark", document.getRemark());
			values.put("DocumentNo", "1");
			values.put("IsSendToHQ", 0);
			values.put("SendToHQDateTime", "");
			dbHelper.myDataBase.insert("Document", null, values);
			
			// add max documentid when insert document
			addMaxDocumentId(document.getShopId(), document.getDocumentId());
			dbHelper.closeDataBase();
		}
		DocumentParam docParam = new DocumentParam(document.getDocumentId(), 
				document.getShopId(), document.getUpdateBy(),
				documentDate, "", document.getDocumentStatus());
		return docParam;
	}
	
	// update document
	public DocumentParam updateDocument(Document document){
		dbHelper.openDataBase();
		
		int year 				= globalVar.calendar.get(Calendar.YEAR);
		int month 				= globalVar.calendar.get(Calendar.MONTH);
		int day 				= globalVar.calendar.get(Calendar.DAY_OF_MONTH);
		
		int documentNo 			= getMaxDocumentNumber(document.getShopId(), 
									document.getDocumentTypeId(), year, month, day);
		
		String strSql = " UPDATE Document SET " +
				" DocumentStatus=" + document.getDocumentStatus() + ", " +
				" UpdateBy=" + document.getUpdateBy() + ", " + 
				" UpdateDate='" + globalVar.dateTimeFormat.format(globalVar.date) + "', " +
				" DocumentNo='" + documentNo + "', " +
				" Remark='" + document.getRemark() + "'" +
				" WHERE DocumentID=" + document.getDocumentId() +
				" AND ShopID=" + document.getShopId();
		
		dbHelper.myDataBase.execSQL(strSql);
		
		if(document.getDocumentStatus() == 2){
			// add max documentnumber if approved document
			addMaxDocumentNumber(document.getShopId(), document.getDocumentTypeId(), year, month, documentNo);
		}
		
		dbHelper.closeDataBase();
		
		return new DocumentParam(document.getDocumentId(), 
				document.getShopId(), document.getUpdateBy(),
				document.getDocumentDate(), "", document.getDocumentStatus());
	}
	
	// create document detail
	public void createDocumentDetail(DocDetailData docDetailData){
		dbHelper.openDataBase();
		
		if(docDetailData.getDocDetailLst().size() > 0){
			if(docDetailData.getDocType() != 20)
				dbHelper.myDataBase.execSQL("DELETE FROM DocDetail WHERE DocumentID=" + docDetailData.getDocumentId() +
						" AND ShopID=" + docDetailData.getShopId());
			
			for(DocDetail docDetail : docDetailData.getDocDetailLst()){
				ContentValues values = new ContentValues();
			
				values.put("DocDetailID", getMaxDocumentDetailId(docDetailData.getDocumentId(), 
						docDetailData.getShopId()));
				values.put("DocumentID", docDetailData.getDocumentId());
				values.put("ShopID", docDetailData.getShopId());
				values.put("MaterialID", docDetail.getMaterialId());
				values.put("UnitName", docDetail.getUnitName());
				values.put("MaterialQty", docDetail.getMaterialQty());
				values.put("MaterialPricePerUnit", docDetail.getMaterialPricePerUnit());
				values.put("MaterialNetPrice", docDetail.getMaterialNetPrice());
				values.put("MaterialTaxType", docDetail.getMaterialTaxType());
				values.put("MaterialTaxPrice", docDetail.getMaterialTaxPrice());
						
				dbHelper.myDataBase.insert("DocDetail", null, values);
			}
		}
		dbHelper.closeDataBase();
	}
}
