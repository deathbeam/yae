package non;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class InputHandle implements InputProcessor {
    public String getButton(int code) {
        if (code == com.badlogic.gdx.Input.Buttons.LEFT) return "left";
        if (code == com.badlogic.gdx.Input.Buttons.RIGHT) return "right";
        if (code == com.badlogic.gdx.Input.Buttons.MIDDLE) return "middle";
        if (code == com.badlogic.gdx.Input.Buttons.BACK) return "back";
        if (code == com.badlogic.gdx.Input.Buttons.FORWARD) return "forward";
        return "unknown";
    }
    
    public String getKey(int code) {
        return com.badlogic.gdx.Input.Keys.toString(code).toLowerCase();
    }
    
    public boolean keyDown (int keycode) {
        if (Non.ready) Non.script.invoke("non", "keydown", getKey(keycode));
        return true;
    }

    public boolean keyUp (int keycode) {
        if (Non.ready) Non.script.invoke("non", "keyup", getKey(keycode));
        return true;
    }

    public boolean keyTyped (char character) {
        if (Non.ready) Non.script.invoke("non", "keytyped", character);
        return true;
    }

   
    public boolean touchDown (int x, int y, int pointer, int button) {
        if (Non.ready) Non.script.invoke("non", "touchdown", new Vector2(x, y), pointer, getButton(button));
        return true;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
       if (Non.ready) Non.script.invoke("non", "touchup", new Vector2(x, y), pointer, getButton(button));
       return true;
    }

    public boolean touchDragged (int x, int y, int pointer) {
       if (Non.ready) Non.script.invoke("non", "touchdragged", new Vector2(x, y), pointer);
       return true;
    }

    public boolean mouseMoved (int x, int y) {
       if (Non.ready) Non.script.invoke("non", "mousemoved", new Vector2(x, y));
       return true;
    }

    public boolean scrolled (int amount) {
       if (Non.ready) Non.script.invoke("non", "scrolled", amount);
       return true;
    }
}