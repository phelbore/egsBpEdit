package bp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipInputStream;

public class EPBFile {
	private MetaData meta;
	private BlockData block;
	
	public MetaData getMetaData() {
		return meta;
	}
	
	public BlockData getBlockData() {
		return block;
	}
	
	public EPBFile(File f) throws IOException {
		DataInputStream  dataIS = new DataInputStream(new FileInputStream(f));
		byte[] buf = new byte[(int)f.length()];
		dataIS.readFully(buf);
		dataIS.close();
		ZipInputStream zipInStream = new ZipInputStream(new ByteArrayInputStream(buf));		
		int fileStart = validateBuf(buf);
		if(fileStart > 0) {
			meta= new MetaData(Arrays.copyOfRange(buf, 0, fileStart));
			try {
				ByteArrayOutputStream dataBufOut = new ByteArrayOutputStream();
				zipInStream.getNextEntry();
				int currentByte;
				while((currentByte = zipInStream.read()) != -1) {
					dataBufOut.write(currentByte);
				}
				block= new BlockData(dataBufOut.toByteArray());
			} catch (IOException e) {
				throw new Error("Invalid EPB");
			}
		} else {
			throw new Error("Invalid EPB");
		}
	}
	
	/* should maybe use the zip data structure at the end of the file for this, instead
	 * see: https://users.cs.jmu.edu/buchhofp/forensics/formats/pkzip.html
	 */
	private int validateBuf(byte[] epbBuf) {
		int dataStart = 0;
		// Flags
		boolean fileHeader = false;
		boolean endCentralDirectory = false;
		
		//Check for valid signatures
		for(int i = epbBuf.length-1; i>0; i--) {
			if(epbBuf[i] == 0x04 && epbBuf[i-1] == 0x03 && epbBuf[i-2] == 0x00 && epbBuf[i-3] == 0x00) {
				epbBuf[i-2] = 0x4b;
				epbBuf[i-3] = 0x50;
				fileHeader = true;
				dataStart = i-3;
			}
			if(epbBuf[i] == 0x06 && epbBuf[i-1] == 0x05 && epbBuf[i-2] == 0x4b && epbBuf[i-3] == 0x50) {
				endCentralDirectory = true;
			}
		}
		if(fileHeader == true && endCentralDirectory == true) {
			return dataStart;
		}
		return -1;
	}
}
