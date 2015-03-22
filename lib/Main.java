import java.io.*;

public class Main implements Runner.OutputListener {
    private static final String VERSION = "v0.5.0";
    
    private static File JAR_FILE;
    private static File PROJECT_DIR;
    private static File PROJECT_DATA;
    private static File PROJECT_FILE;
    
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
        if (!PROJECT_DATA.exists()) {
            ZipUtils.extract(JAR_FILE, PROJECT_DIR, "data/");
            new File("data").renameTo(PROJECT_DATA);
            new Main("resolveDependencies", true);
        }
    }
    
    private static void printHelp() {
        System.out.println("Commands:");
        System.out.println("  non build <PLATFORM>              # build your application for specified <PLATFORM>");
        System.out.println("  non clean                         # clean temporary data for your project");
        System.out.println("  non hello <LANGUAGE>              # generate Hello World! project for specified <LANGUAGE>");
        System.out.println("  non start <PLATFORM>              # start your application for specified <PLATFORM>");
        System.out.println("  non update                        # update your project's runtime version and dependencies");
        System.out.println("  non version                       # print current compiler version");
        System.exit(-1);
    }
    
    public static void main(String[] args) throws java.net.URISyntaxException {
        if (args.length <= 0) {
            printHelp();
        }
        
        JAR_FILE = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        PROJECT_DIR = new File(System.getProperty("user.dir"));
        PROJECT_DATA = new File(PROJECT_DIR, ".non");
        PROJECT_FILE = new File(PROJECT_DATA, "VERSION");
        
        if (args[0].equals("build")) {
            if (args.length <= 1) {
                printHelp();
            }
            
            System.out.println("Packaging your application");
            check();
            
            if (args[1].equals("desktop")) {
                new Main("update desktop:dist --offline");
            } else if (args[1].equals("android")) {
                new Main("update android:dist --offline");
            } else if (args[1].equals("ios")) {
                new Main("update ios:dist --offline");
            } else {
                printHelp();
            }
        } else if (args[0].equals("start")) {
            if (args.length <= 1) {
                printHelp();
            }
            
            System.out.println("Starting your application");
            check();
            
            if (args[1].equals("desktop")) {
                new Main("update desktop:run --offline");
            } else if (args[1].equals("android")) {
                new Main("update android:run --offline");
            } else if (args[1].equals("ios")) {
                new Main("update ios:run --offline");
            } else {
                printHelp();
            }
        } else if (args[0].equals("hello")) {
            if (args.length <= 1) {
                printHelp();
            }
            
            System.out.println("Generating Hello World! project");
            check();
            
            if (args[1].equals("ruby")) {
                new Main("helloruby --offline");
            } else if (args[1].equals("lua")) {
                new Main("hellolua --offline");
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
            String version = FileUtils.readFile(PROJECT_FILE);
            System.out.println(version + "found");
            
            if (!version.equals(VERSION)) {
                FileUtils.deleteDirectory(PROJECT_DATA);
                check();
            }
        } else if (args[0].equals("version")) {
            System.out.println(VERSION);
        } else {
            printHelp();
        }
    }
}