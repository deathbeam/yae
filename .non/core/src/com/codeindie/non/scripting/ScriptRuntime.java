package com.codeindie.non.scripting;

import com.codeindie.non.Non;
import java.util.HashMap;

public abstract class ScriptRuntime {
    public static ScriptRuntime byExtension(String ext) {
        ScriptRuntime r = null;
        Non.log("ScriptRuntime", "Loading scripting language...");
        if (JavaScript.extension().equalsIgnoreCase(ext)) r = new JavaScript();
        else if (CoffeeScript.extension().equalsIgnoreCase(ext)) r = new CoffeeScript();
        else if (TypeScript.extension().equalsIgnoreCase(ext)) r = new TypeScript();
        else if (Lua.extension().equalsIgnoreCase(ext)) r = new Lua();
        Non.log("Language", r.getClass().getSimpleName() + " " + r.version());
        if (r == null) Non.error("Language", "Wrong extension!");
        return r;
    }
	
    public String version() { return "Unknown"; }
    
    public Object invoke(String object, String method)  { return invoke(object, method, null); }
    public abstract Object invoke(String object, String method, HashMap<String,Object> args);
    public abstract Object eval(String script);
    public abstract void init();
    public abstract void put(String key, Object value); 
}