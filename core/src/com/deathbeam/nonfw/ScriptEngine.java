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
package com.deathbeam.nonfw;

import com.badlogic.gdx.files.FileHandle;
import java.io.IOException;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 *
 * @author Thomas Slusny
 */
public final class ScriptEngine {
    private final Context context;
    private final Scriptable main;
    private final Scriptable non;
    
    public ScriptEngine() throws IOException {
        context = Context.enter();
        main = context.initStandardObjects();
        eval(Utils.getInternalResource("game.js"));
        non = (Scriptable)main.get("non", main);
    }
    
    public void eval(FileHandle handle) {
        try {
            String ext = Utils.getExtension(handle.name());
            String script = Utils.readFile(handle.read());
            if ("js".equals(ext)) {
                context.evaluateString(main, script, "JavaScript", 1, null);
            } else if (CoffeeScriptCompiler.EXTENSION.equals(ext)) {
                CoffeeScriptCompiler.init();
                context.evaluateString(main, CoffeeScriptCompiler.compile(script), CoffeeScriptCompiler.NAME, 1, null);
            } else if (TypeScriptCompiler.EXTENSION.equals(ext)) {
                TypeScriptCompiler.init();
                context.evaluateString(main, TypeScriptCompiler.compile(script), TypeScriptCompiler.NAME, 1, null);
            } else if (RoyCompiler.EXTENSION.equals(ext)) {
                RoyCompiler.init();
                context.evaluateString(main, RoyCompiler.compile(script), RoyCompiler.NAME, 1, null);
            }
        } catch (IOException ex) {
            Utils.error("ScriptEngine", ex.getMessage());
        }
    }
    
    public void put(String key, Object obj) {
        main.put(key, main, obj);
    }
    
    public Object get(String key) {
        return main.get(key, main);
    }
    
    public void invoke(String method, Object... args) {
        ScriptableObject.callMethod(non, method, args);
    }
}