package non;

import java.util.HashMap;
import non.modules.*;

public class JModule {
    private static HashMap<String, Module> modules = new HashMap<String, Module>();
    
    public static Module get(String name) {
        if (!modules.containsKey(name)) {
            if (name.equals("graphics")) modules.put("graphics", new NonGraphics());
            if (name.equals("network")) modules.put("network", new NonNetwork());
        }
        
        return modules.get(name);
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