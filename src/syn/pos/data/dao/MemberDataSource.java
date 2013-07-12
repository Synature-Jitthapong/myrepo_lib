package syn.pos.data.dao;

import java.util.ArrayList;
import java.util.List;

import syn.pos.data.model.Member;
import syn.pos.data.model.MemberGroup;
import syn.pos.data.model.Promotion;
import syn.pos.data.model.PromotionDiscount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class MemberDataSource{

	private DataBaseHelper dbHelper;
	private GlobalVar globalVar;
	
	private static final String TB_NAME = "members";
	
	public MemberDataSource(Context context) {
		dbHelper = new DataBaseHelper(context);
		globalVar = new GlobalVar(context, dbHelper);
	}
	
	// add
	public int getMaxMemberId(){
		int memberId = 0;
		String strSql = "SELECT MAX(MemberID) " +
				" FROM members ";
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		
		if(cursor.moveToFirst()){
			memberId = cursor.getInt(0);
		}

		cursor.close();
		return memberId + 1;
	}
	
	public void addNewMember(syn.pos.data.model.Member member){
		dbHelper.openDataBase();
		
		ContentValues cv = new ContentValues();
		cv.put("MemberID", getMaxMemberId());
		cv.put("MemberGroupID", member.getMemberGroupId());
		cv.put("MemberPassword", member.getMemberPassword());
		cv.put("MemberCode", member.getMemberCode());
		cv.put("MemberGender", member.getMemberGender());
		cv.put("MemberFirstName", member.getMemberFirstName());
		cv.put("MemberLastName", member.getMemberLastName());
		cv.put("MemberAddress1", member.getMemberAddress1());
		cv.put("MemberAddress2", member.getMemberAddress2());
		cv.put("MemberCity", member.getMemberCity());
		cv.put("MemberProvince", member.getMemberProvince());
		cv.put("MemberZipCode", member.getMemberZipCode());
		cv.put("MemberTelephone", member.getMemberTelephone());
		cv.put("MemberMobile", member.getMemberMobile());
		cv.put("MemberFax", member.getMemberFax());
		cv.put("MemberEmail", member.getMemberEmail());
		cv.put("MemberBirthDay", member.getMemberBirthDay());
		cv.put("MemberAdditional", member.getMemberAdditional());
		cv.put("MemberIDNumber", member.getMemberIDNumber());
		cv.put("MemberIDIssueDate", member.getMemberIDIssueDate());
		cv.put("MemberIDExpiration", member.getMemberIDExpireDate());
		cv.put("MemberBlood", member.getMemberBlood());
		cv.put("MemberPictureFileServer", member.getMemberPictureFileServer());
		cv.put("MemberPictureFileClient", member.getMemberPictureFileClient());
		cv.put("Activated", member.getActivate());
		cv.put("MemberExpiration", member.getMemberExpiration());
		cv.put("Deleted", member.getDeleted());
		cv.put("LangID", member.getLangId());
		cv.put("InputDate", globalVar.dateFormat.format(globalVar.date));
		cv.put("InputBy", member.getInputBy());
		cv.put("UpdateDate", globalVar.dateTimeFormat.format(globalVar.date));
		cv.put("UpdateBy", member.getUpdateBy());
		cv.put("LastUseDate", member.getLastUseDate());
		cv.put("ImageFolder", member.getImageFolder());
		cv.put("MemberMainPrice", member.getMemberMainPrice());
		cv.put("InsertAtProductLevelID", member.getInsertAtProductLevelID());
		cv.put("AlreadyExportToHQ", member.getAlreadyExportToHQ());
		cv.put("IsCreateCard", member.getIsCreateCard());
		cv.put("MemberDepartmentID", member.getMemberDepartmentId());
		
		
		dbHelper.myDataBase.insert(TB_NAME, null, cv);
		
		dbHelper.closeDataBase();
	}
	
	// update 
	public void updateMember(syn.pos.data.model.Member member){
		dbHelper.openDataBase();
		String strSql = "UPDATE " + TB_NAME + 
				" SET ";
		dbHelper.closeDataBase();
	}
	
	// delete
	public void deleteMember(int memberId){
		
	}
	
	// get member discount value
	public Promotion getPromotionMember(int memberGroupId){
		Promotion promotion = new Promotion();
		String strSql = " SELECT a.PriceGroupID, b.PercentDiscount, b.DiscountAmount " +
				" FROM promotionlink a " +
				" INNER JOIN promotionpricegroup b " +
				" on a.PriceGroupID=b.PriceGroupID " +
				" WHERE a.LinkID=" + memberGroupId + 
				" AND b.TypeID=1 AND b.Activated=1 ";
		dbHelper.openDataBase();
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			promotion.setPriceGroupId(cursor.getInt(0));
			promotion.setPercentDiscount(cursor.getDouble(1));
			promotion.setDiscontAmount(cursor.getDouble(2));
		}
		cursor.close();
		dbHelper.closeDataBase();
		return promotion;
	}
	
	// get each member
	public Member getMember(String memberCode, String memberName, String memberTel){
		Member member = new Member();
		
		dbHelper.openDataBase();
		
		String strSql = " SELECT MemberID, " +
				" MemberGroupID, MemberCode, MemberGender, " +
				" MemberFirstName, MemberLastName, " +
				" MemberAddress1, MemberAddress2, " +
				" MemberCity, MemberProvince, MemberZipCode, " +
				" MemberTelephone, MemberMobile, MemberFax, " +
				" MemberEmail, MemberBirthDay, MemberExpiration " + 
				" FROM members ";
		if(memberName != "")
			strSql += " WHERE (MemberFirstName = '" + memberName + "'" +
					" OR MemberLastName = '" + memberName + "')";
		else if(memberTel != "")
			strSql += " WHERE MemberTelephone = '" + memberTel + "'";
		else 
			strSql += " WHERE MemberCode = '" + memberCode + "'";
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		
		if(cursor.moveToFirst()){
			member.setMemberId(cursor.getInt(0));
			member.setMemberGroupId(cursor.getInt(1));
			member.setMemberCode(cursor.getString(2));
			member.setMemberGender(cursor.getInt(3));
			member.setMemberFirstName(cursor.getString(4));
			member.setMemberLastName(cursor.getString(5));
			member.setMemberAddress1(cursor.getString(6));
			member.setMemberAddress2(cursor.getString(7));
			member.setMemberCity(cursor.getString(8));
			member.setMemberProvince(cursor.getInt(9));
			member.setMemberZipCode(cursor.getString(10));
			member.setMemberTelephone(cursor.getString(11));
			member.setMemberMobile(cursor.getString(12));
			member.setMemberFax(cursor.getString(13));
			member.setMemberEmail(cursor.getString(14));
			member.setMemberBirthDay(cursor.getString(15));
			member.setMemberExpiration(cursor.getString(16));
		}

		cursor.close();
		dbHelper.closeDataBase();
		
		return member;
	}
	
	// get membergroup
	public List<MemberGroup> listMemberGroup(){
		List<MemberGroup> mgl = new ArrayList<MemberGroup>();
		dbHelper.openDataBase();
		String strSql = "SELECT MemberGroupID, " +
				" MemberGroupCode, MemberGroupName FROM MemberGroup";
		Cursor cursor = dbHelper.myDataBase.rawQuery(strSql, null);
		if(cursor.moveToFirst()){
			do{
				MemberGroup mg = new MemberGroup();
				mg.setMemberGroupId(cursor.getInt(0));
				mg.setMemberGroupCode(cursor.getString(1));
				mg.setMemberGroupName(cursor.getString(2));
				
				mgl.add(mg);
			}while(cursor.moveToNext());
		}
		cursor.close();
		dbHelper.closeDataBase();
		return mgl;
	}
	
}
