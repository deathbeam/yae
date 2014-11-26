package com.codeindie.non.scripting;

import com.badlogic.gdx.files.FileHandle;
import com.codeindie.non.Utils;
import java.io.IOException;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class CoffeeScript extends ScriptRuntime {
    private Scriptable scope;

    public CoffeeScript() {
        Context context = Context.enter();
        context.setOptimizationLevel(-1);
        try {
            scope = context.initStandardObjects();
            context.evaluateString(scope, Utils.readFile(Utils.getResource("res/coffeescript.js").read()), "coffeescript.js", 0, null);
        } catch (IOException ex) {
            Utils.error("scripting", ex.getMessage());
        } finally {
            Context.exit();
        }
        
        e = new ScriptEngineManager().getEngineByName("JavaScript");
        ScriptEngineFactory f = e.getFactory();
        
        System.out.println( "Engine name: " +f.getEngineName() );
        System.out.println( "Engine Version: " +f.getEngineVersion() );
        System.out.println( "LanguageName: CoffeeScript" );
        System.out.println( "Language Version: 1.8.0" );
    }
    
    @Override
    public void invoke(String pack, String funct) {
        try {
            e.eval(pack + "." + funct + "();");
        } catch (ScriptException ex) {
            Utils.log("scripting", ex.getMessage());
        }
    }
    
    @Override
    public void invoke(String pack, String funct, String args) {
        try {
            e.eval(pack + "." + funct + "(" + args + ");");
        } catch (ScriptException ex) {
            Utils.log("scripting", ex.getMessage());
        }
    }

    @Override
    public Object eval(FileHandle file) {
        try {
            return e.eval(compile(Utils.readFile(file.read())));
        } catch (ScriptException ex) {
            Utils.warning("Scripting", ex.getMessage());
        } catch (IOException ex) {
            Utils.error("Resource not found", ex.getMessage());
        }
        return null;
    }
    
    private String compile (String script) {
        Context context = Context.enter();
        try {
            Scriptable compileScope = context.newObject(scope);
            compileScope.setParentScope(scope);
            compileScope.put("script", compileScope, script);
            return (String)context.evaluateString(compileScope, "CoffeeScript.compile(script);", "CoffeeScriptCompiler", 1, null);
        } finally {
            Context.exit();
        }
    }
}
