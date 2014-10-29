/*
 * The MIT License
 *
 * Copyright 2014 Tomas.
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
package com.deathbeam.nonfw;

/**
 *
 * @author Tomas
 */
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.io.IOException;

public class TypeScriptCompiler {
    private static Scriptable scope;
    public static boolean LOADED;
    public static String NAME = "TypeScript";
    public static String EXTENSION = "ts";
    
    public static void init() {
        if (LOADED) return;
        Context context = Context.enter();
        context.setOptimizationLevel(-1); // Without this, Rhino hits a 64K bytecode limit and fails
        try {
            scope = context.initStandardObjects();
            context.evaluateString(scope, Utils.readFile(Utils.getInternalResource("langs/typescript.js").read()), "typescript.js", 0, null);
            context.evaluateString(scope, Utils.readFile(Utils.getInternalResource("langs/typescript.compile.js").read()), "typescript.compile.js", 0, null);
        } catch (IOException ex) {
            Utils.error(NAME, ex.getMessage());
        } finally {
            Context.exit();
        }
        LOADED = true;
    }

    public static String compile (String script) {
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