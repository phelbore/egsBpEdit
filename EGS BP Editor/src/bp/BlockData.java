package bp;

public class BlockData {
	private final byte[] blockData;

	public BlockData(byte[] byteArray) {
		blockData= byteArray;
	}
	
	public final byte[] bytes() {
		return blockData;
	}
}
