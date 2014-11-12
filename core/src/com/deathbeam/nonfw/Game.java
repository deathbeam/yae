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

/**
 *
 * @author Thomas Slusny
 */
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.JsonValue;
import com.deathbeam.nonfw.audio.Audio;
import com.deathbeam.nonfw.graphics.Graphics;
import com.deathbeam.nonfw.graphics.Image;
import com.deathbeam.nonfw.input.Keyboard;
import com.deathbeam.nonfw.input.Mouse;
import com.deathbeam.nonfw.input.Touch;
import com.deathbeam.nonfw.math.Math;
import com.deathbeam.nonfw.network.Network;
import com.deathbeam.nonfw.physics.Physics;
import com.deathbeam.nonfw.scripting.CoffeeScript;
import com.deathbeam.nonfw.scripting.Groovy;
import com.deathbeam.nonfw.scripting.JavaScript;
import com.deathbeam.nonfw.scripting.Lua;
import com.deathbeam.nonfw.scripting.Python;
import com.deathbeam.nonfw.scripting.Ruby;
import com.deathbeam.nonfw.scripting.ScriptRuntime;
import com.deathbeam.nonfw.scripting.TypeScript;
import com.deathbeam.nonfw.tiled.Tiled;
import java.io.IOException;

public class Game implements ApplicationListener {
    // Game configuration holder
    private final JsonValue conf;
    
    // Splash screen variables
    public static boolean loaded = false;
    private Image splash;
    private String text;
    private int numPeriods;
    private float loadingTmr;
    private int loadMode;
    
    // Scripting runtime
    public static ScriptRuntime scripting;
    
    // Functions invoked from scripts
    public Object ready, draw, update, resize, close;
    
    // Game modules
    public Audio audio;
    public Graphics graphics;
    public Keyboard keyboard;
    public Mouse mouse;
    public Touch touch;
    public Math math;
    public Network network;
    public Tiled tiled;
    public Physics physics;
    
    public int getFPS() {
        return Gdx.graphics.getFramesPerSecond();
    }
    
    public float getDelta() {
        return Gdx.graphics.getDeltaTime();
    }
    
    public void warning(String type, String msg) {
        Utils.warning(type, msg);
    }
    
    public void error(String type, String msg) {
        Utils.error(type, msg);
    }
    
    public void log(String type, String msg) {
        Utils.log(type, msg);
    }
    
    public void debug(String type, String msg) {
        Utils.debug(type, msg);
    }
    
    public void dump(Object obj) {
        Utils.dump(obj);
    }
    
    public Object load(String scriptPath) throws IOException {
        return scripting.eval(Utils.getResource(scriptPath));
    }
    
    public Game(JsonValue args) {
        conf = args;
    }
    
    private void init() {
        // Hide splash screen
        loadMode = 0;
        graphics.dispose();
        graphics = null;
        
        // Load needed modules
        String[] mods = conf.get("modules").asStringArray();
        for (String mod : mods) {
            if ("audio".equalsIgnoreCase(mod)) audio = new Audio();
            else if ("graphics".equalsIgnoreCase(mod)) graphics = new Graphics();
            else if ("keyboard".equalsIgnoreCase(mod)) keyboard = new Keyboard();
            else if ("mouse".equalsIgnoreCase(mod)) mouse = new Mouse();
            else if ("touch".equalsIgnoreCase(mod)) touch = new Touch();
            else if ("math".equalsIgnoreCase(mod)) math = new Math();
            else if ("tiled".equalsIgnoreCase(mod)) tiled = new Tiled();
            else if ("network".equalsIgnoreCase(mod)) network = new Network();
            else if ("physics".equalsIgnoreCase(mod)) physics = new Physics();
        }
        
        // Initialize scripting runtime
        String ext = Utils.getExtension(conf.getString("main"));
        if (ext.equalsIgnoreCase(JavaScript.getExtension())) scripting = new JavaScript();
        else if (ext.equalsIgnoreCase(CoffeeScript.getExtension())) scripting = new CoffeeScript();
        else if (ext.equalsIgnoreCase(TypeScript.getExtension())) scripting = new TypeScript();
        else if (ext.equalsIgnoreCase(Lua.getExtension())) scripting = new Lua();
        else if (ext.equalsIgnoreCase(Python.getExtension())) scripting = new Python();
        else if (ext.equalsIgnoreCase(Ruby.getExtension())) scripting = new Ruby();
        else if (ext.equalsIgnoreCase(Groovy.getExtension())) scripting = new Groovy();
        
        // Evaluate and run scripts
        scripting.put("non", this);
        String script = conf.getString("main");
        try {
            scripting.eval(Utils.getResource(script));
        } catch (IOException ex) {
            Utils.error("Resource not found", script);
        }
        scripting.put("int_ready", ready);
        scripting.put("int_draw", draw);
        scripting.put("int_update", update);
        scripting.put("int_resize", resize);
        scripting.put("int_close", close);
        if (ready!=null) scripting.invoke("int_ready");
    }

    @Override
    public void create () {
        graphics = new Graphics();
        try {
            splash = new Image(Utils.getInternalResource("splash.png"));
        } catch (IOException ex) {
            Utils.warning("Resource not found", "splash.png");
            splash = new Image();
        }
        
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
            graphics.begin();
            graphics.draw(splash, Gdx.graphics.getWidth() / 2 - splash.getWidth() / 2, Gdx.graphics.getHeight() / 2 - splash.getHeight() / 2);
            graphics.draw(text + Utils.repeat(".", numPeriods), (int) (Gdx.graphics.getWidth() / 2 - graphics.getFont().getBounds(text).width /2), Gdx.graphics.getHeight() / 2 + splash.getHeight() / 2 + 15, Color.BLACK);
            graphics.end();
            
            if (loadMode == 1) {
                loadingTmr += Gdx.graphics.getDeltaTime();
                if (loadingTmr > 0.3f) {
                    numPeriods = (numPeriods + 1) % 4;
                    loadingTmr = 0;
                }
                if(loaded) init();
            }
            return;
        }
        
        if (update!=null) scripting.invoke("int_update");
        if (graphics != null) {
            graphics.begin();
            if (draw!=null) scripting.invoke("int_draw");
            graphics.end();
        }
    }

    @Override
    public void resize (int width, int height) {
        if (resize!=null) scripting.invoke("int_resize");
        if (graphics!=null) graphics.resize().update();
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
        if (close!=null) scripting.invoke("int_close");
        if (graphics!=null) graphics.dispose();
        if (physics!=null) physics.dispose();
        Utils.clearCache();
    }
}