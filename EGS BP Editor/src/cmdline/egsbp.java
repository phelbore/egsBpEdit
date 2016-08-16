package cmdline;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import bp.EPBFile;

public class egsbp {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		for (int j= 0; j<args.length; j++) {
			File arg= new File(args[j]);
			EPBFile data = new EPBFile(arg);
			System.out.print(".");
			writeFile(args[j]+".meta", data.getMetaData().bytes());
			System.out.print(".");
			writeFile(args[j]+".block", data.getBlockData().bytes());
			System.out.println(".done");
		}
	}
	
	private static void writeFile(String name, byte[] data) throws IOException {
		FileOutputStream fos = new FileOutputStream(name);
		fos.write(data);
		fos.close();
	}

}

