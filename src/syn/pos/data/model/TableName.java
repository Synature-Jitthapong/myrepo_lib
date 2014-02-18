package syn.pos.data.model;

import java.util.ArrayList;
import java.util.List;

public class TableName {
	public List<TableZone> TableZone = new ArrayList<TableZone>();
	public List<TableNameOld> TableName = new ArrayList<TableNameOld>();
	
	public static class TableNameOld{
		private int TableID;
		private int ZoneID;
		private String TableName;
		private int Capacity;
		private int STATUS;
		private int Ordering;
		
		public int getTableID() {
			return TableID;
		}
		public void setTableID(int tableID) {
			TableID = tableID;
		}
		public int getZoneID() {
			return ZoneID;
		}
		public void setZoneID(int zoneID) {
			ZoneID = zoneID;
		}
		public String getTableName() {
			return TableName;
		}
		public void setTableName(String tableName) {
			TableName = tableName;
		}
		public int getCapacity() {
			return Capacity;
		}
		public void setCapacity(int capacity) {
			Capacity = capacity;
		}
		public int getSTATUS() {
			return STATUS;
		}
		public void setSTATUS(int sTATUS) {
			STATUS = sTATUS;
		}
		public int getOrdering() {
			return Ordering;
		}
		public void setOrdering(int ordering) {
			Ordering = ordering;
		}
	}
	
	public static class TableZone{
		private int ZoneID;
		private String ZoneName;
		
		public int getZoneID() {
			return ZoneID;
		}
		public void setZoneID(int zoneID) {
			ZoneID = zoneID;
		}
		public String getZoneName() {
			return ZoneName;
		}
		public void setZoneName(String zoneName) {
			ZoneName = zoneName;
		}
		@Override
		public String toString() {
			return ZoneName;
		}
	}
}
