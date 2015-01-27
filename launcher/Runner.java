public class Runner implements Runnable {
    public interface OutputListener {
        void print(String message);
    }
    
    private boolean draw = false;
    private boolean error = false;
    private String errorLog = "";
    private Thread thread;
    private OutputListener listener;
    
    private long start;
    
    public Runner(OutputListener listener) {
        this.listener = listener;
    }
    
    public void run() {
        String loadString = "";
                
        while (draw) {
            if (loadString == "") loadString = "   ";
            else if (loadString == "   ") loadString = ".  ";
            else if (loadString == ".  ") loadString = ".. ";
            else if (loadString == ".. ") loadString = "...";
            else if (loadString == "...") loadString = "   ";
            
            listener.print(loadString + "\b\b\b");
                    
            try { thread.sleep(250); } catch (Exception e) { }
        }
    }
    
    public void start() {
        start = System.currentTimeMillis();
    }
    
    public void stop() {
        thread.interrupt();
        
        if (error) {
            listener.print(errorLog + "\n");
            listener.print("BUILD FAILED\n");
        } else {
            listener.print("BUILD SUCCESFULL\n");
        }
        
        long end = (System.currentTimeMillis() - start) / 1000;
        
        if (end > 59) {
            end = end / 60;
            if (end > 59) listener.print("Total time: " + end / 60 + " hours");
            else listener.print("Total time: " + end + " minutes");
        } else {
            listener.print("Total time: " + end + " seconds");
        }
        
        System.exit(error ? -1 : 0);
    }
    
    public void warn(String msg) {
        listener.print("\n " + msg + "\n");
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
            listener.print(" DONE\n");
        } else {
            listener.print(" FAILED\n");
            stop();
        }
    }
}