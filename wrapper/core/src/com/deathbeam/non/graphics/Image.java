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
package com.deathbeam.non.graphics;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.io.IOException;

/**
 *
 * @author Thomas Slusny
 */
public class Image extends Texture {
    private Vector2 origin;
    private Vector2 scale;
    private Rectangle source;
    private float rotation;
    
    public Image() {
        super(32, 32, Pixmap.Format.Alpha);
        init();
    }
    
    public Image(FileHandle handle) throws IOException {
        super(handle);
        init();
    }
    
    public Vector2 getOrigin() {
        return origin;
    }
    
    public Image setOrigin(Vector2 origin) {
        this.origin = origin;
        return this;
    }
    
    public Vector2 getScale() {
        return scale;
    }
    
    public Image setScale(Vector2 scale) {
        this.scale = scale;
        return this;
    }
    
    public Rectangle getSource() {
        return source;
    }
    
    public Image setSource(Rectangle source) {
        this.source = source;
        return this;
    }
    
    public float getRotation() {
        return rotation;
    }
    
    public Image setRotation(float rotation) {
        this.rotation = rotation;
        return this;
    }
    
    private void init() {
        origin = Vector2.Zero;
        scale = new Vector2(1, 1);
        source = new Rectangle(0, 0, super.getWidth(), super.getHeight());
        rotation = 0;
    }
}