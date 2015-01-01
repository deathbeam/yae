package non;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import non.scripting.ScriptRuntime;
import non.plugins.Plugin;
import java.io.IOException;

public class Non implements ApplicationListener {
    public static ScriptRuntime script;
    public static AssetManager assets;
    private static String platform;
    private static JsonValue args;
    
    private static ready;
    private static SpriteBatch loadingBatch;
    private static Texture loadingImage;
    private static BitmapFont loadingFont;
    private static String loadingText;
    
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
        return platform;
    }
    
    public static Object warning(String type, String msg) {
        Gdx.app.error("NoNonsense", "[" + type + "] " + msg);
        return null;
    }
    
    public static Object error(String type, String msg) {
        Gdx.app.error("NoNonsense", "[" + type + "] " + msg);
        Gdx.app.exit();
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
    
    public static void setPlatform(String platform) {
        this.platform = platform;
    }
    
    public static FileHandle file(String path) {
        try { return Gdx.files.internal(path); }
        catch(IOException e) { return error("Resource not found", path); }
        
    }
    
    public void create () {
        ready = false;
        loadingBatch = new SpriteBatch();
        loadingFont = new BitmapFont();
        loadingImage = new Texture(file("res/loading.png");
        loadingText = "Loading...";
        
        assets = new AssetManager();
        args = new JsonReader().parse(file("non.cfg"));
        String main = args.getString("main");
        script = ScriptRuntime.byExtension(getExtension(main));
        Plugin.loadAll();
        
        script.init();
        Gdx.input.setInputProcessor(new InputHandle());
        script.eval(file(main).readString());
        script.invoke("non", "load", assets);
    }

    public void render () {        
        if(assets.update()) {
            if (!ready) {
                loadingText = "Touch screen to continue...";
                
                if (Gdx.input.isTouched()) {
                    script.invoke("non", "ready");
                    ready = true;
                }
                
                return;
            }
            
            Plugin.updateBefore();
            script.invoke("non", "update", getDelta());
            script.invoke("non", "draw");
            Plugin.updateAfter();
        }
        
        if (!ready)
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            
            loadingText = "Loading: " + assets.getProgress();
            loadingBatch.begin();
            loadingFont.setColor(Color.BLACK);
            loadingFont.draw(loadingBatch, loadingText, 15, 15);
            loadingBatch.draw(loadingImage,
                getWidth()/2 - loadingImage.getWidth()/2,
                getHeight()/2 - loadingImage.getHeight()/2);
                
            loadingBatch.end();
        }
    }

    public void resize (int width, int height) { script.invoke("non", "resize", width, height); }
    public void pause ()                       { script.invoke("non", "pause"); }
    public void resume ()                      { script.invoke("non", "resume"); }
    
    public void dispose () { 
        script.invoke("non", "close");
        Plugin.dispose();
        assets.dispose();
    }
}