public class Main {
    static final String repo   = "non-dev/non";
    static final String branch = "master";
    static final String dir    = ".non";
    static final String subdir = "non";
    
    static final String[] excludes = new String[] {
        "launcher/", ".gitignore", "LICENSE", "README.md"
    };
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar non.jar <task-name> <task-arguments>");
            System.exit(-1);
        }
        
        long start = System.currentTimeMillis();
        
        String arg = args[0];
        
        if (args.length > 1) {
            for(int i = 1; i < args.length; i++) {
                arg += " " + args[i];
            }
        }
        
        Runner runner = new Runner();
        runner.start();
        
        boolean upToDate = true;
        String newVer = "0.0.0";
        FileHandle outputDir = new FileHandle(dir + "/" + subdir);
        
        try {
            runner.wait("Checking for latest version");
            FileHandle toCheck = new FileHandle(new Download("https://raw.githubusercontent.com/"+repo+"/"+branch+"/VERSION" ).get());
            newVer = toCheck.read().trim();
            if (!outputDir.exists()) {
                upToDate = false;
            } else {
                String curVer = new FileHandle(dir, "VERSION").read().trim();
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
                
                outputDir.deldir();
                FileHandle download = new FileHandle(new Download("https://github.com/"+repo+"/archive/"+branch+".zip").get());
                
                new Zip(download.file()).unpack(".", new Zip.NameMapper() {
                    public String map(String name) {
                        for(String exc: excludes) {
                            if (name.contains(exc)) return null;
                        }
                        
                        return name;
                    }
                });
                
                new FileHandle("master.zip").file().delete();
                new FileHandle("VERSION").file().delete();
                new FileHandle("non-master").file().renameTo(new FileHandle(dir).file());
            } catch(Exception e) {
                runner.error(e);
            } finally {
                runner.finish();
            }
        }
        
        try {          
            runner.wait("Executing 'gradlew " + arg + "'");
            
            new Gradle(outputDir.file()).execute(arg);
        } catch(Exception e) {
            runner.error(e);
        } finally {
            runner.finish();
        }
              
        runner.stop();
        
        if (runner.isError()) {
            System.out.println("\nBUILD FAILED");
        } else {
            System.out.println("\nBUILD SUCCESFULL");
        }
        
        long end = (System.currentTimeMillis() - start) / 1000;
        
        if (end > 59) {
            end = end / 60;
            if (end > 59) System.out.print("Total time: " + end / 60 + " hours");
            else System.out.print("Total time: " + end + " minutes");
        } else {
            System.out.print("Total time: " + end + " seconds");
        }
        
        System.exit(runner.isError() ? -1 : 0);
    }
}