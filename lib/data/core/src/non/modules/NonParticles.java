package non.modules;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import non.Non;

public class NonParticles extends Module {
    protected NonGraphics graphics;
    
    public void setGraphics(NonGraphics graphics) {
        this.graphics = graphics;
    }
    
    public void draw(ParticleEffect particle) {
        graphics.checkBatch();
        particle.draw(graphics.getBatch(), Non.getDelta());
    }
}