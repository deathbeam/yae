package non.luan.module;

import com.badlogic.gdx.Gdx;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.NonVM;
import non.luan.LuanBase;

public class LuanCompass extends LuanBase {
    public LuanCompass(NonVM vm) {
        super(vm, "NonCompass");
    }

    @Override
    public void init() {
        // pitch, roll, azimuth = non.compass.getOrientation()
        set("getOrientation", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return varargsOf(
                valueOf(Gdx.input.getPitch()),
                valueOf(Gdx.input.getRoll()),
                valueOf(Gdx.input.getAzimuth()));
        }});

        // azimuth = non.compass.getAzimuth()
        set("getAzimuth", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(Gdx.input.getAzimuth());
        }});

        // pitch = non.compass.getPitch()
        set("getPitch", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(Gdx.input.getPitch());
        }});

        // roll = non.compass.getRoll()
        set("getRoll", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(Gdx.input.getRoll());
        }});
    }
}