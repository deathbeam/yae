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
package com.deathbeam.non;

/**
 *
 * @author Thomas Slusny
 */
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.deathbeam.non.scripting.ScriptRuntime;
import com.deathbeam.non.plugins.Plugins;
import java.io.IOException;

public class Non implements ApplicationListener {
    public static ScriptRuntime script;
    // Game configuration holder
    private JsonValue args;
    
    public static JsonValue configure() {
        try { return new JsonReader().parse(getResource("non.cfg")); }
        catch(IOException e) { error("Resource not found", "non.cfg"); }
        return null;
    }
    
    public static String getExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) extension = fileName.substring(i+1);
        return extension;
    }

	public static int getWidth() { return Gdx.graphics.getWidth(); }
    public static int getHeight() { return Gdx.graphics.getHeight(); }
    public static int getFPS() { return Gdx.graphics.getFramesPerSecond(); }
    public static float getDelta() { return Gdx.graphics.getDeltaTime(); }
    public static Object warning(String type, String msg) { Gdx.app.error(type, msg); return null; }
    public static Object error(String type, String msg) { Gdx.app.error(type, msg); Gdx.app.exit(); return null; }
    public static Object log(String type, String msg) { Gdx.app.log(type, msg); return null; }
    public static Object debug(String type, String msg) { Gdx.app.debug(type, msg); return null; }
    public static Object dump(Object obj) { System.out.println(obj); return null; }
    public static FileHandle getResource(String path) throws IOException { return Gdx.files.internal(path); }

    @Override
    public void create () {
        args = configure();
        String main = args.getString("main");
        script = ScriptRuntime.byExtension(getExtension(main));
        Plugins.load();
        
        script.eval(main);
        script.invoke("non", "ready", null);
    }

    public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        script.invoke("non", "update", null);
        script.invoke("non", "draw", null);
    }

    public void resize (int width, int height) {
    	script.invoke("non", "resize", null);
    }

    public void pause () {
    	script.invoke("non", "pause", null);
    }

    public void resume () {
    	script.invoke("non", "resume", null);
    }

    public void dispose () {
    	script.invoke("non", "close", null);
    }
}
