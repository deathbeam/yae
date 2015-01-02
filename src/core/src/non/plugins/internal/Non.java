package non.plugins.internal;
import non.plugins.Plugin;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonValue;

public class Non extends Plugin {
    public String author()      { return "Thomas Slusny"; }
    public String license()     { return "MIT"; }
    public String description() { return "Core plugin of NON."; }
    
    public int getWidth()                          { return non.Non.getWidth(); }
    public int getHeight()                         { return non.Non.getHeight(); }
    public int getFPS()                            { return non.Non.getFPS(); }
    public float getDelta()                        { return non.Non.getDelta(); }
    public JsonValue getConfig()                   { return non.Non.getConfig(); }
    
    public String getPlatform()                    { return non.Non.getPlatform(); }
    public boolean checkPlatform(String p)         { return getPlatform().equalsIgnoreCase(p); }
    
    public Object warning(String type, String msg) { return non.Non.warning(type, msg); }
    public Object error(String type, String msg)   { return non.Non.error(type, msg); }
    public Object log(String type, String msg)     { return non.Non.log(type, msg); }
    public Object debug(String type, String msg)   { return non.Non.debug(type, msg); }
    
    public FileHandle file(String path) 		   { return non.Non.file(path); }
    public Object require(String path) 			   { return non.Non.script.eval(file(path).readString()); }
	public void exit() 							   { non.Non.exit(); }
    
    public Object 
		load, ready, draw, update, resize, close, pause, resume, 
		keydown, keyup, keytyped, touchdown, touchup, touchdragged, mousemoved, scrolled;
}