package non.plugins.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import non.plugins.Plugin;

public class Input extends Plugin {
    public String author()      { return "Thomas Slusny"; }
    public String license()     { return "MIT"; }
    public String description() { return "Plugin for handling input from user."; }

    public class Keyboard {
        public void show()             { Gdx.input.setOnscreenKeyboardVisible(true); }
        public void hide()             { Gdx.input.setOnscreenKeyboardVisible(false); }
        public int getKey(String name) { return com.badlogic.gdx.Input.Keys.valueOf(name); }

        public boolean isKeyJustPressed(String name) { 
            int key = getKey(name); 
            return Gdx.input.isKeyJustPressed(key) && key != 0;
        }

        public boolean isKeyPressed(String name) {
            int key = getKey(name);
            return Gdx.input.isKeyPressed(key) && key != 0;
        }
    }
    
    public class Mouse {
        public int getX()                           { return Gdx.input.getX(); }
        public int getY()                           { return Gdx.input.getY(); }
        public boolean isClicked()                  { return Gdx.input.isTouched(); }
        public boolean justClicked()                { return Gdx.input.justTouched(); }
        public boolean isButtonPressed(String name) { return Gdx.input.isButtonPressed(getButton(name)); }
        public void setPosition(int x, int y)       { Gdx.input.setCursorPosition(x, y); }
        public void fix()                           { Gdx.input.setCursorCatched(true); }
        public void release()                       { Gdx.input.setCursorCatched(false); }
        
        public int getButton(String name) {
            if ("Left".equals(name)) return com.badlogic.gdx.Input.Buttons.LEFT;
            if ("Right".equals(name)) return com.badlogic.gdx.Input.Buttons.RIGHT;
            if ("Middle".equals(name)) return com.badlogic.gdx.Input.Buttons.MIDDLE;
            if ("Back".equals(name)) return com.badlogic.gdx.Input.Buttons.BACK;
            if ("Forward".equals(name)) return com.badlogic.gdx.Input.Buttons.FORWARD;
            return -1;
        }
    }
    
    public class Touch {
        public int getX()            { return Gdx.input.getX(); }
        public int getY()            { return Gdx.input.getY(); }
        public boolean isTouched()   { return Gdx.input.isTouched(); }
        public boolean justTouched() { return Gdx.input.justTouched(); }
    }
    
    public class Accelerometer {
        public float getX()         { return Gdx.input.getAccelerometerX(); }
        public float getY()         { return Gdx.input.getAccelerometerY(); }
        public float getZ()         { return Gdx.input.getAccelerometerZ(); }
        public int getOrientation() { return Gdx.input.getRotation(); }
        
        public Matrix4 getTransform() { 
            Matrix4 matrix = new Matrix4();
            Gdx.input.getRotationMatrix(matrix.val);
            return matrix;
        }
    }
    
    public final Keyboard keyboard = new Keyboard();
    public final Mouse mouse = new Mouse();
    public final Touch touch = new Touch();
    public final Accelerometer accelerometer = new Accelerometer();

    public void vibrate(int duration) { Gdx.input.vibrate(duration); }
}