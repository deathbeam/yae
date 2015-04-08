package non;

import java.util.HashSet;
import java.util.Set;
import com.badlogic.gdx.Gdx;

public class Logger {
    private static final Set knownNotImplemented = new HashSet<String>();

    public void log(String tag, String msg) {
        Gdx.app.log(tag, msg);
    }

    public void logE(String tag, String msg) {
        Gdx.app.error(tag, msg);
    }

    public void logE(String tag, String msg, Throwable t) {
        Gdx.app.error(tag, msg, t);
    }

    public void error(String tag, Throwable t) {
        Gdx.app.error(tag, "Internal error", t);
        Gdx.app.exit();
    }
    
    public void luaError(String tag, Throwable t) {
        Gdx.app.error(tag, "Lua error", t);
        Gdx.app.exit();
    }

    public void notImplemented(String tag, String method) {
        if (knownNotImplemented.contains(method)) return;
        knownNotImplemented.add(method);
        Gdx.app.log(tag, "WARNING! \"" + method + "\" is not implemented.");
    }
}