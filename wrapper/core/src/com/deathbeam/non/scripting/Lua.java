package com.deathbeam.non.scripting;

import java.io.IOException;
import java.util.HashMap;
import org.keplerproject.luajava.*;
import com.deathbeam.non.Non;

public class Lua extends ScriptRuntime {
	private LuaState engine;
	
	public Lua() {
		engine = LuaStateFactory.newLuaState();
        engine.openLibs();
	}

	public Object invoke(String object, String method, HashMap<String, Object> args) {
		String script = merge(object, method, args, ":", ",", "(", ")", "");
        try { return engine.LdoString(script); }
        catch(Exception e) { return null; }
	}

	public Object eval(String script) {
		try {
			return engine.LdoString(Non.getResource(script).readString());
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