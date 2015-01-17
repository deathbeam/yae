package non.plugins;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonValue;

import non.Non;
import non.NonBuffer;

public class non extends Plugin {
    public String author()      { return "Thomas Slusny"; }
    public String license()     { return "MIT"; }
    public String description() { return "Core plugin of NON."; }
    
    public int width()                         { return Non.getWidth(); }
    public int height()                        { return Non.getHeight(); }
    public int fps()                           { return Non.getFPS(); }
    public float delta()                       { return Non.getDelta(); }
    public JsonValue config()                  { return Non.getConfig(); }
    public boolean platform(String p)          { return Non.getPlatform().equalsIgnoreCase(p); }
    
    public void error(String type, String msg) { Non.error(type, msg); }
    public void log(String type, String msg)   { Non.log(type, msg); }
    public void debug(String type, String msg) { Non.debug(type, msg); }
    
    public FileHandle file(String path)        { return Non.file(path); }
    public Object require(String path)         { return Non.script.eval(file(path).readString()); }
    public void quit()                         { Non.quit(); }
    
    public NonBuffer buffer()                  { return new NonBuffer(); }
    public NonBuffer buffer(byte[] data)       { return new NonBuffer(data); }
    
    public Object 
        load, ready, draw, update, resize, close, pause, resume, 
        keydown, keyup, keytyped, touchdown, touchup, touchdragged, mousemoved, scrolled;
}