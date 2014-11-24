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

import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 *
 * @author Thomas Slusny
 */
public abstract class ScriptRuntime {
    private static ScriptRuntime current;
    
    public static void setCurrent(ScriptRuntime runtime) { current = runtime; }
    public static void getCurrent() { return current; }
    
    public static ScriptRuntime byExtension(String ext) {
        if ("coffee".equalsIgnoreCase(ext)) return new CoffeeScript();
        if ("groovy".equalsIgnoreCase(ext)) return new Groovy();
        if ("js".equalsIgnoreCase(ext)) return new JavaScript();
        if ("lua".equalsIgnoreCase(ext)) return new Lua();
        if ("py".equalsIgnoreCase(ext)) return new Python();
        if ("rb".equalsIgnoreCase(ext)) return new Ruby();
        if ("ts".equalsIgnoreCase(ext)) return new TypeScript();
    }
    
    protected ScriptEngine e;
    
    public abstract void invoke(String pack, String funct);
    public abstract void invoke(String pack, String funct, String args);
    public abstract Object eval(FileHandle file);

    public Object eval(String script) {
        try {
            return e.eval(script);
        } catch (ScriptException ex) {
            Utils.warning("Scripting", ex.getMessage());
            return null;
        }
    }
    
    public void put(String key, Object value) {
        if (e!=null) e.put(key, value);
    }

    public Object get(String key) {
        if (e==null) return null;
        return e.get(key);
    }
}
