/*
 * The MIT License
 *
 * Copyright 2014 Thomas Slusny.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.deathbeam.non.scripting;

import com.badlogic.gdx.files.FileHandle;
import com.deathbeam.non.Utils;

import java.io.IOException;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 *
 * @author Thomas Slusny
 */
public class TypeScript extends ScriptRuntime {
    private Scriptable scope;
    
    public static String getName() {
        return "TypeScript";
    }

    public static String getExtension() {
        return "ts";
    }
    
    public TypeScript() {
        Context context = Context.enter();
        context.setOptimizationLevel(-1); // Without this, Rhino hits a 64K bytecode limit and fails
        try {
            scope = context.initStandardObjects();
            context.evaluateString(scope, Utils.readFile(Utils.getInternalResource("typescript.js").read()), "typescript.js", 0, null);
            context.evaluateString(scope, Utils.readFile(Utils.getInternalResource("typescript.compile.js").read()), "typescript.compile.js", 0, null);
        } catch (IOException ex) {
            Utils.error("scripting", ex.getMessage());
        } finally {
            Context.exit();
        }
        
        e = new ScriptEngineManager().getEngineByName("JavaScript");
        ScriptEngineFactory f = e.getFactory();
        
        System.out.println( "Engine name: " +f.getEngineName() );
        System.out.println( "Engine Version: " +f.getEngineVersion() );
        System.out.println( "LanguageName: TypeScript" );
        System.out.println( "Language Version: 0.9.1" );
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
            e.eval(compile(Utils.readFile(file.read())));
        } catch (ScriptException ex) {
            Utils.warning("scripting", ex.getMessage());
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
            return (String)context.evaluateString(compileScope, "TScompile(script);", "TypeScriptCompiler", 1, null);
        } finally {
            Context.exit();
        }
    }
}
