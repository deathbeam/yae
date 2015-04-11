import java.io.*;
import java.util.*;

public class FileUtils {
    public static boolean forceDelete(File file) {
        if (file.isDirectory()) {
            if (file.exists()){
                File[] files = file.listFiles();
                if (files == null) return false;
                for (File child : files) forceDelete(child);
            }
        }
        
        return file.delete();
    }
    
    public static void copy(InputStream in, File file) throws IOException {
        OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        try {
            byte[] buffer = new byte[20000];
            int n = 0;
            while (-1 != (n = in.read(buffer))) {
              out.write(buffer, 0, n);
            }
        } finally {
            closeQuietly(out);
        }
    }
  
    public static String readFile(File file) {
        StringBuilder fileContents = new StringBuilder((int)file.length());
        String lineSeparator = System.getProperty("line.separator");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while(scanner.hasNextLine()) {        
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } catch (Exception e) {
            System.out.println("Trying to read " + file.getAbsolutePath() + " failed!");
            return null;
        } finally {
            if (scanner != null) scanner.close();
        }
    }
    
    public static String readStream(InputStream stream) {
        StringBuffer stringBufferOfData = new StringBuffer();
        Scanner toRead = new Scanner(stream);
        
        for (String line; toRead.hasNextLine() && (line = toRead.nextLine()) != null; ) { 
            stringBufferOfData.append(line).append("\n");
        }
        
        toRead.close();

        return stringBufferOfData.toString();
    }
    
    public static void closeQuietly(InputStream input) {
        try { if (input != null) input.close(); }
        catch (IOException ioe) {}
    }
    
    public static void closeQuietly(OutputStream output) {
        try { if (output != null) output.close(); }
        catch (IOException ioe) {}
    }
}