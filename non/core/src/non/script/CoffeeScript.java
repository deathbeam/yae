package non.script;

import org.mozilla.javascript.*;
import java.io.*;
import non.Non;
import non.plugins.Plugin;

public class CoffeeScript {
    private final Scriptable scope;
    
    public CoffeeScript() {
        Context context = Context.enter();
        try {
            scope = context.initStandardObjects();
        } finally {
            Context.exit(); 
        }
    }
    
    public Object invoke(String object, String method, Object... args) {
        Context context = Context.enter();
        try {
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
                return func.call(context, scope, scope, values);
            } else {
                return func.call(context, scope, scope, null);
            }
        } finally {
            Context.exit();
        }
    }

    public Object eval(String script) {
        Context context = Context.enter();
        
        try {
            context.setOptimizationLevel(-1);
            return context.evaluateString(scope, script, "CoffeeScript", 1, null);
        } finally {
            Context.exit();
        }
    }

    public void put(String key, Object value) {
        ScriptableObject.putProperty(scope, key, convert(value));
    }
    
    public Object get(String key) {
        return scope.get(key, scope);
    }
    
    public Object convert(Object javaValue) {
        Context.enter();
        
        try {
            return javaValue == null? UniqueTag.NULL_VALUE:
                   javaValue instanceof Scriptable? javaValue:
                   Context.javaToJS(javaValue, scope);
        } finally {
            Context.exit();
        }
    }
}