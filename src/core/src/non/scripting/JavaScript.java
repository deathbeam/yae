package non.scripting;

import org.mozilla.javascript.*;

public class JavaScript extends ScriptRuntime {
    public static String extension() { return "js"; }
    public String version()          { return "1.7R4"; }
    
    private final Context engine;
    private final Scriptable scope;
    
    public JavaScript() {
        engine = Context.enter();
        engine.setOptimizationLevel(-1);
        scope = engine.initStandardObjects();
    }
    
    public void init() {
        String script =
        "non.load = function(a) {};" +
		"non.ready = function() {};" +
		"non.update = function(a) {};" +
		"non.draw = function() {};" +
		"non.resize = function(a,b) {};" +
		"non.pause = function() {};" +
		"non.resume = function() {};" +
		"non.close = function() {};" +
		"non.keydown = function(a) {};" +
		"non.keyup = function(a) {};" +
		"non.keytyped = function(a) {};" +
		"non.touchdown = function(a,b,c) {};" +
		"non.touchup = function(a,b,c) {};" +
		"non.touchdragged = function(a,b) {};" +
		"non.mousemoved = function(a) {};" +
		"non.scrolled = function(a) {};";
        eval(script);
        
    }
    
    public Object invoke(String object, String method, Object... args) {
        Scriptable obj = (Scriptable)scope.get(object, scope);
        Function func = (Function)obj.get(method, obj);
        
        if (args != null) {
            Object[] values = new Object[args.length];
            for (int i = 0; i < args.length; i++) values[i] = convert(args[i]);
            return func.call(engine, scope, scope, values);
        } else {
            return func.call(engine, scope, scope, null);
        }
    }

    public Object eval(String script) {
        return engine.evaluateString(scope, script, "JavaScript", 1, null);
    }

    public void put(String key, Object value) {
        ScriptableObject.putProperty(scope, key, convert(value));
    }
    
    public Object convert(Object javaValue) {
        return javaValue == null? null:
               javaValue instanceof Scriptable? javaValue:
               Context.javaToJS(javaValue, scope);
    }
}