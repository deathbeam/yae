package non.scripting;

import java.util.HashMap;
import org.mozilla.javascript.*;

public class JavaScript extends ScriptRuntime {
    public static String extension() { return "js"; }
    public String version()          { return "1.7R4"; }
    
    protected Context engine;
    protected Scriptable scope;
    
    public JavaScript() {
        engine = Context.enter();
        engine.setOptimizationLevel(-1);
        scope = engine.initStandardObjects();
    }
    
    public void init() {
        String script =
        "non.ready  = function() { };" +
        "non.update = function() { };" +
        "non.draw   = function() { };" +
        "non.resize = function() { };" +
        "non.close  = function() { };" +
        "non.pause  = function() { };" +
        "non.resume = function() { };";
        engine.evaluateString(scope, script, "JavaScript", 1, null);
        
    }

    public Object invoke(String object, String method, HashMap<String, Object> args) {
        String script = merge(object, method, args, ".", ",", "(", ")", ";");
        
        return engine.evaluateString(scope, script, "JavaScript", 1, null);
    }
    
    public Object invoke(String object, String method, Object... args) {
        Scriptable obj = (Scriptable)scope.get(object, scope);
        Function func = (Function)obj.get(method, obj);
        return func.call(engine, scope, scope, args);
    }

    public Object eval(String script) {
        return engine.evaluateString(scope, script, "JavaScript", 1, null);
    }

    public void put(String key, Object value) {
        ScriptableObject.putProperty(scope, key, toJS(value));
    }
    
    private Object toJS(Object javaValue) {
        return Context.javaToJS(javaValue, scope);
    }
    
    private String merge(String object, String method, HashMap<String,Object> args, 
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