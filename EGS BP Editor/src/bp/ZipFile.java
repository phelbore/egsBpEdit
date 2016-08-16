package bp;

/*
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
*/

public class ZipFile {
	/*
	private byte[] zipData;
	private byte[] metaData;
	private byte[] blockData;
	
	public ZipFile(byte[] epbIn) {
		parseEPB(epbIn);
	}

	public ZipFile(MetaData meta, BlockData block) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ZipEntry metaEntry;
		ZipEntry blockEntry;
		metaEntry = new ZipEntry("meta");
		metaEntry.setMethod(ZipEntry.DEFLATED);
		blockEntry = new ZipEntry("data");
		blockEntry.setMethod(ZipEntry.DEFLATED);
		
		ZipOutputStream zipOutStream = new ZipOutputStream(outStream);
		try {
			writeEntryToZipOutStream(meta.getOriginalInput(), metaEntry, zipOutStream);
			writeEntryToZipOutStream(block.getOriginalInput(), blockEntry, zipOutStream);
			zipData = outStream.toByteArray();
			zipOutStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void writeEntryToZipOutStream(byte[] entryBuf, ZipEntry zipEntry, ZipOutputStream zipOutStream) throws IOException {
		zipOutStream.putNextEntry(zipEntry);
		for(int i=0; i<entryBuf.length; i++) {
			zipOutStream.write(entryBuf[i]);
		}
		zipOutStream.closeEntry();
	}
	
	private void parseEPB(byte[] epbIn) {
		int fileStart = 0;
		
		// Internal buffers
		byte[] metaBuf = null;
		
		ByteArrayInputStream zipInByteStream = new ByteArrayInputStream(epbIn);
		ByteArrayOutputStream dataBufOut = new ByteArrayOutputStream();
		ZipInputStream zipInStream = new ZipInputStream(zipInByteStream);
		
		if((fileStart = validateBuf(epbIn)) > 0) {
			metaBuf = Arrays.copyOfRange(epbIn, 0, fileStart);

			try {
				zipInStream.getNextEntry();
				int currentByte;
				while((currentByte = zipInStream.read()) != -1) {
					dataBufOut.write(currentByte);
				}
			} catch (IOException e) {
				throw new Error("Invalid EPB");
			}
		} else {
			throw new Error("Invalid EPB");
		}
		setBlockData(dataBufOut.toByteArray());
		setMetaData(Arrays.copyOf(metaBuf,  metaBuf.length));
		setZipData(Arrays.copyOf(epbIn, epbIn.length));
}
	
	//Returns 0 on invalid file, block data start if valid
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

	public byte[] getBlockData() {
		return blockData;
	}

	public void setBlockData(byte[] blockData) {
		this.blockData = blockData;
	}

	public byte[] getMetaData() {
		return metaData;
	}

	public void setMetaData(byte[] metaData) {
		this.metaData = metaData;
	}

	public byte[] getZipData() {
		return zipData;
	}

	public void setZipData(byte[] zipData) {
		this.zipData = zipData;
	}
	*/
}
