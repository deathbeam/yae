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
    public static boolean noError = true;
    public static boolean draw = true;
    public static String errorLog = "";
    public static String taskLog = "";
    public static String loadString = "   ";
    
    static Thread waiting;
    
    interface NameMapper {
        String map(String name);
    }
    
    interface CharCallback {
        void character(char c);
    }

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        if (args.length == 0) {
            System.out.println("Usage: java -jar non.jar <task-name> <task-arguments>");
            System.exit(-1);
        }
        
        waiting = new Thread(new Runnable() {
            public void run() {
                while(Main.noError) {
                    try {
                        if (Main.draw) {
                            if (Main.loadString == "   ") Main.loadString = ".  ";
                            else if(Main.loadString == ".  ") Main.loadString = ".. ";
                            else if(Main.loadString == ".. ") Main.loadString = "...";
                            else if(Main.loadString == "...") Main.loadString = "   ";

                            System.out.print(Main.loadString + "\b\b\b");
                            
                            Thread.sleep(250);
                        }
                    } catch(InterruptedException e) {
                        Main.noError = false;
                    }
                }
            }
        });
        
        waiting.start();
        
        String arg = args[0];
        
        if (args.length > 1) {
            for(int i = 1; i < args.length; i++) {
                arg += " " + args[i];
            }
        }
        
        File outputDir = new File(".non");
        
        wait("Building cache");
        
        if (!outputDir.exists()) {
            unpack(new File("non.jar"), outputDir, new NameMapper() {
                public String map(String name) {
                    if (name.contains("launcher/")) return null;
                    if (name.contains("META-INF/")) return null;
                    return name;
                }
            });
        }
        
        finish();
        
        CharCallback callback = new CharCallback() {
            public void character(char c) {
                errorLog += c;
            }
        };
        
        wait("Executing 'gradlew " + arg + "'");
        execute(outputDir, arg, callback);
        finish();
        
        waiting.interrupt();
        
        checkErrors();
        
        long end = (System.currentTimeMillis() - start) / 1000;
        
        if (end > 59) {
            end = end / 60;
            if (end > 59) System.out.print("Total time: " + end / 60 + " hours");
            else System.out.print("Total time: " + end + " minutes");
        } else {
            System.out.print("Total time: " + end + " seconds");
        }
    }
    
    static void wait(String msg) {
        System.out.print("> " + msg);
        draw = true;
    }
    
    static void finish() {
        draw = false;
        if (noError) System.out.println(" DONE");
        else System.out.println(" FAILED");
    }
    
    static void error(String error) {
        noError = false;
        errorLog = error;
    }
    
    static void checkErrors() {
        if (!noError) {
            int i = errorLog.lastIndexOf("BUILD");
            if (i >= 0) System.out.println("\n" + errorLog.substring(0, i));
            else System.out.println("\n" + errorLog);
            System.out.println("\nBUILD FAILED");
        } else {
            System.out.println("\nBUILD SUCCESFULL");
        }
    }

    static void execute (File workingDir, String parameters, CharCallback callback) {
        String targetFile = (System.getProperty("os.name").contains("Windows") ? "gradlew.bat" : "gradlew");
        String exec = (workingDir.getAbsolutePath() + "/" + targetFile).replace("\\", "/");
		
        String[] params = parameters.split(" ");
        String[] commands = new String[params.length + 1];
        commands[0] = exec;
        
        for (int i = 0; i < params.length; i++) {
            commands[i + 1] = params[i];
        }
		
        startProcess(commands, workingDir, callback);
    }

    static void startProcess (String[] commands, File directory, final CharCallback callback) {
        try {
            final Process process = new ProcessBuilder(commands).redirectErrorStream(true).directory(directory).start();

            Thread t = new Thread(new Runnable() {
                public void run () {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()), 1);
                    try {
                        int c = 0;
                        while ((c = reader.read()) != -1) {
                            Main.taskLog += (char)c;						
                        }
                    } catch (IOException e) { }
                }
            });
            t.setDaemon(true);
            t.start();
            process.waitFor();
            t.interrupt();
            
            if (process.exitValue() != 0) error(taskLog);
        } catch (Exception e) {
            error(e.getMessage());
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
                    error("Failed to unpack zip entry " + e.getName());
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
            if (directory.isFile()) throw new IOException(directory + " exists and is not a directory.");
        } else if (!directory.mkdirs()) {
            throw new IOException("Unable to create directory " + directory);
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