package com.codeindie.non.plugins;

import com.badlogic.gdx.Gdx;
import com.codeindie.non.scripting.ScriptRuntime;
import com.codeindie.non.Utils;

public class Core extends Plugin {
    public String name() { return "non"; }
    public String author() { return "Thomas Slusny"; }
    public String license() { return "MIT"; }
    public String description() { return "Core plugin of NON."; }
    
    public Object ready, draw, update, resize, close, pause, resume;

    public int getWidth() { return Gdx.graphics.getWidth(); }
    public int getHeight() { return Gdx.graphics.getHeight(); }
    public int getFPS() { return Gdx.graphics.getFramesPerSecond(); }
    public float getDelta() { return Gdx.graphics.getDeltaTime(); }
    public void warning(String type, String msg) { Utils.warning(type, msg); }
    public void error(String type, String msg) { Utils.error(type, msg); }
    public void log(String type, String msg) { Utils.log(type, msg); }
    public void debug(String type, String msg) { Utils.debug(type, msg); }
    public void dump(Object obj) { Utils.dump(obj); }

    public void invoke(String funct) {
        if ("ready".equals(funct) && ready == null) return;
        if ("update".equals(funct) && update == null) return;
        if ("draw".equals(funct) && draw == null) return;
        if ("resize".equals(funct) && resize == null) return;
        if ("close".equals(funct) && close == null) return;
        if ("pause".equals(funct) && pause == null) return;
        if ("resume".equals(funct) && resume == null) return;
        ScriptRuntime.getCurrent().invoke(name(), funct);    
    }
}
