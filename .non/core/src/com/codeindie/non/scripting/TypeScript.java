package com.codeindie.non.scripting;

import java.io.IOException;
import java.util.HashMap;
import org.mozilla.javascript.*;
import com.codeindie.non.Non;

public class TypeScript extends JavaScript {
    public static String extension() { return "ts"; }
    public String version()          { return "0.9"; }
    
    public Object eval(String script) {
        try {
            return engine.evaluateString(scope, initializer + compile(Non.getResource(script).readString()), "TypeScript", 1, null);
        } catch (IOException e) {
            return Non.error("Resource not found", script);
        }
    }
    
    private String compile(String script) {
        Context context = Context.enter();
        try {
            Scriptable compileScope = engine.newObject(scope);
            compileScope.setParentScope(scope);
            context.evaluateString(compileScope, Non.getResource("res/typescript.js").readString(), "TypeScript", 1, null);
            context.evaluateString(compileScope, Non.getResource("res/typescript.compile.js").readString(), "TypeScript", 1, null);
            compileScope.put("script", compileScope, script);
            return (String)context.evaluateString(compileScope, "TScompile(script);", "TypeScript Compiler", 1, null);
        } catch (IOException e) {
			Non.error("Resource not found", "typescript.js");
            return "";
        } finally {
            Context.exit();
        }
    }
}