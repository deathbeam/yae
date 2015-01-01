package non.plugins.internal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import non.Non;
import non.plugins.Plugin;

public class Graphics extends Plugin {
    public String author()      { return "Thomas Slusny"; }
    public String license()     { return "MIT"; }
    public String description() { return "Plugin for drawing and rendering of images and text."; }
    
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private BitmapFont font;
    private float rotation, scale;
    private Vector2 translation;
    private Color background;
    
    public Class<?> texture = Texture.class;
    public Class<?> font = BitmapFont.class;
    
    public void loadPlugin() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        camera = new OrthographicCamera();
        scale = 1;
        rotation = 0;
        translation = Vector2.Zero;
        backgroundColor = Color.BLACK;
        setSize(Non.getWidth(), Non.getHeight());
    }
    
    public void unloadPlugin() {
        batch.dispose();
        font.dispose();
    }
    
    public void updatePluginBefore() {
        Gdx.gl.glClearColor(background.r, background.g, background.b, background.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    
    public Color newColor(String name) {
        if name.startsWith("#") return Color.valueOf(name.replace("#",""));
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
    
    public ShaderProgram newShader(String vert, String frag) {
        return new ShaderProgram(Non.file(vert), Non.file(frag));
    }
    
    public Texture newTexture(String file) {
        return newImage(file, false);
    }
    
    public Texture newTexture(String file, boolean raw) {
        return (raw) ? new Texture(Non.file(file)) : Non.assets.get(file, texture);
    }
    
    public Sprite newSprite(Texture tex) {
        return newSprite(tex, 0, 0, tex.getWidth(), tex.getHeight());
    }
    
    public Sprite newSprite(Texture tex, int sourceX, int sourceY, int sourceW, int sourceH) {
        return new Sprite(tex, sourceX, sourceY, sourceW, sourceH);
    }
    
    public BitmapFont newFont(String file) {
        return newFont(file, false);
    
    public BitmapFont newFont(String file, boolean raw) {
        return (raw) ? new BitmapFont(Non.file(file)) : Non.assets.get(file, font);
    }
    
    public SpriteBatch getBatch() {
        return batch;
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
    
    public Vector2 getSize() {
        return new Vector2(camera.viewportWidth, camera.viewportHeight);
    }
    
    public BitmapFont getFont() {
        return font;
    }
    
    public Graphics setFont(BitmapFont fnt) {
        font = fnt;
        return this;
    }
    
    public Graphics setShader(ShaderProgram shader) {
        batch.setShader(shader);
        return this;
    }
    
    public Graphics setBlending(int src, int dest) {
        batch.setBlendFunction(src, dest);
        return this;
    }
    
    public Graphics setBackgroundColor(Color color) {
        background = color;
    }
    
    public Graphics setColor(Color color) {
        batch.setColor(color);
        font.setColor(color);
    }
    
    public Graphics resetColor() {
        batch.setColor(Color.WHITE);
        font.setColor(Color.WHITE);
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
    
    public Graphics translate(int x, int y) {
        return translate(new Vector2(x, y));
    }
    
    public Graphics translate(Vector2 position) {
        translation = position;
        update();
        return this;
    }
    
    public Graphics resize(int w, int h) {
        camera.setToOrtho(true, w, h);
        update();
        return this;
    }
    
    public Graphics begin() {
        batch.begin();
        resetColor();
        update();
        return this;
    }
    
    public Graphics end() {
        batch.end();
        return this;
    }
    
    private Graphics update() {
        camera.rotate(rotation);
        camera.translate(translation);
        camera.zoom = scale;
        camera.update();                             
        batch.setProjectionMatrix(camera.combined);
        return this;
    }
    
    public Graphics print(String text) {
        return draw(text, 0, 0);
    }
    
    public Graphics print(String text, int x, int y) {
        return draw(text, x, y, 1);
    }
    
    public Graphics print(String text, int x, int y, float scale) {
        font.setScale(scale, -scale);
        font.draw(batch, text, x, y);
        return this;
    }
    
    public Graphics draw(Sprite sprite) {
        sprite.draw(batch);
        return this;
    }
    
    public Graphics draw(Texture tex) {
        return draw(tex, 0, 0);
    }
    
    public Graphics draw(Texture tex, float x, float y) {
        
        return draw(tex, x, y, 0, 0, tex.getWidth(), tex.getHeight());
    }
    
    public Graphics draw(Texture tex, float x, float y, int sourceX, int sourceY, int sourceW, int sourceH) {
        
        return draw(tex, x, y, tex.getWidth(), tex.getHeight(), sourceX, sourceY, sourceW, sourceH);
    }
    
    public Graphics draw(Texture tex, float x, float y, float width, float height,
                         int sourceX, int sourceY, int sourceW, int sourceH) {
        
        return draw(tex, x, y, 0, 0, width, height, 1, 1, 0, sourceX, sourceY, sourceW, sourceH);
    }
    
    public Graphics draw(Texture tex, float x, float y, float originX, float originY, float width,
                         float height, float scaleX, float scaleY, float rotation,
                         int sourceX, int sourceY, int sourceW, int sourceH) {
        
        batch.draw(
                tex, x, y, 
                originX, originY,
                width, height, 
                scaleX, scaleY, rotation,
                sourceX, sourceY, 
                sourceW, sourceH,
                false, true
        );
        
        return this;
    }
}