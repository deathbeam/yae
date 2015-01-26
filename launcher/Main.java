public class Main {
    static final String repo   = "non-dev/non";
    static final String branch = "master";
    static final String dir    = "non";
    static final String temp   = ".non";
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar non.jar <task-name> <task-arguments>");
            System.exit(-1);
        }
        
        Runner runner = new Runner();
        runner.start();
        
        FileHandle outputDir = new FileHandle(temp);
        String curVer = new FileHandle(dir, "VERSION").read().trim();
        String newVer = curVer;
        boolean upToDate;
        
        try {
            runner.wait("Checking for latest version");
            
            try {
                FileHandle toCheck = new Download("https://raw.githubusercontent.com/"+repo+"/"+branch+"/"+dir+"/VERSION" ).get();
                newVer = toCheck.read().trim();
                new FileHandle("VERSION").delete();
            } catch(Exception e) { }
            
            if (!outputDir.exists()) {
                upToDate = false;
            } else {
                upToDate = curVer.equals(newVer);
            }
        } catch(Exception e) {
            runner.error(e);
        } finally {
            runner.finish();
        }
        
        if (!upToDate) {
            try {
                runner.wait("Downloading non " + newVer);
                
                FileHandle download = new Download("https://github.com/"+repo+"/archive/"+branch+".zip").get();
                
                outputDir.deldir();
                new Zip(download).unpack();
                new FileHandle("master.zip").delete();
                new FileHandle("non-master/"+dir).copydir(temp);
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