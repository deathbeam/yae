package non.rhino.modules;

import com.badlogic.gdx.graphics.Texture;
import org.mozilla.javascript.Scriptable;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import non.rhino.Arguments;
import non.modules.NonParticles;


public class RhinoParticles extends NonParticles {
    public NonParticles draw(Scriptable rhinoArgs) {
        Arguments args = new Arguments(rhinoArgs);
        if (args.has("particle")) {
            ParticleEffect particle = (ParticleEffect)args.get("particle", null);
            float[] position = args.getNumArray("position", new float[]{0,0});
            
            return super.draw(particle, position);
        }
        
        return this;
    }
}