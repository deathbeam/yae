package non;

import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {
    public boolean keyDown(int keycode) {
        Non.callMethod("keydown", Non.getKey(keycode));
        return false;
    }

    public boolean keyUp(int keycode) {
        Non.callMethod("keyup", Non.getKey(keycode));
        return false;
    }

    public boolean keyTyped (char character) {
        Non.callMethod("keytyped", ""+character);
        return false;
    }
   
    public boolean touchDown (int x, int y, int pointer, int button) {
        Non.callMethod("touchdown", x, y, pointer, Non.getButton(button));
        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
       Non.callMethod("touchup", x, y, pointer, Non.getButton(button));
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