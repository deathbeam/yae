import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Zip {
    public interface NameMapper {
        String map(String name);
    }
    
    private File zip;
    
    public Zip(String zipFile) {
        this(new FileHandle(zipFile));
    }
    
    public Zip(FileHandle file) {
        zip = file.file();
    }
    
    public InputStream find(String toSearch) throws Exception {
        ZipFile zf = null;

        try {
            zf = new ZipFile(zip);
            Enumeration e = zf.entries();

            while(e.hasMoreElements()) {
                ZipEntry entry = (ZipEntry)e.nextElement();
                if(entry.getName().toLowerCase().contains(toSearch)) {
                    return zf.getInputStream(entry);                     
                }
            }
        } finally {
            try { if (zf != null) zf.close(); }
            catch (IOException e) { }
            return null;
        }
    }
    
    public void unpack() {
        unpack(".");
    }
		
    public void unpack(String outputFile) throws Exception {
        unpack(outputFile, new NameMapper() {
            public String map(String name) {
                return name;
            }
        });
    }
    
    public void unpack(String outputFile, NameMapper m) throws Exception {
        FileHandle output = new FileHandle(outputFile);
        output.mkdir();
        ZipFile zf = null;
        
        try {
            zf = new ZipFile(zip);
            Enumeration<? extends ZipEntry> en = zf.entries();
            
            while (en.hasMoreElements()) {
                ZipEntry e = (ZipEntry) en.nextElement();
                InputStream is = zf.getInputStream(e);
                
                try {
                    String name = m.map(e.getName());
                    
                    if (name != null) {
                        FileHandle handle = new FileHandle(output.file(), name);
                        
                        if (e.isDirectory()) {
                            handle.mkdir();
                        } else {
                            handle.parent().mkdir();
                            handle.copy(is);
                        }
                    }
                } finally {
                    try { if (is != null) is.close(); }
                    catch (IOException ioe) { }
                }
            }
        } finally {
            try { if (zf != null) zf.close(); }
            catch (IOException e) { }
        }
    }
}