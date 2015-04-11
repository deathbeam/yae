import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

public class ZipUtils {
    public static void unpack(File zip, String name, File file) throws IOException {
        final boolean isDir = name.endsWith("/");
        ZipFile zf = new ZipFile(zip);
        ZipEntry ze;
        File zef;
        Enumeration<? extends ZipEntry> zes = zf.entries();
        String zen;
        
        if (isDir) file.mkdirs();
        
        while (zes.hasMoreElements()) {
            ze = zes.nextElement();
            
            if (isDir) {
                zen = ze.getName().replaceFirst(name, "");
            } else {
                int s = name.lastIndexOf("/");
                zen = s > -1 ? ze.getName().substring(s + 1) : ze.getName();
            }
            
            zef = new File(file, zen);
            
            if (!ze.getName().startsWith(name)) continue;

            if (ze.isDirectory()) {
                new File(file, zen).mkdirs();
                continue;
            } else {
                new File(new File(file, zen).getParent()).mkdirs();
            }

            InputStream in = new BufferedInputStream(zf.getInputStream(ze));
            try {
                FileUtils.copy(in, zef);
            } finally {
                FileUtils.closeQuietly(in);
            }
        }
    }
}