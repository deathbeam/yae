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
    interface NameMapper {
        String map(String name);
    }
    
    interface CharCallback {
        void character(char c);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("> Welcome to no nonsense command-line interface.");
        
        if (args.length == 0) {
            System.out.println("Usage: java -jar non.jar <task-name>");
            System.exit(-1);
        }
        
        String arg = args[0];
        
        if (args.length > 1) {
            for(int i = 1; i < args.length; i++) {
                arg += " " + args[i];
            }
        }
        
        File outputDir = new File(".non");
        
        if (!outputDir.exists()) {
            System.out.print("Building cache... ");
            
            unpack(new File("non.jar"), outputDir, new NameMapper() {
                public String map(String name) {
                    if (name.contains("launcher/")) return null;
                    if (name.contains("META-INF/")) return null;
                    return name;
                }
            });
            
            System.out.print("DONE\n");
        }
        
        String windowsFile = "gradlew.bat";
        String unixFile = "gradlew";
        CharCallback callback = new CharCallback() {
            public void character(char c) {
                System.out.print(c);
            }
        };
            
        execute(outputDir, windowsFile, unixFile, arg, callback);
    }

    static boolean execute (File workingDir, String windowsFile, String unixFile, String parameters, CharCallback callback) {
        
        String exec = workingDir.getAbsolutePath() + "/" + (System.getProperty("os.name").contains("Windows") ? windowsFile : unixFile);
        String log = "Executing '" + exec + " " + parameters + "'";
        
        for(int i = 0; i < log.length(); i++) {
            callback.character(log.charAt(i));
        }
        
        callback.character('\n');
		
        String[] params = parameters.split(" ");
        String[] commands = new String[params.length + 1];
        commands[0] = exec;
        
        for (int i = 0; i < params.length; i++) {
            commands[i + 1] = params[i];
        }
		
        return startProcess(commands, workingDir, callback);
    }

    static boolean startProcess (String[] commands, File directory, final CharCallback callback) {
        try {
            final Process process = new ProcessBuilder(commands).redirectErrorStream(true).directory(directory).start();

            Thread t = new Thread(new Runnable() {
                public void run () {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()), 1);
                    try {
                        int c = 0;
                        while ((c = reader.read()) != -1) {
                            callback.character((char)c);						
                        }
                    } catch (IOException e) { }
                }
            });
            t.setDaemon(true);
            t.start();
            process.waitFor();
            t.interrupt();
            return process.exitValue() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
                        File file = new File(output.getAbsolutePath(), name);
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