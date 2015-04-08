package non;

import com.badlogic.gdx.Gdx;

public class Logger {
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
    
    public void luaerror(String tag, Throwable t) {
        Gdx.app.error(tag, "Lua error", t);
        Gdx.app.exit();
    }
}