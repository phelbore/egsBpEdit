package bp;

import java.util.Arrays;

public class BlockData {
	private byte[] originalInput;

	public BlockData() {
		
	}
	
	public void populate(byte[] data) {
		originalInput = Arrays.copyOf(data, data.length);
	}
	
	public byte[] getOriginalInput() {
		return originalInput;
	}
}
