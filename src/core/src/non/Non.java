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
    
    public static Object warning(String type, String msg) {
        Gdx.app.error("NoNonsense", "[" + type + "] " + msg);
        return null;
    }
    
    public static Object error(String type, String msg) {
        Gdx.app.error("NoNonsense", "[" + type + "] " + msg);
        quit();
        return null;
    }
    
    public static Object log(String type, String msg) {
        Gdx.app.log("NoNonsense", "[" + type + "] " + msg);
        return null;
    }
    
    public static Object debug(String type, String msg) {
        Gdx.app.debug("NoNonsense", "[" + type + "] " + msg);
        return null;
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
        
        if (checkPlatform("desktop")) {
            int width = 800;
            int height = 600;
            boolean fullscreen = false;
    		
            if (args.has("desktop")) {
                JsonValue desktop = args.get("desktop");
                if (desktop.has("display")) {
                    JsonValue display = desktop.get("display");
                    if (display.has("width")) width = display.get("width").asInt();
                    if (display.has("height")) height = display.get("height").asInt();
                    if (display.has("fullscreen")) fullscreen = display.get("fullscreen").asBoolean();
                }
            }
    		
            Gdx.graphics.setDisplayMode(width, height, fullscreen);
        }
		
        loadingBatch = new SpriteBatch();
        loadingBg = new Texture(file("res/loading_bg.png"));
        loadingImage = new Texture(file("res/loading.png"));
        loadingBar = new Texture(file("res/loading_bar.png"));
        loadingBarBg = new Texture(file("res/loading_bar_bg.png"));
        
        assets = new AssetManager();
        Gdx.input.setInputProcessor(new InputHandle());
        
        script = Language.init(args.getString("language"));
        Plugin.loadAll();

        script.eval(file("main." + script.extension()).readString());
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