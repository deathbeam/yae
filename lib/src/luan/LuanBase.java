package non.luan;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import non.NonVM;

public abstract class LuanBase extends LuaTable {
	public NonVM vm;
	public String tag;
	
	public LuanBase(NonVM vm, String tag) {
		super();
		this.vm = vm;
		this.tag = tag;
		init();
	}

	public abstract void init();
	public void dispose() { }

	public boolean getArgBoolean(Varargs args, int i) {
		return args.checkboolean(i);
	}

	public boolean getArgBoolean(Varargs args, int i, boolean defaultVal) {
		return isArgSet(args, i) ? args.checkboolean(i) : defaultVal;
	}

	public Object getArgData(Varargs args, int i) {
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
	
	public String getLuaType(int t) {
		switch (t) {
			case LuaValue.TNIL:			return "nil";
			case LuaValue.TBOOLEAN:		return "boolean";
			case LuaValue.TNUMBER:		return "number";
			case LuaValue.TSTRING:		return "string";
			case LuaValue.TTABLE:		return "table";
			case LuaValue.TFUNCTION:	return "function";
			case LuaValue.TUSERDATA:	return "userdata";
			case LuaValue.TTHREAD:		return "thread";	
			default: return "unknown";
		}
	}

	public void log(String msg) {
		vm.log(tag, msg);
	}

	public void logE(String msg) {
		vm.logE(tag, msg);
	}

	public void logE(String msg, Exception e) {
		vm.logE(tag, msg, e);
	}

	public void handleError(Exception e) {
		vm.handleError(tag, e);
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