package non.scripting;

import non.Non;
import java.util.HashMap;

public abstract class ScriptRuntime {
    public static ScriptRuntime byExtension(String ext) {
        ScriptRuntime r = null;
        Non.log("ScriptRuntime", "Loading scripting language...");
        if (JavaScript.extension().equalsIgnoreCase(ext)) r = new JavaScript();
        else if (Lua.extension().equalsIgnoreCase(ext)) r = new Lua();
        Non.log("Language", r.getClass().getSimpleName() + " " + r.version());
        if (r == null) Non.error("Language", "Wrong extension!");
        return r;
    }
	
    public String version() { return "Unknown"; }
    
    public abstract Object invoke(String object, String method, Object... args);
    public abstract Object eval(String script);
    public abstract void init();
    public abstract void put(String key, Object value); 
}