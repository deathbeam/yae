import java.io.File;
import java.io.IOException;

import org.zeroturnaround.zip.ZipUtil;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("##############################################################################");
		System.out.println("##");
		System.out.println("##  No nonsense command-line interface");
		System.out.println("##");
		System.out.println("##############################################################################");
		System.out.println("");
		
		File f = new File(".non");
		if (!f.exists()) {
			f.mkdir();
			System.out.print("Building cache...");
			ZipUtil.unpack(new File("non.jar"), f);
			System.out.print(" DONE\n");
		}
	}
}