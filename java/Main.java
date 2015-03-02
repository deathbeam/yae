import java.io.*;

public class Main implements Runner.OutputListener {
    public Main(String dir, String args) {
        Runner runner = new Runner(this);
        runner.start();
        
        Gradle gradle = new Gradle(new File(dir), this);
        
        try {
            runner.wait("Executing 'gradle " + args + "'");
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
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: <path> <task-name> <?task-arguments>");
            System.exit(-1);
        }
        
        String dir = args[0];
        String joinedArgs = args[1];
        
        if (args.length > 2) {
            for(int i = 2; i < args.length; i++) {
                joinedArgs += " " + args[i];
            }
        }
        
        new Main(dir, joinedArgs);
    }
}