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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;

public class Game implements ApplicationListener {
    public static boolean loaded = false;
    public static ScriptEngine js;
    
    private final JsonValue conf;
    private Graphics gfx;
    private Image splash;
    private String text;
    private float loadingTmr;
    private boolean drawSplash = true;
    
    public Game(JsonValue args) throws ScriptException, IOException {
        conf = args;
    }
    
    private void init() {
        try {
            js = new ScriptEngine();
            js.eval(Utils.getResource(conf.getString("main")));
            js.invoke("int_init", conf);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        
        js.invoke("int_update");
        js.invoke("int_draw");
    }

    @Override
    public void resize (int width, int height) {
        if (loaded && js!= null) js.invoke("int_resize");
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
        if (loaded && js!= null) js.invoke("int_dispose");
        Utils.clearCache();
    }
}