package non.javan.module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;

public class JavanWindow {
    private String title;
    
    public JavanWindow(String title) {
        this.title = title;
    }
    
    public float fromPixels(float pixel) {
        return pixel / Gdx.graphics.getDensity();
    }
    
    public float[] fromPixels(float px, float py) {
        return new float[] {
            px / Gdx.graphics.getDensity(),
            py / Gdx.graphics.getDensity()
        };
    }
    
    public Object[] getMode() {
        return new Object[] {
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight(),
            Gdx.graphics.isFullscreen()
        };
    }
    
    public DisplayMode[] getFullscreenModes() {
        return Gdx.graphics.getDisplayModes();
    }
    
    public float getHeight() {
        return Gdx.graphics.getHeight();
    }
    
    public float getPixelScale() {
        return Gdx.graphics.getDensity();
    }
    
    public String getTitle() {
        return title;
    }
    
    public float getWidth() {
        return Gdx.graphics.getWidth();
    }
    
    public boolean isFullscreen() {
        return Gdx.graphics.isFullscreen();
    }
    
    public void setFullscreen(boolean fullscreen) {
        setMode(getWidth(), getHeight(), fullscreen);
    }
    
    public void setMode(float width, float height) {
        setMode(width, height, false);
    }
    
    public void setMode(float width, float height, boolean fullscreen) {
        Gdx.graphics.setDisplayMode((int)width, (int)height, fullscreen);
    }
    
    public void setTitle(String title) {
        this.title = title;
        Gdx.graphics.setTitle(title);
    }
    
    public float toPixels(float dimension) {
        return dimension * Gdx.graphics.getDensity();
    }
    
    public float[] toPixels(float dx, float dy) {
        return new float[] {
            dx * Gdx.graphics.getDensity(),
            dy * Gdx.graphics.getDensity()
        };
    }
}