package non;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.script.ScriptException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.luan.LuanBase;
import non.luan.module.*;

public class NonVM implements ApplicationListener, InputProcessor, Disposable {
    private static final String TAG = "NonVM";
    private static final String VERSION = "v0.7.0";
    private static final Set knownNotImplemented = new HashSet<String>();
    private static final Set modules = new HashSet<LuanBase>();

    private final Map cfg;
    private LuaEngine lua;
    private LoadingScreen loading;
    private boolean ready;
    private int state;

    public NonVM(Map config) {
        cfg = config;
        
        if (config.containsKey("logging")) {
            String logLevel = (String)config.get("logging");

            if ("error".equalsIgnoreCase(logLevel)) {
                Gdx.app.setLogLevel(1);
            } else if ("info".equalsIgnoreCase(logLevel)) {
                Gdx.app.setLogLevel(2);
            } else if ("debug".equalsIgnoreCase(logLevel)) {
                Gdx.app.setLogLevel(3);
            } else {
                Gdx.app.setLogLevel(0);
            }
        }
    }

    public Map getConfig() {
        return cfg;
    }

    public LuaEngine getLua() {
        return lua;
    }

    public void log(String tag, String msg) {
        Gdx.app.log(tag, msg);
    }

    public void logE(String tag, String msg) {
        Gdx.app.error(tag, msg);
    }

    public void logE(String tag, String msg, Exception e) {
        Gdx.app.error(tag, msg, e);
    }

    public void handleError(String tag, Exception e) {
        Gdx.app.error(tag, "", e);
        Gdx.app.exit();
    }
    
    public void handleLuaError(String tag, LuaError e) {
        Gdx.app.error(tag, "", e);
        Gdx.app.exit();
    }

    public void handleLuaError(String tag, ScriptException e) {
        Gdx.app.error(tag, "", e);
        Gdx.app.exit();
    }

    public void notImplemented(String s) {
        if (knownNotImplemented.contains(s)) return;
        knownNotImplemented.add(s);
        log(TAG, "WARNING! \"" + s + "\" is not implemented.");
    }

    public LuaValue toLua(Object o) {
        return (LuaValue)LuaEngine.toLua(o);
    }

    public Object toJava(LuaValue o) {
        return LuaEngine.toJava(o);
    }

    public void create () {    
        loading = new LoadingScreen();
        loading.setText("Starting the engine");
    }

    public void render () {
        if (ready) {
            runCallback("run", Gdx.graphics.getDeltaTime());
            return;
        }
        
        if (loadCore()) {
            if (Gdx.input.isTouched()) {
                loading.dispose();
                runCallback("load");
                ready = true;
                resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                return;
            }
        }
        
        loading.render();
    }

    public void resize(int width, int height) { 
        if (ready) {
            runCallback("resize", width, height);
            return;
        }
        
        loading.resize(width, height);
    }
    
    public void pause() {
        runCallback("visible", LuaValue.FALSE);
    }
    
    public void resume() {
        runCallback("visible", LuaValue.TRUE);
    }
    
    public void dispose() { 
        if (ready) {
            runCallback("quit");
        } else {
            loading.dispose();
        }

        for (Object module : modules) ((LuanBase)module).dispose();
        if (Gdx.app.getType() == ApplicationType.Android) System.exit(0);
    }
    
    
    
    public boolean keyDown(int keycode) {
        runCallback("keypressed", LuanKeyboard.getKey(keycode));
        return false;
    }

    public boolean keyUp(int keycode) {
        runCallback("keyreleased", LuanKeyboard.getKey(keycode));
        return false;
    }

    public boolean keyTyped (char character) {
        runCallback("textinput", ""+character);
        return false;
    }
   
    public boolean touchDown (int x, int y, int pointer, int buttoncode) {
        runCallback("touchpressed", x, y, pointer);
        runCallback("mousepressed", x, y, LuanMouse.getButton(buttoncode));
        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int buttoncode) {
        runCallback("touchreleased", x, y, pointer);
        runCallback("mousereleased", x, y, LuanMouse.getButton(buttoncode));
        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        runCallback("touchmoved", x, y, pointer);
        return false;
    }

