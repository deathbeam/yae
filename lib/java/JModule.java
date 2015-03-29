package non;

import java.util.Map;
import java.util.HashMap;
import non.modules.*;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.yaml.snakeyaml.Yaml;

public class JModule {
    private static HashMap<String, Module> modules = new HashMap<String, Module>();
    private static LuaEngine script;
    private static Map config;
    
    public static Class<?> gdx = Gdx.class;
    
    public static void init(Map config) {
        JModule.config = config;

        script = new LuaEngine();
        script.put("NON", JModule.class);
    }
    
    public static Module get(String name) {
        if (!modules.containsKey(name)) {
            if (name.equals("graphics")) modules.put("graphics", new NonGraphics());
            if (name.equals("network")) modules.put("network", new NonNetwork());
        }
        
        return modules.get(name);
    }
    
    public static Object call(String method, Object... args) {
        if (script == null) return null;
        
        try {
            if (args != null && args.length > 0) {
                String tempArg = "int_arg0";
                String argstring = tempArg;
                script.put(tempArg, args[0]);
                
                if (args.length > 1) {
                    for (int i = 1; i < args.length; i++) {
                        tempArg = "int_arg" + i;
                        argstring += ", " + tempArg;
                        script.put(tempArg, args[i]);
                    }
                }
                
                return script.eval("non." + method + "(" + argstring + ")");
            }
                
            return script.eval("non." + method + "()");
        } catch(Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
            return null;
        }
    }
    
    public static Object eval(FileHandle file) {
        if (script == null) return null;
        
        try {
            return script.compile(file).eval();
        } catch(Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
            return null;
        }
    }
    
    public static Map getConfig() {
        return config;
    }
    
    public static String getPlatform() {
        ApplicationType platform = Gdx.app.getType();
  
        if (platform == ApplicationType.Desktop)
          return "desktop";
        else if (platform == ApplicationType.Android)
          return "android";
        else if (platform == ApplicationType.iOS)
          return "ios";
        
        return "unknown";
    }
    
    public static FileHandle localFile(String path) {
        return Gdx.files.local(path);
    }
    
    public static int getKey(String name) {
        return com.badlogic.gdx.Input.Keys.valueOf(name);
    }
    
    public static int getButton(String name) {
       if ("Left".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.LEFT;
       if ("Right".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.RIGHT;
       if ("Middle".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.MIDDLE;
       if ("Back".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.BACK;
       if ("Forward".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.FORWARD;
       return -1;
    }
    
    public static void update(float dt) {
        for(Module module: modules.values()) module.update(dt);
    }
    
    public static void updateAfter(float dt) {
        for(Module module: modules.values()) module.updateAfter(dt);
    }
    
    public static void resize(float w, float h) {
        for(Module module: modules.values()) module.resize(w, h);
    }
    
    public static void dispose() {
        for(Module module: modules.values()) module.dispose();
    }
}