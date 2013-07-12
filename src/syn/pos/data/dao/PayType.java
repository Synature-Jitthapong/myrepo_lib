package syn.pos.data.dao;

import android.content.ContentValues;
import android.content.Context;
import syn.pos.data.model.Payment;

public class PayType {

	private DataBaseHelper dbHelper;
	public PayType(Context context){
		dbHelper = new DataBaseHelper(context);
	}
	
	public long insertPayType(Payment pm){
		dbHelper.openDataBase();

		try {
			dbHelper.myDataBase.execSQL("BEGIN");
			dbHelper.myDataBase.execSQL("DELETE FROM PayType");
			ContentValues cv;
			for(Payment.PayType pt : pm.getPayType()){
				cv = new ContentValues();
				
				cv.put("PayTypeID", pt.getPayTypeID());
				cv.put("PayTypeCode", pt.getPayTypeCode());
				cv.put("PayTypeName", pt.getPayTypeName());
				
				try {
					dbHelper.myDataBase.insert("PayType", null, cv);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			

			dbHelper.myDataBase.execSQL("DELETE FROM PaymentTypeButton");
			for(Payment.PaymentAmountButton pab : pm.getPaymentAmountButton()){
				cv = new ContentValues();
				
				cv.put("PaymentAmountID", pab.getPaymentAmountID());
				cv.put("PaymentAmount", pab.getPaymentAmountID());
				
				try {
					dbHelper.myDataBase.insert("PaymentAmountButton", null, cv);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			dbHelper.myDataBase.execSQL("COMMIT");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dbHelper.myDataBase.execSQL("ROLLBACK");
		}
		
		dbHelper.closeDataBase();
		return 0;
	}
}
