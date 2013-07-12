package syn.pos.data.dao;

import syn.pos.data.model.Document;
import syn.pos.data.model.DocumentParam;
import android.content.Context;

public class DocumentDataSource extends DocumentBase {
	private int documentId;
	private int shopId;
	private int staffId;
	
	// document object
	private Document doc;
	
	public DocumentDataSource(Context context, int documentId, int shopId, int staffId) {
		super(context);
		this.documentId = documentId;
		this.shopId 	= shopId;
		this.staffId	= staffId;
		
		doc = new Document();
		doc.setDocumentId(this.documentId);
		doc.setShopId(this.shopId);
		doc.setUpdateBy(this.staffId);
		
	}
	
	public DocumentParam createVoidDocument(){
		doc.setDocumentTypeId(DocumentType.VOIDDOCUMENT);
		doc.setDocumentStatus(DocumentStatus.COMPLETE_DOCUMENT);
		return super.createDocument(doc);
	}
	
	public DocumentParam approveVoidDocument(){
		doc.setDocumentTypeId(DocumentType.VOIDDOCUMENT);
		doc.setDocumentStatus(DocumentStatus.COMPLETE_DOCUMENT);
		return super.updateDocument(doc);
	}
	
	public DocumentParam createSaleDocument(){
		doc.setDocumentTypeId(DocumentType.SALEDOCUMENT);
		doc.setDocumentStatus(DocumentStatus.COMPLETE_DOCUMENT);
		return super.createDocument(doc);
	}
	
	public DocumentParam approveSaleDocument(){
		doc.setDocumentTypeId(DocumentType.SALEDOCUMENT);
		doc.setDocumentStatus(DocumentStatus.COMPLETE_DOCUMENT);
		return super.updateDocument(doc);
	}
	
	public DocumentParam approveReceiptDocument(){
		doc.setDocumentTypeId(DocumentType.RECEIPTSTOCK);
		doc.setDocumentStatus(DocumentStatus.COMPLETE_DOCUMENT);
		return super.updateDocument(doc);
	}
	
	public DocumentParam saveReceiptDocument(){
		doc.setDocumentTypeId(DocumentType.RECEIPTSTOCK);
		doc.setDocumentStatus(DocumentStatus.WORKING_DOCUMENT);
		return super.updateDocument(doc);
	}
	
	public DocumentParam createReceiptDocument(){
		doc.setDocumentTypeId(DocumentType.RECEIPTSTOCK);
		doc.setDocumentStatus(DocumentStatus.WORKING_DOCUMENT);
		return super.createDocument(doc);
	}
	
	public DocumentParam approveStockCountDocument(){
		doc.setDocumentTypeId(DocumentType.DAILYSTOCK);
		doc.setDocumentStatus(DocumentStatus.COMPLETE_DOCUMENT);
		return super.updateDocument(doc);
	}
	
	public DocumentParam saveStockCountDocument(){
		doc.setDocumentTypeId(DocumentType.DAILYSTOCK);
		doc.setDocumentStatus(DocumentStatus.WORKING_DOCUMENT);
		return super.updateDocument(doc);
	}
	
	public DocumentParam createStockCountDocument(){
		doc.setDocumentTypeId(DocumentType.DAILYSTOCK);
		doc.setDocumentStatus(DocumentStatus.WORKING_DOCUMENT);
		return super.createDocument(doc);
	}
	
	public DocumentParam createAddFromStockCountDocument(){
		doc.setDocumentTypeId(DocumentType.ADD_FROM_DAILYSTOCK);
		doc.setDocumentStatus(DocumentStatus.WORKING_DOCUMENT);
		return super.createDocument(doc);
	}
	
	public DocumentParam approveAddFromStockCountDocument(){
		doc.setDocumentTypeId(DocumentType.ADD_FROM_DAILYSTOCK);
		doc.setDocumentStatus(DocumentStatus.COMPLETE_DOCUMENT);
		return super.updateDocument(doc);
	}
	
	public DocumentParam createReduceFromStockCountDocument(){
		doc.setDocumentTypeId(DocumentType.REDUCE_FROM_DAILYSTOCK);
		doc.setDocumentStatus(DocumentStatus.WORKING_DOCUMENT);
		return super.createDocument(doc);
	}
	
	public DocumentParam approveReduceFromStockCountDocument(){
		doc.setDocumentTypeId(DocumentType.REDUCE_FROM_DAILYSTOCK);
		doc.setDocumentStatus(DocumentStatus.COMPLETE_DOCUMENT);
		return super.updateDocument(doc);
	}
}
