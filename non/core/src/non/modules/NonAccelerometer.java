package non.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class NonAccelerometer extends Module {
    public float getX() {
        return Gdx.input.getAccelerometerX();
    }
        
    public float getY() {
        return Gdx.input.getAccelerometerY();
    }
        
    public float getZ() {
        return Gdx.input.getAccelerometerZ();
    }
        
    public int getRotation() {
        return Gdx.input.getRotation();
    }
}