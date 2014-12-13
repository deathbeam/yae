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
    
    public Object invoke(String method, HashMap<String,Object> args)  { return invoke(null, method, args); }
    public abstract Object invoke(String object, String method, HashMap<String,Object> args);
    public abstract Object eval(String script);
    public abstract void put(String key, Object value);
    
    public String merge(String object, String method, HashMap<String,Object> args, 
        String methodJoiner, String argJoiner, String left, String right, String end) {
        
        String result = "";
        
        if(args != null) {
            Object[] set = args.keySet().toArray();
            String sArgs = (String)set[0];
            if (sArgs.length() > 1) {
                for (int i = 1; i < sArgs.length() - 1; i++) sArgs += argJoiner + (String)set[i];
            }
            
            for(Object key: set) put((String)key, args.get(key));
            if (object != null) result = object + methodJoiner + method + left + sArgs + right + end;
            else result = method + left + sArgs + right + end;
        } else {
            if (object != null) result = object + methodJoiner + method + left + right + end;
            else result = method + left + right + end;
        }

        return result;
    } 
}