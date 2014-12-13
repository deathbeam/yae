package com.codeindie.non.scripting;

import java.io.IOException;
import java.util.HashMap;
import org.mozilla.javascript.*;
import com.codeindie.non.Non;

public class CoffeeScript extends JavaScript {
    public static String extension() { return "coffee"; }
    public String version()          { return "1.8.0"; }

    public Object eval(String script) {
        try {
            return engine.evaluateString(scope, initializer + compile(Non.getResource(script).readString()), "CoffeeScript", 1, null);
        } catch (IOException e) {
            return Non.error("Resource not found", script);
        }
    }
    
    private String compile(String script) {
        Context context = Context.enter();
        try {
            Scriptable compileScope = engine.newObject(scope);
            compileScope.setParentScope(scope);
            context.evaluateString(compileScope, Non.getResource("res/coffeescript.js").readString(), "CoffeeScript", 1, null);
            compileScope.put("script", compileScope, script);
            return (String)context.evaluateString(compileScope, "CoffeeScript.compile(script);", "CoffeeScript Compiler", 1, null);
        } catch (IOException e) {
			Non.error("Resource not found", "coffeescript.js");
            return "";
        } finally {
            Context.exit();
        }
    }
}