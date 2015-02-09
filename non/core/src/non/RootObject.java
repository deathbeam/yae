package non;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonValue;

import non.modules.*;

public class RootObject {
    public int getWidth() {
        return Non.getWidth();
    }
    
    public int getHeight() {
        return Non.getHeight();
    }
    
    public int getFPS() {
        return Non.getFPS();
    }
    
    public float getDelta() {
        return Non.getDelta();
    }
    
    public void error(String type, String msg) {
        Non.error(type, msg);
    }
    
    public void log(String type, String msg) {
        Non.log(type, msg);
    }
    
    public void debug(String type, String msg) {
        Non.debug(type, msg);
    }
    
    public FileHandle file(String path) {
        return Non.file(path);
    }
    
    public void quit() {
        Non.quit();
    }
    
    public Object 
        load, ready, draw, update, resize, close, pause, resume, 
        keydown, keyup, keytyped, touchdown, touchup, touchdragged, mousemoved, scrolled;
}