package non.luan.module;

import com.badlogic.gdx.Gdx;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.NonVM;
import non.luan.LuanBase;

public class LuanAccelerometer extends LuanBase {
    public LuanAccelerometer(NonVM vm) {
        super(vm, "NonAccelerometer");
    }

    @Override
    public void init() {
        // x, y, z = non.accelerometer.getRotation()
        set("getRotation", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return varargsOf(
                valueOf(Gdx.input.getAccelerometerX()),
                valueOf(Gdx.input.getAccelerometerY()),
                valueOf(Gdx.input.getAccelerometerZ()));
        }});

        // x = non.accelerometer.getX()
        set("getX", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(Gdx.input.getAccelerometerX());
        }});

        // y = non.accelerometer.getY()
        set("getY", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(Gdx.input.getAccelerometerY());
        }});

        // z = non.accelerometer.getZ()
        set("getZ", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(Gdx.input.getAccelerometerZ());
        }});
    }
}