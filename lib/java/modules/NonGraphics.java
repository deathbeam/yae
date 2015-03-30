package non.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import non.BlendMode;

public class NonGraphics extends Module {
    public SpriteBatch batch;
    public ShapeRenderer shapes;
    public OrthographicCamera camera;
    
    private FreeTypeFontGenerator fontGenerator;
    private BitmapFont curFont;
    private ShaderProgram curShader;
    private float rotation, scale, tx, ty;
    private String blendMode;
    private Color backgroundColor, curColor;
    
    public Texture image(FileHandle file) {
        return new Texture(file);
    }
    
    public BitmapFont font(FileHandle file, int size) {
        fontGenerator = new FreeTypeFontGenerator(file);
        return fontGenerator.generateFont(size);
    }
    
    public ShaderProgram shader(FileHandle vertFile, FileHandle fragFile) {
        return new ShaderProgram(vertFile, fragFile);
    }
    
    public void setFont(BitmapFont font) {
        curFont = font;
    }
    
    public BitmapFont getFont() {
        return curFont;
    }
    
    public void setShader(ShaderProgram shader) {
        curShader = shader;
        batch.setShader(shader);
    }
    
    public ShaderProgram getShader() {
        return curShader;
    }
    
    public float getWidth() {
        return camera.viewportWidth;
    }
    
    public float getHeight() {
        return camera.viewportHeight;
    }
    
    public void setSize(float width, float height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        updateMatrices();
    }
    
    public void setBlending(String name) {
        int[] mode = BlendMode.getOpenGLBlendMode(name);
        batch.setBlendFunction(mode[0], mode[1]);
        blendMode = name;
    }
    
    public String getBlending() {
        return blendMode;
    }
    
    public void setColor(float r, float g, float b, float a) {
        curColor.r = r;
        curColor.g = g;
        curColor.b = b;
        curColor.a = a;
    
        batch.setColor(curColor);
        shapes.setColor(curColor);
        curFont.setColor(curColor);
    }
    
    public Color getColor() {
        return curColor;
    }
    
    public void setBackgroundColor(float r, float g, float b, float a) {
        backgroundColor.r = r;
        backgroundColor.g = g;
        backgroundColor.b = b;
        backgroundColor.a = a;
    }
    
    public Color getBackgroundColor() {
        return backgroundColor;
    }
    
    public NonGraphics() {
        curFont = new BitmapFont();
        curShader = SpriteBatch.createDefaultShader();
        batch = new SpriteBatch(1000, curShader);
        shapes = new ShapeRenderer();
        shapes.setAutoShapeType(true);
        camera = new OrthographicCamera();
        camera.setToOrtho(true);
        curColor = new Color(1, 1, 1, 1);
        backgroundColor = new Color(0, 0, 0, 1);
        updateMatrices();
    }
    
    public void dispose() {
        batch.dispose();
        shapes.dispose();
        curFont.dispose();
        curShader.dispose();
        if (fontGenerator != null) fontGenerator.dispose();
    }
    
    public void update(float dt) {
        scale = 1;
        tx = 0;
        ty = 0;
        rotation = 0;
        
        camera.setToOrtho(true);
        camera.update();
        updateMatrices();
        
        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        curColor.r = 1;
        curColor.g = 1;
        curColor.b = 1;
        curColor.a = 1;
        
        shapes.setColor(curColor);
        batch.setColor(curColor);
        curFont.setColor(curColor);
    }
    
    public void updateAfter(float dt) {
        flush();
    }
    
    public void rotate(float degrees, boolean doUpdate) {
        if (rotation != degrees) {
            rotation = degrees;
            camera.rotate(rotation);
            camera.update();
            if (doUpdate) updateMatrices();
        }
    }

    public void scale(float factor, boolean doUpdate) {
        factor = 1/factor;
        if (scale != factor) {
            scale = factor;
            camera.zoom = scale;
            camera.update();
            if (doUpdate) updateMatrices();
        }
    }
    
