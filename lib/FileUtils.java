import java.io.*;
import java.util.*;

public class FileUtils {
    public static boolean deleteDirectory(File directory) {
        if(directory.exists()){
            File[] files = directory.listFiles();
            if(null!=files){
                for(int i=0; i<files.length; i++) {
                    if(files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    }
                    else {
                        files[i].delete();
                    }
                }
            }
        }
        return(directory.delete());
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
}