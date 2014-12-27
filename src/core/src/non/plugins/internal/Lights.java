package non.plugins.internal;

import box2dLight.ChainLight;
import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import non.plugins.Plugin;

public class Lights extends Plugin {
    public String author()         { return "Thomas Slusny"; }
    public String license()        { return "MIT"; }
    public String description()    { return "2D dynamic lighting."; }
    public String[] dependencies() { return new String[] { "graphics", "physics" }; }
    
    private final RayHandler handler = new RayHandler(null);

    public Lights init(Physics physics) { handler.setWorld(physics.getWorld()); return this; }
    public Lights setGammaCorrection(boolean wanted) { RayHandler.setGammaCorrection(wanted); return this; }
    public Lights setDiffuseLight(boolean use) { RayHandler.useDiffuseLight(use); return this; }
    public Lights setAmbient(Color color) { handler.setAmbientLight(color); return this; }
    public Lights setBlur(boolean blur) { handler.setBlur(blur); return this; }
    public Lights setBlurNum(int num) { handler.setBlurNum(num); return this; }
    public Lights setShadows(boolean shadows) { handler.setShadows(shadows); return this; }
    public Lights setCulling(boolean culling) { handler.setCulling(culling); return this; }
    public Lights setLightmaps(boolean isAutomatic) { handler.setLightMapRendering(isAutomatic); return this; }
    
    public void draw(Graphics graphics) {
        graphics.display();
        handler.setCombinedMatrix(graphics.getProjection());
        handler.render();
        graphics.begin();
    }
    
    public void update() {
        handler.update();
    }
    
    public DirectionalLight newDirectionalLight(int rays, Color color, float direction) {
        return new DirectionalLight(handler, rays, color, direction);
    }
    
    public PointLight newPointLight(int rays) {
        return new PointLight(handler, rays);
    }
    
    public PointLight newPointLight(int rays, Color color, float distance, float x, float y) {
        return new PointLight(handler, rays, color, distance, x, y);
    }
    
    public ConeLight newConeLight(int rays, Color color, float distance, float x, float y, float direction, float cone) {
        return new ConeLight(handler, rays, color, distance, x, y, direction, cone);
    }
	
    public ChainLight newChainLight(int rays, Color color, float distance, int rayDirection) {
        return new ChainLight(handler, rays, color, distance, rayDirection);
    }
	
    public ChainLight newChainLight(int rays, Color color, float distance, int rayDirection, float[] chains) {
        return new ChainLight(handler, rays, color, distance, rayDirection, chains);
    }
}
