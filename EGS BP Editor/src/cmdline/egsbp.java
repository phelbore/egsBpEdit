package cmdline;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import bp.ShipType;
import bp.ZipFile;

public class egsbp {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		for (int j= 0; j<args.length; j++) {
			File arg= new File(args[j]);
			DataInputStream  dataIS = new DataInputStream(new FileInputStream(arg));
			byte[] buf = new byte[(int)arg.length()];
			dataIS.readFully(buf);
			dataIS.close();
			System.out.print(args[j]+": "+ShipType.get(buf[8])+" .");
			ZipFile data = new ZipFile(buf);
			System.out.print(".");
			writeFile(args[j]+".meta", data.getMetaData());
			System.out.print(".");
			writeFile(args[j]+".block", data.getBlockData());
			System.out.println(".done");
		}
	}
	
	private static void writeFile(String name, byte[] data) throws IOException {
		FileOutputStream fos = new FileOutputStream(name);
		fos.write(data);
		fos.close();
	}

}

