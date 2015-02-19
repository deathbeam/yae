package non;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import java.io.IOException;
import non.modules.*;

public class Non implements ApplicationListener, InputProcessor {
    public static final String TAG = "No Nonsense";
    public static final String E_RESOURCE = "Resource not found - ";
    public static final String E_ARGUMENT = "Argument not found - ";
    public static final String E_PLUGIN = "Plugin not found - ";
    
    public static AssetManager assets;
    public static NonScript script;
    public static JsonValue config;
    
    private boolean ready;
    private SpriteBatch loadingBatch;
    private Texture loadingBg, loadingImage, loadingBar, loadingBarBg;
    private Vector2 loadingPos, loadingBarPos;
    private float percent, barWidth, loadProgress;
    private int loadState;

    public Non(NonScript script) {
        this.script = script;
    }
    
    public void create () {
        loadingBatch = new SpriteBatch();
        assets = new AssetManager();
        
        assets.load("res/loading.png", Texture.class);
        assets.load("res/loading_bg.png", Texture.class);
        assets.load("res/loading_bar.png", Texture.class);
        assets.load("res/loading_bar_bg.png", Texture.class);
        assets.finishLoading();
        
        loadingImage = assets.get("res/loading.png", Texture.class);
        loadingBg = assets.get("res/loading_bg.png", Texture.class);
        loadingBar = assets.get("res/loading_bar.png", Texture.class);
        loadingBarBg = assets.get("res/loading_bar_bg.png", Texture.class);
    }

    public void render () {
        if (ready) {
            script.render();
            return;
        }
        
        if (loadCore()) {
            if (assets.update()) {
                if (Gdx.input.isTouched()) {
                    script.ready();
                    ready = true;
                    resize(getWidth(), getHeight());
                    loadingBatch.dispose();
                    assets.unload("res/loading.png");
                    assets.unload("res/loading_bg.png");
                    assets.unload("res/loading_bar.png");
                    assets.unload("res/loading_bar_bg.png");
                    loadingBatch = null;
                    loadingBg = null;
                    loadingImage = null;
                    loadingBar = null;
                    loadingBarBg = null;
                    return;
                }
            }
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        loadingBatch.begin();
        loadingBatch.draw(loadingBg, 
            0, 0, getWidth(), getHeight(), 
            0, 0, loadingBg.getWidth(), loadingBg.getHeight());
        loadingBatch.draw(loadingImage, loadingPos.x, loadingPos.y);
        loadingBatch.draw(loadingBarBg, loadingBarPos.x, loadingBarPos.y);
			
        percent = Interpolation.linear.apply(percent, 
            (loadProgress + (assets != null ? assets.getProgress() : 0f)) / 2f, 0.1f);
        barWidth = loadingBar.getWidth() * percent;
            
        loadingBatch.draw(loadingBar,
            loadingBarPos.x, loadingBarPos.y, 
            barWidth, loadingBar.getHeight(),
            0, 0, barWidth, loadingBar.getHeight());
			
        loadingBatch.end();
    }

    public void resize(int width, int height) { 
        if (ready) {
            script.resize(width, height);
            return;
        }
        
        loadingBatch.setProjectionMatrix(
            loadingBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height));
                
        loadingBarPos = new Vector2(
            (width - loadingBar.getWidth())/2,
            (height - loadingBar.getHeight())/2);
		
        loadingPos = new Vector2(
            (width - loadingImage.getWidth())/2,
            (height + loadingBar.getHeight())/2);
    }
	
    public void pause() {
        if (ready) script.pause(); 
    }
	
    public void resume() {
        if (ready) script.resume();
    }
    
    public boolean keyDown(int keycode) {
        if (ready) script.keyDown(keycode);
        return false;
    }

    public boolean keyUp(int keycode) {
        if (ready) script.keyUp(keycode);
        return false;
    }

    public boolean keyTyped (char character) {
        if (ready) script.keyTyped(character);
        return false;
    }
   
    public boolean touchDown (int x, int y, int pointer, int button) {
        if (ready) script.touchDown(x, y, pointer, button);
        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
       if (ready) script.touchUp(x, y, pointer, button);
       return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
       if (ready) script.touchDragged(x, y, pointer);
       return false;
    }

