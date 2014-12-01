package com.codeindie.non.scripting;

import java.io.IOException;
import java.util.HashMap;
import org.keplerproject.luajava.*;
import com.codeindie.non.Non;

public class Lua extends ScriptRuntime {
    public static String extension() { return "lua"; }
    public String version() { return "5.1"; }
    
	private LuaState engine;
    private String initializer = 
        "non.ready = function()\nend\n" +
        "non.update = function()\nend\n" +
        "non.draw = function()\nend\n" +
        "non.resize = function()\nend\n" +
        "non.close = function()\nend\n" +
        "non.pause = function()\nend\n" +
        "non.resume = function()\nend\n";
	
	public Lua() {
		engine = LuaStateFactory.newLuaState();
        engine.openLibs();
	}

	public Object invoke(String object, String method, HashMap<String, Object> args) {
		String script = merge(object, method, args, ":", ",", "(", ")", "");
        Non.log("test", script);
        try { return engine.LdoString(script); }
        catch(Exception e) { return Non.error("Lua error", e.getMessage()); }
	}

	public Object eval(String script) {
		try {
			return engine.LdoString(initializer + Non.getResource(script).readString());
		} catch (IOException e) {
			return Non.error("Resource not found", script);
		}
	}

	public void put(String key, Object value) {
        try { engine.pushObjectValue(value); }
        catch(LuaException e) { Non.warning("Lua", e.getMessage()); }
        engine.setGlobal(key);
	}

	public Object get(String key) {
		return null;
	}
}