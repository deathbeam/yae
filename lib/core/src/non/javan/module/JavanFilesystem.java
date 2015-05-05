package non.javan.module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class JavanFilesystem {
    public boolean append(String filename, String text) {
        return append(filename, text, "internal");
    }
    
    public boolean append(String filename, String text, String filetype) {
        newFile(filename, filetype).writeString(text, true);
        return true;
    }
    
    public boolean copy(String filenameFrom, String filenameTo) {
        return copy(filenameFrom, filenameTo, "internal", "internal");
    }
    
    public boolean copy(String filenameFrom, String filenameTo, String filetypeFrom, String filetypeTo) {
        newFile(filenameFrom, filetypeFrom).copyTo(newFile(filenameTo, filetypeTo));
        return true;
    }
    
    public boolean createDirectory(String filename) {
        return createDirectory(filename, "internal");
    }
    
    public boolean createDirectory(String filename, String filetype) {
        newFile(filename, filetype).mkdirs();
        return true;
    }
    
    public boolean exists(String filename) {
        return exists(filename, "internal");
    }
    
    public boolean exists(String filename, String filetype) {
        return newFile(filename, filetype).exists();
    }
    
    public String[] getDirectoryItems(String filename) {
        return getDirectoryItems(filename, "internal");
    }
    
    public String[] getDirectoryItems(String filename, String filetype) {
        FileHandle[] children = newFile(filename, filetype).list();
        String[] items = new String[children.length];
        
        for (int i = 0; i < children.length; i++) {
            items[i] = children[i].path();
        }
        
        return items;
    }
    
    public String getExternalDirectory() {
        return Gdx.files.getExternalStoragePath();
    }
    
    public String getLocalDirectory() {
        return Gdx.files.getLocalStoragePath();
    }
    
    public String getWorkingDirectory() {
        return Gdx.files.internal(".").file().getAbsolutePath();
    }
    
    public long getLastModified(String filename) {
        return getLastModified(filename, "internal");
    }
    
    public long getLastModified(String filename, String filetype) {
        return newFile(filename, filetype).lastModified();
    }
    
    public long getSize(String filename) {
        return getSize(filename, "internal");
    }
    
    public long getSize(String filename, String filetype) {
        return newFile(filename, filetype).length();
    }
    
    public boolean isDirectory(String filename) {
        return isDirectory(filename, "internal");
    }
    
    public boolean isDirectory(String filename, String filetype) {
        return newFile(filename, filetype).isDirectory();
    }
    
    public boolean isFile(String filename) {
        return isFile(filename, "internal");
    }
    
    public boolean isFile(String filename, String filetype) {
        return !isDirectory(filename, filetype);
    }
    
    // chunk = non.filesystem.load(filename, filetype)
    //    set("load", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
    //        try {
    //            return vm.getLua().getContext().getGlobals().load(newFile(args, 1, 2).reader(), getArgString(args, 1));
    //        } catch (Exception e) {
    //            handleError(e);
    //            return NONE;
    //        }
    //    }});
    
    public boolean move(String filenameFrom, String filenameTo) {
        return move(filenameFrom, filenameTo, "internal", "internal");
    }
    
    public boolean move(String filenameFrom, String filenameTo, String filetypeFrom, String filetypeTo) {
        newFile(filenameFrom, filetypeFrom).moveTo(newFile(filenameTo, filetypeTo));
        return true;
    }
    
    public String read(String filename) {
        return read(filename, "internal");
    }
    
    public String read(String filename, String filetype) {
        return newFile(filename, filetype).readString();
    }
    
    public boolean remove(String filename) {
        return remove(filename, "internal");
    }
    
    public boolean remove(String filename, String filetype) {
        return newFile(filename, filetype).deleteDirectory();
    }
    
    public boolean write(String filename, String text) {
        return write(filename, text, "internal");
    }
    
    public boolean write(String filename, String text, String filetype) {
        newFile(filename, filetype).writeString(text, false);
        return true;
    }

    public static FileHandle newFile(String filename, String filetype) {
        if ("internal".equals(filetype)) return Gdx.files.internal(filename);
        else if ("local".equals(filetype)) return Gdx.files.local(filename);
        else if ("external".equals(filetype)) return Gdx.files.external(filename);
        else if ("classpath".equals(filetype)) return Gdx.files.classpath(filename);
        else if ("absolute".equals(filetype)) return Gdx.files.absolute(filename);
        return null;
    }
}