package non.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import non.Non;
    
public class NonMouse extends Module {
    public void show() {
        Gdx.input.setCursorCatched(false);
    }
        
    public void hide() {
        Gdx.input.setCursorCatched(true);
    }
    
    public void setVisible(boolean val) {
        Gdx.input.setCursorCatched(!val);
    }
        
    public float getX() {
        return Gdx.input.getX();
    }
        
    public float getY() {
        return Gdx.input.getY();
    }
        
    public Vector2 getPosition() {
        return new Vector2(Gdx.input.getX(), Gdx.input.getY());
    }
        
    public void setPosition(float x, float y) {
        Gdx.input.setCursorPosition((int)x, (int)y);
    }
        
    public boolean isDown() {
        return Gdx.input.isTouched();
    }
        
    public boolean isDown(String name) {
        return Gdx.input.isButtonPressed(Non.getButton(name));
    }
}