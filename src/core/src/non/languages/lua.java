package non.languages;

import java.util.HashMap;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;

public class lua extends Language {
    public String extension() { return "lua"; }
    public String version()   { return "5.1"; }
    
    private final Globals _G;
    
    public lua() {
        _G = JsePlatform.standardGlobals();
    }

    public Object invoke(String object, String method, Object... args) {
        LuaValue func;
        if (method != null) {
            func = _G.get(object).get(method);
        } else {
            func = _G.get(object);
        }

        if (func == LuaValue.NIL) return LuaValue.NIL;
        
        if (args != null) {
            LuaValue[] values = new LuaValue[args.length];
            for (int i = 0; i < args.length; i++) values[i] = (LuaValue)convert(args[i]);
			
            switch(values.length) {
                case 1: return func.call(values[0]);
                case 2: return func.call(values[0], values[1]);
                case 3: return func.call(values[0], values[1], values[2]);
            }
			
            return func.call(LuaValue.listOf(values));
        } else {
            return func.call();
        }
    }

    public Object eval(String script) {
        return _G.load(script).call();
    }

    public void put(String key, Object value) {
        _G.set((LuaValue)convert(key), (LuaValue)convert(value));
    }
    
    public Object get(String key) {
        return _G.get(key);
    }
    
    public Object convert(Object javaValue) {
        return javaValue == null? LuaValue.NIL:
               javaValue instanceof LuaValue? javaValue:
               CoerceJavaToLua.coerce(javaValue);
    }
}