package non.luan.obj;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.NonVM;
import non.luan.LuanBase;

public class LuanObjQuad extends LuanBase {
    public float x, y, width, height, sw, sh;

    public LuanObjQuad(LuanBase base, float x, float y, float width, float height, float sw, float sh) { 
        super(base.vm, "NonImage");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sw = sw;
        this.sh = sh;
    }

    private LuanObjQuad self (Varargs args) {
        try {
            return (LuanObjQuad)getArgData(args, 1);
        } catch (Exception e) {
            handleError(e);
            return null;
        }
    }

    public void init() {
        // x, y, w, h = Quad:getViewport()
        set("getViewport", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return LuaValue.varargsOf(new LuaValue[] {
                valueOf(self(args).x),
                valueOf(self(args).y),
                valueOf(self(args).width),
                valueOf(self(args).height)});
        }});

        // Quad:setViewport(x, y, width, height)
        set("setViewport", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                self(args).x = getArgFloat(args, 2);
                self(args).y = getArgFloat(args, 3);
                self(args).width = getArgFloat(args, 4);
                self(args).height = getArgFloat(args, 5);
            } catch (Exception e) {
                handleError(e);
            } finally {
                return LuaValue.NONE;
            }
        }});

        set("type", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf("Quad");
        }});

        set("typeOf", new VarArgFunction() { @Override public Varargs invoke(Varargs args) { 
            try {
                String s = getArgString(args, 2); 
                return valueOf(s.equals("Object") || s.equals("Quad"));
            } catch (Exception e) {
                handleError(e);
                return LuaValue.FALSE;
            }
        }});
    }
}