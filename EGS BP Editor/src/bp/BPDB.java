package bp;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class BPDB {
	DB db;
	HTreeMap<Integer, String> BPType;
	
	public BPDB() {
		db = DBMaker.fileDB("epbEditor.db").closeOnJvmShutdown().make();
		BPType = db.hashMap("ShipType", Serializer.INTEGER, Serializer.STRING).createOrOpen();
	}
	
	public void setType(Integer id, String name) {
		BPType.put(id, name);
		db.commit();
	}
	
	public String[] getTypes() {
		return BPType.getKeys().toArray(new String[0]);
	}
	
	public String getTypeByID(Integer id) {
		if(BPType.containsKey(id)) {
			return BPType.get(id);
		} else {
			return "Unknown";
		}
	}
	public void close() {
		db.close();
	}
}
