/*
 * The MIT License
 *
 * Copyright 2014 Thomas Slusny.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.deathbeam.nonfw.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.deathbeam.nonfw.tiled.TiledMap;
import com.deathbeam.nonfw.Utils;
import java.io.IOException;

/**
 *
 * @author Thomas Slusny
 */
public final class Graphics {
    public final SpriteBatch batch;
    private final OrthographicCamera camera;
    private BitmapFont font;
    private float rotation, scale;
    private Vector2 translation;
    
    public Graphics() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true);
        scale = 1;
        rotation = 0;
        translation = Vector2.Zero;
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
        return new ShaderProgram(Utils.getResource(vert), Utils.getResource(frag));
    }
    
    public Image newImage(String file) throws IOException {
        return new Image(Utils.getResource(file));
    }
    
    public BitmapFont newFont(String file) throws IOException {
        return new BitmapFont(Utils.getResource(file));
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
    
    public Graphics rotate(float angle) {
        rotation = angle;
        update();
        return this;
    }
    
    public Graphics scale(float factor) {
        scale = factor;
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
        return this;
    }
    
    public Graphics end() {
        batch.end();
        return this;
    }
    
    public Graphics draw(TiledMap map) {
        end();
        camera.setToOrtho(false);
        camera.translate(0, map.size.y - Gdx.graphics.getHeight());
        update();
        map.draw(this);
        camera.setToOrtho(true);
        update();
        begin();
        return this;
    }
    
    public Graphics draw(CharSequence text) {
        return draw(text, 0, 0);
    }

    public Graphics draw(CharSequence text, int x, int y) {
        return draw(text, x, y, Color.WHITE);
    }
    
    public Graphics draw(CharSequence text, int x, int y, Color color) {
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
        batch.draw(
                image, x, y, 
                image.origin.x, image.origin.y,
                image.size.x, image.size.y, 
                image.scale.x, image.scale.y, image.rotation,
                (int)image.source.x, (int)image.source.y, 
                (int)image.source.width, (int)image.source.height,
                false, true
        );
        return this;
    }
    
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
    
    public Graphics update() {
        camera.rotate(rotation);
        camera.translate(translation);
        camera.zoom = scale;
        camera.update();                             
        batch.setProjectionMatrix(camera.combined);
        return this;
    }
}