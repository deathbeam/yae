package non.luan.module;

import java.lang.reflect.Field;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import non.NonVM;
import non.luan.LuanBase;

public class LuanSystem extends LuanBase {
    private boolean runningOnOuya;

    public LuanSystem(NonVM vm) {
        super(vm, "NonSystem");

        try {
            Field field = Class.forName("android.os.Build").getDeclaredField("DEVICE");
            Object device = field.get(null);
            runningOnOuya = "ouya_1_1".equals(device) || "cardhu".equals(device);
        } catch (Exception e) { }
    }

    @Override
    public void init() {
        // contents = non.system.getClipboardText()
        set("getClipboardText", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return valueOf(Gdx.app.getClipboard().getContents());
        }});

        // os, version = non.system.getOS()
        set("getOS", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            String name = "unknown";
            String version = Long.toString(Gdx.app.getVersion());
  
            if (Gdx.app.getType() == ApplicationType.Desktop) {
                String osname = System.getProperty("os.name").toLowerCase();

                if (osname.startsWith("windows")) {
                    name = "Windows";
                } else if (osname.startsWith("linux")) {
                    name = "Linux";
                } else if (osname.startsWith("mac") || osname.startsWith("darwin")) {
                    name = "OS X";
                } else if (osname.startsWith("sunos")) {
                    name = "Solaris";
                }

                version = System.getProperty("os.version");
            } else if (Gdx.app.getType() == ApplicationType.Android) {
                name = "Android";
            } else if (Gdx.app.getType() == ApplicationType.iOS) {
                name = "iOS";
            } else if (runningOnOuya) {
                name = "Ouya";
            }
        
            return varargsOf(valueOf(name), valueOf(version));
        }});

        // java, native = non.system.getMemoryUse()
        set("getMemoryUse", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            return varargsOf(valueOf(Gdx.app.getJavaHeap()), valueOf(Gdx.app.getNativeHeap()));
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

        // non.system.vibrate(seconds)
        set("vibrate", new VarArgFunction() { @Override public Varargs invoke(Varargs args) {
            try {
                Gdx.input.vibrate((int)(getArgFloat(args, 1, 1f) * 1000f));
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