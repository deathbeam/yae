package com.codeindie.non;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import com.codeindie.non.scripting.ScriptRuntime;
import com.codeindie.non.plugins.PluginManager;

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
    private Core non;
    
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
        non = (Core)PluginManager.get("non");
        
        try {
            ScriptRuntime.getCurrent().eval(Utils.getResource(script));
        } catch (IOException ex) {
            Utils.error("Resource not found", script);
        }
        
        non.invoke("ready");
    }

    @Override
    public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        
        try {
            splash = new Sprite(new Texture(Utils.getResource("res/loading.png")));
        } catch (IOException ex) {
            Utils.warning("Resource not found", "loading.png");
            splash = new Sprite(new Texture(32, 32, Pixmap.Format.Alpha));
        }
        
        splash.setPosition(getWidth() / 2 - splash.getWidth() / 2, getHeight() / 2 - splash.getHeight() / 2);
        if (conf!=null) {
            loadMode = 1;
            text = "Loading assets";
            LOADED = true;
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
        
        non.invoke("update");
        non.invoke("draw");
    }

    @Override
    public void resize (int width, int height) {
        non.invoke("resize");
    }

    @Override
    public void pause () {
        non.invoke("pause");
    }

    @Override
    public void resume () {
        non.invoke("resume");
    }

    @Override
    public void dispose () {
        non.invoke("close");
        PluginManager.dispose();
    }
}
