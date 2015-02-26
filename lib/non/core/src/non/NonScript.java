package non;

import com.badlogic.gdx.assets.AssetManager;
import non.modules.Module;

public interface NonScript {
    void load(AssetManager assets);
    void ready();
    void render();
    void resize(int width, int height);
	void pause();
	void resume();
    void keyDown(int keycode);
    void keyUp(int keycode);
    void keyTyped (char character);
    void touchDown (int x, int y, int pointer, int button);
    void touchUp (int x, int y, int pointer, int button);
    void touchDragged (int x, int y, int pointer);
    void mouseMoved (int x, int y);
    void scrolled (int amount);
    void close();
    Module getModule(String name);
}