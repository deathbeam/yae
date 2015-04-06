package non.luan.module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.NonVM;
import non.luan.LuanBase;

public class LuanWindow extends LuanBase {
    private String title;

    public LuanWindow(NonVM vm) {
        super(vm, "NonWindow");
        title = (String)vm.getConfig().get("name");
    }

    @Override
    public void init() {
        // dx, dy = non.window.fromPixels(px, py)
        set("fromPixels", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                if (isArgSet(args, 2)) {
                    return varargsOf(
                        valueOf(getArgFloat(args, 1) / Gdx.graphics.getDensity()),
                        valueOf(getArgFloat(args, 2) / Gdx.graphics.getDensity()));
                }
                
                return valueOf(getArgFloat(args, 1) / Gdx.graphics.getDensity());
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // width, height, fullscreen = non.window.getMode()
        set("getMode", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return varargsOf(
                valueOf(Gdx.graphics.getWidth()),
                valueOf(Gdx.graphics.getHeight()),
                valueOf(Gdx.graphics.isFullscreen()));
        }});

        // modes = non.window.getFullscreenModes()
        set("getFullscreenModes", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                DisplayMode[] modes = Gdx.graphics.getDisplayModes();
                LuaTable t = tableOf();

                for (int i = 0; i < modes.length; i++) {
                    LuaTable cht = tableOf();
                    cht.set("width", modes[i].width);
                    cht.set("height", modes[i].height);
                    t.rawset(i + 1, cht);
                }

                return t;
            } catch (Exception e) {
                handleError(e);
                return NONE;
            }
        }});

        // height = non.window.getHeight()
        set("getHeight", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(Gdx.graphics.getHeight());
        }});

        // scale = non.window.getPixelScale()
        set("getPixelScale", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(Gdx.graphics.getDensity());
        }});

        // title = non.window.getTitle()
        set("getTitle", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(title);
        }});

        // width = non.window.getWidth()
        set("getWidth", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(Gdx.graphics.getWidth());
        }});

        // is_fullscreen = non.window.isFullscreen()
        set("isFullscreen", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(Gdx.graphics.isFullscreen());
        }});

        // non.window.setFullscreen(is_fullscreen)
        set("setFullscreen", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                Gdx.graphics.setDisplayMode(
                    Gdx.graphics.getWidth(),
                    Gdx.graphics.getHeight(),
                    getArgBoolean(args, 1));
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // success = non.window.setMode(width, height, fullscreen)
        set("setMode", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                return valueOf(Gdx.graphics.setDisplayMode(
                    getArgInt(args, 1),
                    getArgInt(args, 2),
                    getArgBoolean(args, 3, false)));
            } catch (Exception e) {
                handleError(e);
                return FALSE;
            }
        }});

        // non.window.setTitle(title)
        set("setTitle", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                title = getArgString(args, 1);
                Gdx.graphics.setTitle(title);
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});

        // px, py = non.window.toPixels(dx, dy)
        set("toPixels", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                if (isArgSet(args, 2)) {
                    return varargsOf(
                        valueOf(getArgFloat(args, 1) * Gdx.graphics.getDensity()),
                        valueOf(getArgFloat(args, 2) * Gdx.graphics.getDensity()));
                }
                
                return valueOf(getArgFloat(args, 1) * Gdx.graphics.getDensity());
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});
    }
}