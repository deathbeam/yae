package com.codeindie.non.scripting;

import java.io.IOException;
import java.util.HashMap;
import org.mozilla.javascript.*;
import com.codeindie.non.Non;

public class Lua extends ScriptRuntime {
    public static String extension() { return "lua"; }
    public String version() { return "5.1.1"; }
    
    private Context engine;
    private Scriptable scope;
    private String initializer = 
        "console = { };" +
        "non.ready = function() { };" +
        "non.update = function() { };" +
        "non.draw = function() { };" +
        "non.resize = function() { };" +
        "non.close = function() { };" +
        "non.pause = function() { };" +
        "non.resume = function() { };";
	
    public Lua() {
        engine = Context.enter();
        engine.setOptimizationLevel(-1);
        scope = engine.initStandardObjects();
    }

    public Object invoke(String object, String method, HashMap<String, Object> args) {
        String script = merge(object, method, args, ".", ",", "(", ")", ";");
        return engine.evaluateString(scope, script, "Lua", 1, null);
    }

    public Object eval(String script) {
        try {
            engine.evaluateString(scope, Non.getResource("res/lua+parser.min.js").readString(), "Lua", 1, null);
            return engine.evaluateString(scope, initializer + compile(Non.getResource(script).readString()), "Lua", 1, null);
        } catch (IOException e) {
            return Non.error("Resource not found", script);
        }
    }

    public void put(String key, Object value) {
        ScriptableObject.putProperty(scope, key, Context.javaToJS(value, scope));
    }

    public Object get(String key) {
        return scope.get(key, scope);
    }
    
    private String compile(String script) {
        Context context = Context.enter();
        try {
            Scriptable compileScope = engine.newObject(scope);
            compileScope.setParentScope(scope);
            compileScope.put("script", compileScope, script);
            return (String)context.evaluateString(compileScope, "lua_load(script);", "LuaCompiler", 1, null);
        } finally {
            Context.exit();
        }
    }
}