    public boolean mouseMoved (int x, int y) {
       if (ready) script.mouseMoved(x, y);
       return false;
    }

    public boolean scrolled (int amount) {
       if (ready) script.scrolled(amount);
       return false;
    }
    
    public void dispose () { 
        if (ready) script.close();
        assets.dispose();
    }
    
    private boolean loadCore() {
        if (loadState > 3) return true;
        
        switch (loadState) {
            case 0:
                config = new JsonReader().parse(file("config.json"));
                Gdx.input.setInputProcessor(this);
                break;
            case 1:
                if (getPlatform().equalsIgnoreCase("desktop")) {
                    int width = 800;
                    int height = 600;
                    boolean fullscreen = false;
                		
                    if (config.has("desktop")) {
                        JsonValue desktop = config.get("desktop");
                        if (desktop.has("display")) {
                            JsonValue display = desktop.get("display");
                            if (display.has("width")) width = display.getInt("width");
                            if (display.has("height")) height = display.getInt("height");
                            if (display.has("fullscreen")) fullscreen = display.getBoolean("fullscreen");
                        }
                    }
                		
                    Gdx.graphics.setDisplayMode(width, height, fullscreen);
                }
                
                break;
            case 2:
                if (config.has("name")) Gdx.graphics.setTitle(config.getString("name"));
                
                if (config.has("logging")) {
                    String logLevel = config.getString("logging");
                        
                    if ("".equalsIgnoreCase(logLevel))
                        Gdx.app.setLogLevel(0);
                    else if ("error".equalsIgnoreCase(logLevel))
                        Gdx.app.setLogLevel(1);
                    else if ("info".equalsIgnoreCase(logLevel))
                        Gdx.app.setLogLevel(2);
                    else if ("debug".equalsIgnoreCase(logLevel))
                        Gdx.app.setLogLevel(3);
                }
                    
                break;
            case 3:
                script.load(assets);   
                break;
        }
            
        loadState++;
        loadProgress += 0.25f;
        return false;
    }
    
    public static String getButton(int code) {
        if (code == com.badlogic.gdx.Input.Buttons.LEFT) return "Left";
        if (code == com.badlogic.gdx.Input.Buttons.RIGHT) return "Right";
        if (code == com.badlogic.gdx.Input.Buttons.MIDDLE) return "Middle";
        if (code == com.badlogic.gdx.Input.Buttons.BACK) return "Back";
        if (code == com.badlogic.gdx.Input.Buttons.FORWARD) return "Forward";
        return "unknown";
    }
    
    public static int getButton(String name) {
       if ("Left".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.LEFT;
       if ("Right".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.RIGHT;
       if ("Middle".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.MIDDLE;
       if ("Back".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.BACK;
       if ("Forward".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.FORWARD;
       return -1;
    }
    
    public static String getKey(int code) {
        return com.badlogic.gdx.Input.Keys.toString(code);
    }
    
    public static int getKey(String name) {
        return com.badlogic.gdx.Input.Keys.valueOf(name);
    }
    
    public static String getExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) extension = fileName.substring(i+1);
        return extension;
    }
    
    public static int getWidth() {
        return Gdx.graphics.getWidth();
    }
    
    public static int getHeight() {
        return Gdx.graphics.getHeight();
    }
    
    public static int getFPS() {
        return Gdx.graphics.getFramesPerSecond();
    }
    
    public static float getDelta() {
        return Gdx.graphics.getDeltaTime();
    }
    
    public static String getPlatform() {
        ApplicationType type = Gdx.app.getType();
        if (type == ApplicationType.Desktop) return "desktop";
        if (type == ApplicationType.Android) return "android";
        if (type == ApplicationType.iOS) return "ios";
        if (type == ApplicationType.Applet || type == ApplicationType.WebGL) return "web";
        return "unknown";
    }
    
    public static void error(String type, String msg) {
        Gdx.app.error(type, msg);
    }
    
    public static void log(String type, String msg) {
        Gdx.app.log(type, msg);
    }
    
    public static void debug(String type, String msg) {
        Gdx.app.debug(type, msg);
    }
    
    public static FileHandle file(String path) {
        return Gdx.files.internal(path);
    }
	
    public static void quit() {
        Gdx.app.exit();
    }
}