import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;

public class Download {
    public static boolean isInternetReachable() { 
        try { 
            URL url = new URL("http://www.google.com"); 
            HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection(); 
            urlConnect.getContent();
        } catch (UnknownHostException e) {
            return false;
        } catch (IOException e) { 
            return false; 
        }
        
        return true;
    }
    
    private String urlString;
    
    public Download(String url) {
        this.urlString = url;
    }

    public FileHandle get() throws Exception {
        String link = urlString;
        URL url  = new URL(link);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        Map< String, List< String >> header = http.getHeaderFields();
        while(isRedirected( header )) {
            link = header.get( "Location" ).get( 0 );
            url = new URL(link);
            http = (HttpURLConnection)url.openConnection();
            header = http.getHeaderFields();
        }
        
        InputStream input  = http.getInputStream();
        byte[] buffer = new byte[4096];
        int n = -1;
        File file = new File(link.substring(link.lastIndexOf('/') + 1, link.length()));
        OutputStream output = new FileOutputStream(file);
        while ((n = input.read(buffer)) != -1) {
            output.write( buffer, 0, n );
        }
        output.close();
        return new FileHandle(file);
    }
    
    private boolean isRedirected( Map<String, List<String>> header ) {
        for( String hv : header.get( null )) {
            if(   hv.contains( " 301 " ) || hv.contains( " 302 " )) return true;
        }
        
        return false;
    }
}