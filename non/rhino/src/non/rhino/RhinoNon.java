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
    public static JavaScript script;
    
    public Module getModule(String name) {
        if (!modules.containsKey(name)) {
            if (name.equals("accelerometer")) modules.put("accelerometer", new NonAccelerometer());
            if (name.equals("audio")) modules.put("audio", new NonAudio());
            if (name.equals("graphics")) modules.put("graphics", new RhinoGraphics());
            if (name.equals("keyboard")) modules.put("keyboard", new NonKeyboard());
            if (name.equals("lights")) modules.put("lights", new NonLights());
            if (name.equals("math")) modules.put("math", new NonMath());
            if (name.equals("mouse")) modules.put("mouse", new NonMouse());
            if (name.equals("network")) modules.put("network", new RhinoNetwork());
            if (name.equals("physics")) modules.put("physics", new RhinoPhysics());
            if (name.equals("touch")) modules.put("touch", new NonTouchScreen());
            if (name.equals("gui")) modules.put("gui", new RhinoGui());
        }
        
        return modules.get(name);
    }
    
    public void load(AssetManager assets) {
        script = new JavaScript();
        script.put("non", new RootObject());
        script.eval(Non.file("res/javascript/class.js").readString(), "class.js");
        script.eval(Non.file("res/javascript/rhino/require.js").readString(), "require.js");
        script.eval(Non.file("main.js").readString(), "main.js");
        script.invoke("non", "load", assets);
    }
    
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        
        for(Module module: modules.values()) module.update(dt);
        script.invoke("non", "update", dt);
        script.invoke("non", "draw");
        for(Module module: modules.values()) module.updateAfter(dt);
    }

    public void ready() {
        script.invoke("non", "ready");
    }

    public void resize(int width, int height) { 
        for(Module module: modules.values()) module.resize(width, height);
        script.invoke("non", "resize", width, height);
    }
	
    public void pause() {
        script.invoke("non", "pause"); 
    }
	
    public void resume() {
        script.invoke("non", "resume");
    }
    
    public void keyDown(int keycode) {
        for(Module module: modules.values()) module.keyPressed(keycode);
        script.invoke("non", "keydown", Non.getKey(keycode));
    }

    public void keyUp(int keycode) {
        script.invoke("non", "keyup", Non.getKey(keycode));
    }

    public void keyTyped (char character) {
        for(Module module: modules.values()) module.keyTyped(character);
        script.invoke("non", "keytyped", ""+character);
    }
   
    public void touchDown (int x, int y, int pointer, int button) {
        script.invoke("non", "touchdown", x, y, pointer, Non.getButton(button));
    }

    public void touchUp (int x, int y, int pointer, int button) {
       script.invoke("non", "touchup", x, y, pointer, Non.getButton(button));
    }

    public void touchDragged (int x, int y, int pointer) {
       script.invoke("non", "touchdragged", x, y, pointer);
    }

    public void mouseMoved (int x, int y) {
       script.invoke("non", "mousemoved", x, y);
    }

    public void scrolled (int amount) {
       script.invoke("non", "scrolled", amount);
    }
    
    public void close () { 
        script.invoke("non", "close");
        for(Module module: modules.values()) module.dispose();
    }
}