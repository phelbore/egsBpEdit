package bp;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.HashMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class MetaData {
	private static DB metaDB = DBMaker.fileDB("meta.db").make();
	private HTreeMap<String, Integer> type = metaDB.hashMap("type", Serializer.STRING, Serializer.INTEGER).create();
	private int blockCount;
	private int blockRecordCount;
	private HashMap<Integer, BlockMetaRecord> blockRecords = new HashMap<Integer, BlockMetaRecord>();
	private byte[] originalInput;

	private final int blockCountOffset = 132;
	private final int blockRecordCountOffset = 136;
	
	public void populate(byte[] metaBuf) {
		setBlockCount(twoByte(metaBuf, blockCountOffset));
		setBlockRecordCount(twoByte(metaBuf, blockRecordCountOffset));
		int blockType;
		for(int i=0; i<getBlockRecordCount(); i++) {
			BlockMetaRecord temp = new BlockMetaRecord();

			int bufLoc = blockRecordCountOffset+2+(i*8);
			blockType = Byte.toUnsignedInt(metaBuf[bufLoc]);
			blockRecords.put(1, new BlockMetaRecord());
			temp.setBlockType(blockType);
			temp.setBlockCount(twoByte(metaBuf,bufLoc+4));
			System.out.println(temp.getBlockType() + " = " + temp.getBlockCount());
		}
		originalInput = Arrays.copyOf(metaBuf, metaBuf.length);
	}
	
	public int twoByte(byte[] buffer, int location) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(2);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.put(buffer[location]);
		byteBuffer.put(buffer[location+1]);
		byteBuffer.flip();
		return byteBuffer.getShort();
	}
	
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
	public HashMap<Integer, BlockMetaRecord> getBlockRecords() {
		return blockRecords;
	}
	public void setBlockRecords(HashMap<Integer, BlockMetaRecord> blockRecords) {
		this.blockRecords = blockRecords;
	}

	public byte[] getOriginalInput() {
		return originalInput;
	}

	public HTreeMap<String, Integer> getType() {
		return type;
	}
}
