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
    
    public static String getKey(int code) {
        return com.badlogic.gdx.Input.Keys.toString(code);
    }
    
    public boolean keyDown(int keycode) {
        JModule.call("keydown", getKey(keycode));
        return false;
    }

    public boolean keyUp(int keycode) {
        JModule.call("keyup", getKey(keycode));
        return false;
    }

    public boolean keyTyped (char character) {
        JModule.call("keytyped", ""+character);
        return false;
    }
   
    public boolean touchDown (int x, int y, int pointer, int button) {
        JModule.call("touchdown", x, y, pointer, getButton(button));
        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
       JModule.call("touchup", x, y, pointer, getButton(button));
       return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
       JModule.call("touchdragged", x, y, pointer);
       return false;
    }

    public boolean mouseMoved (int x, int y) {
       JModule.call("mousemoved", x, y);
       return false;
    }

    public boolean scrolled (int amount) {
       JModule.call("mousescrolled", amount);
       return false;
    }
}