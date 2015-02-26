package non.modules;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;

import non.Non;

public class NonParticles extends Module {
    protected NonGraphics graphics;
    public Class<?> effectLoader = ParticleEffect.class;
    
    public NonParticles link(NonGraphics graphics) {
        this.graphics = graphics;
        return this;
    }
    
    public ParticleEffect load(String file) {
        if (Non.assets.isLoaded(file)) {
            return (ParticleEffect)Non.assets.get(file, effectLoader);
        }
        
        ParticleEffect p = new ParticleEffect();
        p.load(Non.file(file), Non.file("."));
        return p;
    }
    
    public NonParticles draw(ParticleEffect particle) {
        graphics.checkBatch();
        particle.draw(graphics.getBatch(), Non.getDelta());
        return this;
    }
}