package non;

import com.badlogic.gdx.InputProcessor;

public class InputHandle implements InputProcessor {
    public String getButton(int code) {
        if (code == com.badlogic.gdx.Input.Buttons.LEFT) return "Left";
        if (code == com.badlogic.gdx.Input.Buttons.RIGHT) return "Right";
        if (code == com.badlogic.gdx.Input.Buttons.MIDDLE) return "Middle";
        if (code == com.badlogic.gdx.Input.Buttons.BACK) return "Back";
        if (code == com.badlogic.gdx.Input.Buttons.FORWARD) return "Forward";
        return "Unknown";
    }
    
    public String getKey(int code) {
        return com.badlogic.gdx.Input.Keys.toString(code);
    }
    
    public boolean keyDown (int keycode) {
        Non.script.invoke("non", "keydown", getKey(keycode));
        return true;
    }

    public boolean keyUp (int keycode) {
        Non.script.invoke("non", "keyup", getKey(keycode));
        return true;
    }

    public boolean keyTyped (char character) {
        Non.script.invoke("non", "keytyped", character);
        return true;
    }

   
    public boolean touchDown (int x, int y, int pointer, int button) {
        Non.script.invoke("non", "touchdown", x, y, getButton(button));
        return true;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
       Non.script.invoke("non", "touchup", x, y, getButton(button));
       return true;
    }

    public boolean touchDragged (int x, int y, int pointer) {
       Non.script.invoke("non", "touchdragged", x, y);
       return true;
    }

    public boolean mouseMoved (int x, int y) {
       Non.script.invoke("non", "mousemoved", x, y);
       return true;
    }

    public boolean scrolled (int amount) {
       Non.script.invoke("non", "scrolled", amount);
       return true;
    }
}