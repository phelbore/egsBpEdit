package bp;
import java.io.File;

public class bpFile {

	private File bpFile;
	
	public bpFile(File f) {
		bpFile = f;
	}

	public File getBpFile() {
		return bpFile;
	}

	public void setBpFile(File bpFile) {
		this.bpFile = bpFile;
	}

}
