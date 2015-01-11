package non.languages;

import non.Non;
import java.util.HashMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;

public abstract class Language {
    public abstract String extension();
    public abstract String version();
    public abstract Object invoke(String object, String method, Object... args);
    public abstract Object eval(String script);
    public abstract Object convert(Object javaValue);
    public abstract Object get(String key);
    public abstract void put(String key, Object value);
    
    public static Language init(String name) {
        Non.log(Non.TAG, "Loading language " + name);
        Language r = null;
        try {
            r = (Language)ClassReflection.newInstance(ClassReflection.forName("non.languages." + name));
            Non.log(Non.TAG, "> version - " + r.version());
            Non.log(Non.TAG, "> extension - " + r.extension());
        } catch (Exception e) {
            Non.error(Non.TAG, Non.E_LANGUAGE + name);
            Non.quit();
        }
        
        return r;
    }
}