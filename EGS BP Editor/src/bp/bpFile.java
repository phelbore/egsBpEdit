package bp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class bpFile {

	private File bpFile = null;
	private byte [] bpBuffer = null;
	private byte [] zipBuffer = null;
	
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
		byte [] zipBuf = null;
		boolean fileHeader = false;
		boolean endCentralDirectory = false;
		int fileStart = 0;
		for(int i = bpBuf.length-1; i>0; i--) {
			if(bpBuf[i] == 0x04 && bpBuf[i-1] == 0x03 && bpBuf[i-2] == 0x00 && bpBuf[i-3] == 0x00) {
				bpBuf[i-2] = 0x4b;
				bpBuf[i-3] = 0x50;
				fileHeader = true;
				fileStart = i-3;
				System.out.println(fileStart);
			}
			if(bpBuf[i] == 0x06 && bpBuf[i-1] == 0x05 && bpBuf[i-2] == 0x4b && bpBuf[i-3] == 0x50) {
				bpBuf[i-2] = 0x4b;
				bpBuf[i-3] = 0x50;
				System.out.println(bpBuf[i+16]);
				endCentralDirectory = true;
			}
		}
		
		if(fileHeader == true && endCentralDirectory == true) {
			System.out.println(bpBuf.length);
			System.out.println(fileStart);
			zipBuf = Arrays.copyOfRange(bpBuf, fileStart, bpBuf.length);
			return zipBuf;
		} else {
			throw new Error("File not valid)");
		}
	}

	public File getBpFile() {
		return bpFile;
	}

	public void setBpFile(File bpFile) {
		this.bpFile = bpFile;
	}

}
