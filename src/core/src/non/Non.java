package non;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
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
import non.languages.Language;
import non.plugins.Plugin;
import java.io.IOException;

public class Non implements ApplicationListener {
    public static final String TAG = "No Nonsense";
    public static final String E_RESOURCE = "Resource not found - ";
    public static final String E_ARGUMENT = "Argument not found - ";
    public static final String E_PLUGIN = "Plugin not found - ";
    public static final String E_LANGUAGE = "Incorrect scripting language - ";
    
    public static boolean ready;
    public static Language script;
    public static AssetManager assets;
    private static JsonValue args;
    
    // loading screen
    private SpriteBatch loadingBatch;
    private Texture loadingBg, loadingImage, loadingBar, loadingBarBg;
    private Vector2 loadingPos, loadingBarPos;
    private float percent, barWidth;
    
    public static String getExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) extension = fileName.substring(i+1);
        return extension;
    }
    
    public static JsonValue getConfig() {
        return args;
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
        if (type == ApplicationType.Applet) return "applet";
        if (type == ApplicationType.WebGL) return "web";
        return "desktop";
    }
    
    public static boolean checkPlatform(String p) {
        return getPlatform().equalsIgnoreCase(p);
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
        args = new JsonReader().parse(file("non.cfg"));
        
        if (args.has("logging")) {
            String logLevel = args.getString("logging");
            
            if ("none".equalsIgnoreCase(logLevel)
                Gdx.app.setLogLevel(0);
            else if ("error".equalsIgnoreCase(logLevel)
                Gdx.app.setLogLevel(1);
            else if ("info".equalsIgnoreCase(logLevel)
                Gdx.app.setLogLevel(2);
            else if ("debug".equalsIgnoreCase(logLevel)
                Gdx.app.setLogLevel(3);
        }
        
        if (checkPlatform("desktop")) {
            int width = 800;
            int height = 600;
            boolean fullscreen = false;
    		
            if (args.has("desktop")) {
                JsonValue desktop = args.get("desktop");
                if (desktop.has("display")) {
                    JsonValue display = desktop.get("display");
                    if (display.has("width")) width = display.getInt("width");
                    if (display.has("height")) height = display.getInt("height");
                    if (display.has("fullscreen")) fullscreen = display.getBoolean("fullscreen");
                }
            }
    		
            Gdx.graphics.setDisplayMode(width, height, fullscreen);
        }
		
        loadingBatch = new SpriteBatch();
        
        FileHandle fl = file("res/loading.png");
        
        if (fl.exists()) {
            loadingImage = new Texture(fl);
        } else {
            error(TAG, E_RESOURCE + fl.name();
            quit();
        }
        
        fl = file("res/loading_bg.png");
        
        if (fl.exists()) {
            loadingBg = new Texture(fl);
        } else {
            error(TAG, E_RESOURCE + fl.name();
            quit();
        }
        
        fl = file("res/loading_bar.png");
        
        if (fl.exists()) {
            loadingBar = new Texture(fl);
        } else {
            error(TAG, E_RESOURCE + fl.name();
            quit();
        }
        
        fl = file("res/loading_bar_bg.png");
        
        if (fl.exists()) {
            loadingBarBg = new Texture(fl);
        } else {
            error(TAG, E_RESOURCE + fl.name();
            quit();
        }
        
        assets = new AssetManager();
        Gdx.input.setInputProcessor(new InputHandle());
        
        if args.has("language") {
            script = Language.init(args.getString("language"));
        } else {
            error(TAG, E_ARGUMENT + "language");
        }
        
        if (args.has("plugins")) {
            Plugin.init(args.get("plugins").asStringArray());
        }
        
        FileHandle main = file("main." + script.extension());
        
        if (main.exists()) {
            script.eval(main.readString());
        } else {
            error(TAG, E_RESOURCE + main.name());
            quit();
        }
        
        script.invoke("non", "load", assets);
    }

    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if(assets.update()) {
            if (!ready) {
                if (Gdx.input.isTouched()) {
                    script.invoke("non", "ready");
                    ready = true;
                    resize(getWidth(), getHeight());
                }
            } else {
                Plugin.updateBefore();
                script.invoke("non", "update", getDelta());
                script.invoke("non", "draw");
                Plugin.updateAfter();
            }
        }
        
        if (!ready) {
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
            Plugin.resize(); 
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
    
    public void dispose () { 
        if (ready) script.invoke("non", "close");
        Plugin.dispose();
        assets.dispose();
    }
}