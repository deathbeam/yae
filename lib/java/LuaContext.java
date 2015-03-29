/*******************************************************************************
* Copyright (c) 2013 LuaJ. All rights reserved.
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;

import javax.script.ScriptContext;
import javax.script.SimpleScriptContext;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.luajc.LuaJC;

public class LuaContext extends SimpleScriptContext implements ScriptContext {
    public final Globals globals;
    private final InputStream stdin;
    private final PrintStream stdout;
    private final PrintStream stderr;
    
    public LuaContext() {
        this("true".equals(System.getProperty("org.luaj.debug")),
            "true".equals(System.getProperty("org.luaj.luajc")));
    }
    
    public LuaContext(boolean createDebugGlobals, boolean useLuaJCCompiler) {
        globals = createDebugGlobals?
                JsePlatform.debugGlobals():
                JsePlatform.standardGlobals();
            if (useLuaJCCompiler)
                LuaJC.install(globals);
            stdin = globals.STDIN;
            stdout = globals.STDOUT;
            stderr = globals.STDERR;
    }
    
    @Override
    public void setErrorWriter(Writer writer) {
        globals.STDERR = writer != null?
                new PrintStream(new WriterOutputStream(writer)):
                stderr;
    }

    @Override
    public void setReader(Reader reader) {
        globals.STDIN = reader != null?
                new ReaderInputStream(reader):
                stdin;
    }

    @Override
    public void setWriter(Writer writer) {
        globals.STDOUT = writer != null?
                new PrintStream(new WriterOutputStream(writer), true):
                stdout;
    }

    static final class WriterOutputStream extends OutputStream {
        final Writer w;
        WriterOutputStream(Writer w) {
            this.w = w;
        }
        public void write(int b) throws IOException {
            w.write(new String(new byte[] {(byte)b}));
        }
        public void write(byte[] b, int o, int l) throws IOException {
            w.write(new String(b, o, l));
        }
        public void write(byte[] b) throws IOException {
            w.write(new String(b));
        }
        public void close() throws IOException {
            w.close();
        }
        public void flush() throws IOException {
            w.flush();
        }
    }
    
    static final class ReaderInputStream extends InputStream {
        final Reader r;
        ReaderInputStream(Reader r) {
            this.r = r;
        }
        public int read() throws IOException {
            return r.read();
        }
    }
}