    public boolean mouseMoved (int x, int y) {
        runCallback("mousemoved", x, y);
        return false;
    }

    public boolean scrolled (int amount) {
        runCallback("mousescrolled", amount);
        return false;
    }

    public Object runCallback(String name, Object... args) {
        if (lua == null) return null;
        LuaValue callback = toLua(toLua(lua.get("non")).get(name));
        if (callback == LuaValue.NIL) return null;

        switch (args.length) {
            case 0: return toJava(callback.call());
            case 1: return toJava(callback.call(toLua(args[0])));
            case 2: return toJava(callback.call(toLua(args[0]),toLua(args[1])));
            case 3: return toJava(callback.call(toLua(args[0]),toLua(args[1]), toLua(args[2])));
        }

        return null;
    }

    private boolean loadCore() {
        if (state > 2) return true;
        
        switch (state) {
        case 1:
            lua = new LuaEngine();
            setupCoreFunctions();
            setupCoreLibrary();
            loading.setText("Initializing the game");
            break;
        case 2:
            try {
                lua.eval(Gdx.files.internal("non/boot.lua"));
            } catch (ScriptException e) {
                handleLuaError(TAG, e);
            }
            
            Gdx.input.setInputProcessor(this);
            loading.setText("Touch screen to continue");
            break;
        }

        state++;
        return false;
    }

    private void setupCoreFunctions() {
        lua.getContext().getGlobals().set("require", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                String filename = args.checkjstring(1) + ".lua";
                String filetype = args.narg() >= 2 && !args.isnil(2) ? args.checkjstring(2) : "internal";
                FileHandle file = LuanFilesystem.newFile(filename, filetype);
                if (file == null) throw new Exception("Wrong file type \"" + filetype + "\"");
                return toLua(lua.eval(file));
            } catch (Exception e) {
                handleError(TAG, e);
                return LuaValue.NONE;
            }
        }});

        lua.getContext().getGlobals().set("print", new VarArgFunction() { @Override public LuaValue invoke(Varargs args) {
            StringBuffer s = new StringBuffer();

            for (int i = 1; i <= args.narg(); i++) {
                if (i > 1) s.append("\t");
                s.append(args.arg(i).toString());
            }

            log(TAG, s.toString());
            return LuaValue.NONE;
        }});
    }

    private void setupCoreLibrary() {
        lua.put("non", LuaValue.tableOf());

        toLua(lua.get("non")).set("getVersion", new VarArgFunction() { @Override public LuaValue invoke(Varargs args) {
            return toLua(VERSION);
        }});

        toLua(lua.get("non")).set("getConfig", new VarArgFunction() { @Override public LuaValue invoke(Varargs args) {
            return toLua(cfg);
        }});

        LuanBase accelerometer = new LuanAccelerometer(this);
        toLua(lua.get("non")).set("accelerometer", accelerometer);
        modules.add(accelerometer);

        LuanBase audio = new LuanAudio(this);
        toLua(lua.get("non")).set("audio", audio);
        modules.add(audio);

        LuanBase compass = new LuanCompass(this);
        toLua(lua.get("non")).set("compass", compass);
        modules.add(compass);

        LuanBase filesystem = new LuanFilesystem(this);
        toLua(lua.get("non")).set("filesystem", filesystem);
        modules.add(filesystem);

        LuanBase graphics = new LuanGraphics(this);
        toLua(lua.get("non")).set("graphics", graphics);
        modules.add(graphics);

        LuanBase keyboard = new LuanKeyboard(this);
        toLua(lua.get("non")).set("keyboard", keyboard);
        modules.add(keyboard);

        LuanBase mouse = new LuanMouse(this);
        toLua(lua.get("non")).set("mouse", mouse);
        modules.add(mouse);

        LuanBase system = new LuanSystem(this);
        toLua(lua.get("non")).set("system", system);
        modules.add(system);

        LuanBase timer = new LuanTimer(this);
        toLua(lua.get("non")).set("timer", timer);
        modules.add(timer);

        LuanBase touch = new LuanTouch(this);
        toLua(lua.get("non")).set("touch", touch);
        modules.add(touch);

        LuanBase window = new LuanWindow(this);
        toLua(lua.get("non")).set("window", window);
        modules.add(window);
    }
}