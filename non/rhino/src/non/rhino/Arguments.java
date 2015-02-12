package non.rhino;

import org.mozilla.javascript.*;
import java.io.*;
import non.rhino.RhinoNon;

public class Arguments {
    private final Scriptable s;
    
    public Arguments(Scriptable s) {
        this.s = s;
    }
    
    public boolean has(String key) {
        return s.has(key, s);
    }
    
    public Object get(String key, Object defaultValue) {
        if (has(key)) {
            return RhinoNon.script.convertToJava(s.get(key, s), Object.class);
        }
        
        return defaultValue;
        
    }
    
    public Function getFunction(String key) {
        if (has(key)) {
            return (Function)s.get(key, s);
        }
        
        return null;
    }
    
    public String getString(String key, String defaultValue) {
        if (has(key)) {
            return (String)RhinoNon.script.convertToJava(s.get(key, s), String.class);
        }
        
        return defaultValue;
    }
    
    public boolean getBool(String key, Boolean defaultValue) {
        if (has(key)) {
            return (Boolean)RhinoNon.script.convertToJava(s.get(key, s), Boolean.class);
        }
        
        return defaultValue;
    }
    
    public float getNum(String key, Float defaultValue) {
        if (has(key)) {
            return (Float)RhinoNon.script.convertToJava(s.get(key, s), Float.class);
        }
        
        return defaultValue;
    }
    
    public Object[] getArray(String key, Object[] defaultValue) {
        if (has(key)) {
            Object[] jsArray = ((NativeArray)s.get(key, null)).toArray();
            Object[] values = new Object[jsArray.length];
            
            for (int i = 0; i < jsArray.length; i++) {
                values[i] = RhinoNon.script.convertToJava(jsArray[i], Object.class);
            }
            
            return values;
        }
        
        return defaultValue;
    }
    
    public String[] getStringArray(String key, String[] defaultValue) {
        if (has(key)) {
            Object[] jsArray = ((NativeArray)s.get(key, s)).toArray();
            String[] values = new String[jsArray.length];
            
            for (int i = 0; i < jsArray.length; i++) {
                values[i] = (String)RhinoNon.script.convertToJava(jsArray[i], String.class);
            }
            
            return values;
        }
        
        return defaultValue;
    }
    
    public boolean[] getBoolArray(String key, boolean[] defaultValue) {
        if (has(key)) {
            Object[] jsArray = ((NativeArray)s.get(key, s)).toArray();
            boolean[] values = new boolean[jsArray.length];
            
            for (int i = 0; i < jsArray.length; i++) {
                values[i] = (Boolean)RhinoNon.script.convertToJava(jsArray[i], Boolean.class);
            }
            
            return values;
        }
        
        return defaultValue;
    }
    
    public float[] getNumArray(String key, float[] defaultValue) {
        if (has(key)) {
            Object[] jsArray = ((NativeArray)s.get(key, s)).toArray();
            float[] values = new float[jsArray.length];
            for (int i = 0; i < jsArray.length; i++) {
                values[i] = (Float)RhinoNon.script.convertToJava(jsArray[i], Float.class);
            }
            
            return values;
        }
        
        return defaultValue;
    }
}