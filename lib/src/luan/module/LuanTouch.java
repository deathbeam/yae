package non.luan.module;

import com.badlogic.gdx.Gdx;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.NonVM;
import non.luan.LuanBase;

public class LuanTouch extends LuanBase {
	public LuanTouch(NonVM vm) {
		super(vm, "NonTouch");
	}

	@Override
	public void init() {
		// x, y = non.touch.getPosition(pointer)
		set("getPosition", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			int pointer = getArgInt(args, 1, 0);
			return varargsOf(valueOf(Gdx.input.getX(pointer)), valueOf(Gdx.input.getY(pointer)));
		}});

		// x = non.touch.getX(pointer)
		set("getX", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			int pointer = getArgInt(args, 1, 0);
			return valueOf(Gdx.input.getX(pointer));
		}});

		// y = non.touch.getY(pointer)
		set("getY", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			int pointer = getArgInt(args, 1, 0);
			return valueOf(Gdx.input.getY(pointer));
		}});

		// is_down = non.touch.isDown(pointer)
		set("isDown", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			int pointer = getArgInt(args, 1, 0);
			return valueOf(Gdx.input.isTouched(pointer));
		}});
	}
}