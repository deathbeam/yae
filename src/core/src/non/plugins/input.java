package non.plugins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import non.InputHandle;

public class input extends Plugin {
    public String author()      { return "Thomas Slusny"; }
    public String license()     { return "MIT"; }
    public String description() { return "Plugin for handling input from user."; }

    public class Keyboard {
        public void show() {
            Gdx.input.setOnscreenKeyboardVisible(true);
        }
        
        public void hide() {
            Gdx.input.setOnscreenKeyboardVisible(false);
        }
        
        public boolean isDown(String name) {
            int key = InputHandle.getKey(name);
            return Gdx.input.isKeyPressed(key) && key != 0;
        }
    }
    
    public class Mouse {
        public void fix() {
            Gdx.input.setCursorCatched(true);
        }
        
        public void release() {
            Gdx.input.setCursorCatched(false);
        }
        
        public float getX() {
            return Gdx.input.getX();
        }
        
        public float getY() {
            return Gdx.input.getY();
        }
        
        public Vector2 getPosition() {
            return new Vector2(Gdx.input.getY(), Gdx.input.getY());
        }
        
        public void setPosition(float x, float y) {
            Gdx.input.setCursorPosition((int)x, (int)y);
        }
        
        public boolean isDown() {
            return Gdx.input.isTouched();
        }
        
        public boolean isDown(String name) {
            return Gdx.input.isButtonPressed(InputHandle.getButton(name));
        }
    }
    
    public class Touch {
        public float getX() {
            return Gdx.input.getX();
        }
        
        public float getX(int index) {
            return Gdx.input.getX(index);
        }
        
        public float getY() {
            return Gdx.input.getY();
        }
        
        public float getY(int index) {
            return Gdx.input.getY(index);
        }
        
        public boolean isDown() {
            return Gdx.input.isTouched();
        }
        
        public boolean isDown(int index) {
            return Gdx.input.isTouched(index);
        }
    }
    
    public class Accelerometer {
        public float getX() {
            return Gdx.input.getAccelerometerX();
        }
        
        public float getY() {
            return Gdx.input.getAccelerometerY();
        }
        
        public float getZ() {
            return Gdx.input.getAccelerometerZ();
        }
        
        public int getRotation() {
            return Gdx.input.getRotation();
        }
    }
    
    public final Keyboard keyboard = new Keyboard();
    public final Mouse mouse = new Mouse();
    public final Touch touch = new Touch();
    public final Accelerometer accelerometer = new Accelerometer();

    public void vibrate(int duration) { Gdx.input.vibrate(duration); }
}