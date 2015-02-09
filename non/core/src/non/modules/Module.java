package non.modules;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public abstract class Module {
    public void update(float dt) {}
    public void resize(float w, float h) {}
    public void dispose() {}
    
    private static HashMap<String, Module> modules = new HashMap<String, Module>();
    
    public static Module get(String name) {
        if (!modules.containsKey(name)) {
            if (name.equals("accelerometer")) modules.put("accelerometer", new NonAccelerometer());
            if (name.equals("audio")) modules.put("audio", new NonAudio());
            if (name.equals("graphics")) modules.put("graphics", new NonGraphics());
            if (name.equals("keyboard")) modules.put("keyboard", new NonKeyboard());
            if (name.equals("lights")) modules.put("lights", new NonLights());
            if (name.equals("math")) modules.put("math", new NonMath());
            if (name.equals("mouse")) modules.put("mouse", new NonMouse());
            if (name.equals("network")) modules.put("network", new NonNetwork());
            if (name.equals("physics")) modules.put("physics", new NonPhysics());
            if (name.equals("touch")) modules.put("touch", new NonTouchScreen());
        }
        
        return modules.get(name);
    }
    
    public static void updateAll(float dt) { for(Module module: modules.values()) module.update(dt); }
    public static void resizeAll(float w, float h) { for(Module module: modules.values()) module.resize(w, h); }
    public static void disposeAll() { for(Module module: modules.values()) module.dispose(); }
}