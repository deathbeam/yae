package non.javan.module;

import com.badlogic.gdx.Gdx;

public class JavanTouch {
    public float[] getPosition() {
        return getPosition(0);
    }
    
    public float[] getPosition(int index) {
        return new float[] {
            Gdx.input.getX(index),
            Gdx.input.getY(index)
        };
    }
    
    public float getX() {
        return getX(0);
    }
    
    public float getX(int index) {
        return Gdx.input.getX(index);
    }
    
    public float getY() {
        return getY(0);
    }
    
    public float getY(int index) {
        return Gdx.input.getY(index);
    }
    
    public boolean isDown() {
        return isDown(0);
    }
    
    public boolean isDown(int index) {
        return Gdx.input.isTouched(index);
    }
}