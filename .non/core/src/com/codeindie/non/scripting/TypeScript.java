package com.codeindie.non.scripting;

import java.io.IOException;
import java.util.HashMap;
import org.mozilla.javascript.*;
import com.codeindie.non.Non;

public class TypeScript extends ScriptRuntime {
    public static String extension() { return "ts"; }
    public String version() { return "0.9"; }
    
	private Context engine;
	private Scriptable scope;
    private String initializer = 
        "non.ready = function() { };" +
        "non.update = function() { };" +
        "non.draw = function() { };" +
        "non.resize = function() { };" +
        "non.close = function() { };" +
        "non.pause = function() { };" +
        "non.resume = function() { };";
	
	public TypeScript() {
		engine = Context.enter();
		engine.setOptimizationLevel(-1);
		scope = engine.initStandardObjects();
	}

	public Object invoke(String object, String method, HashMap<String, Object> args) {
		String script = merge(object, method, args, ".", ",", "(", ")", ";");
        return engine.evaluateString(scope, script, "TypeScript", 1, null);
	}

	public Object eval(String script) {
		try {
            engine.evaluateString(scope, Non.getResource("res/typescript.js").readString(), "TypeScript", 1, null);
            engine.evaluateString(scope, Non.getResource("res/typescript.compile.js").readString(), "TypeScript", 1, null);
			return engine.evaluateString(scope, initializer + compile(Non.getResource(script).readString()), "TypeScript", 1, null);
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
            return (String)context.evaluateString(compileScope, "TScompile(script);", "TypeScriptCompiler", 1, null);
        } finally {
            Context.exit();
        }
    }
}