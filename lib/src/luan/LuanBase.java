package non.luan;

import com.badlogic.gdx.utils.Disposable;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import non.NonVM;

public abstract class LuanBase extends LuaTable implements Disposable {
    public NonVM vm;
    public String tag;
    
    public LuanBase(NonVM vm, String tag) {
        super();
        this.vm = vm;
        this.tag = tag;
        init();
    }

    public abstract void init();

    @Override
    public void dispose() { }

    public boolean getArgBoolean(Varargs args, int i) {
        return args.checkboolean(i);
    }

    public boolean getArgBoolean(Varargs args, int i, boolean defaultVal) {
        return isArgSet(args, i) ? args.checkboolean(i) : defaultVal;
    }

    public LuaTable getArgData(Varargs args, int i) {
        return args.checktable(i);
    }

    public float getArgFloat(Varargs args, int i) {
        return (float)args.checkdouble(i);
    }

    public float getArgFloat(Varargs args, int i, float defaultVal) {
        return isArgSet(args, i) ? (float)args.checkdouble(i) : defaultVal;
    }

    public int getArgInt(Varargs args, int i) {
        return args.checkint(i);
    }

    public int getArgInt(Varargs args, int i, int defaultVal) {
        return isArgSet(args, i) ? args.checkint(i) : defaultVal;
    }

    public String getArgString(Varargs args, int i) {
        return args.checkjstring(i);
    }

    public String getArgString(Varargs args, int i, String defaultVal) {
        return isArgSet(args, i) ? args.checkjstring(i) : defaultVal;
    }
    
    public boolean isArgSet(Varargs args, int i) {
        return args.narg() >= i && !args.isnil(i);
    }

    public void log(String msg) {
        vm.getLogger().log(tag, msg);
    }

    public void logError(String msg) {
        vm.getLogger().logE(tag, msg);
    }

    public void logError(String msg, Throwable t) {
        vm.getLogger().logE(tag, msg, t);
    }

    public void handleError(Throwable t) {
        vm.getLogger().error(tag, t);
    }

    public VarArgFunction notImplemented(final String method, final Varargs returnType) {
        return (new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                vm.notImplemented(method);
                return returnType;
            }
        });
    }
}