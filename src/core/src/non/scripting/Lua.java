package non.scripting;

import java.util.HashMap;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;

public class Lua extends ScriptRuntime {
    public static String extension() { return "lua"; }
    public String version()          { return "5.1"; }
    
    private final Globals _G;
    
    public Lua() {
        _G = JsePlatform.standardGlobals();
    }
    
    public void init() {
        String script =
		"non.load = function(a) \n end \n" +
		"non.ready = function() \n end \n" +
		"non.update = function(a) \n end \n" +
		"non.draw = function() \n end \n" +
		"non.resize = function(a,b) \n end \n" +
		"non.pause = function() \n end \n" +
		"non.resume = function() \n end \n" +
		"non.close = function() \n end \n" +
		"non.keydown = function(a) \n end \n" +
		"non.keyup = function(a) \n end \n" +
		"non.keytyped = function(a) \n end \n" +
		"non.touchdown = function(a,b,c) \n end \n" +
		"non.touchup = function(a,b,c) \n end \n" +
		"non.touchdragged = function(a,b) \n end \n" +
		"non.mousemoved = function(a) \n end \n" +
		"non.scrolled = function(a) \n end \n";
        eval(script);
    }

    public Object invoke(String object, String method, Object... args) {
        LuaValue func = _G.get(object).get(method);
        
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
    
    public Object convert(Object javaValue) {
        return javaValue == null? LuaValue.NIL:
               javaValue instanceof LuaValue? javaValue:
               CoerceJavaToLua.coerce(javaValue);
    }
}