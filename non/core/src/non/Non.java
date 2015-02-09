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
import non.script.JavaScript;
import java.io.IOException;
import non.modules.*;

public class Non implements ApplicationListener, InputProcessor {
    public static final String TAG = "No Nonsense";
    public static final String E_RESOURCE = "Resource not found - ";
    public static final String E_ARGUMENT = "Argument not found - ";
    public static final String E_PLUGIN = "Plugin not found - ";
    
    public static boolean ready;
    public static JavaScript script;
    public static AssetManager assets;
    private static JsonValue config;
    
    private SpriteBatch loadingBatch;
    private Texture loadingBg, loadingImage, loadingBar, loadingBarBg;
    private Vector2 loadingPos, loadingBarPos;
    private float percent, barWidth;
    
    public static String getButton(int code) {
        if (code == com.badlogic.gdx.Input.Buttons.LEFT) return "left";
        if (code == com.badlogic.gdx.Input.Buttons.RIGHT) return "right";
        if (code == com.badlogic.gdx.Input.Buttons.MIDDLE) return "middle";
        if (code == com.badlogic.gdx.Input.Buttons.BACK) return "back";
        if (code == com.badlogic.gdx.Input.Buttons.FORWARD) return "forward";
        return "unknown";
    }
    
    public static int getButton(String name) {
       if ("left".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.LEFT;
       if ("right".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.RIGHT;
       if ("middle".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.MIDDLE;
       if ("back".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.BACK;
       if ("forward".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.FORWARD;
       return -1;
    }
    
    public static String getKey(int code) {
        return com.badlogic.gdx.Input.Keys.toString(code).toLowerCase();
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
    
    public static JsonValue getConfig() {
        return config;
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
    
    public void create () {
        ready = false;
        config = new JsonReader().parse(file("config.json"));
        loadingBatch = new SpriteBatch();
        assets = new AssetManager();
        script = new JavaScript();
        script.put("non", new RootObject());
        script.eval(file("res/non.js").readString(), "non.js");
        Gdx.input.setInputProcessor(this);
        
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
        
        FileHandle fl = file("res/loading.png");
        
        if (fl.exists()) {
            loadingImage = new Texture(fl);
        } else {
            error(TAG, E_RESOURCE + fl.name());
            quit();
        }
        
        fl = file("res/loading_bg.png");
        
        if (fl.exists()) {
            loadingBg = new Texture(fl);
        } else {
            error(TAG, E_RESOURCE + fl.name());
            quit();
        }
        
        fl = file("res/loading_bar.png");
        
        if (fl.exists()) {
            loadingBar = new Texture(fl);
        } else {
            error(TAG, E_RESOURCE + fl.name());
            quit();
        }
        
        fl = file("res/loading_bar_bg.png");
        
        if (fl.exists()) {
            loadingBarBg = new Texture(fl);
        } else {
            error(TAG, E_RESOURCE + fl.name());
            quit();
        }
        
        fl = file("main.js");
        
        if (fl.exists()) {
            script.eval(fl.readString(), "main.js");
        } else {
            error(TAG, E_RESOURCE + fl.name());
            quit();
        }
        
        script.invoke("non", "load", assets);
    }

    public void render () {
        if(assets.update()) {
            if (!ready) {
                if (Gdx.input.isTouched()) {
                    script.invoke("non", "ready");
                    ready = true;
                    resize(getWidth(), getHeight());
                }
            } else {
                Module.updateAll(getDelta());
                script.invoke("non", "update", getDelta());
                script.invoke("non", "draw");
            }
        }
        
        if (!ready) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            loadingBatch.begin();
            loadingBatch.draw(loadingBg, 
                0, 0, getWidth(), getHeight(), 
                0, 0, loadingBg.getWidth(), loadingBg.getHeight());
            loadingBatch.draw(loadingImage, loadingPos.x, loadingPos.y);
            loadingBatch.draw(loadingBarBg, loadingBarPos.x, loadingBarPos.y);
			
            percent = Interpolation.linear.apply(percent, assets.getProgress(), 0.1f);
            barWidth = loadingBar.getWidth() * percent;
            
            loadingBatch.draw(loadingBar,
                loadingBarPos.x, loadingBarPos.y, 
                barWidth, loadingBar.getHeight(),
                0, 0, barWidth, loadingBar.getHeight());
			
            loadingBatch.end();
        }
    }

    public void resize(int width, int height) { 
        if (ready) {
            Module.resizeAll(width, height); 
            script.invoke("non", "resize", width, height);
        } else {
            loadingBatch.setProjectionMatrix(
                loadingBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height));
                
            loadingBarPos = new Vector2(
                (width - loadingBar.getWidth())/2,
                (height - loadingBar.getHeight())/2);
		
            loadingPos = new Vector2(
                (width - loadingImage.getWidth())/2,
                (height + loadingBar.getHeight())/2);
        }
    }
	
    public void pause() {
        if (ready) script.invoke("non", "pause"); 
    }
	
    public void resume() {
        if (ready) script.invoke("non", "resume");
    }
    
    public boolean keyDown(int keycode) {
        if (ready) script.invoke("non", "keydown", getKey(keycode));
        return true;
    }

    public boolean keyUp(int keycode) {
        if (ready) script.invoke("non", "keyup", getKey(keycode));
        return true;
    }

    public boolean keyTyped (char character) {
        if (ready) script.invoke("non", "keytyped", character);
        return true;
    }
   
    public boolean touchDown (int x, int y, int pointer, int button) {
        if (ready) script.invoke("non", "touchdown", new Vector2(x, y), pointer, getButton(button));
        return true;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
       if (ready) script.invoke("non", "touchup", new Vector2(x, y), pointer, getButton(button));
       return true;
    }

    public boolean touchDragged (int x, int y, int pointer) {
       if (ready) script.invoke("non", "touchdragged", new Vector2(x, y), pointer);
       return true;
    }

    public boolean mouseMoved (int x, int y) {
       if (ready) script.invoke("non", "mousemoved", new Vector2(x, y));
       return true;
    }

    public boolean scrolled (int amount) {
       if (ready) script.invoke("non", "scrolled", amount);
       return true;
    }
    
    public void dispose () { 
        if (ready) script.invoke("non", "close");
        assets.dispose();
        Module.disposeAll();
    }
}