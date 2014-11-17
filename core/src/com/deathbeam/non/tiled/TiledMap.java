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
package com.deathbeam.non.tiled;

import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.deathbeam.non.Utils;
import com.deathbeam.non.graphics.Graphics;

import java.io.IOException;
/**
 *
 * @author Thomas Slusny
 */
public class TiledMap {
    public final com.badlogic.gdx.maps.tiled.TiledMap map;
    private TiledMapRenderer renderer;
    public int tileSize;
    public String orientation;
    public final Vector2 size;
    
    public TiledMap(String file) throws IOException {
        map = new TmxMapLoader().load(Utils.DIR + "/" + file);
        tileSize = (Integer)map.getProperties().get("tilewidth");
        orientation = (String)map.getProperties().get("orientation");
        size = new Vector2((Integer)map.getProperties().get("width") * tileSize, (Integer)map.getProperties().get("height") * tileSize);
    }

    public void draw(Graphics gfx) {
        if (renderer == null) {
            if("orthogonal".equals(orientation)) renderer = new OrthogonalTiledMapRenderer(map, 1, gfx.batch);
            if("isometric".equals(orientation)) renderer = new IsometricTiledMapRenderer(map, 1, gfx.batch);
        }
        renderer.setView(gfx.getProjection(), 0, 0, size.x, size.y);
        renderer.render();
    }
}