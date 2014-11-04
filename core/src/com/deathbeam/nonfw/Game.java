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
import com.deathbeam.nonfw.graphics.Image;
import com.deathbeam.nonfw.graphics.Graphics;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.JsonValue;
import com.deathbeam.nonfw.audio.Audio;
import com.deathbeam.nonfw.input.Keyboard;
import com.deathbeam.nonfw.input.Mouse;
import com.deathbeam.nonfw.math.Math;
import com.deathbeam.nonfw.input.Touch;
import com.deathbeam.nonfw.network.Network;
import com.deathbeam.nonfw.tiled.Tiled;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;

public class Game implements ApplicationListener {
    public static boolean loaded = false;
    
    private final JsonValue conf;
    private Graphics gfx;
    private Image splash;
    private String text;
    private float loadingTmr;
    private boolean drawSplash = true;
    private ScriptRuntime scripting;
    
    public Object ready;
    public Object draw;
    public Object update;
    
    public Audio audio;
    public Graphics graphics;
    public Keyboard keyboard;
    public Mouse mouse;
    public Touch touch;
    public Math math;
    public Network network;
    public Tiled tiled;
    
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
    
    public Game(JsonValue args) throws ScriptException, IOException {
        conf = args;
    }
    
    private void init() {
        String[] mods = conf.get("modules").asStringArray();
        for (String mod : mods) {
            if ("audio".equals(mod)) audio = new Audio();
            if ("graphics".equals(mod)) graphics = new Graphics();
            if ("keyboard".equals(mod)) keyboard = new Keyboard();
            if ("mouse".equals(mod)) mouse = new Mouse();
            if ("touch".equals(mod)) touch = new Touch();
            if ("math".equals(mod)) math = new Math();
            if ("tiled".equals(mod)) tiled = new Tiled();
            if ("network".equals(mod)) network = new Network();
        }
        
        String ext = Utils.getExtension(conf.getString("main"));
        if (ext.equalsIgnoreCase(JavaScript.getExtension())) {
            scripting = new JavaScript();
        } else if (ext.equalsIgnoreCase(CoffeeScript.getExtension())) {
            scripting = new CoffeeScript();
        } else if (ext.equalsIgnoreCase(TypeScript.getExtension())) {
            scripting = new TypeScript();
        } else if (ext.equalsIgnoreCase(Lua.getExtension())) {
            scripting = new Lua();
        }
        
        scripting.put("non", this);
        
        try {
            scripting.eval(Utils.getResource(conf.getString("main")));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        scripting.put("int_ready", ready);
        scripting.put("int_draw", draw);
        scripting.put("int_update", update);
        scripting.invoke("int_ready");
    }

    @Override
    public void create () {
        gfx = new Graphics();
        text = "Loading assets";
        try {
            splash = new Image(Utils.getInternalResource("splash.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        Utils.loadPackage();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if(drawSplash) {
            loadingTmr += Gdx.graphics.getDeltaTime();
            if (loadingTmr > 0.3f) {
                if ("Loading assets".equals(text))
                    text = "Loading assets.";
                else if ("Loading assets.".equals(text))
                    text = "Loading assets..";
                else if ("Loading assets..".equals(text))
                    text = "Loading assets...";
                else if ("Loading assets...".equals(text))
                    text = "Loading assets";
                loadingTmr = 0;
            }
            gfx.begin();
            gfx.draw(splash, Gdx.graphics.getWidth() / 2 - splash.getWidth() / 2, Gdx.graphics.getHeight() / 2 - splash.getHeight() / 2);
            gfx.draw(text, (int) (Gdx.graphics.getWidth() / 2 - gfx.getFont().getBounds(text).width /2), Gdx.graphics.getHeight() / 2 + splash.getHeight() / 2 + 15, Color.BLACK);
            gfx.end();
            if(loaded) { init(); drawSplash = false; }
            return;
        }
        
        scripting.invoke("int_update");
        if (graphics != null) {
            graphics.begin();
            scripting.invoke("int_draw");
            graphics.end();
        }
    }

    @Override
    public void resize (int width, int height) {
        //if (loaded && scripting!= null) scripting.invoke("int_resize");
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
        //if (loaded && scripting!= null) scripting.invoke("int_dispose");
        Utils.clearCache();
    }
}