import java.io.*;

public class FileHandle {
    private File file;
    
    public FileHandle(String file) {
        this.file = new File(file);
    }
    
    public FileHandle(File file) {
        this.file = file;
    }
    
    public FileHandle(String dir, String file) {
        this.file = new File(dir, file);
    }
    
    public FileHandle(File dir, String file) {
        this.file = new File(dir, file);
    }
    
    public File file() {
        return file;
    }
    
    public FileHandle parent() {
        return new FileHandle(file.getParentFile());
    }
    
    public boolean exists() {
        return file.exists();
    }
    
    public void mkdir() throws Exception {
        if (file.exists()) {
            if (file.isFile()) throw new Exception(file.getName() + " exists and is not a directory.");
        } else if (!file.mkdirs()) {
            throw new Exception("Unable to create directory " + file.getName());
        }
    }
    
    public boolean deldir() {
        return deldir(file);
    }
    
    private boolean deldir(File file) {
        if(file.exists()) {
            File[] files = file.listFiles();
            
            if(files !=null) {
                for(File fl: files) {
                    if(fl.isDirectory()) {
                        deldir(fl);
                    } else {
                        fl.delete();
                    }
                }
            }
        }
        
        return file.delete();
    }
    
    public void copy(InputStream in) throws Exception {
        OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        try {
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while (-1 != (n = in.read(buffer))) out.write(buffer, 0, n);
        } finally {
            try { if (out != null) out.close(); }
            catch (IOException ioe) { }
        }
    }
    
    public String read() throws Exception {
        InputStream in = new FileInputStream(file);
        final StringBuffer sBuffer = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        final char[] buffer = new char[1024];
        int cnt;
        
        while ((cnt = br.read(buffer, 0, buffer.length)) > -1) {
            sBuffer.append(buffer, 0, cnt);
        }
        
        br.close();
        in.close();
        return sBuffer.toString();
    }
}