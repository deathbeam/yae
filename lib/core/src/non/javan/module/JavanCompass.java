package non.javan.module;

import com.badlogic.gdx.Gdx;

public class JavanCompass {
    public float[] getOrientation() {
        return new float[] {
            Gdx.input.getPitch(),
            Gdx.input.getRoll(),
            Gdx.input.getAzimuth()
        };
    }
    
    public float getAzimuth() {
        return Gdx.input.getAzimuth();
    }
    
    public float getPitch() {
        return Gdx.input.getPitch();
    }
    
    public float getRoll() {
        return Gdx.input.getRoll();
    }
}