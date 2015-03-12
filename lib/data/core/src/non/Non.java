package non;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
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

public class Non implements ApplicationListener, Profile {
    private static ScriptingContainer script;
    private static Object receiver;
    public static boolean ready;
    
    private Map config;
    private String loadPath;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture background, logo, bar;
    private Vector2 logoPos, barPos;
    private int loadState;
    
    public void setLoadPath(String path) {
        loadPath = path;
    }
    
    public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();
        
        background = new Texture(file("non/background.png"));
        logo = new Texture(file("non/logo.png"));
        bar = new Texture(file("non/bar.png"));
    }

    public void render () {
        if (ready) {
            ModuleHandler.update(getDelta());
            callMethod("update", getDelta());
            callMethod("draw");
            ModuleHandler.updateAfter(getDelta());
            return;
        }
        
        if (loadCore()) {
            if (Gdx.input.isTouched()) {
                batch.dispose();
                batch = null;
                font.dispose();
                font = null;
                background.dispose();
                background = null;
                logo.dispose();
                logo = null;
                bar.dispose();
                bar = null;
                callMethod("ready");
                ready = true;
                resize(getWidth(), getHeight());
                return;
            }
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        float scale = (float)getWidth() / (float)background.getWidth();
        batch.draw(background,
            0, 0, 0, 0, background.getWidth() * scale, background.getHeight() * scale,
            scale, scale, 0,
            0, 0, background.getWidth(), background.getHeight(), false, false);
            
        batch.draw(logo, logoPos.x, logoPos.y);
        batch.draw(bar, barPos.x, barPos.y);
        
        String text = "";
        
        switch(loadState) {
        case 0: text = "Loading \"config.yml\" and applying configuration"; break;
        case 1: text = "Starting Ruby VM 2.0"; break;
        case 2: text = "Loading game's assets"; break;
        default: text = "Touch screen to continue"; break;
        }
        
        font.setColor(0, 0, 0, 1);
        font.draw(batch, text, (getWidth() - font.getBounds(text).width) /2, barPos.y + (bar.getHeight() + 12) /2);
        
        batch.end();
    }

    public void resize(int width, int height) { 
        if (ready) {
            ModuleHandler.resize(width, height);
            callMethod("resize", width, height);
            return;
        }
        
        batch.setProjectionMatrix(
            batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height));
		
        logoPos = new Vector2((width - logo.getWidth())/2, (height - logo.getHeight())/2);
        barPos = new Vector2((width - bar.getWidth())/2, logoPos.y - bar.getHeight());
    }
	
    public void pause() {
        if (ready) callMethod("pause"); 
    }
	
    public void resume() {
        if (ready) callMethod("resume");
    }
    
    public void dispose() { 
        if (ready) {
            callMethod("close");
            ModuleHandler.dispose();
        }
        
        if (script != null) {
            script.terminate();
        }
    }
    
    public boolean allowBuiltin(String name) {
        return name.startsWith("thread") || name.startsWith("jruby") || name.startsWith("java");
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

    private boolean loadCore() {
        if (loadState > 2) return true;
        
        switch (loadState) {
            case 0:
                config = (Map<String, Object>)new Yaml().load(file("non/config.yml").readString());
                if (config.containsKey("name")) Gdx.graphics.setTitle((String)config.get("name"));
                
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
                
                if (getPlatform().equalsIgnoreCase("desktop")) {
                    loadPath = file("main.rb").parent().file().getAbsolutePath();
                }

                System.setProperty("jruby.compile.mode", "OFF");
                System.setProperty("jruby.bytecode.version", "1.6");
                System.setProperty("jruby.compat.version", "2.0");
                System.setProperty("jruby.backtrace.mask", "true");
                System.setProperty("jruby.backtrace.color", "true");
                    
                break;
            case 1:
                script = new ScriptingContainer();
                script.setProfile(this);

                script.runScriptlet("$:.unshift '" + loadPath + "' ; $:.uniq!");
                break;
            case 2:
                try { 
                    receiver = script.runScriptlet(file("non/initializer.rb").read(), "initializer.rb");
                } catch(Exception e) {
                    quit();
                }
                
                Gdx.input.setInputProcessor(new InputHandler());
                break;
        }
            
        loadState++;
        return false;
    }
    
    public static Object callMethod(String method, Object... args) {
        try {
            return script.callMethod(receiver, method, args);
        } catch(Exception e) {
            quit();
            return null;
        }
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