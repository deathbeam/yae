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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.JsonValue;
import com.deathbeam.non.audio.Audio;
import com.deathbeam.non.graphics.Graphics;
import com.deathbeam.non.graphics.Image;
import com.deathbeam.non.input.Keyboard;
import com.deathbeam.non.input.Mouse;
import com.deathbeam.non.input.Touch;
import com.deathbeam.non.lights.Lights;
import com.deathbeam.non.math.Math;
import com.deathbeam.non.network.Network;
import com.deathbeam.non.physics.Physics;
import com.deathbeam.non.scripting.CoffeeScript;
import com.deathbeam.non.scripting.Groovy;
import com.deathbeam.non.scripting.JavaScript;
import com.deathbeam.non.scripting.Lua;
import com.deathbeam.non.scripting.Python;
import com.deathbeam.non.scripting.Ruby;
import com.deathbeam.non.scripting.ScriptRuntime;
import com.deathbeam.non.scripting.TypeScript;
import com.deathbeam.non.tiled.Tiled;

import java.io.IOException;

public class Game implements ApplicationListener {
    // Game configuration holder
    private final JsonValue conf;
    
    // Splash screen variables
    public static boolean LOADED = false;
    private Image splash;
    private String text;
    private float loadingTmr;
    private int numPeriods, loadMode;
    
    // Scripting runtime
    public static ScriptRuntime scripting;
    
    // Functions invoked from scripts
    public Object ready, draw, update, resize, close, pause, resume;
    
    // Game modules
    public static Audio audio;
    public static Graphics graphics;
    public static Keyboard keyboard;
    public static Mouse mouse;
    public static Touch touch;
    public static Math math;
    public static Network network;
    public static Tiled tiled;
    public static Physics physics;
    public static Lights lights;
	
	public int getWidth() {
		return Gdx.graphics.getWidth();
	}
	
	public int getHeight() {
		return Gdx.graphics.getHeight();
	}
    
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
            else if ("lights".equalsIgnoreCase(mod)) lights = new Lights();
        }
        
        // Initialize scripting runtime
		String script = conf.getString("main");
        String ext = Utils.getExtension(script);
        if (ext.equalsIgnoreCase(JavaScript.getExtension())) scripting = new JavaScript();
        else if (ext.equalsIgnoreCase(CoffeeScript.getExtension())) scripting = new CoffeeScript();
        else if (ext.equalsIgnoreCase(TypeScript.getExtension())) scripting = new TypeScript();
        else if (ext.equalsIgnoreCase(Lua.getExtension())) scripting = new Lua();
        else if (ext.equalsIgnoreCase(Python.getExtension())) scripting = new Python();
        else if (ext.equalsIgnoreCase(Ruby.getExtension())) scripting = new Ruby();
        else if (ext.equalsIgnoreCase(Groovy.getExtension())) scripting = new Groovy();
        
        // Evaluate and run scripts
        scripting.put("non", this);
        try {
            scripting.eval(Utils.getResource(script));
        } catch (IOException ex) {
            Utils.error("Resource not found", script);
        }
        if (ready!=null) scripting.invoke("non", "ready");
    }

    @Override
    public void create () {
        graphics = new Graphics();
        try {
            splash = new Image(Utils.getInternalResource("res/loading.png"));
        } catch (IOException ex) {
            Utils.warning("Resource not found", "loading.png");
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
                if(LOADED) init();
            }
            return;
        }
        
        if (update!=null) scripting.invoke("non", "update");
        if (graphics != null && draw != null) {
            graphics.begin();
            scripting.invoke("non", "draw");
            graphics.end();
        }
    }

    @Override
    public void resize (int width, int height) {
		if (graphics!=null) graphics.resize().update();
        if (resize!=null) scripting.invoke("non", "resize");
    }

    @Override
    public void pause () {
		if (pause!=null) scripting.invoke("non", "pause");
    }

    @Override
    public void resume () {
		if (resume!=null) scripting.invoke("non", "resume");
    }

    @Override
    public void dispose () {
        if (close!=null) scripting.invoke("non", "close");
        if (graphics!=null) graphics.dispose();
        if (physics!=null) physics.dispose();
        Utils.clearCache();
    }
}
