public class Runner {
    private static boolean draw = false;
    private static boolean error = false;
    private static String errorLog = "";
    private static Thread thread;
    
    private long start;
    
    public Runner() {
        thread = new Thread(new Runnable() {
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
        });
    }
    
    public void start() {
        start = System.currentTimeMillis();
        thread.start();
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
    }

    public void finish() {
        draw = false;
        
        if (!error) {
            System.out.println(" DONE");
        } else {
            System.out.println(" FAILED");
            stop();
        }
    }
}