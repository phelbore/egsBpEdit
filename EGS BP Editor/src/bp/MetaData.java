package bp;

import java.util.HashMap;

public class MetaData {
	private int blockCount;
	private int blockRecordCount;
	private HashMap<Integer, BlockCountRecord> blockRecords;
	public int getBlockCount() {
		return blockCount;
	}
	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}
	public int getBlockRecordCount() {
		return blockRecordCount;
	}
	public void setBlockRecordCount(int blockRecordCount) {
		this.blockRecordCount = blockRecordCount;
	}
	public HashMap<Integer, BlockCountRecord> getBlockRecords() {
		return blockRecords;
	}
	public void setBlockRecords(HashMap<Integer, BlockCountRecord> blockRecords) {
		this.blockRecords = blockRecords;
	}
	
}
