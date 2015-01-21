public class Runner implements Runnable {
    private boolean draw = false;
    private boolean error = false;
    private String errorLog = "";
    private Thread thread;
    
    private long start;
    
    public void run() {
        String loadString = "";
                
        while (draw) {
            if (loadString == "") loadString = "   ";
            else if (loadString == "   ") loadString = ".  ";
            else if (loadString == ".  ") loadString = ".. ";
            else if (loadString == ".. ") loadString = "...";
            else if (loadString == "...") loadString = "   ";
            
            System.out.print(loadString + "\b\b\b");
                    
            try { thread.sleep(250); } catch (Exception e) { }
        }
    }
    
    public void start() {
        start = System.currentTimeMillis();
    }
    
    public void stop() {
        thread.interrupt();
        
        if (error) {
            System.out.println(errorLog);
            System.out.println("BUILD FAILED");
        } else {
            System.out.println("BUILD SUCCESFULL");
        }
        
        long end = (System.currentTimeMillis() - start) / 1000;
        
        if (end > 59) {
            end = end / 60;
            if (end > 59) System.out.print("Total time: " + end / 60 + " hours");
            else System.out.print("Total time: " + end + " minutes");
        } else {
            System.out.print("Total time: " + end + " seconds");
        }
        
        System.exit(error ? -1 : 0);
    }

    public void error(Exception e) {
        error = true;
        errorLog += e.getMessage() + "\n";
    }

    public void wait(String msg) {
        System.out.print("> " + msg);
        draw = true;
        thread = new Thread(this);
        thread.start();
    }

    public void finish() {
        draw = false;
        thread.interrupt();
        
        if (!error) {
            System.out.println(" DONE");
        } else {
            System.out.println(" FAILED");
            stop();
        }
    }
}