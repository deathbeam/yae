package non.rhino;

import org.mozilla.javascript.*;
import java.io.*;

public class JavaScript {
    private final Scriptable scope;
    
    public JavaScript() {
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
                for (int i = 0; i < args.length; i++) values[i] = convertToJs(args[i]);
                return func.call(context, scope, scope, values);
            } else {
                return func.call(context, scope, scope, null);
            }
        } finally {
            Context.exit();
        }
    }

    public Object eval(String script, String tag) {
        Context context = Context.enter();
        
        try {
            context.setOptimizationLevel(-1);
            return context.evaluateString(scope, script, tag, 1, null);
        } finally {
            Context.exit();
        }
    }
    
    public Object call(Function function, Object... args) {
        if (function == null) return null;
        
        Context context = Context.enter();
        try {
            if (args != null) {
                Object[] values = new Object[args.length];
                for (int i = 0; i < args.length; i++) values[i] = convertToJs(args[i]);
                return function.call(context, scope, scope, values);
            } else {
                return function.call(context, scope, scope, null);
            }
        } finally {
            Context.exit(); 
        }
    }

    public void put(String key, Object value) {
        ScriptableObject.putProperty(scope, key, convertToJs(value));
    }
    
    public Object get(String key) {
        return scope.get(key, scope);
    }
    
    public Object convertToJs(Object javaValue) {
        Context.enter();
        
        try {
            return javaValue == null? null:
                   javaValue instanceof Scriptable? javaValue:
                   Context.javaToJS(javaValue, scope);
        } finally {
            Context.exit();
        }
    }
    
    public Object convertToJava(Object jsValue, Class<?> desiredType) {
        Context.enter();
        
        try {
            return jsValue == null? null:
                   Context.jsToJava(jsValue, desiredType);
        } finally {
            Context.exit();
        }
    }
}