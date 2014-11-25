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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import com.deathbeam.non.scripting.ScriptRuntime;
import com.deathbeam.non.plugins.PluginManager;

import java.io.IOException;

public class Game implements ApplicationListener {
    // Game configuration holder
    private final JsonValue conf;
    
    // Splash screen variables
    public static boolean LOADED = false;
    private Sprite splash;
    private String text;
    private float loadingTmr;
    private int numPeriods, loadMode;
    private SpriteBatch batch;
    private BitmapFont font;
    
    // Functions invoked from scripts
    public Object ready, draw, update, resize, close, pause, resume;

	  public int getWidth() { return Gdx.graphics.getWidth(); }
    public int getHeight() { return Gdx.graphics.getHeight(); }
    public int getFPS() { return Gdx.graphics.getFramesPerSecond(); }
    public float getDelta() { return Gdx.graphics.getDeltaTime(); }
    public void warning(String type, String msg) { Utils.warning(type, msg); }
    public void error(String type, String msg) { Utils.error(type, msg); }
    public void log(String type, String msg) { Utils.log(type, msg); }
    public void debug(String type, String msg) { Utils.debug(type, msg); }
    public void dump(Object obj) { Utils.dump(obj); }
    
    public Object load(String scriptPath) throws IOException {
        return scripting.eval(Utils.getResource(scriptPath));
    }
    
    public Game(JsonValue args) {
        conf = args;
    }
    
    private void init() {
        // Hide splash screen
        loadMode = 0;
        batch.dispose();
        batch = null;
        font.dispose();
        font = null;
        
        // Initialize scripting runtime
        String script = conf.getString("main");
        String ext = Utils.getExtension(script);
        ScriptRuntime.setCurrent(ScriptRuntime.byExtension(ext));
        PluginManager.load();
        // Evaluate and run scripts
        ScriptRuntime.getCurrent().put("non", this);
        try {
            ScriptRuntime.getCurrent().eval(Utils.getResource(script));
        } catch (IOException ex) {
            Utils.error("Resource not found", script);
        }
        if (ready!=null) ScriptRuntime.getCurrent().invoke("non", "ready");
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        
        try {
            splash = new Sprite(new Texture(Utils.getInternalResource("res/loading.png")));
        } catch (IOException ex) {
            Utils.warning("Resource not found", "loading.png");
            splash = new Sprite(new Texture(32, 32, Pixmap.Format.Alpha));
        }
        
        splash.setPosition(getWidth() / 2 - splash.getWidth() / 2, getHeight() / 2 - splash.getHeight() / 2);
        PluginManager.load();
        if (conf!=null) {
            loadMode = 1;
            text = "Loading assets";
            Utils.loadPackage();
        } else {
            loadMode = 2;
            text = "Game not found! Try http://deathbeam.github.io/non";
        }
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if(loadMode > 0) {
            batch.begin();
            splash.draw(batch);
            font.draw(batch, text + Utils.repeat(".", numPeriods), (int) (getWidth() / 2 - font.getBounds(text).width /2), getHeight() / 2 + splash.getHeight() / 2 + 15);
            batch.end();
            
            if (loadMode == 1) {
                loadingTmr += Gdx.graphics.getDeltaTime();
                if (loadingTmr > 0.3f) {
                    numPeriods = (numPeriods + 1) % 4;
                    loadingTmr = 0;
                }
                if(LOADED) init();
            }
            return;
        }
        
        if (update!=null) ScriptRuntime.getCurrent().invoke("non", "update");
        if (draw!=null) ScriptRuntime.getCurrent().invoke("non", "draw");
    }

    @Override
    public void resize (int width, int height) {
        if (resize!=null) ScriptRuntime.getCurrent().invoke("non", "resize");
    }

    @Override
    public void pause () {
        if (pause!=null) ScriptRuntime.getCurrent().invoke("non", "pause");
    }

    @Override
    public void resume () {
        if (resume!=null) ScriptRuntime.getCurrent().invoke("non", "resume");
    }

    @Override
    public void dispose () {
        if (close!=null) ScriptRuntime.getCurrent().invoke("non", "close");
        Utils.clearCache();
    }
}
