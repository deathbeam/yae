package non.modules;

import box2dLight.ChainLight;
import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import non.Non;

public class NonLights extends Module {
    private RayHandler handler;
    
    public NonLights setPhysics(NonPhysics physics) { handler.setWorld(physics.getWorld()); return this; }
    public NonLights setGammaCorrection(boolean wanted) { RayHandler.setGammaCorrection(wanted); return this; }
    public NonLights setDiffuseLight(boolean use) { RayHandler.useDiffuseLight(use); return this; }
    public NonLights setAmbient(Color color) { handler.setAmbientLight(color); return this; }
    public NonLights setBlur(boolean blur) { handler.setBlur(blur); return this; }
    public NonLights setBlurNum(int num) { handler.setBlurNum(num); return this; }
    public NonLights setShadows(boolean shadows) { handler.setShadows(shadows); return this; }
    public NonLights setCulling(boolean culling) { handler.setCulling(culling); return this; }
    public NonLights setLightmaps(boolean isAutomatic) { handler.setLightMapRendering(isAutomatic); return this; }
    public boolean pointAtLight(float x, float y) { return handler.pointAtLight(x, y); }
    public boolean pointAtShadow(float x, float y) { return handler.pointAtShadow(x, y); }
    
    public NonLights() {
        handler = new RayHandler(null);
    }
    
    public void dispose() {
        handler.dispose();
    }
    
    public void update(float dt) {
        handler.update();
    }
    
    public void draw(NonGraphics graphics) {
        graphics.flush();
        handler.setCombinedMatrix(graphics.getBatch().getProjectionMatrix());
        handler.render();
    }
    
    public DirectionalLight directional(int rays, Color color, float direction) {
        return new DirectionalLight(handler, rays, color, direction);
    }
    
    public PointLight point(int rays, Color color, float distance, float x, float y) {
        return new PointLight(handler, rays, color, distance, x, y);
    }
    
    public ConeLight cone(int rays, Color color, float distance, float x, float y, float direction, float cone) {
        return new ConeLight(handler, rays, color, distance, x, y, direction, cone);
    }
	
    public ChainLight chain(int rays, Color color, float distance, int rayDirection, float[] chains) {
        return new ChainLight(handler, rays, color, distance, rayDirection, chains);
    }
}