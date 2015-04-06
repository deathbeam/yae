package non.luan.module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.utils.ObjectIntMap;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.NonVM;
import non.luan.LuanBase;

public class LuanMouse extends LuanBase {
	private static ObjectIntMap<String> buttonNames;

	public LuanMouse(NonVM vm) {
		super(vm, "NonMouse");
	}

	@Override
	public void init() {
		// x, y = non.mouse.getPosition()
		set("getPosition", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return varargsOf(valueOf(Gdx.input.getX()), valueOf(Gdx.input.getY()));
		}});

		// x = non.mouse.getX()
		set("getX", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf(Gdx.input.getX());
		}});

		// y = non.mouse.getY()
		set("getY", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf(Gdx.input.getY());
		}});

		// is_down = non.mouse.isDown(button1, button2, ...)
		set("isDown", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
	            boolean isDown = false;

	            for (int i = 1; i <= args.narg(); ++i) {
	            	isDown |= Gdx.input.isKeyPressed(getButton(getArgString(args, i)));
	            }

				return valueOf(isDown);
			} catch (Exception e) {
				handleError(e);
				return FALSE;
			}
		}});

		// is_visible = non.mouse.isVisible()
		set("isVisible", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf(!Gdx.input.isCursorCatched());
		}});

		// non.mouse.setPosition(x, y)
		set("setPosition", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				Gdx.input.setCursorPosition(getArgInt(args, 1), getArgInt(args, 2));
			} catch (Exception e) {
				handleError(e);
			} finally {
				return NONE;
			}
		}});

		// non.mouse.setVisible(is_visible)
		set("setVisible", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				Gdx.input.setCursorCatched(!getArgBoolean(args, 1));
			} catch (Exception e) {
				handleError(e);
			} finally {
				return NONE;
			}
		}});

		// non.mouse.setX(x)
		set("setX", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				Gdx.input.setCursorPosition(getArgInt(args, 1), Gdx.input.getY());
			} catch (Exception e) {
				handleError(e);
			} finally {
				return NONE;
			}
		}});

		// non.mouse.setY(x)
		set("setY", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				Gdx.input.setCursorPosition(Gdx.input.getX(), getArgInt(args, 1));
			} catch (Exception e) {
				handleError(e);
			} finally {
				return NONE;
			}
		}});
	}

	public static int getButton(String buttonname) {
		if (buttonNames == null) {
			buttonNames = new ObjectIntMap<String>();
			for (int i = 0; i < 255; i++) {
				String name = getButton(i);
				if (name != null) buttonNames.put(name, i);
			}
		}

		return buttonNames.get(buttonname, -1);
    }

    public static String getButton(int buttoncode) {
    	switch (buttoncode) {
    	case Buttons.LEFT: return "left";
    	case Buttons.RIGHT: return "right";
    	case Buttons.MIDDLE: return "middle";
    	case Buttons.BACK: return "back";
    	case Buttons.FORWARD: return "forward";
    	default: return null;
    	}
    }
}