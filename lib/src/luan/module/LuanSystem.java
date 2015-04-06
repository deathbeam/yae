package non.luan.module;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.NonVM;
import non.luan.LuanBase;

public class LuanSystem extends LuanBase {
	public LuanSystem(NonVM vm) {
		super(vm, "NonSystem");
	}

	@Override
	public void init() {
		// contents = non.system.getClipboardText()
		set("getClipboardText", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			return valueOf(Gdx.app.getClipboard().getContents());
		}});

		// platform = non.system.getPlatform()
		set("getPlatform", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			ApplicationType platform = Gdx.app.getType();
  
	        if (platform == ApplicationType.Desktop)
	          return valueOf("desktop");
	        else if (platform == ApplicationType.Android)
	          return valueOf("android");
	        else if (platform == ApplicationType.iOS)
	          return valueOf("ios");
        
        	return valueOf("unknown");
		}});

		// success = non.system.openURL(url)
		set("openURL", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				String url = getArgString(args, 1);
				return valueOf(Gdx.net.openURI(url));
			} catch (Exception e) {
				handleError(e);
				return FALSE;
			}
		}});

		// non.system.setClipboardText(text)
		set("setClipboardText", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			try {
				String text = getArgString(args, 1);
				Gdx.app.getClipboard().setContents(text);
			} catch (Exception e) {
				handleError(e);
			} finally {
				return NONE;
			}
		}});

		// non.system.quit()
		set("quit", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
			Gdx.app.exit();
			return NONE;
		}});
	}
}