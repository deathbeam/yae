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
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.luan.LuanBase;
import non.luan.module.*;

public class NonVM implements ApplicationListener, InputProcessor {
    private static final String TAG = "NonVM";
    private static final String VERSION = "v0.7.0";

    private final Set toDispose = new HashSet<Disposable>();
    private final Logger logger = new Logger();
    private final Callbacks callbacks = new Callbacks(this);
    private final Map config;
    
    private LoadingScreen loading;
    private LuaEngine lua;
    private boolean ready;
    private int state;

    public NonVM(Map config) {
        this.config = config;
    }

    @Override
    public void create () {
        loading = new LoadingScreen();
        loading.setText("Starting the engine");
    }

    @Override
    public void render() {
        if (ready) {
            callbacks.run();
            return;
        }
        
        if (setupCore()) {
            if (Gdx.input.isTouched()) {
                ready = true;
                loading.dispose();
                callbacks.enable();
                callbacks.load();
                callbacks.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                return;
            }
        }
        
        loading.render();
    }

    @Override
    public void resize(int width, int height) { 
        callbacks.resize(width, height);
        if (!ready) loading.resize(width, height);
    }

    @Override
    public void pause() {
        callbacks.visible(false);
    }

    @Override
    public void resume() {
        callbacks.visible(true);
    }

    @Override
    public void dispose() {
        callbacks.quit();
        if (!ready) loading.dispose();
        for (Object disposable : toDispose) ((Disposable)disposable).dispose();
        if (Gdx.app.getType() == ApplicationType.Android) System.exit(0);
    }

    @Override
    public boolean keyDown(int keycode) {
        callbacks.keypressed(LuanKeyboard.getKey(keycode));
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        callbacks.keyreleased(LuanKeyboard.getKey(keycode));
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        callbacks.textinput(""+character);
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int buttoncode) {
        callbacks.touchpressed(x, y, pointer);
        callbacks.mousepressed(x, y, LuanMouse.getButton(buttoncode));
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int buttoncode) {
        callbacks.touchreleased(x, y, pointer);
        callbacks.mousereleased(x, y, LuanMouse.getButton(buttoncode));
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        callbacks.touchmoved(x, y, pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        callbacks.mousemoved(x, y);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        callbacks.mousescrolled(amount);
        return false;
    }

    public Map getConfig() {
        return config;
    }

    public LuaEngine getLua() {
        return lua;
    }

    public Logger getLogger() {
        return logger;
    }

    public LuaValue getLibrary() {
        return (LuaValue)lua.get("non");
    }

    public void ensureDispose(Disposable d) {
        toDispose.add(d);
    }

    private boolean setupCore() {
        if (state > 2) return true;
        
        switch (state) {
        case 1:
            lua = new LuaEngine();
            setupCoreFunctions();
            setupCoreLibrary();
            loading.setText("Initializing the game");
            break;
        case 2:
            bootEngine();
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
                String filetype = args.narg() == 2 && !args.isnil(2) ? args.checkjstring(2) : "internal";
                FileHandle file = LuanFilesystem.newFile(filename, filetype);
                if (file == null) throw new Exception("Wrong file type \"" + filetype + "\"");
                return lua.convert(lua.eval(file));
            } catch (Exception e) {
                logger.error(TAG, e);
                return LuaValue.NONE;
            }
        }});

        lua.getContext().getGlobals().set("print", new VarArgFunction() { @Override public LuaValue invoke(Varargs args) {
            StringBuffer s = new StringBuffer();

            for (int i = 1; i <= args.narg(); i++) {
                if (i > 1) s.append("\t");
                s.append(args.arg(i).toString());
            }

            logger.log(TAG, s.toString());
            return LuaValue.NONE;
        }});
    }

    private void setupCoreLibrary() {
        LuaTable non = LuaValue.tableOf();

        non.set("getVersion", new VarArgFunction() { @Override public LuaValue invoke(Varargs args) {
            return LuaValue.valueOf(VERSION);
        }});

        non.set("getConfig", new VarArgFunction() { @Override public LuaValue invoke(Varargs args) {
            return lua.convert(config);
        }});

        non.set("accelerometer", new LuanAccelerometer(this));
        non.set("audio", new LuanAudio(this));
        non.set("compass", new LuanCompass(this));
        non.set("filesystem", new LuanFilesystem(this));
        non.set("graphics", new LuanGraphics(this));
        non.set("keyboard", new LuanKeyboard(this));
        non.set("mouse", new LuanMouse(this));
        non.set("system", new LuanSystem(this));
        non.set("timer", new LuanTimer(this));
        non.set("touch", new LuanTouch(this));
        non.set("window", new LuanWindow(this));

        lua.put("non", non);
    }

    private void bootEngine() {
        try {
            lua.eval(Gdx.files.internal("non/boot.lua"));
        } catch (ScriptException e) {
            logger.luaError(TAG, e);
        }
        
        Gdx.input.setInputProcessor(this);
    }

}