package non;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;

import org.luaj.vm2.Globals;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.ResourceFinder;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class vm implements ApplicationListener, InputProcessor, ResourceFinder {
    public static final String TAG = "NonVM";
    public static final String VERSION = "v0.7.0";
    public static final Helpers helpers = new Helpers();
    public static Globals lua;
    
    private final Callbacks callbacks = new Callbacks(this);
    private LoadingScreen loading;
    private boolean ready;
    private int state;
    private Map config;

    public vm(Map config) {
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
        
        if (initialize()) {
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
        if (Gdx.app.getType() == ApplicationType.Android) System.exit(0);
    }

    @Override
    public boolean keyDown(int keycode) {
        callbacks.keypressed(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        callbacks.keyreleased(keycode);
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
        callbacks.mousepressed(x, y, buttoncode);
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int buttoncode) {
        callbacks.touchreleased(x, y, pointer);
        callbacks.mousereleased(x, y, buttoncode);
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

    @Override
    public InputStream findResource(String name) {
        return Gdx.files.internal(name).read();
    }

    private LuaTable convertConfig(Map config) {
        LuaTable table = LuaValue.tableOf();

        for (Object entry : config.entrySet()) {
            Map.Entry field = (Map.Entry)entry;
            String key = field.getKey().toString();
            Object value = field.getValue();

            if (field instanceof Map) {
                value = convertConfig((Map)value);
            }

            table.set(key, CoerceJavaToLua.coerce(value));
        }

        return table;
    }

    private boolean initialize() {
        if (state > 2) return true;
        
        switch (state) {
        case 1:
            lua = JsePlatform.standardGlobals();
            lua.get("package").set("path", "?.lua;?/init.lua");
            lua.set("config", convertConfig(config));
            
            lua.set("print", new VarArgFunction() { @Override public LuaValue invoke(Varargs args) {
                StringBuffer s = new StringBuffer();

                for (int i = 1; i <= args.narg(); i++) {
                    if (i > 1) s.append("\t");
                    s.append(args.arg(i).toString());
                }

                Gdx.app.log(TAG, s.toString());
                return LuaValue.NONE;
            }});
            
            loading.setText("Initializing the game");
            break;
        case 2:
            lua.get("require").call("non");
            Gdx.input.setInputProcessor(this);
            loading.setText("Touch screen to continue");
            break;
        }

        state++;
        return false;
    }
}