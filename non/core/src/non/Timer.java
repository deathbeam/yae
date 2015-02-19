package non;

import com.badlogic.gdx.utils.TimeUtils;

public class Timer {
    private long time, initial;
    private boolean running;
	
    public void start() {
        time = 0;
        setInitial();
        running = true;
    }
	
    public void resume() {
        if (running) return;
        setInitial();
        running = true;
    }
	
    public void pause() {
        if (!running) return;
        time += getCount();
        running = false;
    }
	
    public boolean isRunning() {
        return running;
    }
    
    public long getMillis() {
        if (running) {
            time += getCount();
            setInitial();
        }
        
        return time;
    }
    
    public float getSeconds() {
        return getMillis() / 1000.0f;
    }
    
    public float getMinutes() {
        return getSeconds() / 60.0f;
    }
	
    private void setInitial() {
        initial = TimeUtils.millis();
    }

    private long getCount() {
        return TimeUtils.millis() - initial;
    }
}