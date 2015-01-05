package non.languages;

import non.Non;
import java.util.HashMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;

public abstract class Language {
    public static Language init(String name) {
        Non.log("Language", "Loading scripting language...");
        Language r = null;
        try {
            r = (Language)ClassReflection.newInstance(ClassReflection.forName("non.languages." + name));
            Non.log("Language", name + " version " + r.version());
        } catch (Exception e) {
            Non.error("Language", "Wrong name!");
        } finally {
            return r;
        }
    }
	
    public abstract String extension();
    public abstract String version();
    public abstract Object invoke(String object, String method, Object... args);
    public abstract Object eval(String script);
    public abstract Object convert(Object javaValue);
    public abstract void init();
    public abstract void put(String key, Object value);
}