package non.plugins;

import box2dLight.ChainLight;
import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;

public class lights extends Plugin {
    public String author()         { return "Thomas Slusny"; }
    public String license()        { return "MIT"; }
    public String description()    { return "2D dynamic lighting."; }
    public String[] dependencies() { return new String[] { "graphics", "physics" }; }
    
    private RayHandler handler;

    public lights setGammaCorrection(boolean wanted) { RayHandler.setGammaCorrection(wanted); return this; }
    public lights setDiffuseLight(boolean use) { RayHandler.useDiffuseLight(use); return this; }
    public lights setAmbient(Color color) { handler.setAmbientLight(color); return this; }
    public lights setBlur(boolean blur) { handler.setBlur(blur); return this; }
    public lights setBlurNum(int num) { handler.setBlurNum(num); return this; }
    public lights setShadows(boolean shadows) { handler.setShadows(shadows); return this; }
    public lights setCulling(boolean culling) { handler.setCulling(culling); return this; }
    public lights setLightmaps(boolean isAutomatic) { handler.setLightMapRendering(isAutomatic); return this; }
    
    public void plugin_load() {
        handler = new RayHandler(null);
        physics physics = (physics)Plugin.get("physics");
        handler.setWorld(physics.getWorld());
    }
    
    public void plugin_unload() {
        handler.dispose();
    }
    
    public void plugin_update_before() {
        handler.update();
    }
    
    public void draw() {
        graphics graphics = (graphics)Plugin.get("graphics");
        graphics.flush();
        handler.setCombinedMatrix(graphics.getBatch().getProjectionMatrix());
        handler.render();
    }
    
    public DirectionalLight directional(int rays, Color color, float direction) {
        return new DirectionalLight(handler, rays, color, direction);
    }
    
    public PointLight point(int rays) {
        return new PointLight(handler, rays);
    }
    
    public PointLight point(int rays, Color color, float distance, float x, float y) {
        return new PointLight(handler, rays, color, distance, x, y);
    }
    
    public ConeLight cone(int rays, Color color, float distance, float x, float y, float direction, float cone) {
        return new ConeLight(handler, rays, color, distance, x, y, direction, cone);
    }
	
    public ChainLight chain(int rays, Color color, float distance, int rayDirection) {
        return new ChainLight(handler, rays, color, distance, rayDirection);
    }
	
    public ChainLight chain(int rays, Color color, float distance, int rayDirection, float[] chains) {
        return new ChainLight(handler, rays, color, distance, rayDirection, chains);
    }
}