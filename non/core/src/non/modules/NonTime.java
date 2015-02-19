package non.modules;

import java.util.Date;
import com.badlogic.gdx.utils.TimeUtils;
import non.Timer;

public class NonTime extends Module {
    public Date date() {
        return new Date();
    }
    
    public Timer timer() {
        return new Timer();
    }
    
    public long nanos() {
        return System.nanoTime();
    }
    
    public long millis() {
        return System.currentTimeMillis();
    }
}