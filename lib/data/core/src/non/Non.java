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
import javax.script.*;
import com.badlogic.gdx.utils.Array;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class Non implements ApplicationListener {
    private static ScriptEngine script;
    private static int runtime;
    private static final int RUBY = 0;
    private static final String RUBY_MAIN = "main.rb";
    private static final int LUA = 1;
    private static final String LUA_MAIN = "main.lua";
    
    private boolean ready;
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
        if (Gdx.files.internal(RUBY_MAIN).exists()) {
            runtime = RUBY;
        } else if (Gdx.files.internal(LUA_MAIN).exists()) {
            runtime = LUA;
        } else {
            System.out.println("Wrong main script extension!");
            Gdx.app.exit();
        }
        
        config = (Map<String, Object>)new Yaml().load(Gdx.files.internal("non/config.yml").readString());
        
        if (config.containsKey("name")) Gdx.graphics.setTitle((String)config.get("name"));
        
        if (Gdx.app.getType() == ApplicationType.Desktop) {
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
            
            switch (runtime) {
            case RUBY: loadPath = Gdx.files.internal(RUBY_MAIN).parent().file().getAbsolutePath(); break;
            case LUA: loadPath = Gdx.files.internal(LUA_MAIN).parent().file().getAbsolutePath(); break;
            }
            
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
                
        batch = new SpriteBatch();
        font = new BitmapFont();
        
        background = new Texture(Gdx.files.internal("non/background.png"));
        logo = new Texture(Gdx.files.internal("non/logo.png"));
        bar = new Texture(Gdx.files.internal("non/bar.png"));
    }

    public void render () {
        if (ready) {
            JModule.update(Gdx.graphics.getDeltaTime());
            callMethod("update", Gdx.graphics.getDeltaTime());
            callMethod("draw");
            JModule.updateAfter(Gdx.graphics.getDeltaTime());
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
                resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                return;
            }
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        float scale = (float)Gdx.graphics.getWidth() / (float)background.getWidth();
        batch.draw(background, 0, 0, background.getWidth() * scale, background.getHeight() * scale);
            
        batch.draw(logo, logoPos.x, logoPos.y);
        batch.draw(bar, barPos.x, barPos.y);
        
        String text = "";
        
        switch(loadState) {
        case 0: text = ""; break;
        case 1: text = "Starting the engine"; break;
        case 2: text = "Initializing the game"; break;
        default: text = "Touch screen to continue"; break;
        }
        
        font.setColor(0, 0, 0, 1);
        font.draw(batch, text, (Gdx.graphics.getWidth() - font.getBounds(text).width) /2, barPos.y + (bar.getHeight() + 12) /2);
        
        batch.end();
    }

    public void resize(int width, int height) { 
        if (ready) {
            JModule.resize(width, height);
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
            callMethod("quit");
            JModule.dispose();
        }
    }

    private boolean loadCore() {
        if (loadState > 2) return true;
        
        switch (loadState) {
            case 1:
                switch (runtime) {
                case RUBY:
                    System.setProperty("jruby.compile.mode", "OFF");
                    System.setProperty("jruby.bytecode.version", "1.6");
                    System.setProperty("jruby.compat.version", "2.0");
                    System.setProperty("jruby.backtrace.mask", "true");
                    System.setProperty("jruby.backtrace.color", "true");
                    System.setProperty("org.jruby.embed.localvariable.behavior", "transient");
                    script = new ScriptEngineManager().getEngineByName("jruby");
                    try { script.eval("$:.unshift '" + loadPath + "' ; $:.uniq!"); }
                    catch(Exception e) { Gdx.app.exit(); }
                    break;
                case LUA:
                    script = new ScriptEngineManager().getEngineByName("luaj");
                    break;
                }
                
                break;
            case 2:
                switch (runtime) {
                case RUBY:
                    try { script.eval(Gdx.files.internal("non/ruby/initializer.rb").readString()); }
                    catch(Exception e) { Gdx.app.exit(); }
                    break;
                case LUA:
                    script.put("NON_MODULE", JModule.class);
                    script.put("NON_GDX", Gdx.class);
                    script.put("NON_NON", Non.class);
                    script.put("NON_SCRIPT", script);
                    try { script.eval(Gdx.files.internal("non/lua/initializer.lua").readString()); }
                    catch(Exception e) { e.printStackTrace(); Gdx.app.exit(); }
                    break;
                }
                
                Gdx.input.setInputProcessor(new JInput());
                break;
        }
            
        loadState++;
        return false;
    }
    
    public static FileHandle lcal(String path) {
        return Gdx.files.local(path);
    }
    
    public static String getPlatform() {
        ApplicationType platform = Gdx.app.getType();
  
        if (platform == ApplicationType.Desktop)
          return "desktop";
        else if (platform == ApplicationType.Android)
          return "android";
        else if (platform == ApplicationType.iOS)
          return "ios";
        
        return "unknown";
    }
    
    public static Object callMethod(String method, Object... args) {
        try {
            switch (runtime) {
            case RUBY:
                return ((Invocable)script).invokeFunction(method,args);
            case LUA:
                if (args != null && args.length > 0) {
                    String tempArg = "int_arg0";
                    String argstring = tempArg;
                    script.put(tempArg, args[0]);
                    
                    if (args.length > 1) {
                        for (int i = 1; i < args.length; i++) {
                            tempArg = "int_arg" + i;
                            argstring += ", " + tempArg;
                            script.put(tempArg, args[i]);
                        }
                    }
                    
                    return script.eval(method + "(" + argstring + ")");
                }
                
                return script.eval(method + "()");
            }
            
            return null;
        } catch(Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
            return null;
        }
    }
}