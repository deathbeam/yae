package non;

import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {
    public boolean keyDown(int keycode) {
        Non.script.callMethod(Non.receiver, "keydown", Non.getKey(keycode));
        return false;
    }

    public boolean keyUp(int keycode) {
        Non.script.callMethod(Non.receiver, "keyup", Non.getKey(keycode));
        return false;
    }

    public boolean keyTyped (char character) {
        Non.script.callMethod(Non.receiver, "keytyped", ""+character);
        return false;
    }
   
    public boolean touchDown (int x, int y, int pointer, int button) {
        Non.script.callMethod(Non.receiver, "touchdown", x, y, pointer, Non.getButton(button));
        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
       Non.script.callMethod(Non.receiver, "touchup", x, y, pointer, Non.getButton(button));
       return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
       Non.script.callMethod(Non.receiver, "touchdragged", x, y, pointer);
       return false;
    }

    public boolean mouseMoved (int x, int y) {
       Non.script.callMethod(Non.receiver, "mousemoved", x, y);
       return false;
    }

    public boolean scrolled (int amount) {
       Non.script.callMethod(Non.receiver, "mousescrolled", amount);
       return false;
    }
}