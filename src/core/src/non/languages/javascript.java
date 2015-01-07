package non.languages;

import org.mozilla.javascript.*;

public class javascript extends Language {
    public String extension() { return "js"; }
    public String version()   { return "1.7R4"; }
    
    private final Context engine;
    private final Scriptable scope;
    
    public javascript() {
        engine = Context.enter();
        engine.setOptimizationLevel(-1);
        scope = engine.initStandardObjects();
    }
    
    public Object invoke(String object, String method, Object... args) {
        Function func;
        if (method != null) {
            Scriptable obj = (Scriptable)scope.get(object, scope);
            func = (Function)obj.get(method, obj);
        } else {
            func = (Function)scope.get(object, scope);
        }
        
        if (func == null) return null;
        
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
    
    public Object get(String key) {
        return scope.get(key, scope);
    }
    
    public Object convert(Object javaValue) {
        return javaValue == null? null:
               javaValue instanceof Scriptable? javaValue:
               Context.javaToJS(javaValue, scope);
    }
}