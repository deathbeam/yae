package non.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import non.Non;

public class NonKeyboard extends Module {
    public void show() {
        Gdx.input.setOnscreenKeyboardVisible(true);
    }
        
    public void hide() {
        Gdx.input.setOnscreenKeyboardVisible(false);
    }
        
    public boolean isDown(String name) {
        int key = Non.getKey(name);
        return Gdx.input.isKeyPressed(key) && key != 0;
    }
}