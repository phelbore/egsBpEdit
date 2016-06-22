package bp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class BpFile {
	public enum FileType { EPB, ZIP }
	private MetaData meta = new MetaData();
	private File bpFile = null;
	private byte [] fileBuf = null;
	private byte [] dataBuf = null;
	private byte [] metaBuf = null;
	private boolean valid = false;
	
	public BpFile(File f, FileType t) {
		bpFile = f;
		try {
			FileInputStream stream = new FileInputStream(f.getAbsolutePath());
			fileBuf = new byte [(int) f.length()];
			stream.read(fileBuf);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(t == FileType.EPB) {
			valid = parseBP();
		} else if(t == FileType.ZIP) {
			
		}
		
		if(valid) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(2);
			byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
			byteBuffer.put(metaBuf[132]);
			byteBuffer.put(metaBuf[133]);
			byteBuffer.flip();
			meta.setBlockCount(byteBuffer.getShort());
		}
	}
	
	public boolean saveFile(File f, FileType t) {
		if(valid) {
			if(t == FileType.EPB) {
				String path = f.getAbsolutePath();
				if(!path.endsWith(".epb")) {
					path = path + ".epb";
				}
			}
			FileOutputStream os;
			try {
				os = new FileOutputStream(f.getAbsolutePath());
				os.write(metaBuf);
				os.write(buildEPBData());
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}
	
	private boolean parseBP() {
		byte [] zipBuf = null;
		if(!bpFile.exists() || fileBuf.length == 0) {
			return false;
		}
		zipBuf = getZipFromBuf(fileBuf);
		if(zipBuf == null) {
			return false;
		}
		try {
			FileOutputStream os = new FileOutputStream(bpFile.getAbsolutePath()+".zip");
			os.write(zipBuf);
			os.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private byte[] buildEPBData() {
		byte[] zipDataBuf;
		ByteArrayOutputStream outBufStream = new ByteArrayOutputStream();
		ZipEntry dataEntry;
		dataEntry = new ZipEntry("0");
		dataEntry.setMethod(ZipEntry.DEFLATED);
		ZipOutputStream zipOutStream = new ZipOutputStream(outBufStream);

		try {
			writeEntryToBuf(dataBuf, dataEntry, zipOutStream);
			zipOutStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		zipDataBuf = outBufStream.toByteArray();
		zipDataBuf[0] = 0x00;
		zipDataBuf[1] = 0x00;
		return zipDataBuf;
	}

	private byte[] getZipFromBuf(byte [] bpBuf) {
		ByteArrayOutputStream outBufStream = new ByteArrayOutputStream();
		boolean validBP;
		try {
			validBP = parseBP(bpBuf);
		} catch (IOException e) {
			throw new Error("File not valid)");
		}
		
		if(validBP) {
			writeZipToBuf(outBufStream);
			return outBufStream.toByteArray();
		} else {
			return null;
		}
	}

	private boolean parseBP(byte[] bpBuf) throws IOException {
		boolean fileHeader = false;
		boolean endCentralDirectory = false;
		boolean validFile = false;
		int fileStart = 0;
		byte [] dataBufZip = null;

		for(int i = bpBuf.length-1; i>0; i--) {
			if(bpBuf[i] == 0x04 && bpBuf[i-1] == 0x03 && bpBuf[i-2] == 0x00 && bpBuf[i-3] == 0x00) {
				bpBuf[i-2] = 0x4b;
				bpBuf[i-3] = 0x50;
				fileHeader = true;
				fileStart = i-3;
			}
			if(bpBuf[i] == 0x06 && bpBuf[i-1] == 0x05 && bpBuf[i-2] == 0x4b && bpBuf[i-3] == 0x50) {
				endCentralDirectory = true;
			}
			if(fileHeader == true && endCentralDirectory == true) {
				metaBuf = Arrays.copyOfRange(bpBuf, 0, fileStart);
				dataBufZip = Arrays.copyOfRange(bpBuf, fileStart, bpBuf.length);
				ByteArrayInputStream zipInByteStream = new ByteArrayInputStream(dataBufZip);
				ByteArrayOutputStream dataBufOut = new ByteArrayOutputStream();
				ZipInputStream zipInStream = new ZipInputStream(zipInByteStream);
				zipInStream.getNextEntry();
				int currentByte;
				while((currentByte = zipInStream.read()) != -1) {
					dataBufOut.write(currentByte);
				}
				dataBuf = dataBufOut.toByteArray();
				validFile = true;
			}
		}
		return validFile;
	}

	private void writeZipToBuf(ByteArrayOutputStream outBufStream) {
		ZipEntry metaEntry;
		ZipEntry dataEntry;
		metaEntry = new ZipEntry("meta");
		metaEntry.setMethod(ZipEntry.DEFLATED);
		dataEntry = new ZipEntry("data");
		dataEntry.setMethod(ZipEntry.DEFLATED);

		ZipOutputStream zipOutStream = new ZipOutputStream(outBufStream);
		try {
			writeEntryToBuf(metaBuf, metaEntry, zipOutStream);
			writeEntryToBuf(dataBuf, dataEntry, zipOutStream);
			zipOutStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeEntryToBuf(byte[] entryBuf, ZipEntry zipEntry, ZipOutputStream zipOutStream) throws IOException {
		zipOutStream.putNextEntry(zipEntry);
		for(int i=0; i<entryBuf.length; i++) {
			zipOutStream.write(entryBuf[i]);
		}
		zipOutStream.closeEntry();
	}

	public boolean isValid() {
		return valid;
	}
}
