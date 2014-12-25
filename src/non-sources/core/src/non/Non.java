package non;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import non.scripting.ScriptRuntime;
import non.plugins.Plugin;
import java.io.IOException;

public class Non implements ApplicationListener {
    public static ScriptRuntime script;
    private static String platform;
    private static JsonValue args;
    
    public static JsonValue configure() {
        try { return new JsonReader().parse(getResource("non.cfg")); }
        catch(IOException e) { error("Resource not found", "non.cfg"); }
        return null;
    }
    
    public static String getExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) extension = fileName.substring(i+1);
        return extension;
    }
    
    public static JsonValue config() { return args; }
    public static int getWidth() { return Gdx.graphics.getWidth(); }
    public static int getHeight() { return Gdx.graphics.getHeight(); }
    public static int getFPS() { return Gdx.graphics.getFramesPerSecond(); }
    public static float getDelta() { return Gdx.graphics.getDeltaTime(); }
    public static String getPlatform() { return platform; }
    public static Object warning(String type, String msg) { Gdx.app.error("NoNonsense", "[" + type + "] " + msg); return null; }
    public static Object error(String type, String msg) { Gdx.app.error("NoNonsense", "[" + type + "] " + msg); Gdx.app.exit(); return null; }
    public static Object log(String type, String msg) { Gdx.app.log("NoNonsense", "[" + type + "] " + msg); return null; }
    public static Object debug(String type, String msg) { Gdx.app.debug("NoNonsense", "[" + type + "] " + msg); return null; }
    public static void setPlatform(String platform) { Non.platform = platform; }
    public static FileHandle getResource(String path) throws IOException { return Gdx.files.internal(path); }
    
    public void create () {
        args = configure();
        String main = args.getString("main");
        script = ScriptRuntime.byExtension(getExtension(main));
        Plugin.loadAll();
        script.init();
        
        try {
            script.eval(Non.getResource(main).readString());
        } catch (IOException e) {
            error("Resource not found", main);
        }
        
        script.invoke("non", "ready");
    }

    public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        script.invoke("non", "update");
        script.invoke("non", "draw");
    }

    public void resize (int width, int height) {
        script.invoke("non", "resize");
    }

    public void pause () {
        script.invoke("non", "pause");
    }

    public void resume () {
        script.invoke("non", "resume");
    }

    public void dispose () {
        script.invoke("non", "close");
    }
}