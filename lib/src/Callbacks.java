package non;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import org.luaj.vm2.LuaValue;

public class Callbacks {
    private NonVM vm;
    private boolean enabled;

    public Callbacks(NonVM vm) {
        this.vm = vm;
    }

    public void enable() {
        enabled = true;
    }

    public void load() {
        runCallback("load");
    }

    public void run() {
        runCallback("run");
    }

    public void resize(int width, int height) { 
        runCallback("resize", width, height);
    }

    public void visible(boolean isVisible) {
        runCallback("visible", LuaValue.valueOf(isVisible));
    }

    public void quit() { 
        runCallback("quit");
    }

    public void keypressed(String keycode) {
        runCallback("keypressed", keycode);
    }

    public void keyreleased(String keycode) {
        runCallback("keyreleased", keycode);
    }

    public void textinput(String character) {
        runCallback("textinput", character);
    }

    public void touchpressed(int x, int y, int pointer) {
        runMobileCallback("mobile", "touchpressed", x, y, pointer);
    }

    public void mousepressed(int x, int y, String buttoncode) {
        runDesktopCallback("desktop", "mousepressed", x, y, buttoncode);
    }

    public void touchreleased(int x, int y, int pointer) {
        runMobileCallback("mobile", "touchreleased", x, y, pointer);
    }

    public void mousereleased(int x, int y, String buttoncode) {
        runDesktopCallback("desktop", "mousereleased", x, y, buttoncode);
    }

    public void touchmoved(int x, int y, int pointer) {
        runMobileCallback("touchmoved", x, y, pointer);
    }

    public void mousemoved(int x, int y) {
        runDesktopCallback("mousemoved", x, y);
    }

    public void mousescrolled(int amount) {
        runDesktopCallback("mousescrolled", amount);
    }

    private void runDesktopCallback(String name, Object... args) {
        if (Gdx.app.getType() != ApplicationType.Desktop) return;
        runCallback(name, args);
    }

    private void runMobileCallback(String name, Object... args) {
        if (Gdx.app.getType() == ApplicationType.Desktop) return;
        runCallback(name, args);
    }

    private void runCallback(String name, Object... args) {
        if (!enabled) return;
        if (vm.getLua() == null) return;

        LuaValue callback = (LuaValue)((LuaValue)vm.getLua().get("non")).get(name);
        if (callback == LuaValue.NIL) return;

        switch (args.length) {
            case 0: callback.call(); break;
            case 1: callback.call(vm.getLua().convert(args[0])); break;
            case 2: callback.call(vm.getLua().convert(args[0]), vm.getLua().convert(args[1])); break;
            case 3: callback.call(vm.getLua().convert(args[0]), vm.getLua().convert(args[1]), vm.getLua().convert(args[2])); break;
        }
    }
}