package syn.pos.data.json;
import java.lang.reflect.Type;
import java.util.List;

import syn.pos.data.model.AppConfigModel;
import syn.pos.data.model.DocDetailData;
import syn.pos.data.model.DocumentParam;
import syn.pos.data.model.GlobalParamFromJSON;
import syn.pos.data.model.MenuGroups;
import syn.pos.data.model.Payment;
import syn.pos.data.model.ProductGroups;
import syn.pos.data.model.ShopData;
import syn.pos.data.model.SyncDataLogModel;
import syn.pos.data.model.TableInfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonDeserialze{
	Gson gson;
	public GsonDeserialze(){
		gson = new Gson();
	}
	
	public syn.pos.data.model.WebServiceResult.WebServiceVersion deserializeWebServiceVersion(String json){
		Type type = new TypeToken<syn.pos.data.model.WebServiceResult.WebServiceVersion>() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public syn.pos.data.model.QueueDisplayInfo deserializeQueueDisplayInfoJSON(String json){
		Type type = new TypeToken<syn.pos.data.model.QueueDisplayInfo>() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public List<syn.pos.data.model.POSData_OrderTransInfo.POSData_OrderItemInfo> 
		deserializeOrderTransInfoJSON(String json){
		Type type = new TypeToken<List<syn.pos.data.model.POSData_OrderTransInfo.POSData_OrderItemInfo>>() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public syn.pos.data.model.Members deserializeMembersInfoJSON(String json){
		Type type = new TypeToken<syn.pos.data.model.Members>() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public List<syn.pos.data.model.KdsOrderInfo> deserializeKdsInfoJSON(String json){
		Type type = new TypeToken<List<syn.pos.data.model.KdsOrderInfo>>() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public syn.pos.data.model.QueueInfo deserializeQueueInfoJSON(String json){
		Type type = new TypeToken<syn.pos.data.model.QueueInfo>() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public List<syn.pos.data.model.QueueInfo> deserializeQueueInfoListJSON(String json){
		Type type = new TypeToken<List<syn.pos.data.model.QueueInfo>>() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public syn.pos.data.model.SummaryTransaction deserializeSummaryTransactionJSON(String json){
		Type type = new TypeToken<syn.pos.data.model.SummaryTransaction>() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public syn.pos.data.model.ReasonGroups deserializeReasonGroupJSON(String json){
		Type type = new TypeToken<syn.pos.data.model.ReasonGroups>() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public syn.pos.data.model.WebServiceResult deserializeWsResultJSON(String json){
		Type type = new TypeToken<syn.pos.data.model.WebServiceResult >() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public syn.pos.data.model.Promotion deserializePromotionJSON(String json){
		Type type = new TypeToken<syn.pos.data.model.Promotion >() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public syn.pos.data.model.OrderSendData deserializeOrderSendDataJSON(String json){
		Type type = new TypeToken<syn.pos.data.model.OrderSendData>() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public DocDetailData deserializeDocDetailJSON(String json){
		Type type = new TypeToken<DocDetailData>() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public DocumentParam deserializeDocumentParamJSON(String json){
		Type type = new TypeToken<DocumentParam>() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public syn.pos.data.model.MemberGroup deserializeMemberGroupJSON(String json){
		Type type = new TypeToken<syn.pos.data.model.MemberGroup >() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public syn.pos.data.model.Member deserializeMemberJSON(String json){
		Type type = new TypeToken<syn.pos.data.model.Member >() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public GlobalParamFromJSON deserializeGlobalParamJSON(String json){
		Type type = new TypeToken<GlobalParamFromJSON>() {}.getType();
		return gson.fromJson(json, type);
	}
	
	public SyncDataLogModel deserializeSyncDataLogJSON(String json){
		Type type = new TypeToken<SyncDataLogModel>() {}.getType();
		SyncDataLogModel sync = gson.fromJson(json, type);
		return sync;
	}
	
	public MenuGroups deserializeMenuGroupsJSON(String json){
		Type type = new TypeToken<MenuGroups>() {}.getType();
		MenuGroups mg = gson.fromJson(json, type);
		return mg;
	}
	
	public MenuGroups.MenuGroup deserializeMenuGroupJSON(String json){
		Type type = new TypeToken<MenuGroups.MenuGroup>() {}.getType();
		MenuGroups.MenuGroup mg = gson.fromJson(json, type);
		return mg;
	}
	
	public MenuGroups.MenuDept deserializeMenuDeptJSON(String json){
		Type type = new TypeToken<MenuGroups.MenuDept>() {}.getType();
		MenuGroups.MenuDept md = gson.fromJson(json, type);
		return md;
	}
	
	public MenuGroups.MenuItem deserializeMenuItemJSON(String json) {
		Type type = new TypeToken<MenuGroups.MenuItem>() {}.getType();
		MenuGroups.MenuItem menuItem = gson.fromJson(json, type);
		return menuItem;
	}
	
	public Payment deserializePayTypeJSON(String json){
		Type type = new TypeToken<Payment>(){}.getType();
		Payment payment = gson.fromJson(json, type);
		return payment;
	}
	
	public ProductGroups.Products deserializeProductJSON(String json){
		Type type = new TypeToken<ProductGroups.Products>(){}.getType();
		ProductGroups.Products productData = gson.fromJson(json, type);
		return productData;
	}
	
	public ProductGroups deserializeProductGroupJSON(String json){
		Type type = new TypeToken<ProductGroups>(){}.getType();
		ProductGroups productGroupData = gson.fromJson(json, type);
		return productGroupData;
	}
	
	public ShopData deserializeShopDataJSON(String json){
		Type type = new TypeToken<ShopData>(){}.getType();
		ShopData shopData = gson.fromJson(json, type);
		return shopData;
	}
	
	public ShopData.Staff deserializeStaffJSON(String json){
		Type type = new TypeToken<ShopData.Staff>(){}.getType();
		ShopData.Staff staff = gson.fromJson(json, type);
		return staff;
	}
	
	public TableInfo deserializeTableInfoJSON(String json) {
		Type type = new TypeToken<TableInfo>() {}.getType();
		TableInfo tableInfo = gson.fromJson(json, type);
		return tableInfo;
	}
	
	public AppConfigModel deserializeAppConfigJSON(String json) {
		Type type = new TypeToken<AppConfigModel>() {}.getType();
		AppConfigModel config = gson.fromJson(json, type);
		return config;
	}
}
