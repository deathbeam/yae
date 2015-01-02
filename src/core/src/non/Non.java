package non;

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
import non.scripting.ScriptRuntime;
import non.plugins.Plugin;
import java.io.IOException;

public class Non implements ApplicationListener {
    public static ScriptRuntime script;
    public static AssetManager assets;
    private static String platform;
    private static JsonValue args;
    
	// loading screen
    public static boolean ready;
    private static SpriteBatch loadingBatch;
    private static Texture loadingBg, loadingImage, loadingBar, loadingBarBg;
	private static Vector2 loadingPos, loadingBarPos;
	private float percent;
	private float barWidth;
    
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
        Non.platform = platform;
    }
    
    public static FileHandle file(String path) {
        return Gdx.files.internal(path);
    }
    
    public void create () {
        ready = false;
        loadingBatch = new SpriteBatch();
		loadingBg = new Texture(file("res/loading_bg.png"));
        loadingImage = new Texture(file("res/loading.png"));
		loadingBar = new Texture(file("res/loading_bar.png"));
		loadingBarBg = new Texture(file("res/loading_bar_bg.png"));
        
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
                if (Gdx.input.isTouched()) {
                    script.invoke("non", "ready");
                    ready = true;
                }
            } else {
				Plugin.updateBefore();
				script.invoke("non", "update", getDelta());
				script.invoke("non", "draw");
				Plugin.updateAfter();
			}
        }
        
        if (!ready) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
            loadingBatch.begin();
			loadingBatch.draw(loadingBg, 0, 0, getWidth(), getHeight(), 0, 0, loadingBg.getWidth(), loadingBg.getHeight());
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
			script.invoke("non", "resize", width, height);
		} else {
			loadingBatch.setProjectionMatrix(loadingBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height));
			loadingBarPos = new Vector2(
				(width - loadingBar.getWidth())/2,
				(height - loadingBar.getHeight())/2);
		
			loadingPos = new Vector2((width - loadingImage.getWidth())/2, (height + loadingBar.getHeight())/2);
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