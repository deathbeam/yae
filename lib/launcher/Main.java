import java.io.*;
import java.net.URISyntaxException;

public class Main implements Runner.OutputListener {
    public static boolean DEBUG = false;
    private static String VERSION;
    private static File JAR, TEMP;
    
    public Main(String args) {
        this(args, false);
    }
    
    public Main(String args, boolean silent) {
        Runner runner = new Runner(this);
        runner.start(silent);
        
        Gradle gradle = new Gradle(TEMP, this);
        
        try {
            gradle.execute(args);
        } catch(Exception e) {
            runner.error(e);
        } finally {
            runner.finish();
        }
              
        runner.stop();
    }
    
    public void print(String msg) {
        System.out.print(msg);
    }
    
    private static void check() {
        try {
            if (!TEMP.exists()) {
                TEMP.mkdirs();
                ZipUtils.unpack(JAR, "lib/gradle/", TEMP);
                ZipUtils.unpack(JAR, "lib/platforms/", new File(TEMP, "platforms/"));
                ZipUtils.unpack(JAR, "lib/resources/", new File(TEMP, "resources/"));
                ZipUtils.unpack(JAR, "lib/VERSION", TEMP);
                new Main("resolveDependencies", true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }      
    }
    
    private static void printHelp() {
        System.out.println("Commands:");
        System.out.println("  non hello LANGUAGE       # generate Hello World! project for specified LANGUAGE");
        System.out.println("  non build PLATFORM       # build your application for specified PLATFORM");
        System.out.println("  non start PLATFORM       # start your application for specified <PLATFORM>");
        System.out.println("  non clean                # clean temporary data for your project");
        System.out.println("  non update               # update your project's runtime version and dependencies");
        System.out.println("  non version              # print current compiler version");
        System.out.println("");
        System.out.println("PLATFORM can be 'desktop', 'android' or 'ios' (defaults to 'desktop')");
        System.out.println("LANGUAGE can be 'lua' or 'moon' (defaults to 'lua')");
        System.exit(-1);
    }
    
    public static void main(String[] args) {
        if (args.length <= 0) {
            printHelp();
        }
        
        try {
            JAR = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e2) {
            e2.printStackTrace();
            System.exit(-1);
        }  
        
        TEMP = new File(new File(System.getProperty("user.dir")), ".non/");
        VERSION = FileUtils.readStream(Main.class.getResourceAsStream("lib/VERSION")).trim();
        
        if (args[0].equals("-d")) {
            DEBUG = true;
            
            switch (args.length) {
            case 2: args = new String[] { args[1] }; break;
            case 3: args = new String[] { args[1], args[2] }; break;
            }
        }
        
        if (args[0].equals("build")) {
            System.out.println("Packaging your application");
            check();
            
            if (args.length <= 1 || args[1].equals("desktop")) {
                new Main("update updateDesktop desktop:dist --offline");
            } else if (args[1].equals("android")) {
                new Main("update updateAndroid android:dist --offline");
            } else if (args[1].equals("ios")) {
                new Main("update updateIOS ios:dist --offline");
            } else {
                printHelp();
            }
        } else if (args[0].equals("start")) {
            System.out.println("Starting your application");
            check();
            
            if (args.length <= 1 || args[1].equals("desktop")) {
                new Main("update updateDesktop desktop:run --offline");
            } else if (args[1].equals("android")) {
                new Main("update updateAndroid android:run --offline");
            } else if (args[1].equals("ios")) {
                new Main("update updateIOS ios:run --offline");
            } else {
                printHelp();
            }
        } else if (args[0].equals("hello")) {
            System.out.println("Generating Hello World! project");
            check();
            
            if (args.length <= 1 || args[1].equals("lua")) {
                new Main("hellolua --offline");
            } else if (args[1].equals("moon")) {
                new Main("hellomoon --offline");
            } else {
                printHelp();
            }
        } else if (args[0].equals("clean")) {
            System.out.println("Cleaning your project's temporary data");
            check();
            new Main("clean cleanAssets --offline");
        } else if (args[0].equals("update")) {
            System.out.println("Updating your project's runtime");
            check();
            String version = FileUtils.readFile(new File(TEMP, "VERSION")).trim();
            
            if (!version.equals(VERSION)) {
                System.out.println("Updating to " + VERSION);
                FileUtils.forceDelete(TEMP);
                check();
            } else {
                System.out.println("You are using latest version of compiler.");
            }
        } else if (args[0].equals("version")) {
            System.out.println(VERSION);
        } else {
            printHelp();
        }
    }
}