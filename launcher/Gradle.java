import java.io.*;

public class Gradle {
    private static final String windowsFile = "gradlew.bat";
    private static final String unixFile = "gradlew";

    private File workingDir;
    private Runner.OutputListener listener;
    
    public Gradle(String working, Runner.OutputListener listener) {
        this(new FileHandle(working), listener);
    }
    
    public Gradle(FileHandle working, Runner.OutputListener listener) {
        workingDir = working.file();
        this.listener = listener;
        new File(workingDir, unixFile).setExecutable(true);
    }

    public void execute (String parameters) throws Exception {
        String targetFile = (System.getProperty("os.name").contains("Windows") ? windowsFile : unixFile);
        String exec = (workingDir.getAbsolutePath() + "/" + targetFile).replace("\\", "/");

        String[] params = parameters.split(" ");
        String[] commands = new String[params.length + 1];
        commands[0] = exec;

        for (int i = 0; i < params.length; i++) {
            commands[i + 1] = params[i];
        }

        startProcess(commands, workingDir);
    }

    private void startProcess (String[] commands, File directory) throws Exception {
        final Process process = new ProcessBuilder(commands).redirectErrorStream(true).directory(directory).start();

        Thread t = new Thread(new Runnable() {
                public void run () {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()), 1);
                    try {
                        String c = null;
                        boolean dexWarning = false;
                        while ((c = reader.readLine()) != null) {
                            if (c.startsWith("BUILD") ||
                                c.startsWith("Total time:") ||
                                c.startsWith("Configuration on demand is an incubating feature.") ||
                                c.trim().equals("")) 
                                continue;
                            
                            if (c.startsWith(":android:preDexReleasewarning")) dexWarning = true;
                            if (dexWarning && c.startsWith(":android:dexRelease")) dexWarning = false;
                            
                            if (!dexWarning) listener.print(c + "\n");           
                        }
                    } catch (IOException e) { }
                }
            });

        t.setDaemon(true);
        t.start();
        process.waitFor();
        t.interrupt();

        if (process.exitValue() != 0) throw new Exception("Task finished with non-zero result");
    }
}