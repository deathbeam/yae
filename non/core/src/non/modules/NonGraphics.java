package non.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import non.Non;
import non.BlendMode;
import non.Line;

public class NonGraphics extends Module {
    protected BitmapFont curFont;
    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private OrthographicCamera camera;
    private float rotation, scale, tx, ty;
    
    public Class<?> imageLoader = Texture.class;
    public Class<?> fontLoader = BitmapFont.class;
    
    public SpriteBatch getBatch() { return batch; }
    public OrthographicCamera getCamera() { return camera; }
    public Color getTint() { return batch.getColor(); }
    public BitmapFont getFont() { return curFont; }
    public NonGraphics setFont(BitmapFont fnt) { curFont = fnt; return this; }
    public NonGraphics setShader(ShaderProgram shader) { batch.setShader(shader); return this; }
    
    public TextBounds measureText(String text) {
        TextBounds bounds = curFont.getBounds(text);
        bounds.height = -bounds.height;
        return bounds;
    }
    
    public TextBounds measureText(String text, float wrap) {
        TextBounds bounds = curFont.getWrappedBounds(text, wrap);
        bounds.height = -bounds.height;
        return bounds;
    }
    
    public NonGraphics setBlending(String name) {
        int[] blendMode = BlendMode.getOpenGLBlendMode(name);
        batch.setBlendFunction(blendMode[0], blendMode[1]);
        return this;
    }
    