    public void translate(float x, float y, boolean doUpdate) {
        if ((tx != x) || (ty != y)) {
            tx = x;
            ty = y;
            camera.translate(-tx, -ty);
            camera.update();
            if (doUpdate) updateMatrices();
        }
    }
    
    public void flush() {
        if (shapes.isDrawing()) shapes.end();
        if (batch.isDrawing()) batch.end();
    }
    
    public void draw(Texture image, float x, float y, float width, float height, float[] scale, float[] origin, float[] source, float rotation) {
        checkBatch();
        batch.draw(
                image, x, y, 
                origin[0], origin[1],
                width, height, 
                scale[0], scale[1], rotation,
                (int)source[0], (int)source[1], 
                (int)source[2], (int)source[3],
                false, true
        );
    }

    public void print(String text, float x, float y, float[] scale, float wrap, String align) {
        checkBatch();
        
        curFont.setScale(scale[0], -scale[1]);
        if ("left".equalsIgnoreCase(align)) 
            curFont.drawWrapped(batch, text, x, y, wrap, BitmapFont.HAlignment.LEFT);
        else if ("right".equalsIgnoreCase(align)) 
            curFont.drawWrapped(batch, text, x, y, wrap, BitmapFont.HAlignment.RIGHT);
        else if ("center".equalsIgnoreCase(align)) 
            curFont.drawWrapped(batch, text, x, y, wrap, BitmapFont.HAlignment.CENTER);
    }
    
    public void rectangle(float x, float y, float width, float height, String mode) {
        checkShapes();
        
        if (mode.equalsIgnoreCase("line")) {
            shapes.set(ShapeRenderer.ShapeType.Line);
        } else if (mode.equalsIgnoreCase("fill")) {
            shapes.set(ShapeRenderer.ShapeType.Filled);
        }
        
        shapes.rect(x, y, width, height);
    }
    
    public void circle(float x, float y, float radius, String mode) {
        checkShapes();
        
        if (mode.equalsIgnoreCase("line")) {
            shapes.set(ShapeRenderer.ShapeType.Line);
        } else if (mode.equalsIgnoreCase("fill")) {
            shapes.set(ShapeRenderer.ShapeType.Filled);
        }
        
        shapes.circle(x, y, radius);
    }
    
    public void ellipse(float x, float y, float width, float height, String mode) {
        checkShapes();
        
        if (mode.equalsIgnoreCase("line")) {
            shapes.set(ShapeRenderer.ShapeType.Line);
        } else if (mode.equalsIgnoreCase("fill")) {
            shapes.set(ShapeRenderer.ShapeType.Filled);
        }
        
        shapes.ellipse(x, y, width, height);
    }
    
    public void polygon(float[] vertices, String mode) {
        checkShapes();
        
        if (mode.equalsIgnoreCase("line")) {
            shapes.set(ShapeRenderer.ShapeType.Line);
        } else if (mode.equalsIgnoreCase("fill")) {
            shapes.set(ShapeRenderer.ShapeType.Filled);
        }
        
        shapes.polygon(vertices);
    }
    
    public void line(float x1, float y1, float x2, float y2, String mode) {
        checkShapes();
        
        if (mode.equalsIgnoreCase("line")) {
            shapes.set(ShapeRenderer.ShapeType.Line);
        } else if (mode.equalsIgnoreCase("fill")) {
            shapes.set(ShapeRenderer.ShapeType.Filled);
        }
        
        shapes.rectLine(x1, y1, x2, y2, 1);
    }
    
    public void point(float x, float y, String mode) {
        checkShapes();
        
        if (mode.equalsIgnoreCase("line")) {
            shapes.set(ShapeRenderer.ShapeType.Line);
        } else if (mode.equalsIgnoreCase("fill")) {
            shapes.set(ShapeRenderer.ShapeType.Filled);
        }
        
        shapes.point(x, y, 0);
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