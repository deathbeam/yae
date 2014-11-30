package com.deathbeam.non.plugins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.deathbeam.non.Non;
import java.io.IOException;

public class Graphics extends Plugin {
    public String author() { return "Thomas Slusny"; }
    public String license() { return "MIT"; }
    public String description() { return "Plugin for drawing and rendering of images and text."; }
    
    class Image extends Texture {
        private Vector2 origin, scale;
        private Rectangle source;
        private float rotation;

        public Image() { super(32, 32, Pixmap.Format.Alpha); init(); }
        public Image(FileHandle handle) throws IOException { super(handle); init(); }
        public Vector2 getOrigin() { return origin; }
        public Image setOrigin(Vector2 origin) { this.origin = origin; return this; }
        public Vector2 getScale() { return scale; }
        public Image setScale(Vector2 scale) { this.scale = scale; return this; }
        public Rectangle getSource() { return source; }
        public Image setSource(Rectangle source) { this.source = source; return this; }
        public float getRotation() { return rotation; }
        public Image setRotation(float rotation) { this.rotation = rotation; return this; }

        private void init() {
            origin = Vector2.Zero;
            scale = new Vector2(1, 1);
            source = new Rectangle(0, 0, super.getWidth(), super.getHeight());
            rotation = 0;
        }
    }
    
    public final SpriteBatch batch;
    private final OrthographicCamera camera;
    private BitmapFont font;
    private float rotation, scale;
    private Vector2 translation;
    
    public Graphics() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        scale = 1;
        rotation = 0;
        translation = Vector2.Zero;
        resize();
        update();
    }
    
    public Color newColor(String name) {
        if ("clear".equals(name)) return Color.CLEAR;
        else if ("white".equals(name)) return Color.WHITE;
        else if ("black".equals(name)) return Color.BLACK;
        else if ("red".equals(name)) return Color.RED;
        else if ("green".equals(name)) return Color.GREEN;
        else if ("blue".equals(name)) return Color.BLUE;
        else if ("light gray".equals(name)) return Color.LIGHT_GRAY;
        else if ("gray".equals(name)) return Color.GRAY;
        else if ("dark gray".equals(name)) return Color.DARK_GRAY;
        else if ("pink".equals(name)) return Color.PINK;
        else if ("orange".equals(name)) return Color.ORANGE;
        else if ("yellow".equals(name)) return Color.YELLOW;
        else if ("magenta".equals(name)) return Color.MAGENTA;
        else if ("cyan".equals(name)) return Color.CYAN;
        else if ("olive".equals(name)) return Color.OLIVE;
        else if ("purple".equals(name)) return Color.PURPLE;
        else if ("maroon".equals(name)) return Color.MAROON;
        else if ("teal".equals(name)) return Color.TEAL;
        else if ("navy".equals(name)) return Color.NAVY;
        return Color.CLEAR;
    }
    
    public Color newColor(float r, float g, float b) {
        return newColor(r, g, b, 1);
    }
    
    public Color newColor(float r, float g, float b, float a) {
        return new Color(r, g, b, a);
    }
    
    public ShaderProgram newShader(String vert, String frag) throws IOException {
        return new ShaderProgram(Non.getResource(vert), Non.getResource(frag));
    }
    
    public Image newImage(String file) throws IOException {
        return new Image(Non.getResource(file));
    }
    
    public BitmapFont newFont(String file) throws IOException {
        return new BitmapFont(Non.getResource(file));
    }
    
    public Graphics setFont(BitmapFont fnt) {
        font = fnt;
        return this;
    }
    
    public BitmapFont getFont() {
        return font;
    }
    
    public Graphics setShader(ShaderProgram shader) {
        batch.setShader(shader);
        return this;
    }
    
    public Graphics setBlending(int src, int dest) {
        batch.setBlendFunction(src, dest);
        return this;
    }
    
    public Matrix4 getProjection() {
        return batch.getProjectionMatrix();
    }
    
    public Matrix4 getTransform() {
        return batch.getTransformMatrix();
    }
    
    public Matrix4 getCombined() {
        return camera.combined;
    }
    
    public Graphics rotate(float angle) {
        rotation = angle;
        update();
        return this;
    }
    
    public Graphics scale(float factor) {
        scale = 1/factor;
        update();
        return this;
    }
    
    public Graphics translate(Vector2 position) {
        translation = position;
        update();
        return this;
    }
    
    public Vector2 getSize() {
        return new Vector2(camera.viewportWidth, camera.viewportHeight);
    }
    
    public Graphics setSize(int x, int y) {
        camera.setToOrtho(true, x, y);
        update();
        return this;
    }
    
    public Graphics begin() {
        batch.begin();
        update();
        return this;
    }
    
    public Graphics end() {
        batch.end();
        return this;
    }
    
    public Graphics draw(String text) {
        return draw(text, 0, 0);
    }

    public Graphics draw(String text, int x, int y) {
        return draw(text, x, y, Color.BLACK);
    }
    
    public Graphics draw(String text, int x, int y, Color color) {
        font.setColor(color);
        font.setScale(1, -1);
        font.draw(batch, text, x, y);
        return this;
    }
    
    public Graphics draw(Image image) {
        return draw(image, 0, 0);
    }
    
    public Graphics draw(Image image, int x, int y) {
        return draw(image, x, y, Color.WHITE);
    }
    
    public Graphics draw(Image image, int x, int y, Color color) {
        batch.setColor(color);
        Vector2 origin = image.getOrigin();
        Vector2 scale = image.getScale();
        Rectangle source = image.getSource();
        batch.draw(
                image, x, y, 
                origin.x, origin.y,
                image.getWidth(), image.getHeight(), 
                scale.x, scale.y, image.getRotation(),
                (int)source.x, (int)source.y, 
                (int)source.width, (int)source.height,
                false, true
        );
        return this;
    }
    
    public Graphics resize() {
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        return this;
    }
    
    public Graphics update() {
        camera.rotate(rotation);
        camera.translate(translation);
        camera.zoom = scale;
        camera.update();                             
        batch.setProjectionMatrix(camera.combined);
        return this;
    }
    
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
