import java.io.*;
import java.net.URISyntaxException;

public class Main implements Runner.OutputListener {
    private static final String VERSION = "v0.6.2";
    private static File PROJECT_DIR, PROJECT_DATA;
    
    public Main(String args) {
        this(args, false);
    }
    
    public Main(String args, boolean silent) {
        Runner runner = new Runner(this);
        runner.start(silent);
        
        Gradle gradle = new Gradle(PROJECT_DATA, this);
        
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
            if (!PROJECT_DATA.exists()) {
                ZipUtils.extract(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()), PROJECT_DIR, "engine/", "java/", "lua/");
                new File(PROJECT_DIR, "engine").renameTo(PROJECT_DATA);
                new File(PROJECT_DATA, "core/src").mkdirs();
                new File(PROJECT_DIR, "java").renameTo(new File(PROJECT_DATA, "core/src/non"));
                new File(PROJECT_DIR, "lua").renameTo(new File(PROJECT_DATA, "lua"));
                new Main("resolveDependencies", true);
            }
        } catch (URISyntaxException e) {
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
        
        PROJECT_DIR = new File(System.getProperty("user.dir"));
        PROJECT_DATA = new File(PROJECT_DIR, ".non");
        
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
            String version = FileUtils.readFile(new File(PROJECT_DATA, "VERSION")).trim();
            
            if (!version.equals(VERSION)) {
                System.out.println("Updating to " + VERSION);
                FileUtils.deleteDirectory(PROJECT_DATA);
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