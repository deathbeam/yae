import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;

public class Download {
    private String urlString;
    
    public Download(String url) {
        this.urlString = url;
    }

    public File get() throws Exception {
        File file = null;
        ReadableByteChannel rbc = null;
        FileOutputStream fos = null;
        
        try {
            URL url = new URL(urlString);
            if (url.getFile().length() < 2) throw new Exception(urlString + " is not a file.");
            file = new File(url.getFile());
            
            rbc = Channels.newChannel(url.openStream()); 
            fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } finally {
            if (rbc != null) {
                try { rbc.close(); }
                catch (Exception e) { }
            }
            
            if (fos != null) {
                try { fos.close(); }
                catch (Exception e) { }
            }
            
            return file;
        }
    }
}