    public NonGraphics() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        shapes.setAutoShapeType(true);
        curFont = new BitmapFont();
        camera = new OrthographicCamera();
    }
    
    public void dispose() {
        batch.dispose();
        curFont.dispose();
    }
    
    public void updateAfter(float dt) {
        reset().flush();
    }
    
    public void resize(float width, float height) {
        camera.setToOrtho(true, width, height);
        updateMatrices();
    }
    
    public Color color(String name) {
        if (name.startsWith("#")) return Color.valueOf(name.replace("#",""));
        if ("clear".equalsIgnoreCase(name)) return Color.CLEAR;
        else if ("white".equalsIgnoreCase(name)) return Color.WHITE;
        else if ("black".equalsIgnoreCase(name)) return Color.BLACK;
        else if ("red".equalsIgnoreCase(name)) return Color.RED;
        else if ("green".equalsIgnoreCase(name)) return Color.GREEN;
        else if ("blue".equalsIgnoreCase(name)) return Color.BLUE;
        else if ("lightgray".equalsIgnoreCase(name)) return Color.LIGHT_GRAY;
        else if ("gray".equalsIgnoreCase(name)) return Color.GRAY;
        else if ("darkgray".equalsIgnoreCase(name)) return Color.DARK_GRAY;
        else if ("pink".equalsIgnoreCase(name)) return Color.PINK;
        else if ("orange".equalsIgnoreCase(name)) return Color.ORANGE;
        else if ("yellow".equalsIgnoreCase(name)) return Color.YELLOW;
        else if ("magenta".equalsIgnoreCase(name)) return Color.MAGENTA;
        else if ("cyan".equalsIgnoreCase(name)) return Color.CYAN;
        else if ("olive".equalsIgnoreCase(name)) return Color.OLIVE;
        else if ("purple".equalsIgnoreCase(name)) return Color.PURPLE;
        else if ("maroon".equalsIgnoreCase(name)) return Color.MAROON;
        else if ("teal".equalsIgnoreCase(name)) return Color.TEAL;
        else if ("navy".equalsIgnoreCase(name)) return Color.NAVY;
        return Color.CLEAR;
    }
    
    public Color color(float r, float g, float b) {
        return color(r, g, b, 1);
    }
    
    public Color color(float r, float g, float b, float a) {
        return new Color(r, g, b, a);
    }
    
    public ShaderProgram shader(String vert, String frag) {
        return new ShaderProgram(Non.file(vert), Non.file(frag));
    }

    public Texture image(String file) {
        return (Non.assets.isLoaded(file)) ? (Texture)Non.assets.get(file, imageLoader) : new Texture(Non.file(file));
    }

    public BitmapFont font(String file) {
        return (Non.assets.isLoaded(file)) ? (BitmapFont)Non.assets.get(file, fontLoader) : new BitmapFont(Non.file(file));
    }
    
    public Vector2 project(Vector2 pos) {
        return project(pos.x, pos.y);
    }
    
    public Vector2 project(float x, float y) {
        Vector3 temp = camera.project(new Vector3(x, y, 0));
        return new Vector2(temp.x, temp.y);
    }
    
    public Vector2 unproject(Vector2 pos) {
        return unproject(pos.x, pos.y);
    }
    
    public Vector2 unproject(float x, float y) {
        Vector3 temp = camera.unproject(new Vector3(x, y, 0));
        return new Vector2(temp.x, temp.y);
    }
    
    public NonGraphics clear(float r, float g, float b) {
        return clear(color(r,g,b));
    }
    
    public NonGraphics clear(float r, float g, float b, float a) {
        return clear(color(r,g,b,a));
    }
    
    public NonGraphics clear(String color) {
        return clear(color(color));
    }
    
    public NonGraphics clear(Color color) {
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        return this;
    }
    
    public NonGraphics tint(float r, float g, float b) {
        return tint(color(r,g,b));
    }
    
    public NonGraphics tint(float r, float g, float b, float a) {
        return tint(color(r,g,b,a));
    }
    
    public NonGraphics tint(String color) {
        return tint(color(color));
    }
    
    public NonGraphics tint(Color color) {
        shapes.setColor(color);
        batch.setColor(color);
        curFont.setColor(color);
        return this;
    }
    
    public NonGraphics rotate(float degrees) {
        return rotate(degrees, 0, 0, 1);
    }
    
    public NonGraphics rotate(float degrees, float x, float y, float z) {
        if (rotation != degrees) {
            rotation = degrees;
            camera.rotate(rotation, x, y, z);
            camera.update();
            updateMatrices();
        }
        
        return this;
    }

    public NonGraphics scale(float factor) {
        factor = 1/factor;
        if (scale != factor) {
            scale = factor;
            camera.zoom = scale;
            camera.update();
            updateMatrices();
        }
        
        return this;
    }
    
    public NonGraphics translate(Vector2 pos) {
        return translate(pos.x, pos.y);
    }
    
    public NonGraphics translate(float x, float y) {
        if ((tx != x) && (ty != y)) {
            tx = x;
            ty = y;
            camera.translate(-tx, -ty);
            camera.update();
            updateMatrices();
        }

        return this;
    }
    
    public NonGraphics reset() {
        return resetColor().resetTransform();
    }
    
    public NonGraphics flush() {
        if (shapes.isDrawing()) shapes.end();
        if (batch.isDrawing()) batch.end();
        return this;
    }
    
    public NonGraphics resetColor() {
        shapes.setColor(Color.WHITE);
        batch.setColor(Color.WHITE);
        curFont.setColor(Color.WHITE);
        return this;
    }
	
    public NonGraphics resetTransform() {
        scale = 1;
        tx = 0;
        ty = 0;
        rotation = 0;
        camera.zoom = scale;
        camera.setToOrtho(true);
        return this;
    }

    public NonGraphics print(String text, float[] position, float[] scale, float wrap, String align) {
        checkBatch();
        
        curFont.setScale(scale[0], -scale[1]);
        if ("left".equalsIgnoreCase(align)) 
            curFont.drawWrapped(batch, text, position[0], position[1], wrap, BitmapFont.HAlignment.LEFT);
        else if ("right".equalsIgnoreCase(align)) 
            curFont.drawWrapped(batch, text, position[0], position[1], wrap, BitmapFont.HAlignment.RIGHT);
        else if ("center".equalsIgnoreCase(align)) 
            curFont.drawWrapped(batch, text, position[0], position[1], wrap, BitmapFont.HAlignment.CENTER);
        
        return this;
    }
	
    public NonGraphics fill(Object shape, String mode) {
        checkShapes();
		
        if (mode.equalsIgnoreCase("line")) {
            shapes.set(ShapeRenderer.ShapeType.Line);
        } else if (mode.equalsIgnoreCase("fill")) {
            shapes.set(ShapeRenderer.ShapeType.Filled);
        }
		
        if (shape instanceof Rectangle) {
            Rectangle cur = (Rectangle)shape;
            shapes.rect(cur.x, cur.y, cur.width, cur.height);
        } else if (shape instanceof Circle) {
            Circle cur = (Circle)shape;
            shapes.circle(cur.x, cur.y, cur.radius);
        } else if (shape instanceof Ellipse) {
            Ellipse cur = (Ellipse)shape;
            shapes.ellipse(cur.x, cur.y, cur.width, cur.height);
        } else if (shape instanceof Polygon) {
            Polygon cur = (Polygon)shape;
            shapes.polygon(cur.getVertices());
        } else if (shape instanceof Line) {
            Line cur = (Line)shape;
            shapes.rectLine(cur.x1, cur.y1, cur.x2, cur.y2, 1);
        } else if (shape instanceof Vector2) {
            Vector2 cur = (Vector2)shape;
            shapes.point(cur.x, cur.y, 0);
        }
		
        return this;
    }
    
    public NonGraphics draw(Texture image, float[] position, float[] size, float[] origin, float[] scale, float[] source, float rotation) {
        checkBatch();
        batch.draw(
                image, position[0], position[1], 
                origin[0], origin[1],
                size[0], size[1], 
                scale[0], scale[1], rotation,
                (int)source[0], (int)source[1], 
                (int)source[2], (int)source[3],
                false, true
        );
        
        return this;
    }
	
    private void checkBatch() {
        if (shapes.isDrawing()) shapes.end();
        if (!batch.isDrawing()) batch.begin();
    }
	
    private void checkShapes() {
        if (batch.isDrawing()) batch.end();
        if (!shapes.isDrawing()) shapes.begin();
    }
    
    private void updateMatrices() {
        shapes.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
    }
}