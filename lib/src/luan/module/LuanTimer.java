package non.luan.module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.NonVM;
import non.luan.LuanBase;

public class LuanTimer extends LuanBase {
    public LuanTimer(NonVM vm) {
        super(vm, "NonTimer");
    }

    @Override
    public void init() {
        // delta = non.timer.getDelta()
        set("getDelta", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(Gdx.graphics.getDeltaTime());
        }});

        // fps = non.timer.getFPS()
        set("getFPS", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(Gdx.graphics.getFramesPerSecond());
        }});

        // timemillis = non.timer.getTime()
        set("getTime", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(TimeUtils.millis());
        }});

        // non.timer.sleep(seconds)
        set("sleep", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                Thread.currentThread().sleep(((long)(getArgFloat(args, 1) * 1000f)));
            } catch (Exception e) {
                handleError(e);
            } finally {
                return NONE;
            }
        }});
    }
}