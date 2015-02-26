package non;

import java.util.HashMap;
import non.modules.*;

public class ModuleHandler {
    private static HashMap<String, Module> modules = new HashMap<String, Module>();
    
    public static Module get(String name) {
        if (!modules.containsKey(name)) {
            if (name.equals("accelerometer")) modules.put("accelerometer", new NonAccelerometer());
            if (name.equals("audio")) modules.put("audio", new NonAudio());
            if (name.equals("graphics")) modules.put("graphics", new NonGraphics());
            if (name.equals("keyboard")) modules.put("keyboard", new NonKeyboard());
            if (name.equals("lights")) modules.put("lights", new NonLights());
            if (name.equals("shapes")) modules.put("shapes", new NonShapes());
            if (name.equals("mouse")) modules.put("mouse", new NonMouse());
            if (name.equals("network")) modules.put("network", new NonNetwork());
            if (name.equals("physics")) modules.put("physics", new NonPhysics());
            if (name.equals("touch")) modules.put("touch", new NonTouchScreen());
            if (name.equals("gui")) modules.put("gui", new NonGui());
            if (name.equals("particles")) modules.put("particles", new NonParticles());
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