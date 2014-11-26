package com.codeindie.non.scripting;

import com.badlogic.gdx.files.FileHandle;
import com.codeindie.non.Utils;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public abstract class ScriptRuntime {
    private static ScriptRuntime current;
    
    public static void setCurrent(ScriptRuntime runtime) { current = runtime; }
    public static ScriptRuntime getCurrent() { return current; }
    
    public static ScriptRuntime byName(String name) {
        if ("CoffeeScript".equalsIgnoreCase(name)) return new CoffeeScript();
        if ("Groovy".equalsIgnoreCase(name)) return new Groovy();
        if ("JavaScript".equalsIgnoreCase(name)) return new JavaScript();
        if ("Lua".equalsIgnoreCase(name)) return new Lua();
        if ("Python".equalsIgnoreCase(name)) return new Python();
        if ("Ruby".equalsIgnoreCase(name)) return new Ruby();
        if ("TypeScript".equalsIgnoreCase(name)) return new TypeScript();
        return null;
    }
    
    public static ScriptRuntime byExtension(String ext) {
        if ("coffee".equalsIgnoreCase(ext)) return new CoffeeScript();
        if ("groovy".equalsIgnoreCase(ext)) return new Groovy();
        if ("js".equalsIgnoreCase(ext)) return new JavaScript();
        if ("lua".equalsIgnoreCase(ext)) return new Lua();
        if ("py".equalsIgnoreCase(ext)) return new Python();
        if ("rb".equalsIgnoreCase(ext)) return new Ruby();
        if ("ts".equalsIgnoreCase(ext)) return new TypeScript();
        return null;
    }
    
    protected ScriptEngine e;
    public abstract void invoke(String pack, String funct);
    public abstract void invoke(String pack, String funct, String args);
    public abstract Object eval(FileHandle file);

    public Object eval(String script) {
        try {
            return e.eval(script);
        } catch (ScriptException ex) {
            Utils.warning("Scripting", ex.getMessage());
            return null;
        }
    }
    
    public void put(String key, Object value) {
        if (e!=null) e.put(key, value);
    }

    public Object get(String key) {
        if (e==null) return null;
        return e.get(key);
    }
}
