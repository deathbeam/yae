package non.javan.module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

public class JavanTimer {
    public float getDelta() {
        return Gdx.graphics.getDeltaTime();
    }
    
    public float getFPS() {
        return Gdx.graphics.getFramesPerSecond();
    }
    
    public float getTime() {
        return TimeUtils.millis();
    }
    
    public void sleep(float seconds) {
        try { Thread.currentThread().sleep((long)(seconds * 1000f)); }
        catch (Exception e) { }
    }
}