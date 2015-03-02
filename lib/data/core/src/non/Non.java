package non;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import java.io.IOException;
import non.modules.*;
import org.jruby.embed.ScriptingContainer;
import org.jruby.javasupport.JavaEmbedUtils;
import com.badlogic.gdx.utils.Array;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;
import org.jruby.Profile;

public class Non implements ApplicationListener {
    public static final String TAG = "No Nonsense";
    public static final String E_RESOURCE = "Resource not found - ";
    public static final String E_ARGUMENT = "Argument not found - ";
    public static final String E_PLUGIN = "Plugin not found - ";
    
    public static Assets assets;
    public static ScriptingContainer script;
    public static Object receiver;
    public static boolean ready;
    
    private Map config;
    private String loadPath;
    private SpriteBatch loadingBatch;
    private Texture loadingBg, loadingImage, loadingBar, loadingBarBg;
    private Vector2 loadingPos, loadingBarPos;
    private float percent, barWidth, loadProgress;
    private int loadState;
    
    public void setLoadPath(String path) {
        loadPath = path;
    }
    
    public void create () {
        loadingBatch = new SpriteBatch();
        assets = new Assets();
        
        assets.load("non/loading.png", Texture.class);
        assets.load("non/loading_bg.png", Texture.class);
        assets.load("non/loading_bar.png", Texture.class);
        assets.load("non/loading_bar_bg.png", Texture.class);
        assets.finishLoading();
        
        loadingImage = assets.get("non/loading.png", Texture.class);
        loadingBg = assets.get("non/loading_bg.png", Texture.class);
        loadingBar = assets.get("non/loading_bar.png", Texture.class);
        loadingBarBg = assets.get("non/loading_bar_bg.png", Texture.class);
    }

    public void render () {
        if (ready) {
            ModuleHandler.update(getDelta());
            script.callMethod(receiver, "render", getDelta());
            ModuleHandler.updateAfter(getDelta());
            return;
        }
        
        if (loadCore()) {
            if (assets.update()) {
                if (Gdx.input.isTouched()) {
                    script.callMethod(receiver, "ready");
                    ready = true;
                    resize(getWidth(), getHeight());
                    loadingBatch.dispose();
                    assets.unload("non/loading.png");
                    assets.unload("non/loading_bg.png");
                    assets.unload("non/loading_bar.png");
                    assets.unload("non/loading_bar_bg.png");
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
            ModuleHandler.resize(width, height);
            script.callMethod(receiver, "resize", width, height);
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
        if (ready) script.callMethod(receiver, "pause"); 
    }
	
    public void resume() {
        if (ready) script.callMethod(receiver, "resume");
    }
    
    public void dispose() { 
        if (ready) {
            script.callMethod(receiver, "close");
            ModuleHandler.dispose();
        }
        
        assets.dispose();
    }
    
    private boolean loadCore() {
        if (loadState > 3) return true;
        
        switch (loadState) {
            case 0:
                config = (Map<String, Object>)new Yaml().load(file("config.yml").readString());
                break;
            case 1:
                if (getPlatform().equalsIgnoreCase("desktop")) {
                    int width = 800;
                    int height = 600;
                    boolean fullscreen = false;
                		
                    if (config.containsKey("desktop")) {
                        Map desktop = (Map<String, Object>)config.get("desktop");
                        if (desktop.containsKey("display")) {
                            Map display = (Map<String, Object>)desktop.get("display");
                            if (display.containsKey("width")) width = (Integer)display.get("width");
                            if (display.containsKey("height")) height = (Integer)display.get("height");
                            if (display.containsKey("fullscreen")) fullscreen = (Boolean)display.get("fullscreen");
                        }
                    }
                		
                    Gdx.graphics.setDisplayMode(width, height, fullscreen);
                }
                
                break;
            case 2:
                if (config.containsKey("name")) Gdx.graphics.setTitle((String)config.get("name"));
                
                if (config.containsKey("logging")) {
                    String logLevel = (String)config.get("logging");
                        
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
                if (getPlatform().equalsIgnoreCase("desktop")) {
                    loadPath = file("main.rb").parent().file().getAbsolutePath();
                }

                System.setProperty("jruby.compile.mode", "OFF");
                System.setProperty("jruby.bytecode.version", "1.6");
                System.setProperty("jruby.compat.version", "2.0");
                System.setProperty("jruby.backtrace.mask", "true");
                System.setProperty("jruby.backtrace.color", "true");
                
                script = new ScriptingContainer();
                script.setProfile(new Profile() {
                    public boolean allowBuiltin(String name) {
                        return name.startsWith("thread");
                    }
                    
                    public boolean allowClass(String name) {
                        return true;
                    }
                    
                    public boolean allowModule(String name) {
                        return true;
                    }
                    
                    public boolean allowLoad(String name) {
                        return true;
                    }
                    
                    public boolean allowRequire(String name) {
                        return true;
                    }
                });

                script.runScriptlet("$:.unshift '" + loadPath + "' ; $:.uniq!");
                receiver = script.runScriptlet(file("non/initializer.rb").readString());
                script.callMethod(receiver, "init", assets);
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