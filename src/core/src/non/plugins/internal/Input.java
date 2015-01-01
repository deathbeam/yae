package non.plugins.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import non.plugins.Plugin;

public class Input extends Plugin {
    public String author()      { return "Thomas Slusny"; }
    public String license()     { return "MIT"; }
    public String description() { return "Plugin for handling input from user."; }
    
    public class Accelerometer {
        public float getX()         { return Gdx.input.getAccelerometerX(); }
        public float getY()         { return Gdx.input.getAccelerometerY(); }
        public float getZ()         { return Gdx.input.getAccelerometerZ(); }
        public int getOrientation() { return Gdx.input.getRotation(); }
        
        public Matrix4 getMatrix() { 
            Matrix4 matrix = new Matrix4();
            Gdx.input.getRotationMatrix(matrix.val);
            return matrix;
        }
    }
    
    public Accelerometer accelerometer;
    public String text;
    
    public void loadPlugin() {
        accelerometer = new Accelerometer();
    }
    
    public int getButton(String name) {
        if ("Left".equals(name)) return com.badlogic.gdx.Input.Buttons.LEFT;
        if ("Right".equals(name)) return com.badlogic.gdx.Input.Buttons.RIGHT;
        if ("Middle".equals(name)) return com.badlogic.gdx.Input.Buttons.MIDDLE;
        if ("Back".equals(name)) return com.badlogic.gdx.Input.Buttons.BACK;
        if ("Forward".equals(name)) return com.badlogic.gdx.Input.Buttons.FORWARD;
        return -1;
    }
    
    public int getKey(String name) {
        return com.badlogic.gdx.Input.Keys.valueOf(name);
    }
    
    public void showTextDialog(String title, String defaultValue) {
        text = "";
        Gdx.input.getTextInput(new TextInputListener() {
            public void input (String text) {
                (Input)Plugin.get("input").text = text;
            }

            public void canceled () { }
        }, title, defaultValue);
    }
    
    public void showKeyboard()                  { Gdx.input.setOnscreenKeyboardVisible(true); }
    public void hideKeyboard()                  { Gdx.input.setOnscreenKeyboardVisible(false); }
    public void setCursorPosition(int x, int y) { Gdx.input.setCursorPosition(x, y); }
    public void fixCursor()                     { Gdx.input.setCursorCatched(true); }
    public void releaseCursor()                 { Gdx.input.setCursorCatched(false); }
    public void vibrate(int duration)           { Gdx.input.vibrate(duration); }
    public String getLastText()                 { return text; }
}