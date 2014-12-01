package com.codeindie.non.scripting;

import java.io.IOException;
import java.util.HashMap;
import org.mozilla.javascript.*;
import com.codeindie.non.Non;

public class JavaScript extends ScriptRuntime {
    public static String extension() { return "js"; }
    public String version() { return "1.7.4"; }
    
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
	
	public JavaScript() {
		engine = Context.enter();
		engine.setOptimizationLevel(-1);
		scope = engine.initStandardObjects();
	}

	public Object invoke(String object, String method, HashMap<String, Object> args) {
		String script = merge(object, method, args, ".", ",", "(", ")", ";");
        return engine.evaluateString(scope, script, "JavaScript", 1, null);
	}

	public Object eval(String script) {
		try {
			return engine.evaluateString(scope, initializer + Non.getResource(script).readString(), "JavaScript", 1, null);
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
}