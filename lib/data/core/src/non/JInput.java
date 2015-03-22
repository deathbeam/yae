package non;

import com.badlogic.gdx.InputProcessor;

public class JInput implements InputProcessor {
    public static String getButton(int code) {
        if (code == com.badlogic.gdx.Input.Buttons.LEFT) return "Left";
        if (code == com.badlogic.gdx.Input.Buttons.RIGHT) return "Right";
        if (code == com.badlogic.gdx.Input.Buttons.MIDDLE) return "Middle";
        if (code == com.badlogic.gdx.Input.Buttons.BACK) return "Back";
        if (code == com.badlogic.gdx.Input.Buttons.FORWARD) return "Forward";
        return "unknown";
    }
    
    public static int getButton(String name) {
       if ("Left".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.LEFT;
       if ("Right".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.RIGHT;
       if ("Middle".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.MIDDLE;
       if ("Back".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.BACK;
       if ("Forward".equalsIgnoreCase(name)) return com.badlogic.gdx.Input.Buttons.FORWARD;
       return -1;
    }
    
    public static String getKey(int code) {
        return com.badlogic.gdx.Input.Keys.toString(code);
    }
    
    public static int getKey(String name) {
        return com.badlogic.gdx.Input.Keys.valueOf(name);
    }
    
    public boolean keyDown(int keycode) {
        Non.callMethod("keydown", getKey(keycode));
        return false;
    }

    public boolean keyUp(int keycode) {
        Non.callMethod("keyup", getKey(keycode));
        return false;
    }

    public boolean keyTyped (char character) {
        Non.callMethod("keytyped", ""+character);
        return false;
    }
   
    public boolean touchDown (int x, int y, int pointer, int button) {
        Non.callMethod("touchdown", x, y, pointer, getButton(button));
        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
       Non.callMethod("touchup", x, y, pointer, getButton(button));
       return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
       Non.callMethod("touchdragged", x, y, pointer);
       return false;
    }

    public boolean mouseMoved (int x, int y) {
       Non.callMethod("mousemoved", x, y);
       return false;
    }

    public boolean scrolled (int amount) {
       Non.callMethod("mousescrolled", amount);
       return false;
    }
}