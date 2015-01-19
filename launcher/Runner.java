public class Runner implements Runnable {
    private boolean draw = false;
    private boolean error = false;
    private String errorLog = "";
    private String loadString = "";
    private Thread thread;
    
    public Runner() {
        thread = new Thread(this);
    }
    
    public void start() {
        thread.start();
    }

    public void run() {
        if (error && !draw) return;
        
        if (loadString == "") loadString = "   ";
        else if (loadString == "   ") loadString = ".  ";
        else if(loadString == ".  ") loadString = ".. ";
        else if(loadString == ".. ") loadString = "...";
        else if(loadString == "...") loadString = "   ";

        System.out.print(loadString + "\b\b\b");
        
        try { thread.sleep(250); } catch (Exception e) { }
    }
    
    public void stop() {
        thread.interrupt();
    }

    public void error(Exception e) {
        error = true;
        draw = false;
        errorLog += e.getMessage() + "\n";
    }

    public void wait(String msg) {
        System.out.print("> " + msg);
        draw = true;
    }

    public void finish() {
        draw = false;
        
        if (!error) {
            System.out.println(" DONE");
        } else {
            System.out.println(" FAILED");
        }
    }
    
    public boolean isError() {
        return error;
    }
}