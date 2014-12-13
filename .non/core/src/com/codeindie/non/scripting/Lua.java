package com.codeindie.non.scripting;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;
import com.codeindie.non.Non;

public class Lua extends ScriptRuntime {
    public static String extension() { return "lua"; }
    public String version()          { return "5.1"; }
    
    private final Globals _G;
    protected String initializer = 
        "non.ready  = function() \n end \n" +
        "non.update = function() \n end \n" +
        "non.draw   = function() \n end \n" +
        "non.resize = function() \n end \n" +
        "non.close  = function() \n end \n" +
        "non.pause  = function() \n end \n" +
        "non.resume = function() \n end \n";
    
    public Lua() {
        _G = JsePlatform.standardGlobals();
    }

    public Object invoke(String object, String method, HashMap<String, Object> args) {
        String script = merge(object, method, args, ":", ",", "(", ")", "");
        
        if (args != null) {
            Object[] parameters = args.values().toArray();
            LuaValue[] values = new LuaValue[parameters.length];
            for (int i = 0; i < parameters.length; i++)
                values[i] = toLua(parameters[i]);
            return _G.get(object).get(method).call(LuaValue.listOf(values));
        } else
            return _G.get(object).get(method).call();
    }

    public Object eval(String script) {
        try {
            return _G.load(initializer + Non.getResource(script).readString()).call();
        } catch (IOException e) {
            return Non.error("Resource not found", script);
        }
    }

    public void put(String key, Object value) {
        _G.set(toLua(key), toLua(value));
    }
    
    private LuaValue toLua(Object javaValue) {
        return javaValue == null? LuaValue.NIL:
               javaValue instanceof LuaValue? (LuaValue) javaValue:
               CoerceJavaToLua.coerce(javaValue);
    }
}