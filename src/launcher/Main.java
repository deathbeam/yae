package launcher;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    static String outputDir;
    static NameMapper mapper;
    
    interface NameMapper {
        String map(String name);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("> Welcome to no nonsense command-line interface.");
        File f = new File(".non");
        if (!f.exists()) {
            System.out.print("Building cache... ");
            unpack(new File("non.jar"), f, new NameMapper() {
                public String map(String name) {
                    if (name.contains("launcher/")) return null;
                    if (name.contains("META-INF/")) return null;
                    return name;
                }
            });
            System.out.print("DONE\n");
        }
    }
    
    static String execute(String command) {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine())!= null) output.append(line + "\n");
        } catch (Exception e) { e.printStackTrace(); }
        return output.toString();
    }
    
    static void unpack(File zip, File output) {
        unpack(zip, output, new NameMapper() {
            public String map(String name) {
                return name;
            }
        });
    }
    
    static void unpack(File zip, File output, NameMapper m) {
        if (!output.exists()) output.mkdir();
        outputDir = output.getAbsolutePath();
        mapper = m;
        ZipFile zf = null;
        try {
            zf = new ZipFile(zip);
            Enumeration<? extends ZipEntry> en = zf.entries();
            while (en.hasMoreElements()) {
                ZipEntry e = (ZipEntry) en.nextElement();
                InputStream is = zf.getInputStream(e);
                try { 
                    String name = mapper.map(e.getName());
                    if (name != null) {
                        File file = new File(outputDir, name);
                        if (e.isDirectory()) {
                            mkdir(file);
                        } else {
                            mkdir(file.getParentFile());
                            copy(is, file);
                        }
                    }
                } catch (IOException ze) {
                    System.out.print("\nFailed to unpack zip entry " + e.getName() + "\n");
                } finally {
                    try { if (is != null) is.close(); }
                    catch (IOException ioe) { }
                }
            }
        } catch (IOException e) {
            System.out.print("\n" + e.getMessage() + "\n");
        } finally {
            try { if (zf != null) zf.close(); }
            catch (IOException e) { }
        }
    }
    
    static void mkdir(File directory) throws IOException {
        if (directory.exists()) {
            if (directory.isFile()) throw new IOException("\n" + directory + " exists and is not a directory.\n");
        } else if (!directory.mkdirs()) {
            throw new IOException("\nUnable to create directory " + directory + "\n");
        }
    }
    
    static void copy(InputStream in, File file) throws IOException {
        OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        try {
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while (-1 != (n = in.read(buffer))) out.write(buffer, 0, n);
        } finally {
            try { if (out != null) out.close(); }
            catch (IOException ioe) { }
        }
    }
}