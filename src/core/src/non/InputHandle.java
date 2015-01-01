package non;

public class InputHandle implements InputProcessor {
    public boolean keyDown (int keycode) {
        Non.script.invoke("non", "keydown", keycode);
        return true;
    }

    public boolean keyUp (int keycode) {
        Non.script.invoke("non", "keyup", keycode);
        return true;
    }

    public boolean keyTyped (char character) {
        Non.script.invoke("non", "keytyped", character);
        return true;
    }

   
    public boolean touchDown (int x, int y, int pointer, int button) {
        Non.script.invoke("non", "touchdown", x, y, button);
        return true;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
       Non.script.invoke("non", "touchup", x, y, button);
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