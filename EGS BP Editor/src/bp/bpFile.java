package bp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class bpFile {

	private File bpFile = null;
	private byte [] bpBuffer = null;
	private byte [] zipBuffer = null;
	private byte [] dataBuf = null;
	private byte [] metaBuf = null;
	
	public bpFile(File f) {
		bpFile = f;
		try {
			FileInputStream stream = new FileInputStream(f.getAbsolutePath());
			bpBuffer = new byte [(int) f.length()];
			stream.read(bpBuffer);
			stream.close();
			System.out.println(parseFile());
		} catch (IOException e) {
			
		}
	}
	
	private boolean parseFile() {
		if(!bpFile.exists() || bpBuffer.length == 0) {
			return false;
		}
		zipBuffer = getZip(bpBuffer);
		try {
			FileOutputStream os = new FileOutputStream(bpFile.getAbsolutePath()+".zip");
			os.write(zipBuffer);
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private byte[] getZip(byte [] bpBuf) {
		ByteArrayOutputStream outBufStream = new ByteArrayOutputStream();
		boolean validBP;
		try {
			validBP = parseBP(bpBuf);
		} catch (IOException e) {
			throw new Error("File not valid)");
		}
		
		if(validBP) {
			writeZip(dataBuf, metaBuf, outBufStream);
			return outBufStream.toByteArray();
		} else {
			throw new Error("File not valid)");
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
				bpBuf[i-2] = 0x4b;
				bpBuf[i-3] = 0x50;
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

	private void writeZip(byte[] dataBuf, byte[] metaBuf, ByteArrayOutputStream outBufStream) {
		ZipEntry metaEntry;
		ZipEntry dataEntry;
		metaEntry = new ZipEntry("meta");
		metaEntry.setMethod(ZipEntry.DEFLATED);
		dataEntry = new ZipEntry("data");
		dataEntry.setMethod(ZipEntry.DEFLATED);

		ZipOutputStream zipOutStream = new ZipOutputStream(outBufStream);
		try {
			writeEntry(metaBuf, metaEntry, zipOutStream);
			writeEntry(dataBuf, dataEntry, zipOutStream);
			zipOutStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeEntry(byte[] metaBuf, ZipEntry metaEntry, ZipOutputStream zipOutStream) throws IOException {
		zipOutStream.putNextEntry(metaEntry);
		for(int i=0; i<metaBuf.length; i++) {
			zipOutStream.write(metaBuf[i]);
		}
		zipOutStream.closeEntry();
	}
}
