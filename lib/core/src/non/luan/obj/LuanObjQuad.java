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

    public void init() {
        // x, y, w, h = Quad:getViewport()
        set("getViewport", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return LuaValue.varargsOf(new LuaValue[] {
                valueOf(x),
                valueOf(y),
                valueOf(width),
                valueOf(height)});
        }});

        // Quad:setViewport(x, y, width, height)
        set("setViewport", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                x = getArgFloat(args, 2);
                y = getArgFloat(args, 3);
                width = getArgFloat(args, 4);
                height = getArgFloat(args, 5);
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