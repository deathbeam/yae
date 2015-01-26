public class Main {
    public static final String REPO   = "non-dev/non";
    public static final String BRANCH = "master";
    public static final String DIR    = "non";
    public static final String TEMP   = ".non";
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar non.jar <task-name> <task-arguments>");
            System.exit(-1);
        }
        
        Runner runner = new Runner();
        runner.start();
        
        FileHandle outputDir = new FileHandle(TEMP);
        Version v = null;
        
        try {
            runner.wait("Checking for latest version");
            
            v = new Version();
        } catch(Exception e) {
            runner.error(e);
        } finally {
            runner.finish();
        }
        
        if (v != null && v.isLower()) {
            try {
                runner.wait("Downloading non " + v.toString());
                
                FileHandle download = new Download("https://github.com/"+REPO+"/archive/"+BRANCH+".zip").get();
                
                outputDir.deldir();
                new Zip(download).unpack();
                new FileHandle("master.zip").delete();
                new FileHandle("non-master/"+DIR).copydir(TEMP);
                new FileHandle("non-master").deldir();
            } catch(Exception e) {
                runner.error(e);
            } finally {
                runner.finish();
            }
        }
        
        Gradle gradle = new Gradle(outputDir);
        
        try {
            runner.wait("Checking dependencies and updating data");
            
            gradle.execute("update");
        } catch(Exception e) {
            runner.error(e);
        } finally {
            runner.finish();
        }
        
        try {
            String arg = args[0];
        
            if (args.length > 1) {
                for(int i = 1; i < args.length; i++) {
                    arg += " " + args[i];
                }
            }
               
            runner.wait("Executing 'gradlew " + arg + "'");
            
            gradle.execute(arg);
        } catch(Exception e) {
            runner.error(e);
        } finally {
            runner.finish();
        }
              
        runner.stop();
    }
}