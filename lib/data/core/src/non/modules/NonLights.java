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
    private NonGraphics graphics;
    
    public void setGraphics(NonGraphics graphics) {
        this.graphics = graphics;
    }

    public void setPhysics(NonPhysics physics) {
        handler.setWorld(physics.getWorld());
    }

    public void setGammaCorrection(boolean wanted) {
        RayHandler.setGammaCorrection(wanted);
    }

    public void setDiffuseLight(boolean use) {
        RayHandler.useDiffuseLight(use);
    }
    
    public void setAmbientColor(float r, float g, float b, float a) {
        handler.setAmbientLight(new Color(r, g, b, a));
    }
    
    public void setBlur(boolean blur) {
        handler.setBlur(blur);
    }
    
    public void setBlurNum(int num) {
        handler.setBlurNum(num);
    }
    
    public void setShadows(boolean shadows) {
        handler.setShadows(shadows);
    }
    
    public void setCulling(boolean culling) {
        handler.setCulling(culling);
    }
    
    public boolean pointAtLight(float x, float y) {
        return handler.pointAtLight(x, y);
    }
    
    public boolean pointAtShadow(float x, float y) {
        return handler.pointAtShadow(x, y);
    }
    
    public NonLights() {
        handler = new RayHandler(null);
    }
    
    public void dispose() {
        handler.dispose();
    }
    
    public void update(float dt) {
        handler.update();
    }
    
    public void draw() {
        graphics.flush();
        handler.setCombinedMatrix(graphics.getBatch().getProjectionMatrix());
        handler.render();
    }
    
    public DirectionalLight directional(int rays, float[] color, float direction) {
        return new DirectionalLight(handler, rays, new Color(color[0], color[1], color[2], color[3]), direction);
    }
    
    public PointLight point(int rays, float[] color, float distance, float x, float y) {
        return new PointLight(handler, rays, new Color(color[0], color[1], color[2], color[3]), distance, x, y);
    }
    
    public ConeLight cone(int rays, float[] color, float distance, float x, float y, float direction, float cone) {
        return new ConeLight(handler, rays, new Color(color[0], color[1], color[2], color[3]), distance, x, y, direction, cone);
    }
	
    public ChainLight chain(int rays, float[] color, float distance, int rayDirection, float[] chains) {
        return new ChainLight(handler, rays, new Color(color[0], color[1], color[2], color[3]), distance, rayDirection, chains);
    }
}