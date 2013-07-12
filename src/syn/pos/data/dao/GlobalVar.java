package syn.pos.data.dao;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import syn.pos.data.model.ShopData;
import android.content.Context;

public class GlobalVar {
	public SimpleDateFormat dateFormat;
	public SimpleDateFormat dateTimeFormat;
	public Date date;
	public Calendar calendar;
	public DecimalFormat decFormat;
	public Currency currency;

	public ShopData.ShopProperty shopProper;
	public ShopData.GlobalProperty globalProp;

	public GlobalVar(Context context, DataBaseHelper db) {
		ShopProperty sp = new ShopProperty(context, db);
		GlobalProperty gb = new GlobalProperty(context, db);

		shopProper = sp.getShopProper();
		globalProp = gb.getGlobalProperty();

		date = new Date();
		calendar = Calendar.getInstance();

		String globalDateFormat = "dd MMMM yyyy";
		if (globalProp.getDateFormat() != ""
				&& globalProp.getDateFormat() != null)
			globalDateFormat = globalProp.getDateFormat();

		dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		dateTimeFormat = new SimpleDateFormat(globalDateFormat + " HH:mm:ss",
				Locale.getDefault());

		String currencyFormat = "#,##0.00";
		if (globalProp.getCurrencyFormat() != ""
				&& globalProp.getCurrencyFormat() != null)
			currencyFormat = globalProp.getCurrencyFormat();

		decFormat = new DecimalFormat();
		decFormat.applyPattern(currencyFormat);

		String currencyCodeISO4217 = "THB";
		// if(globalProp.getCurrencyCode() == "THB")
		// currencyCodeISO4217 = "THB";

		currency = Currency.getInstance(currencyCodeISO4217);

	}
	
}
