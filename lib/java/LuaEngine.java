/*******************************************************************************
* Copyright (c) 2008-2013 LuaJ. All rights reserved.
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
******************************************************************************/
package non;

import java.io.*;
import javax.script.*;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import com.badlogic.gdx.files.FileHandle;

public class LuaEngine extends AbstractScriptEngine implements ScriptEngine, Compilable {
    private static final String __ENGINE_VERSION__   = Lua._VERSION;
    private static final String __NAME__             = "Luaj";
    private static final String __SHORT_NAME__       = "Luaj";
    private static final String __LANGUAGE__         = "lua";
    private static final String __LANGUAGE_VERSION__ = "5.2";
    private static final String __ARGV__             = "arg";
    private static final String __FILENAME__         = "?";
    
    private LuaContext context;

    public LuaEngine() {
        context = new LuaContext();
        context.setBindings(createBindings(), ScriptContext.ENGINE_SCOPE);
        setContext(context);
        put(LANGUAGE_VERSION, __LANGUAGE_VERSION__);
        put(LANGUAGE, __LANGUAGE__);
        put(ENGINE, __NAME__);
        put(ENGINE_VERSION, __ENGINE_VERSION__);
        put(ARGV, __ARGV__);
        put(FILENAME, __FILENAME__);
        put(NAME, __SHORT_NAME__);
        put("THREADING", null);
    }
    
    public CompiledScript compile(FileHandle file) throws ScriptException {
        return compile(file.reader(), file.name());
    }

    @Override
    public CompiledScript compile(String script) throws ScriptException {
        return compile(new StringReader(script));
    }

    @Override
    public CompiledScript compile(Reader script) throws ScriptException {
        return compile(script, "script");
    }

    public CompiledScript compile(Reader script, String name) throws ScriptException {
        put(FILENAME, name);
        try {
            final Globals g = context.globals;
            final LuaFunction f = g.load(script, name).checkfunction();
            return new LuajCompiledScript(f, g);
        } catch ( LuaError lee ) {
            throw new ScriptException(lee.getMessage());
        } catch ( Exception e ) {
            throw new ScriptException("eval threw "+e.toString());
        }
    }

    @Override
    public Object eval(Reader reader, Bindings bindings) throws ScriptException {
        return ((LuajCompiledScript) compile(reader)).eval(context.globals, bindings);
    }

    @Override
    public Object eval(String script, Bindings bindings) throws ScriptException {
        return eval(new StringReader(script), bindings);
    }

    @Override
    protected ScriptContext getScriptContext(Bindings nn) {
        throw new IllegalStateException("LuaEngine should not be allocating contexts.");
    }

    @Override
    public Bindings createBindings() {
        return new SimpleBindings();
    }

    @Override
    public Object eval(String script, ScriptContext context) throws ScriptException {
        return eval(new StringReader(script), context);
    }

    @Override
    public Object eval(Reader reader, ScriptContext context) throws ScriptException {
        return compile(reader).eval(context);
    }

    @Override
    public ScriptEngineFactory getFactory() {
        throw new IllegalStateException("LuaEngine do not supports getFactory().");
    }

    class LuajCompiledScript extends CompiledScript {
        final LuaFunction function;
        final Globals compiling_globals;
        
        LuajCompiledScript(LuaFunction function, Globals compiling_globals) {
            this.function = function;
            this.compiling_globals = compiling_globals;
        }

        public ScriptEngine getEngine() {
            return LuaEngine.this;
        }

        public Object eval() throws ScriptException {
            return eval(getContext());
        }
            
        public Object eval(Bindings bindings) throws ScriptException {
            return eval(((LuaContext) getContext()).globals, bindings);
        }

        public Object eval(ScriptContext context) throws ScriptException {
            return eval(((LuaContext) context).globals, context.getBindings(ScriptContext.ENGINE_SCOPE));
        }
            
        Object eval(Globals g, Bindings b) throws ScriptException {
            g.setmetatable(new BindingsMetatable(b));
            LuaFunction f = function;
            if (f.isclosure())
                f = new LuaClosure(f.checkclosure().p, g);
            else {
                try {
                    f = f.getClass().newInstance();
                } catch (Exception e) {
                    throw new ScriptException(e);
                }
                
                f.initupvalue1(g);
            }
            
            return toJava(f.invoke(LuaValue.NONE));
        }
    }
    
    static class BindingsMetatable extends LuaTable {
        BindingsMetatable(final Bindings bindings) {
            this.rawset(LuaValue.INDEX, new TwoArgFunction() {
                public LuaValue call(LuaValue table, LuaValue key) {
                    if (key.isstring()) return toLua(bindings.get(key.tojstring()));
                    else return this.rawget(key);
                }
            });
            this.rawset(LuaValue.NEWINDEX, new ThreeArgFunction() {
                public LuaValue call(LuaValue table, LuaValue key, LuaValue value) {
                    if (key.isstring()) {
                        final String k = key.tojstring();
                        final Object v = toJava(value);
                        if (v == null) bindings.remove(k);
                        else bindings.put(k, v);
                    } else {
                        this.rawset(key, value);
                    }
                    return LuaValue.NONE;
                }
            });
        }
    }
    
    static private LuaValue toLua(Object javaValue) {
        return javaValue == null? LuaValue.NIL:
            javaValue instanceof LuaValue? (LuaValue) javaValue:
            CoerceJavaToLua.coerce(javaValue);
    }

    static private Object toJava(LuaValue luajValue) {
        switch ( luajValue.type() ) {
        case LuaValue.TNIL: return null;
        case LuaValue.TSTRING: return luajValue.tojstring();
        case LuaValue.TUSERDATA: return luajValue.checkuserdata(Object.class);
        case LuaValue.TNUMBER: return luajValue.isinttype()?
            new Integer(luajValue.toint()): new Double(luajValue.todouble());
        default: return luajValue;
        }
    }

    static private Object toJava(Varargs v) {
        final int n = v.narg();
        switch (n) {
        case 0: return null;
        case 1: return toJava(v.arg1());
        default:
            Object[] o = new Object[n];
            for (int i=0; i<n; ++i)
                o[i] = toJava(v.arg(i+1));
            return o;
        }
    }
}