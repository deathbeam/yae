package non.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class NonTouchScreen extends Module {    
    public float getX(int index) {
        return Gdx.input.getX(index);
    }
        
    public float getY(int index) {
        return Gdx.input.getY(index);
    }
        
    public Vector2 getPosition(int index) {
        return new Vector2(Gdx.input.getX(index), Gdx.input.getY(index));
    }
        
    public boolean isDown() {
        return Gdx.input.isTouched();
    }
        
    public boolean isDown(int index) {
        return Gdx.input.isTouched(index);
    }
}