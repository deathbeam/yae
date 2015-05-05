package non.javan.module;

import com.badlogic.gdx.Gdx;

public class JavanAccelerometer {
    public float[] getRotation() {
        return new float[] {
            Gdx.input.getAccelerometerX(),
            Gdx.input.getAccelerometerY(),
            Gdx.input.getAccelerometerZ()
        };
    }
    
    public float getX() {
        return Gdx.input.getAccelerometerX();
    }
    
    public float getY() {
        return Gdx.input.getAccelerometerY();
    }
    
    public float getZ() {
        return Gdx.input.getAccelerometerZ();
    }
}