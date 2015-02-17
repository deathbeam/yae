package non.rhino;

import java.util.HashMap;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.Gdx;
import non.NonScript;
import non.Non;
import non.modules.*;
import non.rhino.modules.*;

public class RhinoNon implements NonScript {
    private HashMap<String, Module> modules = new HashMap<String, Module>();
    private RootObject root;
    public static JavaScript script;
    
    public Module getModule(String name) {
        if (!modules.containsKey(name)) {
            if (name.equals("accelerometer")) modules.put("accelerometer", new NonAccelerometer());
            if (name.equals("audio")) modules.put("audio", new NonAudio());
            if (name.equals("graphics")) modules.put("graphics", new RhinoGraphics());
            if (name.equals("keyboard")) modules.put("keyboard", new NonKeyboard());
            if (name.equals("lights")) modules.put("lights", new RhinoLights());
            if (name.equals("math")) modules.put("math", new NonMath());
            if (name.equals("mouse")) modules.put("mouse", new NonMouse());
            if (name.equals("network")) modules.put("network", new RhinoNetwork());
            if (name.equals("physics")) modules.put("physics", new RhinoPhysics());
            if (name.equals("touch")) modules.put("touch", new NonTouchScreen());
            if (name.equals("gui")) modules.put("gui", new RhinoGui());
            if (name.equals("particles")) modules.put("particles", new RhinoParticles());
        }
        
        return modules.get(name);
    }
    
    public void load(AssetManager assets) {
        script = new JavaScript();
        root = new RootObject();
        script.put("non", root);
        script.eval(Non.file("res/javascript/class.js").readString(), "class.js");
        script.eval(Non.file("res/javascript/rhino/require.js").readString(), "require.js");
        script.eval(Non.file("main.js").readString(), "main.js");
        script.call(root.load, assets);
    }
    
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        
        for(Module module: modules.values()) module.update(dt);
        script.call(root.update, dt);
        script.call(root.draw);
        for(Module module: modules.values()) module.updateAfter(dt);
    }

    public void ready() {
        script.call(root.ready);
    }

    public void resize(int width, int height) { 
        for(Module module: modules.values()) module.resize(width, height);
        script.call(root.resize, width, height);
    }
	
    public void pause() {
        script.call(root.pause); 
    }
	
    public void resume() {
        script.call(root.resume);
    }
    
    public void keyDown(int keycode) {
        for(Module module: modules.values()) module.keyPressed(keycode);
        script.call(root.keydown, Non.getKey(keycode));
    }

    public void keyUp(int keycode) {
        script.call(root.keyup, Non.getKey(keycode));
    }

    public void keyTyped (char character) {
        for(Module module: modules.values()) module.keyTyped(character);
        script.call(root.keytyped, ""+character);
    }
   
    public void touchDown (int x, int y, int pointer, int button) {
        script.call(root.touchdown, x, y, pointer, Non.getButton(button));
    }

    public void touchUp (int x, int y, int pointer, int button) {
        script.call(root.touchup, x, y, pointer, Non.getButton(button));
    }

    public void touchDragged (int x, int y, int pointer) {
        script.call(root.touchdragged, x, y, pointer);
    }

    public void mouseMoved (int x, int y) {
        script.call(root.mousemoved, x, y);
    }

    public void scrolled (int amount) {
        script.call(root.scrolled, amount);
    }
    
    public void close () { 
        script.call(root.close);
        for(Module module: modules.values()) module.dispose();
    }
}